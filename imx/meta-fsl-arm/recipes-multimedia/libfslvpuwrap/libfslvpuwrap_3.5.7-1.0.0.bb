# Copyright (C) 2012 Freescale Semicondutors
# Released under the MIT license (see COPYING.MIT for the terms)
DESCRIPTION = "Freescale Multimedia VPU wrapper"
DEPENDS = "imx-lib"
LICENSE = "GPLv2"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://EULA.txt;md5=ea4d5c069d7aef0838a110409ea78a01"

PE = "2"

inherit autotools pkgconfig

SRC_URI = "${MPU_INTERNAL_MIRROR}/${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "6d42f2bc8592c03ba65fe78ef2679c68"
SRC_URI[sha256sum] = "15f5595090ebf84d745610db7cfa7316aba6041499f479a27d593cdde896b111"

do_install_append() {
	# FIXME: Drop examples
	rm -r ${D}${datadir}/imx-mm
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
