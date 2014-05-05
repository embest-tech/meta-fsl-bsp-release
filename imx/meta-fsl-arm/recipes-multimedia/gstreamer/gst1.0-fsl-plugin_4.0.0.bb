# Copyright (C) 2014 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require gst1.0-fsl-plugin.inc

EXTRA_OECONF += " CROSS_ROOT=${PKG_CONFIG_SYSROOT_DIR}"

SRC_URI = "${FSL_MIRROR}/gst1.0-fsl-plugins-${PV}.tar.gz"
S = "${WORKDIR}/gst1.0-fsl-plugins-${PV}"

SRC_URI[md5sum] = "a168263fca1f3dd83a8bbff7bb9ab78b"
SRC_URI[sha256sum] = "7d01fd1c3d424a56af857d302f7f84083567d1278f7beaac392e13100a7ab596"

COMPATIBLE_MACHINE = "(mx6)"
