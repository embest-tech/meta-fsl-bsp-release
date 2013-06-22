# Copyright (C) 2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

PR = "${INC_PR}.0"
PE = "1"

require recipes-graphics/gpu-viv-bin-mx6q/gpu-viv-bin-mx6q.inc

LIC_FILES_CHKSUM = "file://usr/include/gc_vdk.h;endline=11;md5=19f5925343fa3da65596eeaa4ddb5fd3"

SRC_URI[md5sum] = "28aade85e2188c5a50c860aa1729d8fa"
SRC_URI[sha256sum] = "531eda3105c4c50eee7c4c9debfb82d0edfe7a804c52d1fa5b9867adf2638ac6"

PACKAGES =+ "libvivante-dfb-mx6"

USE_HFP = "${@base_contains("TUNE_FEATURES", "callconvention-hard", "yes", "no", d)}"

do_configure () {

	if [ "${USE_HFP}" = "no" ]; then
		bberror "${PN}-${PV} uses Hard Floating Point binaries, which are incompatible with your machine tuning"
		exit 1
	fi
}

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${includedir}

    cp ${S}/usr/lib/*.so ${D}${libdir}
    cp -r ${S}/usr/lib/directfb-1.6-0 ${D}${libdir}
    cp -axr ${S}/usr/include/* ${D}${includedir}
    cp -axr ${S}/opt ${D}

    if [ "${USE_X11}" = "yes" ]; then
        cp -r ${S}/usr/lib/dri ${D}${libdir}
        find ${D}${libdir} -name '*-dfb.so' -exec rm '{}' ';'
        find ${D}${libdir} -name '*-fb.so' -exec rm '{}' ';'
    else
        if [ "${USE_DFB}" = "yes" ]; then
            find ${D}${libdir} -name '*-x11.so' -exec rm '{}' ';'
            find ${D}${libdir} -name '*-fb.so' -exec rm '{}' ';'
        else
            # Regular framebuffer
            find ${D}${libdir} -name '*-x11.so' -exec rm '{}' ';'
            find ${D}${libdir} -name '*-dfb.so' -exec rm '{}' ';'
        fi
    fi

    # We'll only have one backend here so we rename it to generic name
    # and avoid rework in other packages, when possible
    rm ${D}${libdir}/libEGL.so ${D}${libdir}/libGAL.so \
       ${D}${libdir}/libVIVANTE.so

    renamed=
    for backend in x11 fb dfb; do
        for f in $(find ${D}${libdir} -name "*-$backend.so"); do
            if [ -n "$renamed" ] && [ "$renamed" != "$backend" ]; then
                bberror "More than one GPU backend is installed ($backend and $renamed)."
                exit 1
            fi

            renamed=$backend
            mv $f $(echo $f | sed "s,-$backend\.so,.so,g")
         done
    done

    find ${D}${libdir} -type f -exec chmod 644 {} \;
    find ${D}${includedir} -type f -exec chmod 644 {} \;
}

FILES_libvivante-dfb-mx6 = "${libdir}/directfb-1.6-0/gfxdrivers/libdirectfb_gal.so"
