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


exit_message ()
{
   echo "To return to this build environment later please run:"
   echo "    source setup-environment build"
}

clean_up()
{
    unset ARM_DIR META_FSL_BSP_RELEASE
    exit_message clean_up
}

# ARM_DIR is the directory that script fsl_setup exists
ARM_DIR=`readlink -f ${BASH_SOURCE[0]}`
ARM_DIR=`dirname $ARM_DIR`

META_FSL_BSP_RELEASE="${ARM_DIR}/sources/meta-fsl-bsp-release/imx"
echo "##Freescale Yocto Release layer" >> build/conf/bblayers.conf
echo "BBLAYERS += \"${META_FSL_BSP_RELEASE}\"" >> build/conf/bblayers.conf

# setup external build servers
echo "# Freescale Build servers" >> build/conf/local.conf
echo "FSL_ARM_GIT_SERVER = \"git.freescale.com/imx\"" >> build/conf/local.conf
echo >> build/conf/local.conf

echo "# Freescale Download - optimize for disk space " >> build/conf/local.conf
mkdir /opt/freescale/yocto/imx/download
echo "DL_DIR = \"/opt/freescale/yocto/imx/download\"" >> build/conf/local.conf
echo >> build/conf/local.conf

echo "# Freescale SSTATE_CACHE - optimize for build time and disk space" >> build/conf/local.conf
mkdir /opt/freescale/yocto/imx/sstate-cache
echo "SSTATE_CACHE = \"/opt/freescale/yocto/imx/sstate-cache\"" >> build/conf/local.conf
echo >> build/conf/local.conf



exit_message
cd build
clean_up
