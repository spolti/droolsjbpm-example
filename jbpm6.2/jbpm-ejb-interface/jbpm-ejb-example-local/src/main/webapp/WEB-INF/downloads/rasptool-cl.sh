#!/bin/bash
#Rasptool client
################################
#Usage:
#./rasptool-cl.sh -s to hide public ip or just ./rasptool-cl.sh
###############################

URL_DOWNLOAD_CL="http://rasptool-demonstration.rhcloud.com/getmyip/downloads/client"
CL_FILE_NAME="./rasptool-cl.jar"

#Verifying if the client exists
if [ -e "$CL_FILE_NAME" ]; then
        echo "Client found!"
        else
                echo "Client not found, downloading..."
                wget -O rasptool-cl.jar $URL_DOWNLOAD_CL
fi


if [ "$1" == "-s"  ];then
        java -jar $CL_FILE_NAME -s
        else
                java -jar $CL_FILE_NAME 
fi
