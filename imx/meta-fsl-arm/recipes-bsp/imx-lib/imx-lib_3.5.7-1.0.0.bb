require recipes-multimedia/imx-lib/imx-lib.inc

PR = "${INC_PR}.0"
PE = "1"

SRC_URI[md5sum] = "93b1d850f078852d1bdd8d05b789df10"
SRC_URI[sha256sum] = "397a257cd24434ad5c355fd948abb9f1d154fce6d094911206e6805a46cf179c"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
