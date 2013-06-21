# Copyright (C) 2013 Freescale Semicondutors
# Released under the MIT license (see COPYING.MIT for the terms)
DESCRIPTION = "Freescale Multimedia codec libs"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://EULA.txt;md5=ea4d5c069d7aef0838a110409ea78a01"

INC_PR = "r1"

inherit fsl-eula-unpack autotools pkgconfig

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"

PACKAGES_DYNAMIC = "${PN}-*"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

FLOATING_POINT_SEL ?= ""
FLOATING_POINT_SEL = "${@bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', '--enable-fhw', '', d)}"
EXTRA_OECONF = "${FLOATING_POINT_SEL}"

do_install_append() {
	# FIXME: This link points to nowhere
	rm ${D}${libdir}/imx-mm/audio-codec/lib_src_ppp_arm11_elinux.so

	# LTIB move the files around or gst-fsl-plugin won't find them
	for p in $(find ${D}${libdir}/imx-mm -mindepth 2 -maxdepth 2 -not -type d); do
		mv $p ${D}${libdir}
	done
	rmdir ${D}${libdir}/imx-mm/video-codec

	# FIXME: Drop examples
#	rm -r ${D}${datadir}/imx-mm
}

python populate_packages_prepend() {
    # FIXME: All binaries lack GNU_HASH in elf binary but as we don't have
    # the source we cannot fix it. Disable the insane check for now.
    # FIXME: gst-fsl-plugin looks for the .so files so we need to deploy those
    for p in d.getVar('PACKAGES', True).split():
        d.setVar("INSANE_SKIP_%s" % p, "ldflags textrel dev-so")
}


# Ensure we get warnings if we miss something
#FILES_${PN} += "${libdir}/*${SOLIBSDEV} ${libdir}/imx-mm/audio-codec/wrap/*${SOLIBS} ${libdir}/imx-mm/audio-codec/wrap/*${SOLIBSDEV}"
#FILES_${PN}-dev = "${libdir}/pkgconfig/*.pc ${includedir}/imx-mm/* ${datadir}/imx-mm/*"
FILES_${PN} += "${libdir}/*${SOLIBSDEV} ${libdir}/imx-mm/audio-codec/wrap/*${SOLIBS} \
				${libdir}/imx-mm/audio-codec/wrap/*${SOLIBSDEV} ${datadir}/imx-mm/*"
FILES_${PN}-dev = "${libdir}/pkgconfig/*.pc ${includedir}/imx-mm/*"

PACKAGE_ARCH = "${MACHINE_ARCH}"
