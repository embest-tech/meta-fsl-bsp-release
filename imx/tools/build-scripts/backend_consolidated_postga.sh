#!/bin/sh
#
# FSL Yocto backend build script. This will not hook in internal layer and build only with release layer

# and is run nightly on the build servers running Jenkins
#
# Copyright (C) 2013-2014 Freescale Semiconductor
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 2 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

# set unset variables
if [ -z "$kernel" ]; then
   kernel='3.10.31'
   echo Setting kernel to $kernel
fi

if [ -z "$WORKSPACE" ]; then
   WORKSPACE=$PWD
   echo Setting WORKSPACE to $WORKSPACE
fi

if [ -z "$branch" ]; then
   branch='imx_3.10.31-1.1.0_alpha'
   echo Setting branch to $branch
fi

if $clean; then
   rm -rf  $WORKSPACE/temp_build_dir
fi

if [ ! -d "$WORKSPACE/temp_build_dir" ]; then
  # Create temp_build_dir if it does not exist.
  mkdir $WORKSPACE/temp_build_dir
fi

# Clear out the images directory
rm -rf $WORKSPACE/images_all

# Directory to store the build binaries
mkdir $WORKSPACE/images_all
mkdir $WORKSPACE/images_all/dfb
mkdir $WORKSPACE/images_all/dfb/imx_sdk
mkdir $WORKSPACE/images_all/fb
mkdir $WORKSPACE/images_all/fb/imx_sdk
mkdir $WORKSPACE/images_all/wayland
mkdir $WORKSPACE/images_all/wayland/imx_sdk

# Clear out the build space
# Delete hidden files
rm -rf $WORKSPACE/temp_build_dir/.??*
# Leave the build folders in place, delete everything else. Trap the error when no files exists
if [ -n "$(find $WORKSPACE/temp_build_dir/* -maxdepth 0 -name 'build_*' -prune -o -exec rm -rf '{}' ';')" ]; then
  echo "Cleaned the build space"
else
  echo "No files in build space"
fi
cd $WORKSPACE/temp_build_dir

# Setup the environment based on the board
repo init -u git://sw-git.freescale.net/fsl-arm-yocto-bsp.git -b $branch
repo sync

# copy the machine configuration files to meta-fsl-arm
if [ -e $WORKSPACE/temp_build_dir/sources/meta-fsl-bsp-release/imx/meta-fsl-arm/conf/machine ]; then
   cp -r $WORKSPACE/temp_build_dir/sources/meta-fsl-bsp-release/imx/meta-fsl-arm/conf/machine $WORKSPACE/temp_build_dir/sources/meta-fsl-arm/conf
fi

# Delete the old configuration file and recreate it
rm -rf $WORKSPACE/temp_build_dir/build_dfb/conf
rm -rf $WORKSPACE/temp_build_dir/build_fb/conf
rm -rf $WORKSPACE/temp_build_dir/build_wayland/conf

# setup all the backend builds
echo "setup dfb builds"
EULA=1 MACHINE=imx6qdlsolo . ./fsl-setup-release.sh -b build_dfb -e dfb
echo "INHERIT += \"rm_work\"" >> conf/local.conf

cd $WORKSPACE/temp_build_dir
echo "setup fb builds"
EULA=1 MACHINE=imx6qdlsolo . ./fsl-setup-release.sh -b build_fb -e fb

# FIXME: sidestep a provider issue
echo "PREFERRED_PROVIDER_virtual/mesa = \"\"" >> conf/local.conf
echo "INHERIT += \"rm_work\"" >> conf/local.conf

cd $WORKSPACE/temp_build_dir
echo "setup wayland builds"
EULA=1 MACHINE=imx6qdlsolo . ./fsl-setup-release.sh -b build_wayland -e wayland

echo "INHERIT += \"rm_work\"" >> conf/local.conf
# FIXME: sidestep a provider issue
echo "PREFERRED_PROVIDER_virtual/mesa = \"\"" >> conf/local.conf

#start the dfb builds first
cd $WORKSPACE/temp_build_dir/build_dfb

rm -rf tmp/deploy

# Build the image
bitbake linux-imx -c deploy
bitbake fsl-image-gui

if $sdk; then
  bitbake fsl-image-gui -c populate_sdk
  mv $WORKSPACE/temp_build_dir/build_dfb/tmp/deploy/sdk/* $WORKSPACE/images_all/dfb/imx_sdk
fi

mv $WORKSPACE/temp_build_dir/build_dfb/tmp/deploy/images/* $WORKSPACE/images_all/dfb

cd $WORKSPACE/temp_build_dir/build_fb
bitbake linux-imx -c deploy
bitbake fsl-image-gui

if $qt5; then
    bitbake fsl-image-qt5
fi

if $sdk; then
  bitbake fsl-image-gui -c populate_sdk
  mv $WORKSPACE/temp_build_dir/build_fb/tmp/deploy/sdk/* $WORKSPACE/images_all/fb/imx_sdk
fi
mv $WORKSPACE/temp_build_dir/build_fb/tmp/deploy/images/* $WORKSPACE/images_all/fb


cd $WORKSPACE/temp_build_dir/build_wayland
bitbake linux-imx -c deploy
bitbake fsl-image-gui

if $qt5; then
    bitbake fsl-image-qt5
fi

if $sdk; then
  bitbake fsl-image-gui -c populate_sdk
  mv $WORKSPACE/temp_build_dir/build_wayland/tmp/deploy/sdk/* $WORKSPACE/images_all/wayland/imx_sdk
fi
mv $WORKSPACE/temp_build_dir/build_wayland/tmp/deploy/images/* $WORKSPACE/images_all/wayland

#Now build SX
cd $WORKSPACE/temp_build_dir/build_dfb
MACHINE=imx6sxsabresd      bitbake linux-imx gpu-viv-bin-mx6q gst-fsl-plugin libfslcodec imx-test -c cleansstate
MACHINE=imx6sxsabresd      bitbake linux-imx -c  deploy
MACHINE=imx6sxsabresd      bitbake fsl-image-gui
mv $WORKSPACE/temp_build_dir/build_dfb/tmp/deploy/images/imx6sxsabresd $WORKSPACE/images_all/dfb

cd $WORKSPACE/temp_build_dir/build_fb
MACHINE=imx6sxsabresd      bitbake linux-imx gpu-viv-bin-mx6q gst-fsl-plugin libfslcodec imx-test  -c cleansstate
MACHINE=imx6sxsabresd      bitbake cmake qtbase qtdeclarative qtgraphicaleffects qtsensors qt3d qtmultimedia qtwayland qtlocation qtwebkit qtwebkit-examples -c cleansstate
MACHINE=imx6sxsabresd      bitbake linux-imx -c deploy
MACHINE=imx6sxsabresd      bitbake fsl-image-gui

if $qt5; then
    MACHINE=imx6sxsabresd      bitbake fsl-image-qt5
fi

mv $WORKSPACE/temp_build_dir/build_fb/tmp/deploy/images/imx6sxsabresd $WORKSPACE/images_all/fb

cd $WORKSPACE/temp_build_dir/build_wayland
MACHINE=imx6sxsabresd      bitbake linux-imx gpu-viv-bin-mx6q gst-fsl-plugin libfslcodec imx-test -c cleansstate
MACHINE=imx6sxsabresd      bitbake cmake qtbase qtdeclarative qtgraphicaleffects qtsensors qt3d qtmultimedia qtwayland qtlocation qtwebkit qtwebkit-examples -c cleansstate
MACHINE=imx6sxsabresd      bitbake linux-imx -c deploy
MACHINE=imx6sxsabresd      bitbake fsl-image-gui

if $qt5; then
    MACHINE=imx6sxsabresd      bitbake fsl-image-qt5
fi
mv $WORKSPACE/temp_build_dir/build_wayland/tmp/deploy/images/imx6sxsabresd $WORKSPACE/images_all/wayland

