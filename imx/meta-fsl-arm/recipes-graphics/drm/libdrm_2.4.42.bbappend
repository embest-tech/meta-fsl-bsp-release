# Freescale imx update xf86drm.h
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PACKAGE_ARCH_mx6 = "${MACHINE_ARCH}"

SRC_URI += "file://drm-update-arm.patch"