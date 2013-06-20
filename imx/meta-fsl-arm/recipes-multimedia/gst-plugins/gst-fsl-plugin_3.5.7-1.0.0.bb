# Copyright (C) 2013 Freescale Semicondutor
# Released under the MIT license (see COPYING.MIT for the terms)

require gst-fsl-plugin.inc

DEPENDS += "libfslcodec libfslvpuwrap libfslparser"

PR = "${INC_PR}.3"
PE = "2"

# SRC_URI += "file://Link-with-the-Real-Time-Extension-lib.patch \
#           "

PACKAGE_NAME = "gst-fsl-plugins"

SRC_URI[md5sum] = "b42b0e7c61774184cae845fdb296ada4"
SRC_URI[sha256sum] = "1e69bb64aaeb183020ad7f6abb3a011e61bfda9910a2fa3051b9c883be5ab540"

COMPATIBLE_MACHINE = "(mx6)"
