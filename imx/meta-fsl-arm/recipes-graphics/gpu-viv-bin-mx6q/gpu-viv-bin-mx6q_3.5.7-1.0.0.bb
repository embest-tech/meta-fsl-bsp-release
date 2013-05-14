# Copyright (C) 2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

PR = "${INC_PR}.0"
PE = "2"

include gpu-viv-bin-mx6q.inc

SRC_URI[md5sum] = "480cc412cf7b078c0cea683d13373442"
SRC_URI[sha256sum] = "a1b274e64e58dd174a2e17766e031d30efff68e5bbe0e91b7d24365616f2e143"

# FIXME: 1.1.0 BSP release uses DirectFB 1.4 and Yocto has 1.6 so
# disable it for now
USE_DFB = "no"
