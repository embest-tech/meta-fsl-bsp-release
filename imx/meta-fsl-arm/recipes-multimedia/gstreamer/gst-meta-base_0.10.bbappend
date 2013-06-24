# Freescale gst-plugin-base/good more modules support

PACKAGES_append = " \
	gst-meta-good-all \
	gst-meta-good-streaming \
"
RDEPENDS_gst-meta-good-all = "\
	gst-plugins-good-videofilter \
	gst-plugins-good-alaw \
	gst-plugins-good-mulaw \
	gst-plugins-good-cairo \
	gst-plugins-good-isomp4 \
	gst-plugins-good-icydemux \
	gst-plugins-good-flxdec \
	gst-plugins-good-png \
	gst-plugins-good-level \
	gst-plugins-good-goom2k1 \
	gst-plugins-good-id3demux \
	gst-plugins-good-videobox \
	gst-plugins-good-audiofx \
	gst-plugins-good-auparse \
	gst-plugins-good-deinterlace \
	gst-plugins-good-jpeg \
	gst-plugins-good-interleave \
	gst-plugins-good-oss4audio \
	gst-plugins-good-gconfelements \
	gst-plugins-good-flv \
	gst-plugins-good-alphacolor \
	gst-plugins-good-shapewipe \
	gst-plugins-good-replaygain \
	gst-plugins-good-videocrop \
	gst-plugins-good-cutter \
	gst-plugins-good-rtpmanager \
	gst-plugins-good-audioparsers \
	gst-plugins-good-alpha \
	gst-plugins-good-ximagesrc \
	gst-plugins-good-wavenc \
	gst-plugins-good-goom \
	gst-plugins-good-pulse \
	gst-plugins-good-video4linux2 \
	gst-plugins-good-multifile \
	gst-plugins-good-speex \
	gst-plugins-good-navigationtest \
	gst-plugins-good-smpte \
	gst-plugins-good-multipart \
	gst-plugins-good-y4menc \
	gst-plugins-good-ossaudio \
	gst-plugins-good-imagefreeze \
	gst-plugins-good-equalizer \
	gst-plugins-good-videomixer \
	gst-plugins-good-gdkpixbuf \
	gst-plugins-good-effectv \
	gst-plugins-good-apetag \
	gst-plugins-good-efence \
	gst-plugins-good-spectrum \
	gst-plugins-good-annodex \
	gst-plugins-good-autodetect \
	gst-plugins-good-souphttpsrc \
	gst-plugins-good-flac \
	gst-plugins-good-wavparse \
	gst-plugins-good-avi \
	gst-plugins-good-matroska \
	gst-meta-good-streaming \
"

RDEPENDS_gst-meta-good-streaming = "gst-plugins-good-rtp \
				gst-plugins-good-rtsp \
				gst-plugins-good-udp \
"

ALLOW_EMPTY_gst-meta-good-all = "1"
ALLOW_EMPTY_gst-meta-good-streaming = "1"

RDEPENDS_gst-meta-base_append_mx6 = " \
	gst-plugins-base-adder \
	gst-plugins-base-app \
	gst-plugins-base-audiorate \
	gst-plugins-base-encodebin \
	gst-plugins-base-gdp \
	gst-plugins-base-ivorbisdec \
	gst-plugins-base-subparse \
	gst-plugins-base-tcp \
	gst-plugins-base-videorate \
"



