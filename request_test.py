import requests

send = {"id": 1, "posterId": 14, "category": "TShirt", "date": "2020/04/09 13:17:46", "latitude": 35.5, "longitude": 48.3, "around": "Towne 100", "attachmentLoc": "TShirt.img", "description": "blue shirt", "additionalInfo": "brand new"}

res = requests.post('http://localhost:3000/update-lost-item', json = send)