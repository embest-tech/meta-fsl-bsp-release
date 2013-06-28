# Copyright (C) 2013 Freescale Semicondutor
# Released under the MIT license (see COPYING.MIT for the terms)

require gst-fsl-plugin.inc

DEPENDS += "libfslcodec libfslvpuwrap libfslparser"

PR = "${INC_PR}.3"
PE = "2"

# SRC_URI += "file://Link-with-the-Real-Time-Extension-lib.patch \
#           "

PACKAGE_NAME = "gst-fsl-plugins"

SRC_URI[md5sum] = "fe298c831e107ae5b93df05c4c29bb65"
SRC_URI[sha256sum] = "6c3215f24dedabf97ad6a7bd5c8d088b01dc4cd4792c56e0df353ae03adb35ff"

COMPATIBLE_MACHINE = "(mx6)"
