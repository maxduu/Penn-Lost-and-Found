import requests

send = {"id": 2, "posterId": 14, "category": "water bottle", "date": "2020/03/12 14:33:12", "latitude": 38.5, "longitude": 46.3, "around": "Skirkanich", "attachmentLoc": "bottle.img", "description": "black metal", "additionalInfo": "engineering sticker, dent on side"}

res = requests.post('http://localhost:3000/create-lost-item', json = send)