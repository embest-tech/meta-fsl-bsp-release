# Copyright (C) 2013 Freescale Semicondutor
# Released under the MIT license (see COPYING.MIT for the terms)

require gst-fsl-plugin.inc

DEPENDS += "libfslcodec libfslvpuwrap libfslparser"

PR = "${INC_PR}.3"
PE = "2"

# SRC_URI += "file://Link-with-the-Real-Time-Extension-lib.patch \
#           "

PACKAGE_NAME = "gst-fsl-plugins"

SRC_URI[md5sum] = "87de48347a0baae9f510fa157c5a630d"
SRC_URI[sha256sum] = "e406fbb8a6800b575355d0eebe03d571131561f8981115f42a5c1e0f9564673d"

COMPATIBLE_MACHINE = "(mx6)"
