# Copyright (C) 2013 Freescale Semiconductor
# FIXME: add a better description to point to right licensing
DESCRIPTION = "This package provides AC3 decoder library and uses"

# FIXME: fix the license
LICENSE = "FSL-mm-excluded-codec"
LICENSE_FLAGS = "license_${PN}-${PV}"
DEPENDS = "libfslcodec"

SECTION = "multimedia"

LIC_FILES_CHKSUM = "file://COPYING;md5=ea4d5c069d7aef0838a110409ea78a01"

inherit fsl-eula-unpack autotools pkgconfig

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"
SRC_URI[md5sum] = "9d27d2979c79c460b19bdcb10ddc5dbd"
SRC_URI[sha256sum] = "9c686925d2d00c33e380e12df2578fc56640d6a06db2451421b16d65437c690c"

FLOATING_POINT_SEL ?= ""
FLOATING_POINT_SEL = "${@bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', '--enable-fhw', '', d)}"
EXTRA_OECONF = "${FLOATING_POINT_SEL}"

do_install_append() {
    # LTIB move the files around or gst-fsl-plugin won't find them
    mv $p ${D}${libdir}/imx-mm/audio-codec/*.so* ${D}${libdir}

    # FIXME: Drop examples
#    rm -r ${D}${datadir}/imx-mm
}

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

# FIXME: All binaries lack GNU_HASH in elf binary but as we don't have
# the source we cannot fix it. Disable the insane check for now.
INSANE_SKIP_${PN} = "ldflags textrel dev-so"

FILES_${PN} += "${libdir}/*${SOLIBSDEV} ${libdir}/imx-mm/audio-codec/wrap/*${SOLIBS} ${libdir}/imx-mm/audio-codec/wrap/*${SOLIBSDEV}"
FILES_${PN}-dev = "${libdir}/pkgconfig/*.pc ${includedir}/imx-mm/* ${datadir}/imx-mm/*"
FILES_${PN}-dbg += "${libdir}/imx-mm/audio-codec/wrap/.debug"

COMPATIBLE_MACHINE = "(mx28|mx5|mx6)"
PACKAGE_ARCH = "${MACHINE_ARCH}"
