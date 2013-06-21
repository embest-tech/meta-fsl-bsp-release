require recipes-bsp/imx-test/imx-test.inc

PR = "${INC_PR}.0"
PE = "1"

SRC_URI[md5sum] = "98788357991eb5b3af289d8084199b48"
SRC_URI[sha256sum] = "eefb26ed66d4ff53cf3467182e8916c02efa847e69321c90d5b6ef65bf1ee665"

COMPATIBLE_MACHINE = "(mx6)"

do_compile() {
        INCLUDE_DIR="-I${STAGING_INCDIR}  -I${STAGING_KERNEL_DIR}/include -I${WORKDIR}/git/include \
	        -I${STAGING_KERNEL_DIR}/drivers/mxc/security/rng/include \
                 -I${STAGING_KERNEL_DIR}/drivers/mxc/security/sahara2/include"
        oe_runmake CROSS_COMPILE="${HOST_PREFIX}" PLATFORM="${PLATFORM}" INC="${INCLUDE_DIR}" LINUXPATH="${STAGING_KERNEL_DIR}" KBUILD_OUTPUT="${STAGING_KERNEL_DIR}" VERBOSE="1" YOCTO_BUILD=1 all
}
