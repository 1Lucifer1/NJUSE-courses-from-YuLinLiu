##############################################################################################################
#     ______    __          _  __        __          _                                   __            
#    / ____/___/ /___ ____ | |/ /   ____/ /__ _   __(_)_______     _____________  ____ _/ /_____  _____
#   / __/ / __  / __ `/ _ \|   /   / __  / _ \ | / / / ___/ _ \   / ___/ ___/ _ \/ __ `/ __/ __ \/ ___/
#  / /___/ /_/ / /_/ /  __/   |   / /_/ /  __/ |/ / / /__/  __/  / /__/ /  /  __/ /_/ / /_/ /_/ / /    
# /_____/\__,_/\__, /\___/_/|_|   \__,_/\___/|___/_/\___/\___/   \___/_/   \___/\__,_/\__/\____/_/     
#             /____/                                                                                   
##############################################################################################################
# Name;         createAlarmDevice.py
# Description:  Script with all REST calls required to create a new device in EdgeX Foundry - Geneva release
#               The device in this use case exposes a REST API which EdgeX Foundry can interact with. 
#               The device does not support sending sensor values (separate script is avilable for that use case)
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
parser.add_argument('-devip',help='Target device IP address', required=True)

args=vars(parser.parse_args())

edgex_ip    = args["ip"]
device_ip   = args["devip"]


def createAddressables():

    # Create the addressable for the device. 
    # This tells EdgeX Foundry how to find the device 
    url = 'http://%s:48081/api/v1/addressable' % edgex_ip

    payload = {
        "name":"judgeAddr",
        "protocol":"HTTP",
        "address":device_ip,
        "port":5000,
        "path":"/api/v1/device/register" # REST endpoint on the test app
    }
    headers = {'content-type': 'application/json'}
    response = requests.post(url, data=json.dumps(payload), headers=headers, verify=False)
    print("Result for createAddressables: %s - Message: %s" % (response, response.text))

# Value descriptors are what they sound like: Describing data values
# Note that these correspond to the same values in the device profile YAML file
def createValueDescriptors():
    url = 'http://%s:48080/api/v1/valuedescriptor' % edgex_ip

    payload =   {
                    "name":"judge",
                    "description":"judgement of temperature",
                    "type":"Str",
                    "uomLabel":"judge",
                    "defaultValue":"low",
                    "formatting":"%s",
                    "labels":["judge","temperature"]
                }
    headers = {'content-type': 'application/json'}
    response = requests.post(url, data=json.dumps(payload), headers=headers, verify=False)
    print("Result for createValueDescriptors #1: %s - Message: %s" % (response, response.text))


# To create a device we need a device profile in YAML format. This function uploads and registers
# the device profile with EdgeX Foundry. Based on the content of the device profile, EdgeX Foundry
# may create entries for the device in the command module as well as meta data.
def uploadDeviceProfile():
    multipart_data = MultipartEncoder(
        fields={
                'file': ('alarmDeviceProfile.yaml', open('alarmDeviceProfile.yaml', 'rb'), 'text/plain')
               }
        )

    url = 'http://%s:48081/api/v1/deviceprofile/uploadfile' % edgex_ip
    response = requests.post(url, data=multipart_data,
                      headers={'Content-Type': multipart_data.content_type})

    print("Result of uploading device profile: %s with message %s" % (response, response.text))




# This is a dummy device service since the existing REST device service doesn't yet support sending commands
def createDeviceService():
    url = 'http://%s:48081/api/v1/deviceservice' % edgex_ip

    payload = {
        "name":"alarm-device-service",
        "description":"Gateway for emergency venting system",
        "labels":["judge","temperature"],
        "adminState":"unlocked",
        "operatingState":"enabled",
        "addressable": {
            "name":"judgeAddr"
        }
    }
    headers = {'content-type': 'application/json'}
    response = requests.post(url, data=json.dumps(payload), headers=headers, verify=False)
    print("Result for createDeviceService: %s - Message: %s" % (response, response.text))



# Finally we can create the actual device. It will be named and will also reference both the 
# device service it supports as well as the device profile it corresponds to
# The device creation requires a protocols section. Perhaps it can be expanded to include
# information about the device, like IP, port, etc. but isn't actively used for these tutorials
def addNewDevice():
    url = 'http://%s:48081/api/v1/device' % edgex_ip

    payload = {
        "name": "alarmDevice",
        "description": "judge temperature",
        "adminState": "unlocked",
        "operatingState": "enabled",
        "protocols": {
            "example": {
            "host": "localhost",
            "port": "0",
            "unitID": "1"
            }
        },
        "addressable": {
            "name": "judgeAddr"
        },
        "labels": [
            "judge",
            "alarm"
        ],
        "location": "China",
        "service": {
            "name": "alarm-device-service" 
        },
        "profile": {
            "name": "tempAlarmer"
        }
    }
    headers = {'content-type': 'application/json'}
    response = requests.post(url, data=json.dumps(payload), headers=headers, verify=False)
    print("Result for addNewDevice: %s - Message: %s" % (response, response.text))


if __name__ == "__main__":
    createAddressables()
    createValueDescriptors()
    uploadDeviceProfile()
    createDeviceService()
    addNewDevice()
