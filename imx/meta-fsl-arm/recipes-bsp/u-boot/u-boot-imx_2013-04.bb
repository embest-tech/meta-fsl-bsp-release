DESCRIPTION = "bootloader for imx platforms"
require recipes-bsp/u-boot/u-boot.inc

PROVIDES += "u-boot"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

DEPENDS_mxs += "elftosb-native"

PR = "4"

# branch=imx_v2013.04_3.5.7_1.0.0_alpha
SRC_URI = "git://${FSL_ARM_GIT_SERVER}/uboot-imx.git;protocol=git"
SRCREV = "8bb54e945754e003a4ffe9092e79d3e0e203fb49"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"

# Fix uboot-machine for imx6dlsabresd - using mx6dl to avoid conflict with imx6dlsabreauto machine
UBOOT_MACHINE_mx6dl = "mx6dlsabresd_config"

