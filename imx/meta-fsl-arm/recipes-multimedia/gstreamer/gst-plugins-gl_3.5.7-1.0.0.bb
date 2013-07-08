# gst-plugins-gl for imx6 Vivante

LICENSE = "GPLv2+ & LGPLv2+ & LGPLv2.1+ "
LIC_FILES_CHKSUM = "file://COPYING;md5=55ca817ccb7d5b5b66355690e9abc605"

DEPENDS += "gstreamer gst-plugins-base virtual/libgles2 virtual/egl jpeg"
DEPENDS_append_mx6 = " gst-fsl-plugin gpu-viv-bin-mx6q"

inherit fsl-eula-unpack gettext gconf autotools pkgconfig

PACKAGE_NAME = "gst-plugins-gl"

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"
SRC_URI[md5sum] = "6d1ed86539858ba8698cf169192b70a2"
SRC_URI[sha256sum] = "97fb1ae3a4fd724a41365acf5e6c9c087da365869b554b41c2e4248a245ac979"

acpaths = "-I ${S}/common/m4 -I ${S}/m4"

# This package doesn't have a configure switch for EGL or GL, so forcibly tell
# configure that it can't find gl.h so it always uses EGL.  If/when we have some
# way for machines to specify their preferred GL flavour this can be
# automatically adapted.
GSTREAMER_DEBUG ?= "--disable-debug"
EXTRA_OECONF = "--disable-libvisual --disable-valgrind ${GSTREAMER_DEBUG} --disable-examples --disable-schemas-install ac_cv_header_GL_gl_h=no"

CFLAGS_append_mx6 = " -DGLIB_DISABLE_DEPRECATION_WARNINGS"

do_compile_prepend() {
        cd ${S}
}

do_install_prepend() {
        cd ${S}
        echo ${PN}
}

do_configure_prepend() {            
	cd ${S}
	export NOCONFIGURE=1
	./autogen.sh
	unset NOCONFIGURE
	echo ${S}
}


FILES_${PN} = "${libdir}/gstreamer-0.10/*.so ${libdir}/*${SOLIBS}"
FILES_${PN}-dbg += "${libdir}/gstreamer-0.10/.debug"
FILES_${PN}-dev += "${libdir}/gstreamer-0.10/*.la"
FILES_${PN}-staticdev += "${libdir}/gstreamer-0.10/*.a"



