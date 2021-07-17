import os
import face_recognition
import requests
import json
import cv2
import time

edgexip = '127.0.0.1'
images = os.listdir('images')
while True:
    image_to_be_matched = face_recognition.load_image_file('peace.jpg')
    tmp = face_recognition.face_encodings(image_to_be_matched)
    if len(tmp) == 0:
        print("not found face")
        time.sleep(2)
        continue
    print("Found face")
    image_to_be_matched_encoded = tmp[0]
    res = "include: "
    for image in images:
        current_image = face_recognition.load_image_file("images/" + image)
        # print(face_recognition.face_encodings(current_image))
        current_image_encoded = face_recognition.face_encodings(current_image)[0]
        result = face_recognition.compare_faces([image_to_be_matched_encoded], current_image_encoded)
    if result[0] == True:
        res = res + image.split('.')[0] + " "
        print(res)
        url = 'http://%s:49986/api/v1/resource/Photo_catcher/photo' % edgexip
        payload = res
        headers = {'content-type': 'application/json'}
        response = requests.post(url, data=json.dumps(payload), headers=headers, verify=False)

