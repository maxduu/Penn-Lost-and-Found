import requests
sendUser = {"password":"999999","last_login":"2020/03/12 14:33:12","lost_items":[1, 2],"id":3,"found_items":[3, 4],"username":"lyeag@seas.upenn.edu","status":2}

sendLostItem = {"id": 2, "posterId": 2, "category": "water bottle", "date": "2020/03/12 14:33:12", "latitude": 38.5, "longitude": 46.3, "around": "Skirkanich", "attachmentLoc": "bottle.img", "description": "black metal", "additionalInfo": "engineering sticker, dent on side"}

res = requests.post('http://localhost:3000/create-user', json = sendUser)