# Freescale imx extra configuration udev rules
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PRINC := "${@int(PRINC) + 1}"

SRC_URI_append_mx6 = " file://9-blacklist.rules"

do_install_prepend () {
	if [ -e "${WORKDIR}/9-blacklist.rules" ]; then
		install -d ${D}${sysconfdir}/udev/rules.d
		install -m 0644 ${WORKDIR}/9-blacklist.rules ${D}${sysconfdir}/udev/rules.d
	fi
}

PACKAGE_ARCH_mx6 = "${MACHINE_ARCH}"
