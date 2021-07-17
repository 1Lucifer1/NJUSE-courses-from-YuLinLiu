import logging
import json
import os
from flask import Flask, render_template, redirect, request, url_for, make_response, jsonify
from flask_restful import Resource, Api, reqparse
import serial
import time
import re
import sys

app = Flask(__name__)
judge = "low"


# @app.route('/')
# def index():
#     content = make_response(render_template('index.html'))
#     return content


# @app.route('/_ajaxAutoRefresh', methods= ['GET'])
# def stuff():
#     return jsonify(color=color)


@app.route('/api/v1/device/register',methods=['POST'])
def register():
    request.get_json(force=True)

    parser = reqparse.RequestParser()
    parser.add_argument('id', required=True)
    args = parser.parse_args()

    id = args['id']

    print("registering device: ", id)

    returnData = "Device registered"

    return returnData, 201


@app.route('/api/v1/device/<id>/judgeTemp',methods=['PUT'])
def judgeTemp(id):
    global judge
    request.get_json(force=True)

    parser = reqparse.RequestParser()
    parser.add_argument('judge', required=True)
    args = parser.parse_args()

    judge = (args['judge'])

    print("requesting device: ", id)

    returnData = "Command accepted"

    if judge == 'high':
        ser.write(b'high')
    elif judge == 'low':
        ser.write(b'low')
    return returnData, 201


if __name__ == "__main__":
    ser = serial.Serial('/dev/cu.usbmodem14201', 9600, timeout=0.5)
    if ser.isOpen():
        print("open success")
    else:
        print("open failed")
    app.run(    debug=False, \
                host='127.0.0.1', \
                port=int(os.getenv('PORT', '5000')), threaded=True)
