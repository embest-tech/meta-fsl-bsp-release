# Copyright (C) 2011-2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-kernel/linux/linux-imx.inc
include linux-fsl-dtb.inc

PR = "${INC_PR}.4"

COMPATIBLE_MACHINE = "(mx6)"

SRC_URI = "git://${FSL_ARM_GIT_SERVER}/linux-2.6-imx.git;protocol=git \
"

SRCREV = "e419bfbf38111e991370b0bbaa978ef217121b95"

LOCALVERSION = "-1.0.0"

# We need to pass it as param since kernel might support more then one
# machine, with different entry points
EXTRA_OEMAKE += "LOADADDR=${UBOOT_ENTRYPOINT}"

do_configure_prepend() {
         echo "copy latest defconfig"
         cp ${S}/arch/arm/configs/imx_v6_v7_defconfig ${S}/.config
         cp ${S}/arch/arm/configs/imx_v6_v7_defconfig ${S}/../defconfig
}

