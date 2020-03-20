
var express = require('express');
var bodyParser = require('body-parser');

var app = express();
app.use(bodyParser.json());

var lost_item = require('./lost_item.js');

app.listen(3000, () => {
    console.log('Listening on port 3000');
});

// create a lost item
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
            console.log('success');
        }
    })

});

// get all lost items
app.use('/all-lost-items', (req, res) => {
    lost_item.find ( (err, allItems) => {
        if (err) {
            res.json({'status' : err});
        }
        else if (allItems.length == 0) {
            res.json({'status' : 'no people'});
        }
        else {
            res.json({'status' : 'success', 'people' : allPeoople});
        }
    });
});

// get specific lost item
app.use('/get-lost-item', (req, res) => {
    var searchId = req.query.id;
    res.send('finish implementation');
});