# Copyright (C) 2013 Freescale Semiconductor
# FIXME: add a better description to point to right licensing
DESCRIPTION = "Microsoft component library, including WMA and WMV789 decoder libraries"

# FIXME: fix the license
LICENSE = "FSL-mm-special-codec"
LICENSE_FLAGS = "license_${PN}-${PV}"
DEPENDS = "libfslcodec"

SECTION = "multimedia"

LIC_FILES_CHKSUM = "file://COPYING;md5=ea4d5c069d7aef0838a110409ea78a01"

inherit fsl-eula-unpack autotools pkgconfig

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"
SRC_URI[md5sum] = "42b2255706bc462f843d7ef4ef710b05"
SRC_URI[sha256sum] = "308e7595d14911bbbfe5072a4bfd3d442570e3fbc59633a0f3391d79e4c9ee7b"

FLOATING_POINT_SEL ?= ""
FLOATING_POINT_SEL = "${@bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', '--enable-fhw', '', d)}"
EXTRA_OECONF = "${FLOATING_POINT_SEL}"

do_install_append() {
    # LTIB move the files around or gst-fsl-plugin won't find them
    mv $p ${D}${libdir}/imx-mm/audio-codec/*.so* ${D}${libdir}
    mv $p ${D}${libdir}/imx-mm/video-codec/*.so* ${D}${libdir}
	rmdir ${D}${libdir}/imx-mm/video-codec

    # FIXME: Drop examples
#    rm -r ${D}${datadir}/imx-mm/video-codec/
}

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

python populate_packages_prepend() {
    # FIXME: All binaries lack GNU_HASH in elf binary but as we don't have
    # the source we cannot fix it. Disable the insane check for now.
    for p in d.getVar('PACKAGES', True).split():
        d.setVar("INSANE_SKIP_%s" % p, "ldflags textrel dev-so")
}


FILES_${PN} += "${libdir}/*${SOLIBSDEV} ${libdir}/imx-mm/audio-codec/wrap/*${SOLIBS} ${libdir}/imx-mm/audio-codec/wrap/*${SOLIBSDEV}"
FILES_${PN}-dev = "${libdir}/pkgconfig/*.pc ${includedir}/imx-mm/* ${datadir}/imx-mm/*"
FILES_${PN}-dbg += "${libdir}/imx-mm/video-codec/.debug \
                    ${libdir}/imx-mm/audio-codec/.debug \
                    ${libdir}/imx-mm/audio-codec/wrap/.debug"

COMPATIBLE_MACHINE = "(mx28|mx5|mx6)"
PACKAGE_ARCH = "${MACHINE_ARCH}"
