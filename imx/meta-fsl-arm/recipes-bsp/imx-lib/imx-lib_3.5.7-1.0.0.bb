require recipes-multimedia/imx-lib/imx-lib.inc

PR = "${INC_PR}.0"
PE = "1"

SRC_URI[md5sum] = "dc1c2851c57e486e7c3664d6e10e6dc6"
SRC_URI[sha256sum] = "70756dcf6a70941c19b764ae7b4f944e646ee6ddfc4ea0371a2862b91b6c657d"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
