# Copyright (C) 2012-2013 Freescale Semiconductor
# Copyright (C) 2012-2013 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-graphics/xorg-driver/xorg-driver-video.inc

PE = "4"
PR = "${INC_PR}.0"

DEPENDS += "virtual/xserver virtual/libx11 virtual/libgal-x11 gpu-viv-bin-mx6q pixman virtual/kernel"

LIC_FILES_CHKSUM = "file://src/vivante_fbdev/vivante.h;endline=19;md5=641ac6e6d013833e36290797f4d7089c"

SRC_URI = "${FSL_MIRROR}/xserver-xorg-video-imx-viv-${PV}.tar.gz \
           file://Makefile-fix-cross-compile.patch"

SRC_URI[md5sum] = "e27c5e6e823502d06672808b92f1e008"
SRC_URI[sha256sum] = "ae4fc547afb6b0e704c191c25c4b6c2752ed04b4ed60c2eb996abafcbc76f004"

S = "${WORKDIR}/xserver-xorg-video-imx-viv-${PV}/EXA/"

CC += "-I${WORKDIR}/xserver-xorg-video-imx-viv-${PV}/DRI_1.10.4/src"

base_do_compile () {

	make -C src/ -f makefile.linux BUILD_HARD_VFP=1 YOCTO=1 BUSID_HAS_NUMBER=1 sysroot=${STAGING_DIR_HOST} || die "make failed"
}

do_install () {

	make -C src/ -f makefile.linux prefix=${D}/usr install || die "make install failed"

	install -d ${D}${includedir}
	cp -axr ${S}/src/vivante_gal/vivante_priv.h ${D}${includedir}
	cp -axr ${S}/src/vivante_gal/vivante_gal.h ${D}${includedir}
	find ${D}${includedir} -type f -exec chmod 660 {} \;
}

RDEPENDS_${PN} += "libvivante-dri-mx6 \
                   xserver-xorg-module-exa \
                   xserver-xorg-extension-glx \
                   xserver-xorg-extension-dri \
                   xserver-xorg-extension-dri2 \
                   mesa-driver-swrast "


PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
