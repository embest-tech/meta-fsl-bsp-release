# Add needed Freescale packages and definitions

# Add it into tools & Test applications group
SOC_TOOLS_TESTAPPS_mx6 += " \
	fsl-alsa-plugins \
"

RDEPENDS_${PN}-tools-testapps += "\
    mtd-utils \
    mtd-utils-ubifs \
    canutils \
"

SOC_TOOLS_TESTAPPS_mx28 = " \
    gst-fsl-plugin-gplay \
"

RDEPENDS_${PN}-gstreamer += " \
    gst-meta-debug \
	gst-meta-good-all \
"

RDEPENDS_${PN}-gstreamer_append_mx6 = " gst-plugins-gl \
"
