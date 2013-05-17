# Copyright (C) 2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

PR = "${INC_PR}.0"
PE = "2"

include gpu-viv-bin-mx6q.inc

SRC_URI[md5sum] = "645fbb47f9ce476049a2d7ed743fe3ab"
SRC_URI[sha256sum] = "7009fc796692c5bfb7495cefbacb9c10e803780cfbc7035e603a0dcf70511e9b"

# FIXME: 1.1.0 BSP release uses DirectFB 1.4 and Yocto has 1.6 so
# disable it for now
USE_DFB = "no"
