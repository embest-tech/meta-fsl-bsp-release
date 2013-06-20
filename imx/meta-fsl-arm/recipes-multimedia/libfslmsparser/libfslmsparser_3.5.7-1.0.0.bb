# Copyright (C) 2013 Freescale Semiconductor
# FIXME: add a better description to point to right licensing
DESCRIPTION = "Microsoft compnent library, including WMA and WMV789 decoder libraries"

# FIXME: fix the license
LICENSE = "FSL-mm-special-codec"
LICENSE_FLAGS = "license_${PN}-${PV}"
#LICENSE = "Proprietary"

DEPENDS = "libfslparser"
SECTION = "multimedia"

LIC_FILES_CHKSUM = "file://COPYING;md5=ea4d5c069d7aef0838a110409ea78a01"

inherit fsl-eula-unpack autotools pkgconfig

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"
SRC_URI[md5sum] = "b0f86fbc63e7431df72d41e6f5466127"
SRC_URI[sha256sum] = "3afa4251b98a76bf1d4810a582910d00ddbdb4863bb26900ba1e948b93be9004"

# FIXME: All binaries lack GNU_HASH in elf binary but as we don't have
# the source we cannot fix it. Disable the insane check for now.
INSANE_SKIP_${PN} = "ldflags textrel"
INSANE_SKIP_${PN}-dev = "ldflags"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

# FIXME: gst-fsl-plugin looks for the .so files so we need to deploy those
FILES_${PN} += "${libdir}/imx-mm/*/*${SOLIBS}"
FILES_${PN}-dev += " ${libdir}/imx-mm/*/*${SOLIBSDEV}"

COMPATIBLE_MACHINE = "(mx28|mx5|mx6)"
PACKAGE_ARCH = "${MACHINE_ARCH}"
