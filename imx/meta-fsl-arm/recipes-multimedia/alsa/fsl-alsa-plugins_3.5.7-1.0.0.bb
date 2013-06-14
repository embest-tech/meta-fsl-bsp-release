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
SRC_URI[md5sum] = "d940ed056233a64d2d747fad4adb28ed"
SRC_URI[sha256sum] = "cab7e3fa5f96be2c6404a50178a4767fad40a8f3d7ff9fd0f4b54ccb0b06265c"

INCLUDE_DIR = "-I${STAGING_KERNEL_DIR}/include"
EXTRA_OECONF = "CFLAGS="${INCLUDE_DIR}""

INSANE_SKIP_${PN} = "dev-so"

FILES_${PN} += "${libdir}/alsa-lib/libasound_module_rate_asrcrate*.so ${libdir}/alsa-lib/*${DEVSOLIBS}"
FILES_${PN}-dbg += "${libdir}/alsa-lib/.debug"
FILES_${PN}-dev += "${libdir}/alsa-lib/*.la"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
