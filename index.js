
var express = require('express');
var bodyParser = require('body-parser');

var app = express();
app.use(bodyParser.json());

var lost_item = require('./lost_item.js');
var found_item = require('./found_item.js');
var user = require('./user.js');

app.listen(3000, () => {
    console.log('Listening on port 3000');
});

// POST to create a new user, ex: 'http://localhost:3000/create-user' and post request
// with body being user object json
app.post('/create-user', (req, res) => {
    var newUser = new user ({
    	id: parseInt(req.body.id),
    	username: req.body.username,
    	password: req.body.password,
    	last_login: Date.parse(req.body.last_login),
    	lost_items: req.body.lost_items,
    	found_items: req.body.found_items,
    	status: parseInt(req.body.status)
    });

    newUser.save( (err) => {
    	if (err) {
    		res.json({'status' : err})
    		console.log(err)
    	}
    	else {
    		res.json({'status': 'success'});
    		console.log('successfully created new user')
    	}
    })

});

// GET all users, ex: 'http://localhost:3000/all-users'
app.use('/all-users', (req, res) => {
    user.find ( (err, allItems) => {
        if (err) {
            res.json({'status' : err});
        }
        else if (allItems.length == 0) {
            res.json({'status' : 'no users'});
        }
        else {
            res.json({'status' : 'success', 'items' : allItems});
            console.log('successfully gotten all users');
        }
    });
});

// GET specific user, ex: 'http://localhost:3000/get-user?id=1'
app.use('/get-user', (req, res) => {
    var searchId = parseInt(req.query.id);
    user.findOne({id: searchId}, (err, item) => {
        if (err) {
            res.json({'status': err});
        }
        else if (!item) {
            res.json({'status': 'no item'});
        }
        else {
            res.json({'status': 'success', 'user': item});
            console.log('successfully gotten user');
        }
    })
});

// POST an update to db, ex: address is 'http://localhost:3000/update-user' and 
// req body contains new object's json
app.post('/update-user', (req, res) => {
    var updateId = parseInt(req.body.id);
    user.findOne({id: updateId}, (err, item) => {
        if (err) {
            res.json({'status': err});
        }
        else if (!item) {
            res.json({'status': 'no item'});
        }
        else {
    		item.username = req.body.username,
    		item.password = req.body.password,
    		item.last_login = Date.parse(req.body.last_login),
    		item.lost_items = req.body.lost_items,
    		item.found_items = req.body.found_items,
    		item.status = parseInt(req.body.status)
            item.save((err) => {
                if (err) {
                    res.json({'status': err});
                }
                else {
                    res.json({'status': 'success'});
                    console.log('successfully updated user');
                }
            })
        }
    })
});

// POST to create a lost item, ex: 'http://localhost:3000/create-lost-item' and post request
// with body being lost-item object json
app.post('/create-lost-item', (req, res) => {
    var newLostItem = new lost_item ({
        id: parseInt(req.body.id),
        posterId: parseInt(req.body.posterId),
        category: req.body.category,
        date: Date.parse(req.body.date),
        latitude: parseFloat(req.body.latitude),
        longitude: parseFloat(req.body.longitude),
        around: req.body.around,
        description: req.body.description,
        attachmentLoc: req.body.attachmentLoc,
        additionalInfo: req.body.additionalInfo
    });

    newLostItem.save( (err) => {
        if (err) {
            res.json({'status' : err});
            console.log(err)
        } 
        else {
            res.json({'status' : 'success'});
            console.log('successfully posted lost item');
        }
    })

});

// GET all lost items, ex: 'http://localhost:3000/all-lost-items'
app.use('/all-lost-items', (req, res) => {
    lost_item.find ( (err, allItems) => {
        if (err) {
            res.json({'status' : err});
        }
        else if (allItems.length == 0) {
            res.json({'status' : 'no items'});
        }
        else {
            res.json({'status' : 'success', 'items' : allItems});
            console.log('successfully gotten all items');
        }
    });
});

// GET specific lost item, ex: 'http://localhost:3000/get-lost-item?id=1'
app.use('/get-lost-item', (req, res) => {
    var searchId = parseInt(req.query.id);
    lost_item.findOne({id: searchId}, (err, item) => {
        if (err) {
            res.json({'status': err});
        }
        else if (!item) {
            res.json({'status': 'no item'});
        }
        else {
            res.json({'status': 'success', 'lost-item': item});
            console.log('successfully gotten lost item');
        }
    })
});

// POST an update to db, ex: address is 'http://localhost:3000/update-lost-item' and 
// req body contains new object's json
app.post('/update-lost-item', (req, res) => {
    var updateId = parseInt(req.body.id);
    lost_item.findOne({id: updateId}, (err, item) => {
        if (err) {
            res.json({'status': err});
        }
        else if (!item) {
            res.json({'status': 'no item'});
        }
        else {
            item.posterId = parseInt(req.body.posterId);
            item.category = req.body.category;
            item.date = Date.parse(req.body.date);
            item.latitude = parseFloat(req.body.latitude);
            item.longitude = parseFloat(req.body.longitude);
            item.around = req.body.around;
            item.description = req.body.description;
            item.attachmentLoc = req.body.attachmentLoc;
            item.additionalInfo = req.body.additionalInfo;
            item.save((err) => {
                if (err) {
                    res.json({'status': err});
                }
                else {
                    res.json({'status': 'success'});
                    console.log('successfully updated lost item');
                }
            })
        }
    })
});

