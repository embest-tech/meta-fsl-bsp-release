# Support for device tree generation
FILES_kernel-devicetree = "/boot/devicetree*"
KERNEL_DEVICETREE_FLAGS ?= "-R 8 -p 0x3000"

python __anonymous () {
    devicetree = d.getVar("KERNEL_DEVICETREE", True) or ''
    if devicetree:
	depends = d.getVar("DEPENDS", True)
	d.setVar("DEPENDS", "%s dtc-native" % depends)
	packages = d.getVar("PACKAGES", True)
	d.setVar("PACKAGES", "%s kernel-devicetree" % packages)
}

do_install_append() {
	if test -n "${KERNEL_DEVICETREE}"; then
		for DTS_FILE in ${KERNEL_DEVICETREE}; do
			if [ ! -f ${DTS_FILE} ]; then
				echo "Warning: ${DTS_FILE} is not available!"
				continue
			fi
			DTS_BASE_NAME=`basename ${DTS_FILE} | awk -F "." '{print $1}'`
			DTB_NAME=`echo ${KERNEL_IMAGE_BASE_NAME} | sed "s/${MACHINE}/${DTS_BASE_NAME}/g"`
			DTB_SYMLINK_NAME=`echo ${KERNEL_IMAGE_SYMLINK_NAME} | sed "s/${MACHINE}/${DTS_BASE_NAME}/g"`

                           ### Freescale's change since preprocessors do not work directly with dtc command below
                           ### Instead Freescale's kernel makefile create a cmd file that will run the dtc internally
                           make  ${DTS_BASE_NAME}.dtb
			#dtc -I dts -O dtb ${KERNEL_DEVICETREE_FLAGS} -o ${DTS_BASE_NAME} ${DTS_FILE}

                           ### Freescale add in arch path and .dtb extension with DTS_BASE_NAME
			install -m 0644 arch/arm/boot/${DTS_BASE_NAME}.dtb ${D}/boot/devicetree-${DTB_SYMLINK_NAME}.dtb
		done
	fi
}

do_deploy_append() {
	if test -n "${KERNEL_DEVICETREE}"; then
		for DTS_FILE in ${KERNEL_DEVICETREE}; do
			if [ ! -f ${DTS_FILE} ]; then
				echo "Warning: ${DTS_FILE} is not available!"
				continue
			fi
			DTS_BASE_NAME=`basename ${DTS_FILE} | awk -F "." '{print $1}'`
			DTB_NAME=` echo ${KERNEL_IMAGE_BASE_NAME} | sed "s/${MACHINE}/${DTS_BASE_NAME}/g"`
			DTB_SYMLINK_NAME=` echo ${KERNEL_IMAGE_SYMLINK_NAME} | sed "s/${MACHINE}/${DTS_BASE_NAME}/g"`
			install -d ${DEPLOYDIR}

                           ### Freescale change - add in arch path and dtb extension wtih DTS_BASE_NAME
			install -m 0644 ${B}/arch/arm/boot/${DTS_BASE_NAME}.dtb ${DEPLOYDIR}/${DTB_NAME}.dtb
			cd ${DEPLOYDIR}
			ln -sf ${DTB_NAME}.dtb ${DTB_SYMLINK_NAME}.dtb
			cd -
		done
	fi
}

pkg_postinst_kernel-devicetree () {
	cd /${KERNEL_IMAGEDEST}
	for DTS_FILE in ${KERNEL_DEVICETREE}
	do
		DTS_BASE_NAME=`basename ${DTS_FILE} | awk -F "." '{print $1}'`
		DTB_SYMLINK_NAME=`echo ${KERNEL_IMAGE_SYMLINK_NAME} | sed "s/${MACHINE}/${DTS_BASE_NAME}/g"`
		update-alternatives --install /${KERNEL_IMAGEDEST}/${DTS_BASE_NAME}.dtb ${DTS_BASE_NAME}.dtb devicetree-${DTB_SYMLINK_NAME}.dtb ${KERNEL_PRIORITY} || true
	done
}

pkg_postrm_kernel-devicetree () {
	cd /${KERNEL_IMAGEDEST}
	for DTS_FILE in ${KERNEL_DEVICETREE}
	do
		DTS_BASE_NAME=`basename ${DTS_FILE} | awk -F "." '{print $1}'`
		DTB_SYMLINK_NAME=`echo ${KERNEL_IMAGE_SYMLINK_NAME} | sed "s/${MACHINE}/${DTS_BASE_NAME}/g"`
		update-alternatives --remove ${DTS_BASE_NAME}.dtb devicetree-${DTB_SYMLINK_NAME}.dtb ${KERNEL_PRIORITY} || true
	done
}
