# Copyright (C) 2013 Freescale Semicondutors

DESCRIPTION = "Platform specific libraries for imx platform"
LICENSE = "LGPLv2.1 & Proprietary"
SECTION = "bsp"
DEPENDS = "virtual/kernel"

INC_PR = "r0"

inherit fsl-eula-unpack autotools pkgconfig

LIC_FILES_CHKSUM = "file://ipu/mxc_ipu_hl_lib.h;endline=13;md5=6c7486b21a8524b1879fa159578da31e"

SRC_URI = "${FSL_MIRROR}/imx-lib-${PV}.bin;fsl-eula=true"
SRC_URI[md5sum] = "4789728a9b3ae3eb15f5d5c9a4507f6f"
SRC_URI[sha256sum] = "3b499c1228febc4e9c43e112f43055088ad537b043ac0ad5cc950fe92a16b12b"

PLATFORM_mx6 = "IMX6Q"
PLATFORM_mx5 = "IMX51"

PARALLEL_MAKE="-j 1"
EXTRA_OEMAKE = ""

do_configure_append () {
    # FIXME: The build system does not allow CC and AR to be overriden
    find ${S} -name Makefile | xargs sed -i 's,^\(CC\|AR\)=,\1 ?=,g'
}

do_compile () {
    INCLUDE_DIR="-I${STAGING_INCDIR} -I${STAGING_KERNEL_DIR}/drivers/mxc/security/rng/include \
                 -I${STAGING_KERNEL_DIR}/drivers/mxc/security/sahara2/include \
                 -I${STAGING_KERNEL_DIR}/include"
    oe_runmake CROSS_COMPILE="${HOST_PREFIX}" PLATFORM="${PLATFORM}" INCLUDE="${INCLUDE_DIR}" all
}

do_install () {
    oe_runmake PLATFORM="${PLATFORM}" DEST_DIR="${D}" install
}

FILES_${PN} += "${libdir}/*${SOLIBS}"
FILES_${PN}-dbg += "${libdir}/.debug"
FILES_${PN}-dev += "${libdir}/*${SOLIBSDEV}"

PR = "${INC_PR}.0"
PE = "1"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
