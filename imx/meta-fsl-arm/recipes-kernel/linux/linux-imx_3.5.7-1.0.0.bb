# Copyright (C) 2011-2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-kernel/linux/linux-imx.inc
include linux-fsl-dtb.inc

PR = "${INC_PR}.1"

COMPATIBLE_MACHINE = "(mx6)"

SRC_URI = "git://${FSL_ARM_GIT_SERVER}/linux-2.6-imx.git;protocol=git \
           file://0001-ENGR00210559-1-Integrate-gpu-openGL2.1-and-DRM.patch \
           file://defconfig-add-drm.patch"

SRCREV = "461120b09550c6c1ce03ac29c70f52eccf21411c"

LOCALVERSION = "-1.0.0"

# We need to pass it as param since kernel might support more then one
# machine, with different entry points
EXTRA_OEMAKE += "LOADADDR=${UBOOT_ENTRYPOINT}"

do_configure_prepend() {
         echo "copy latest defconfig"
         cp ${S}/arch/arm/configs/imx_v6_v7_defconfig ${S}/.config
         cp ${S}/arch/arm/configs/imx_v6_v7_defconfig ${S}/../defconfig
}

