# Provides the i.MX mx6 Freescale board common settings

DEFAULTTUNE_mx6 = "cortexa9hf-neon"

## Note the imx-base appends vpu firmware so not including it here
## Note sdma firmware from firmware-imx is not needed on mx6
MACHINE_FIRMWARE = " firmware-imx-ath6k"

SERIAL_CONSOLE = "115200 ttymxc3"

MACHINE_FEATURES += " pci wifi bluetooth"
