import requests
import os
import json

# sendUser = {"password":"999999","last_login":"2020/03/12 14:33:12","lost_items":[17, 18],"id":7,"found_items":[20, 21],"username":"test2@seas.upenn.edu","status":2}

# sendLostItem = {"id": 2, "posterId": 2, "category": "water bottle", "date": "2020/03/12 14:33:12", "latitude": 38.5, "longitude": 46.3, "around": "Skirkanich", "attachmentLoc": "bottle.img", "description": "black metal", "additionalInfo": "engineering sticker, dent on side"}

# res = requests.post('http://localhost:3000/create-user', json = sendUser)

# pic = open('TestImages/waterbottle.jpg', 'rb')
# res = requests.post('http://localhost:3000/upload', files = {'file': pic})
# print(str(res.file))

# url = 'http://localhost:3000/upload'
# path_img = 'TestImages/waterbottle.jpg'
# with open(path_img, 'rb') as img:
#   name_img= os.path.basename(path_img)
#   files= {'file': (name_img,img,'image/jpeg') }
#   r = requests.post(url,files=files)
#   print(r.json())

res = requests.get('http://localhost:3000/image/d21d6cc9dd815da8b84742d06a1e4b23.jpg')
# print(res.json())