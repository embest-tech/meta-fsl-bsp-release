#!/bin/sh
#
# FSL Yocto nightly build script to build combined. This will not do a complete clean build 
# and is run nightly on the build servers running Jenkins
#
# Copyright (C) 2013,2014 Freescale Semiconductor
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

if [ -z "$image" ]; then
   image='fsl-image-gui'
   echo Setting image to $image
fi

if [ -z "$WORKSPACE" ]; then
   WORKSPACE=$PWD
   echo Setting WORKSPACE to $WORKSPACE
fi

if [ -z "$branch" ]; then
   branch='imx_3.10.31-1.1.0_alpha'
   echo Setting branch to $branch
fi

if [ ! -d "$WORKSPACE/temp_build_dir" ]; then
  # Create temp_build_dir if it does not exist.
  mkdir $WORKSPACE/temp_build_dir
fi

  # Clear out the images directory
  rm -rf $WORKSPACE/images_all

  # Directory to store the build binaries
  mkdir $WORKSPACE/images_all
  mkdir $WORKSPACE/images_all/imx_uboot
  mkdir $WORKSPACE/images_all/imx_sdk

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

  repo init -u git://sw-git.freescale.net/fsl-arm-yocto-bsp.git -b $branch
  repo sync

  # copy the machine configuration files to meta-fsl-arm
  if [ -e $WORKSPACE/temp_build_dir/sources/meta-fsl-bsp-release/imx/meta-fsl-arm/conf/machine ]; then
      cp -r $WORKSPACE/temp_build_dir/sources/meta-fsl-bsp-release/imx/meta-fsl-arm/conf/machine $WORKSPACE/temp_build_dir/sources/meta-fsl-arm/conf
  fi

  # Delete the old configuration file and recreate it
  rm -rf $WORKSPACE/temp_build_dir/build_all/conf

  EULA=1 MACHINE=imx6qdlsolo . ./fsl-setup-release.sh -b build_all -e x11
  echo "INHERIT += \"rm_work\"" >> conf/local.conf

  echo "UBOOT_CONFIG = \"sd\"" >> conf/local.conf
  echo "FSL_KERNEL_DEFCONFIG = \"imx_v7_defconfig\"" >> conf/local.conf

 bitbake -c cleanall linux-imx linux-mfgtool cryptodev cryptodev-headers openssl openssl-native \
imx-lib firmware-imx  u-boot-imx  u-boot-imx-mfgtool imx-kobs imx-uuc udev-extraconf   imx-test packagegroup-base \
xserver-xorg gpu-viv-bin-mx6q xf86-video-imxfb-vivante mesa \
libfslvpuwrap fsl-alsa-plugins gst-plugins-gl gst-fsl-plugin gst1.0-fsl-plugin libfslparser libfslcodec packagegroup-fsl-gstreamer

  # save zImage before it gets wiped out during imx6slevk build
  bitbake -c deploy linux-imx -f
  bitbake -c deploy u-boot-imx -f
  bitbake -c deploy u-boot-imx-mfgtool
  bitbake -c deploy linux-mfgtool

  # build imx6qdlsolo full image first
  bitbake fsl-image-gui
  bitbake fsl-image-qt5

  if $sdk; then
      bitbake fsl-image-gui -c populate_sdk
      mv $WORKSPACE/temp_build_dir/build_all/tmp/deploy/sdk/* $WORKSPACE/images_all/imx_sdk
  fi

  bitbake fsl-image-mfgtool-initramfs

  mv $WORKSPACE/temp_build_dir/build_all/tmp/deploy/images/imx6qdlsolo $WORKSPACE/images_all/

  cd $WORKSPACE/temp_build_dir/build_all

  # add ixm6qsabresd even though it is part of imx6qdlsolo

  # build sd uboot for all core machines

  # SD uboot builds
  MACHINE=imx6qsabresd              bitbake u-boot-imx -c cleansstate
  MACHINE=imx6qsabresd              bitbake u-boot-imx -c deploy -f
  board='imx6qsabresd'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6qsabresd_sd.imx
  MACHINE=imx6qsabreauto            bitbake u-boot-imx -c cleansstate
  MACHINE=imx6qsabreauto            bitbake u-boot-imx -c deploy -f
  board='imx6qsabreauto'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6qsabreauto_sd.imx
  MACHINE=imx6dlsabresd             bitbake u-boot-imx -c cleansstate
  MACHINE=imx6dlsabresd             bitbake u-boot-imx -c deploy -f
  board='imx6dlsabresd'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6dlsabresd_sd.imx
  MACHINE=imx6dlsabreauto           bitbake u-boot-imx -c cleansstate
  MACHINE=imx6dlsabreauto           bitbake u-boot-imx -c deploy -f
  board='imx6dlsabreauto'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6dlsabreauto_sd.imx
  MACHINE=imx6solosabresd           bitbake u-boot-imx -c cleansstate
  MACHINE=imx6solosabresd           bitbake u-boot-imx -c deploy -f
  board='imx6solosabresd'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6solosabresd_sd.imx
  MACHINE=imx6solosabreauto         bitbake u-boot-imx -c cleansstate
  MACHINE=imx6solosabreauto         bitbake u-boot-imx -c deploy -f
  board='imx6solosabreauto'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6solosabreauto_sd.imx
  MACHINE=imx6slevk        bitbake u-boot-imx -c cleansstate
  MACHINE=imx6slevk        bitbake u-boot-imx -c deploy -f
  board='imx6slevk'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6slevk_sd.imx
  MACHINE=imx6sxsabresd    bitbake u-boot-imx -c cleansstate
  MACHINE=imx6sxsabresd    bitbake u-boot-imx -c deploy -f
  board='imx6sxsabresd'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6sxsabresd_sd.imx
  MACHINE=imx6sx17x17arm2    bitbake u-boot-imx -c cleansstate
  MACHINE=imx6sx17x17arm2    bitbake u-boot-imx -c deploy -f
  board='imx6sx17x17arm2'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6sx17x17arm2_sd.imx
  MACHINE=imx6sx19x19ddr3arm2    bitbake u-boot-imx -c cleansstate
  MACHINE=imx6sx19x19ddr3arm2    bitbake u-boot-imx -c deploy -f
  board='imx6sx19x19ddr3arm2'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6sx19x19ddr3arm2_sd.imx
  MACHINE=imx6sx19x19lpddr2arm2    bitbake u-boot-imx -c cleansstate
  MACHINE=imx6sx19x19lpddr2arm2    bitbake u-boot-imx -c deploy -f
  board='imx6sx19x19lpddr2arm2'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6sx19x19lpddr2arm2_sd.imx


  echo "UBOOT_CONFIG = \"qspi2\"" >> conf/local.conf
  MACHINE=imx6sxsabresd    bitbake u-boot-imx -c cleansstate
  MACHINE=imx6sxsabresd    bitbake u-boot-imx -c deploy -f
  board='imx6sxsabresd'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6sxsabresd_qspi2.imx
  MACHINE=imx6sx17x17arm2    bitbake u-boot-imx -c cleansstate
  MACHINE=imx6sx17x17arm2    bitbake u-boot-imx -c deploy -f
  board='imx6sx17x17arm2'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6sx17x17arm2_qspi2.imx
  MACHINE=imx6sx19x19ddr3arm2    bitbake u-boot-imx -c cleansstate
  MACHINE=imx6sx19x19ddr3arm2    bitbake u-boot-imx -c deploy -f
  board='imx6sx19x19ddr3arm2'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6sx19x19ddr3arm2_qspi2.imx
  MACHINE=imx6sx19x19lpddr2arm2    bitbake u-boot-imx -c cleansstate
  MACHINE=imx6sx19x19lpddr2arm2    bitbake u-boot-imx -c deploy -f
  board='imx6sx19x19lpddr2arm2'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6sx19x19lpddr2arm2_qspi2.imx


  echo "UBOOT_CONFIG = \"sata\"" >> conf/local.conf
  MACHINE=imx6qsabresd              bitbake u-boot-imx -c cleansstate
  MACHINE=imx6qsabresd              bitbake u-boot-imx -c deploy
  board='imx6qsabresd'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6qsabresd_sata.imx
  MACHINE=imx6qsabreauto            bitbake u-boot-imx -c cleansstate
  MACHINE=imx6qsabreauto            bitbake u-boot-imx -c deploy
  board='imx6qsabreauto'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6qsabreauto_sata.imx


  echo "UBOOT_CONFIG = \"eimnor\"" >> conf/local.conf
  MACHINE=imx6qsabreauto    bitbake u-boot-imx -c cleansstate
  MACHINE=imx6qsabreauto    bitbake u-boot-imx -c deploy
  board='imx6qsabreauto'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6qsabreauto_eim-nor.imx
  MACHINE=imx6dlsabreauto   bitbake u-boot-imx -c cleansstate
  MACHINE=imx6dlsabreauto   bitbake u-boot-imx -c deploy
  board='imx6dlsabreauto'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6dlsabreauto_eim-nor.imx
  MACHINE=imx6solosabreauto bitbake u-boot-imx -c cleansstate
  MACHINE=imx6solosabreauto bitbake u-boot-imx -c deploy
  board='imx6solosabreauto'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6solosabreauto_eim-nor.imx
  MACHINE=imx6sx19x19ddr3arm2 bitbake u-boot-imx -c cleansstate
  MACHINE=imx6sx19x19ddr3arm2 bitbake u-boot-imx -c deploy
  board='imx6sx19x19ddr3arm2'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6sx19x19ddr3arm2_eim-nor.imx


  echo "UBOOT_CONFIG = \"spinor\"" >> conf/local.conf
  MACHINE=imx6qsabreauto       bitbake u-boot-imx -c cleansstate
  MACHINE=imx6qsabreauto       bitbake u-boot-imx -c deploy
  board='imx6qsabreauto'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6qsabreauto_spi-nor.imx
  MACHINE=imx6dlsabreauto      bitbake u-boot-imx -c cleansstate
  MACHINE=imx6dlsabreauto      bitbake u-boot-imx -c deploy
  board='imx6dlsabreauto'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6dlsabreauto_spi-nor.imx
  MACHINE=imx6solosabreauto    bitbake u-boot-imx -c cleansstate
  MACHINE=imx6solosabreauto    bitbake u-boot-imx -c deploy
  board='imx6solosabreauto'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6solosabreauto_spi-nor.imx
  MACHINE=imx6sx17x17arm2    bitbake u-boot-imx -c cleansstate
  MACHINE=imx6sx17x17arm2    bitbake u-boot-imx -c deploy
  board='imx6sx17x17arm2'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6sx17x17arm2_spi-nor.imx
  MACHINE=imx6sx19x19ddr3arm2    bitbake u-boot-imx -c cleansstate
  MACHINE=imx6sx19x19ddr3arm2    bitbake u-boot-imx -c deploy
  board='imx6sx19x19ddr3arm2'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6sx19x19ddr3arm2_spi-nor.imx
  MACHINE=imx6slevk    bitbake u-boot-imx -c cleansstate
  MACHINE=imx6slevk    bitbake u-boot-imx -c deploy
  board='imx6slevk'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6slevk_spi-nor.imx

  echo "UBOOT_CONFIG = \"nand\"" >> conf/local.conf
  MACHINE=imx6qsabreauto       bitbake u-boot-imx -c cleansstate
  MACHINE=imx6qsabreauto       bitbake u-boot-imx -c deploy
  board='imx6qsabreauto'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6qsabreauto_nand.imx
  MACHINE=imx6dlsabreauto      bitbake u-boot-imx -c cleansstate
  MACHINE=imx6dlsabreauto      bitbake u-boot-imx -c deploy
  board='imx6dlsabreauto'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6dlsabreauto_nand.imx
  MACHINE=imx6solosabreauto    bitbake u-boot-imx -c cleansstate
  MACHINE=imx6solosabreauto    bitbake u-boot-imx -c deploy
  board='imx6solosabreauto'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6solosabreauto_nand.imx
  MACHINE=imx6sx17x17arm2    bitbake u-boot-imx -c cleansstate
  MACHINE=imx6sx17x17arm2    bitbake u-boot-imx -c deploy
  board='imx6sx17x17arm2'
  cp tmp/deploy/images/$board/u-boot-$board.imx $WORKSPACE/images_all/imx_uboot/u-boot-imx6sx17x17arm2_nand.imx

  # build different kernel for sx/slevk - so many differences
  echo "FSL_KERNEL_DEFCONFIG = \"imx_v7_defconfig\"" >> conf/local.conf
  echo "UBOOT_CONFIG = \"sd\"" >> conf/local.conf

  MACHINE=imx6sxsabresd bitbake -c cleansstate linux-imx linux-mfgtool cryptodev cryptodev-headers openssl openssl-native \
imx-lib firmware-imx  u-boot-imx  u-boot-imx-mfgtool imx-kobs imx-uuc udev-extraconf   imx-test packagegroup-base \
xserver-xorg gpu-viv-bin-mx6q xf86-video-imxfb-vivante mesa \
libfslvpuwrap fsl-alsa-plugins gst-plugins-gl gst-fsl-plugin gst1.0-fsl-plugin libfslparser libfslcodec packagegroup-fsl-gstreamer

  MACHINE=imx6sxsabresd          bitbake linux-imx -f -c deploy

  MACHINE=imx6sxsabresd          bitbake fsl-image-gui
  MACHINE=imx6sxsabresd          bitbake fsl-image-qt5

  MACHINE=imx6sxsabresd          bitbake u-boot-imx-mfgtool
  MACHINE=imx6sxsabresd          bitbake linux-mfgtool
  MACHINE=imx6sxsabresd          bitbake fsl-image-mfgtool-initramfs

  cd $WORKSPACE/temp_build_dir/build_all/tmp/deploy/images
  mv $WORKSPACE/temp_build_dir/build_all/tmp/deploy/images/imx6sxsabresd $WORKSPACE/images_all/
  cd $WORKSPACE/temp_build_dir/build_all/tmp/deploy/images

  # Copy the output binaries
  cd $WORKSPACE
  mv $WORKSPACE/temp_build_dir/build_all/tmp/deploy/images/imx6slevk $WORKSPACE/images_all/

  # remove these to avoid confusion
  rm $WORKSPACE/images_all/imx6qdlsolo/uImage
  rm $WORKSPACE/images_all/imx6qdlsolo/u-boot*
  rm $WORKSPACE/images_all/imx6sxsabresd/uImage
  rm $WORKSPACE/images_all/imx6sxsabresd/u-boot*
