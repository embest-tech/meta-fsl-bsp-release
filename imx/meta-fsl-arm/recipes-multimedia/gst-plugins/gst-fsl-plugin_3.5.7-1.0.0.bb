# Copyright (C) 2013 Freescale Semicondutor
# Released under the MIT license (see COPYING.MIT for the terms)

require gst-fsl-plugin.inc

DEPENDS += "libfslcodec libfslvpuwrap libfslparser"

PR = "${INC_PR}.3"
PE = "2"

# SRC_URI += "file://Link-with-the-Real-Time-Extension-lib.patch \
#           "

PACKAGE_NAME = "gst-fsl-plugins"

SRC_URI[md5sum] = "4f35f3c97d97b110ae95c676b32cf4ef"
SRC_URI[sha256sum] = "4dc6256810c0c3028cdf9a62b4f9d01ff86c204ba1af80c5e9fc28ca3ac4031d"

COMPATIBLE_MACHINE = "(mx6)"
