# Copyright (C) 2012 Freescale Semicondutors
# Released under the MIT license (see COPYING.MIT for the terms)
DESCRIPTION = "Freescale Multimedia VPU wrapper"
DEPENDS = "imx-lib"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://EULA.txt;md5=ea4d5c069d7aef0838a110409ea78a01"

PR = "r1.0.38"

inherit fsl-eula-unpack autotools pkgconfig

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"
SRC_URI[md5sum] = "bb7d7500705f9cc433ba04a21995c608"
SRC_URI[sha256sum] = "57004df11e5e2f5e96033d87e85ef8c7791d57cf747db9fb41d49c4d1f38ec15"

do_install_append() {
	# FIXME: Drop examples
	rm -r ${D}${datadir}/imx-mm
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
