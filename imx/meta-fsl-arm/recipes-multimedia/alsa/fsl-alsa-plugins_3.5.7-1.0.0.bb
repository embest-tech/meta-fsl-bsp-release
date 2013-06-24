# Copyright (C) 2013 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale alsa-lib plugins"
LICENSE = "GPLv2"
SECTION = "multimedia"
DEPENDS = "alsa-lib virtual/kernel"

LIC_FILES_CHKSUM = "file://COPYING.GPL;md5=94d55d512a9ba36caa9b7df079bae19f"

PACKAGE_NAME = "fsl-alsa-plugins"

inherit fsl-eula-unpack autotools pkgconfig

PLATFORM_mx6 = "MX6"

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"
SRC_URI[sha256sum] = "67ebf1c41c5fc0e03f861e77771066077d0ccd5d4ee64fa7b9273b4074695bf0"
SRC_URI[md5sum] = "609a0e12269636531ace0a454aef62d2"

INCLUDE_DIR = "-I${STAGING_KERNEL_DIR}/include"
EXTRA_OECONF = "CFLAGS="${INCLUDE_DIR}""

INSANE_SKIP_${PN} = "dev-so"

FILES_${PN} += "${libdir}/alsa-lib/libasound_module_rate_asrcrate*.so ${libdir}/alsa-lib/*${DEVSOLIBS}"
FILES_${PN}-dbg += "${libdir}/alsa-lib/.debug"
FILES_${PN}-dev += "${libdir}/alsa-lib/*.la"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
