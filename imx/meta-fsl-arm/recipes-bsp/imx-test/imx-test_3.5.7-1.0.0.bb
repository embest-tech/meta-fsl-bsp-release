require recipes-bsp/imx-test/imx-test.inc

PR = "${INC_PR}.0"
PE = "1"

SRC_URI[md5sum] = "17ab2bda23b09c9b150842e0f606fc7d"
SRC_URI[sha256sum] = "cbcf77c431dcbfbbd7e816a0d9aa5b79dd52f7cb8f7c5aad723efc3a5ed5ee67"


SRC_URI += "file://hw-fp-fix.patch"


COMPATIBLE_MACHINE = "(mx6)"

## fix for hardware floating point - make sure this file is deleted
do_configure_prepend() {
	rm test/ar3k_bt/hciattach-ar3k.bin
}
