DESCRIPTION = "bootloader for imx platforms"
require recipes-bsp/u-boot/u-boot.inc

PROVIDES += "u-boot"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

DEPENDS_mxs += "elftosb-native"

PR = "v3.5.7_2013.04"

SRC_URI = "git://${FSL_ARM_GIT_SERVER}/uboot-imx.git;protocol=git;branch=imx_v2013.04"

SRCREV = "${FSL_ARM_RELEASE_TAG}"
S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"

## fix uboot machine for mx6 DL and SL since it got changed from 2009.08 release
UBOOT_MACHINE_mx6dl = "mx6dlsabresd_config"
UBOOT_MACHINE_mx6sl = "mx6solosabresd_config"

