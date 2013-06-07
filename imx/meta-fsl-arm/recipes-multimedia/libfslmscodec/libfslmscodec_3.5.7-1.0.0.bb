# Copyright (C) 2013 Freescale Semiconductor
# FIXME: add a better description to point to right licensing
DESCRIPTION = "Microsoft component library, including WMA and WMV789 decoder libraries"

# FIXME: fix the license
LICENSE = "FSL-mm-special-codec"
LICENSE_FLAGS = "license_${PN}-${PV}"
#LICENSE = "Proprietary"
DEPENDS = "libfslcodec"

SECTION = "multimedia"

LIC_FILES_CHKSUM = "file://COPYING;md5=ea4d5c069d7aef0838a110409ea78a01"

inherit fsl-eula-unpack autotools pkgconfig

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"
SRC_URI[md5sum] = "c16cf0455c20bc1916044fa5a1cf8a19"
SRC_URI[sha256sum] = "98ce34770ff8281366f09a637b7818ad33e934a284316a285d3445f5184f0eaf"

FLOATING_POINT_SEL ?= ""
FLOATING_POINT_SEL = "${@bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', '--enable-fhw', '', d)}"
EXTRA_OECONF = "${FLOATING_POINT_SEL}"

do_install_append() {
    # LTIB move the files around or gst-fsl-plugin won't find them
    mv $p ${D}${libdir}/imx-mm/audio-codec/*.so* ${D}${libdir}
    mv $p ${D}${libdir}/imx-mm/video-codec/*.so* ${D}${libdir}

    # FIXME: Drop examples
    rm -r ${D}${datadir}/imx-mm
}

# FIXME: All binaries lack GNU_HASH in elf binary but as we don't have
# the source we cannot fix it. Disable the insane check for now.
INSANE_SKIP_${PN} = "ldflags textrel"

FILES_${PN} += "${libdir}/imx-mm/audio-codec/wrap/*${SOLIBS}"
FILES_${PN}-dev += "${libdir}/imx-mm/audio-codec/wrap/*${SOLIBSDEV}"
FILES_${PN}-dbg += "${libdir}/imx-mm/video-codec/.debug \
                    ${libdir}/imx-mm/audio-codec/.debug \
                    ${libdir}/imx-mm/audio-codec/wrap/.debug"

COMPATIBLE_MACHINE = "(mx28|mx5|mx6)"
PACKAGE_ARCH = "${MACHINE_ARCH}"
