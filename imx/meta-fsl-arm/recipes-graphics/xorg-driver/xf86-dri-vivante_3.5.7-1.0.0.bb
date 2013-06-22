# Copyright (C) 2012-2013 Freescale Semiconductor
# Copyright (C) 2012-2013 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

LICENSE = "MIT"
SECTION = "x11/base"
DEPENDS = "virtual/xserver xf86-video-imxfb-vivante"
LIC_FILES_CHKSUM = "file://src/dri.h;enline=27;md5=1d0d59e1dc96f5197ea3a8b101bf1fcc"

SRC_URI = "${FSL_MIRROR}/xserver-xorg-video-imx-viv-${PV}.tar.gz \
           file://fix-libdrm-link.patch"
SRC_URI[md5sum] = "b17fca1e43af41670f3193a72502122b"
SRC_URI[sha256sum] = "2e2380163c1315f8ff8ed0c9b8945bbdc77346200379d3ab9ab6361704a365b5"

PE = "1"
PR = "r0"

S = "${WORKDIR}/xserver-xorg-video-imx-viv-${PV}/DRI_1.10.4"

inherit fsl-eula-unpack autotools pkgconfig

EXTRA_OECONF_armv7a = " --enable-neon "
CFLAGS += " -I${STAGING_INCDIR}/xorg -DXSERVER_LIBPCIACCESS"

do_install_append () {
    # Install header files
    install -d ${D}${includedir}/xorg
    cp -axr ${S}/src/*.h ${D}${includedir}/xorg
    find ${D}${includedir} -type f -exec chmod 660 {} \;

    # FIXME: don't install libtool (*.la) file
    rm ${D}${libdir}/xorg/modules/extensions/*.la

    # FIXME: The Xorg driver has a hardcoded library name
    mv ${D}${libdir}/xorg/modules/extensions/libdri.so \
       ${D}${libdir}/xorg/modules/extensions/libvivante_dri.so
}

FILES_${PN}-dev += "${includedir}/xorg/*.h"
FILES_${PN} += " ${libdir}/xorg/modules/extensions/*.so"
FILES_${PN}-dbg += " ${libdir}/xorg/modules/extensions/.debug"

RDEPENDS_${PN} += "xserver-xorg-extension-glx \
                   xserver-xorg-extension-dri \
                   xserver-xorg-extension-dri2"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
