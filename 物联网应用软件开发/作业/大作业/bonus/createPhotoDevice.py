##############################################################################################################
#     ______    __          _  __        __          _                                   __            
#    / ____/___/ /___ ____ | |/ /   ____/ /__ _   __(_)_______     _____________  ____ _/ /_____  _____
#   / __/ / __  / __ `/ _ \|   /   / __  / _ \ | / / / ___/ _ \   / ___/ ___/ _ \/ __ `/ __/ __ \/ ___/
#  / /___/ /_/ / /_/ /  __/   |   / /_/ /  __/ |/ / / /__/  __/  / /__/ /  /  __/ /_/ / /_/ /_/ / /    
# /_____/\__,_/\__, /\___/_/|_|   \__,_/\___/|___/_/\___/\___/   \___/_/   \___/\__,_/\__/\____/_/     
#             /____/                                                                                   
##############################################################################################################
# Name;         createTempSensor.py
# Description:  Script with all REST calls required to create a new device in EdgeX Foundry - Geneva release
#               The device in this use case provides int64 values for temperature and humidity. 
#               The device does not support commands (separate script is avilable for that use case)
# Version:      0.1
# Author:       lbd
##############################################################################################################


import requests, json, sys, re, time, os, warnings, argparse
from requests_toolbelt.multipart.encoder import MultipartEncoder
from datetime import datetime

warnings.filterwarnings("ignore")

# Gather information from arguments
parser=argparse.ArgumentParser(description="Python script for creating a new device from scratch in EdgeX Foundry")
parser.add_argument('-ip',help='EdgeX Foundry IP address', required=True)

args=vars(parser.parse_args())

edgex_ip=args["ip"]


# Value descriptors are what they sound like: Describing data values
# For this use case temperature and humidity are the two value types required
# Note that these correspond to the same values in the device profile YAML file
def createValueDescriptors():
    url = 'http://%s:48080/api/v1/valuedescriptor' % edgex_ip
    payload =   {
                    "name":"photo",
                    "description":"Photo Result", 
                    "min":"",
                    "max":"",
                    "type":"String",
                    "uomLabel":"count",
                    "defaultValue":"0",
                    "formatting":"%s",
                    "labels":["photo","result"]
                }
    headers = {'content-type': 'application/json'}
    response = requests.post(url, data=json.dumps(payload), headers=headers, verify=False)
    print("Result for createValueDescriptors #2: %s - Message: %s" % (response, response.text))


# To create a device we need a device profile in YAML format. This function uploads and registers
# the device profile with EdgeX Foundry. Based on the content of the device profile, EdgeX Foundry
# may create entries for the device in the command module as well as meta data. Our device doesn't 
# support commands so the command module will not be updated for this use case
def uploadDeviceProfile():
    multipart_data = MultipartEncoder(
        fields={
                'file': ('photo.yaml', open('photo.yaml', 'rb'), 'text/plain')
               }
        )

    url = 'http://%s:48081/api/v1/deviceprofile/uploadfile' % edgex_ip
    response = requests.post(url, data=multipart_data,
                      headers={'Content-Type': multipart_data.content_type})

    print("Result of uploading device profile: %s with message %s" % (response, response.text))



# Finally we can create the actual device. It will be named and will also reference both the 
# device service it supports as well as the device profile it corresponds to
# The device creation requires a protocols section. Perhaps it can be expanded to include
# information about the device, like IP, port, etc. but isn't actively used for these tutorials
def addNewDevice():
    url = 'http://%s:48081/api/v1/device' % edgex_ip

    payload =   {
                    "name":"Photo_catcher",
                    "description":"Photo catcher",
                    "adminState":"unlocked",
                    "operatingState":"enabled",
                    "protocols": {
                        "example": {
                        "host": "dummy",
                        "port": "1234",
                        "unitID": "1"
                        }
                    },
                    "labels": ["Photo","Recognation"],
                    "location":"China",
                    "service": {
                        "name":"edgex-device-rest"
                    },
                    "profile": {
                        "name":"photo"
                    }
                }
    headers = {'content-type': 'application/json'}
    response = requests.post(url, data=json.dumps(payload), headers=headers, verify=False)
    print("Result for addNewDevice: %s - Message: %s" % (response, response.text))



if __name__ == "__main__":
    createValueDescriptors()
    uploadDeviceProfile()
    addNewDevice()
