# Copyright (C) 2013 Freescale Semicondutor
# Released under the MIT license (see COPYING.MIT for the terms)

require gst-fsl-plugin.inc

DEPENDS += "libfslcodec libfslvpuwrap libfslparser"

PR = "${INC_PR}.3"
PE = "2"

# SRC_URI += "file://Link-with-the-Real-Time-Extension-lib.patch \
#           "

PACKAGE_NAME = "gst-fsl-plugins"

SRC_URI[md5sum] = "ffe1ff58161d541685ecf6eeb1960826"
SRC_URI[sha256sum] = "67461598cc2843670d1b1cb96178d918c65cbd1ccbf5d5fb9731292319d644a8"

COMPATIBLE_MACHINE = "(mx6)"
