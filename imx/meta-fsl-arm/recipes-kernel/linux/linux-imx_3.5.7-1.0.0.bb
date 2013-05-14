# Copyright (C) 2011-2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

PR = "${INC_PR}.1"

COMPATIBLE_MACHINE = "(mx6)"

# Internal 3.5.7 branch
SRC_URI = "git://${FSL_ARM_GIT_SERVER}/linux-2.6-imx.git;protocol=git;branch=imx_3.5.7 \
           file://perfutils-yocto-build-break-fix.patch"

SRCREV = "${FSL_ARM_RELEASE_TAG}"

LOCALVERSION = "-3.5.7+yocto"

# We need to pass it as param since kernel might support more then one
# machine, with different entry points
EXTRA_OEMAKE += "LOADADDR=${UBOOT_ENTRYPOINT}"

do_configure_prepend() {
         echo " Internal Kernel build - copy latest defconfig"
         cp ${S}/arch/arm/configs/imx_v6_v7_defconfig ${S}/.config
         cp ${S}/arch/arm/configs/imx_v6_v7_defconfig ${S}/../defconfig
}


