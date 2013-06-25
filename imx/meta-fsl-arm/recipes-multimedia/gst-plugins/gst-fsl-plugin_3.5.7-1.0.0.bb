# Copyright (C) 2013 Freescale Semicondutor
# Released under the MIT license (see COPYING.MIT for the terms)

require gst-fsl-plugin.inc

DEPENDS += "libfslcodec libfslvpuwrap libfslparser"

PR = "${INC_PR}.3"
PE = "2"

# SRC_URI += "file://Link-with-the-Real-Time-Extension-lib.patch \
#           "

PACKAGE_NAME = "gst-fsl-plugins"

SRC_URI[md5sum] = "63fe35fdd9151d2ad131216aef8cf28d"
SRC_URI[sha256sum] = "804b899fa40ac8ad8e54ce0db4ee04aa77af0079dd9c46120e5e6626ad8ef0cb"

COMPATIBLE_MACHINE = "(mx6)"
