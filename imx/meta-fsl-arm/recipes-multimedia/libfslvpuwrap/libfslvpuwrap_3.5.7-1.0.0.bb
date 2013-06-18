# Copyright (C) 2012 Freescale Semicondutors
# Released under the MIT license (see COPYING.MIT for the terms)
DESCRIPTION = "Freescale Multimedia VPU wrapper"
DEPENDS = "imx-lib"
LICENSE = "GPLv2"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://EULA.txt;md5=ea4d5c069d7aef0838a110409ea78a01"

PR = "r1.0.37"

inherit autotools pkgconfig

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "0568d29a7be1868d6782e7608fedb662"
SRC_URI[sha256sum] = "e810453af66f840bb34b49e3ebd63c595d0e0d6692e0abc7b0889ae86bec3a38"

do_install_append() {
	# FIXME: Drop examples
	rm -r ${D}${datadir}/imx-mm
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
