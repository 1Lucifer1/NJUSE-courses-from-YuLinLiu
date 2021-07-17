import serial
import time
import re
import sys
import os
import requests
import json

edgexip = '127.0.0.1'

def recv():
    while True:
        line = ser.readline()
        if line.decode('utf-8') == '' or re.search(b'OK', line):
            continue
        elif re.search(b'high',line) or re.search(b'low',line):
            print(line.decode('utf-8'))
            continue
        elif re.search(b'.00',line):
            # print(line.decode('utf-8'))
            break
        else:
            print(line.decode('utf-8'))
            continue
    # time.sleep(0.2)
    return line.decode('utf-8')

if __name__ == '__main__':
    ser = serial.Serial('/dev/cu.usbmodem14201', 9600, timeout=0.5)
    if ser.isOpen():
        print("open success")
    else:
        print("open failed")

    while True:
        data = recv()
        # print("test:")
        # print(data)
        temperature = int(float(data[:-2]))
        print(temperature)
        url = 'http://%s:49986/api/v1/resource/Temp_sensor/temperature' % edgexip
        payload = temperature
        headers = {'content-type': 'application/json'}
        response = requests.post(url, data=json.dumps(payload), headers=headers, verify=False)
