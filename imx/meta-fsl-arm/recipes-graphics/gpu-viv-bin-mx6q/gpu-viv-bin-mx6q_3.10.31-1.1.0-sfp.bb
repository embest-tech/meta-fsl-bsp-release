# Copyright (C) 2013, 2014 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require gpu-viv-bin-mx6q.inc

SRC_URI = "${FSL_MIRROR}/${PN}-3.10.31-1.1.0-alpha-sfp.bin;fsl-eula=true \
           file://egl.pc \
           file://egl_x11.pc \
           file://glesv1_cm.pc \
           file://glesv1_cm_x11.pc \
           file://glesv2.pc \
           file://glesv2_x11.pc \
           file://vg.pc \
           file://vg_x11.pc \
           file://gc_wayland_protocol.pc \
           file://wayland-egl.pc \
           file://wayland-viv.pc \
           file://directfbrc \
          "

S="${WORKDIR}/${PN}-3.10.31-1.1.0-alpha-sfp"

SRC_URI[md5sum] = "1df1292786de822687b0dddaf73af62d"
SRC_URI[sha256sum] = "24472ba0d5da85657b9c1595c11a82c201d2ead5b3d7386d3c2323c0834e5e71"

PACKAGE_FP_TYPE = "softfp"

# FIXME skip the QA error for viv-samples
INSANE_SKIP_${PN} += "rpaths"

