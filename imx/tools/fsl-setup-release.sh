#!/bin/sh
#
# FSL Build Enviroment Setup Script
#
# Copyright (C) 2011-2013 Freescale Semiconductor
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

CWD=`pwd`

exit_message ()
{
   echo "To return to this build environment later please run:"
   echo "    source setup-environment <build-dir>"
}

usage()
{
    echo -e "\nUsage: source $PROGNAME
    Optional parameters: [-b build-dir] [-e back-end] [-h]"
echo "
    * [-b build-dir]: Build directory, if unspecified script uses 'build' as output directory
    * [-e back-end]: Options are 'fb', 'dfb', weston'. If unspecified, default to X11
    * [-h]: help

"
}


clean_up()
{

    unset CWD BUILD_DIR BACKEND DIST_FEATURES
    unset fsl_setup_help fsl_setup_error fsl_setup_flag
    unset usage clean_up
    unset ARM_DIR META_FSL_BSP_RELEASE
    exit_message clean_up
}

# get command line options
OLD_OPTIND=$OPTIND
while getopts "k:r:t:b:e:gh" fsl_setup_flag
do
    case $fsl_setup_flag in
        b) BUILD_DIR="$OPTARG";
           ;;
        e)
            BACKEND="$OPTARG"
            if [ "$BACKEND" = "fb" ]; then
                DIST_FEATURES="alsa argp bluetooth ext2 irda largefile pcmcia usbgadget usbhost wifi xattr nfs zeroconf pci 3g \${DISTRO_FEATURES_LIBC}"
            elif [ "$BACKEND" = "dfb" ]; then
                DIST_FEATURES="alsa argp bluetooth ext2 irda largefile pcmcia usbgadget usbhost wifi xattr nfs zeroconf pci 3g directfb \${DISTRO_FEATURES_LIBC}"
            else
                echo -e "\nWRONG BACKEND SPECIFIED"
                fsl_setup_error='true'
            fi
           ;;
        h) fsl_setup_help='true';
           ;;
        ?) fsl_setup_error='true';
           ;;
    esac
done
OPTIND=$OLD_OPTIND

# check the "-h" and other not supported options
if test $fsl_setup_error || test $fsl_setup_help; then
    usage && clean_up && return 1
fi

if [ -z "$BUILD_DIR" ]; then
    BUILD_DIR='build'
fi
if [ ! -e $BUILD_DIR/conf/local.conf ]; then
    echo "Run the 'setup-environment' script before running this script"
    clean_up && return 1
fi

# Run this part only the first time the script is executed
if [ ! -e $BUILD_DIR/conf/local.conf.org ]; then
    cp $BUILD_DIR/conf/local.conf $BUILD_DIR/conf/local.conf.org

    META_FSL_BSP_RELEASE="${CWD}/sources/meta-fsl-bsp-release/imx/meta-fsl-arm"
    echo "##Freescale Yocto Release layer" >> $BUILD_DIR/conf/bblayers.conf
    echo "BBLAYERS += \"${META_FSL_BSP_RELEASE}\"" >> $BUILD_DIR/conf/bblayers.conf

    META_FSL_BSP_RELEASE="${CWD}/sources/meta-fsl-bsp-release/imx/meta-fsl-demos"
    echo "BBLAYERS += \"${META_FSL_BSP_RELEASE}\"" >> $BUILD_DIR/conf/bblayers.conf

    echo >> $BUILD_DIR/conf/local.conf

    # setup external build servers used in uboot and kernel recipes
    echo "# Freescale Build servers" >> $BUILD_DIR/conf/local.conf
    echo "FSL_ARM_GIT_SERVER = \"git.freescale.com/imx\"" >> $BUILD_DIR/conf/local.conf
    echo >> $BUILD_DIR/conf/local.conf

    if [ -e "$BACKEND" ]; then
        echo "DISTRO_FEATURES = \"$DIST_FEATURES\"" >> $BUILD_DIR/conf/local.conf
        echo >> $BUILD_DIR/conf/local.conf
    fi
fi


cd  $BUILD_DIR
clean_up
