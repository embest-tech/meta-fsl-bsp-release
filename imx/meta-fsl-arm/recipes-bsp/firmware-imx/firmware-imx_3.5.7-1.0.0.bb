require recipes-bsp/firmware-imx/firmware-imx.inc

PR = "${INC_PR}.0"
PE="1"

SRC_URI[md5sum] = "c816d6757d62a046a520d47c07cee65f"
SRC_URI[sha256sum] = "9cca0a3f42159c6554d6732bb2550e3aa82cac5ac4fdd26729664d4fa826595a"

COMPATIBLE_MACHINE = "(mx6)"
