#!/bin/bash

adb pull /sdcard/myAccelData myAccelData/.
# adb rm /sdcard/myAccelData/*.csv 
echo "Please remove old myAccelData files manually!\n"
adb shell
