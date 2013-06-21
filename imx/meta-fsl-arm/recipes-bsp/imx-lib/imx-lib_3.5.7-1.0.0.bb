require recipes-multimedia/imx-lib/imx-lib.inc

PR = "${INC_PR}.0"
PE = "1"

SRC_URI[md5sum] = "4789728a9b3ae3eb15f5d5c9a4507f6f"
SRC_URI[sha256sum] = "3b499c1228febc4e9c43e112f43055088ad537b043ac0ad5cc950fe92a16b12b"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
