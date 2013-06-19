DESCRIPTION = "bootloader for imx platforms"
require recipes-bsp/u-boot/u-boot.inc

PROVIDES += "u-boot"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

DEPENDS_mxs += "elftosb-native"

PR = "2"

SRC_URI = "git://${FSL_ARM_GIT_SERVER}/uboot-imx.git;protocol=git;branch=imx_v2013.04_3.5.7_1.0.0_alpha"

SRCREV = "${FSL_ARM_RELEASE_TAG}"
S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"

# Fix uboot-machine for imx6dlsabresd - using mx6dl to avoid conflict with imx6dlsabreauto machine
UBOOT_MACHINE_mx6dl = "mx6dlsabresd_config"

