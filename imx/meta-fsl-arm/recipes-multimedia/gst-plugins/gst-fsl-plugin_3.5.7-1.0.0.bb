# Copyright (C) 2013 Freescale Semicondutor
# Released under the MIT license (see COPYING.MIT for the terms)

require gst-fsl-plugin.inc

DEPENDS += "libfslcodec libfslvpuwrap libfslparser"

PR = "${INC_PR}.3"
PE = "2"

# SRC_URI += "file://Link-with-the-Real-Time-Extension-lib.patch \
#           "

PACKAGE_NAME = "gst-fsl-plugins"

SRC_URI[md5sum] = "7088ecb5c28817d525f9c589b59286db"
SRC_URI[sha256sum] = "f0e890697122cab33de1075a98accfcc72ebf38ece6d6703ba5cf67173426b6f"

COMPATIBLE_MACHINE = "(mx6)"
