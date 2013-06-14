require recipes-bsp/imx-test/imx-test.inc

PR = "${INC_PR}.0"
PE = "1"

SRC_URI[md5sum] = "6a93f6321a2cdda15242ff296823c0e2"
SRC_URI[sha256sum] = "0bea2388e302395814bbe8a3675fa87f6d99e403f7d4d4f9d01c7af48c1ce730"

SRC_URI += "file://imx-test-Yocto-build-patches.patch"

COMPATIBLE_MACHINE = "(mx6)"

do_compile() {
        INCLUDE_DIR="-I${STAGING_INCDIR}  -I${STAGING_KERNEL_DIR}/include -I${WORKDIR}/git/include \
	        -I${STAGING_KERNEL_DIR}/drivers/mxc/security/rng/include \
                 -I${STAGING_KERNEL_DIR}/drivers/mxc/security/sahara2/include"
        oe_runmake CROSS_COMPILE="${HOST_PREFIX}" PLATFORM="${PLATFORM}" INC="${INCLUDE_DIR}" LINUXPATH="${STAGING_KERNEL_DIR}" KBUILD_OUTPUT="${STAGING_KERNEL_DIR}" VERBOSE="1" YOCTO_BUILD=1 all
}
