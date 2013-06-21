# Copyright (C) 2013 Freescale Semiconductor
# FIXME: add a better description to point to right licensing
DESCRIPTION = "Freescale AAC+ decoder libraries "

PE = "2"
# FIXME: fix the license
LICENSE = "FSL-mm-special-codec"
LICENSE_FLAGS = "license_${PN}-${PV}"

DEPENDS = "libfslcodec"
SECTION = "multimedia"

LIC_FILES_CHKSUM = "file://COPYING;md5=ea4d5c069d7aef0838a110409ea78a01"

inherit fsl-eula-unpack autotools pkgconfig

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"

SRC_URI[md5sum] = "4d050eb6e1a1472c3e8bbafd7c39d894"
SRC_URI[sha256sum] = "9ef38d86526481ead4df165438e983b057754f5d91982799fb9422399ea0f63c"

FLOATING_POINT_SEL ?= ""
FLOATING_POINT_SEL = "${@bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', '--enable-fhw', '', d)}"
EXTRA_OECONF = "${FLOATING_POINT_SEL}"

do_install_append() {
    # LTIB move the files around or gst-fsl-plugin won't find them
    mv $p ${D}${libdir}/imx-mm/audio-codec/*.so* ${D}${libdir}

    # FIXME: Drop examples
#    rm -r ${D}${datadir}/imx-mm
}

# FIXME: All binaries lack GNU_HASH in elf binary but as we don't have
# the source we cannot fix it. Disable the insane check for now.
INSANE_SKIP_${PN} = "ldflags textrel dev-so"

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

FILES_${PN} += "${libdir}/*${SOLIBSDEV} ${libdir}/imx-mm/audio-codec/wrap/*${SOLIBS} \
				${libdir}/imx-mm/audio-codec/wrap/*${SOLIBSDEV} ${datadir}/imx-mm/*"
FILES_${PN}-dev = "${libdir}/pkgconfig/*.pc ${includedir}/imx-mm/*"
FILES_${PN}-dbg += "${libdir}/imx-mm/audio-codec/.debug  ${libdir}/imx-mm/audio-codec/wrap/.debug"

COMPATIBLE_MACHINE = "(mx28|mx5|mx6)"
PACKAGE_ARCH = "${MACHINE_ARCH}"
