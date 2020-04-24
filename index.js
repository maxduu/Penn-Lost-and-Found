
var express = require('express');
var bodyParser = require('body-parser');
var cors = require('cors');
var path = require('path');
var crypto = require('crypto');
var multer = require('multer');
var GridFsStorage = require('multer-gridfs-storage');
var Grid = require('gridfs-stream');
var mongoose = require('mongoose');


var app = express();

app.use(bodyParser.json());
app.use(cors());

var lost_item = require('./Schemas/lost_item');
var found_item = require('./Schemas/found_item');
var user = require('./Schemas/user');
var admin = require('./Schemas/admin');
var ban = require('./Schemas/ban');
var warning = require('./Schemas/warning');
var report = require('./Schemas/report');
const Chat = require('./Schemas/chat'); 
const Message = require('./Schemas/message'); 

app.listen(3000, () => {
    console.log('Listening on port 3000');
});

//Import Routes

const chatRoute = require('./routes/chatRoute')
const messageRoute = require('./routes/messageRoute')

//Use middleware chat and messaging routes

app.use('/chat', chatRoute); 
app.use('/message', messageRoute); 

const mongoURI = 'mongodb+srv://Access:ilovelukeyeagley@cluster0-elsk0.mongodb.net/test?retryWrites=true&w=majority';

const conn = mongoose.createConnection(mongoURI);

let gfs;

conn.once('open', () => {
    gfs = Grid(conn.db, mongoose.mongo);
    gfs.collection('uploads');
})

const storage = new GridFsStorage({
    url: mongoURI,
    file: (req, file) => {
      return new Promise((resolve, reject) => {
        crypto.randomBytes(16, (err, buf) => {
          if (err) {
            return reject(err);
          }
          const filename = buf.toString('hex') + path.extname(file.originalname);
          const fileInfo = {
            filename: filename,
            bucketName: 'uploads'
          };
          resolve(fileInfo);
        });
      });
    }
  });
  const upload = multer({ storage });

// filename important to link to item!
app.post('/upload', upload.single('file'), (req, res) => {
    res.json({'id': req.file.id});
});

// GET route to create a new user, ex: 'http://localhost:3000/create-user...' and get request
// query has parameters that are the user object json attributes
app.use('/create-user', (req, res) => {
	var lost_items_array= JSON.parse(req.query.lost_items)
	var found_items_array= JSON.parse(req.query.found_items)
	console.log(lost_items_array)
    var newUser = new user ({
    	id: parseInt(req.query.id),
    	username: req.query.username,
    	password: req.query.password,
    	last_login: Date.parse(req.query.last_login),
    	lost_items: lost_items_array,
    	found_items: found_items_array,
    	status: parseInt(req.query.status)
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

// GET route to update the db, ex: address is 'http://localhost:3000/update-user' and 
// query params contains new object's json attributes
app.use('/update-user', (req, res) => {
    var updateId = parseInt(req.query.id);
    user.findOne({id: updateId}, (err, item) => {
        if (err) {
            res.json({'status': err});
        }
        else if (!item) {
            res.json({'status': 'no item'});
        }
        else {
        	if (req.query.username) {
        		item.username = req.query.username;
        	}
        	if (req.query.password) {
        		item.password = req.query.password;
        	}
        	if (req.query.last_login) {
        		item.last_login = Date.parse(req.query.last_login);
        	}
        	if (req.query.lost_items) {
        		var lost_items_array= JSON.parse(req.query.lost_items);
        		item.lost_items = lost_items_array;
        	}
        	if (req.query.found_items) {
        		var found_items_array= JSON.parse(req.query.found_items);
        		item.found_items = found_items_array;
        	}
        	if (req.query.status) {
        		item.status = parseInt(req.query.status)
        	}
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

// GET to create a lost item, ex: 'http://localhost:3000/create-lost-item' and get request
// has query params being lost-item object json attributes
app.use('/create-lost-item', (req, res) => {
    var newLostItem = new lost_item ({
        id: parseInt(req.query.id),
        posterId: parseInt(req.query.posterId),
        category: req.query.category,
        date: Date.parse(req.query.date),
        latitude: parseFloat(req.query.latitude),
        longitude: parseFloat(req.query.longitude),
        around: req.query.around,
        description: req.query.description,
        attachmentLoc: req.query.attachmentLoc,
        additionalInfo: req.query.additionalInfo
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
            console.log('successfully gotten all lost items');
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

app.use('/get-poster-lost-items', (req, res) => {
    var searchId = parseInt(req.query.id);
    lost_item.find({posterId: searchId}, (err, items) => {
        if (err) {
            res.json({'status': err});
        }
        else if (!items) {
            res.json({'status': 'no item'});
        }
        else {
            res.json({'status': 'success', 'lost_items': items});
            console.log('successfully gotten lost items');
        }
    })
});

// GET route to update the db, ex: address is 'http://localhost:3000/update-lost-item' and 
// query params contains new object's json attributes
app.use('/update-lost-item', (req, res) => {
    var updateId = parseInt(req.query.id);
    lost_item.findOne({id: updateId}, (err, item) => {
        if (err) {
            res.json({'status': err});
        }
        else if (!item) {
            res.json({'status': 'no item'});
        }
        else {
        	if (req.query.posterId) {
        		item.posterId = parseInt(req.query.posterId);
        	}
        	if (req.query.category) {
        		item.category = req.query.category;
        	}
        	if (req.query.date) {
        		item.date = Date.parse(req.query.date);
        	}
            if (req.query.latitude) {
            	item.latitude = parseFloat(req.query.latitude);
            }
            if (req.query.longitude) {
            	item.longitude = parseFloat(req.query.longitude);
            }
            if (req.query.around) {
            	item.around = req.query.around;
            }
            if (req.query.description) {
            	item.description = req.query.description;
            }
            if (req.query.attachmentLoc) {
            	item.attachmentLoc = req.query.attachmentLoc;
            }
            if (req.query.additionalInfo) {
            	item.additionalInfo = req.query.additionalInfo;
            }
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

// GET to remove a lost item
app.use('/remove-lost-item', async (req, res) => {
	id = parseInt(req.query.id);
	const result = await lost_item.deleteOne({"id" : id}).exec();
	if (result.deletedCount == 0) {
		console.log('no lost item with id exists');
		res.json({'status': 'not an item'});
	} else {
		console.log('successfully removed lost item with id ' + id);
		res.json({'status' : 'success'});
	}
});

// GET to create a found item, ex: 'http://localhost:3000/create-found-item' and get request
// with query params being found-item object json attributes
app.use('/create-found-item', (req, res) => {
    var newFoundItem = new found_item ({
        id: parseInt(req.query.id),
        posterId: parseInt(req.query.posterId),
        category: req.query.category,
        date: Date.parse(req.query.date),
        latitude: parseFloat(req.query.latitude),
        longitude: parseFloat(req.query.longitude),
        around: req.query.around,
    });

    newFoundItem.save( (err) => {
        if (err) {
            res.json({'status' : err});
            console.log(err)
        } 
        else {
            res.json({'status' : 'success'});
            console.log('successfully posted found item');
        }
    })

});

// GET all found items, ex: 'http://localhost:3000/all-found-items'
app.use('/all-found-items', (req, res) => {
    found_item.find ( (err, allItems) => {
        if (err) {
            res.json({'status' : err});
        }
        else if (allItems.length == 0) {
            res.json({'status' : 'no items'});
        }
        else {
            res.json({'status' : 'success', 'items' : allItems});
            console.log('successfully gotten all found items');
        }
    });
});

// GET specific found item, ex: 'http://localhost:3000/get-found-item?id=1'
app.use('/get-found-item', (req, res) => {
    var searchId = parseInt(req.query.id);
    found_item.findOne({id: searchId}, (err, item) => {
        if (err) {
            res.json({'status': err});
        }
        else if (!item) {
            res.json({'status': 'no item'});
        }
        else {
            res.json({'status': 'success', 'found-item': item});
            console.log('successfully gotten found item');
        }
    })
});

app.use('/get-poster-found-items', (req, res) => {
    var searchId = parseInt(req.query.id);
    found_item.find({posterId: searchId}, (err, items) => {
        if (err) {
            res.json({'status': err});
        }
        else if (!items) {
            res.json({'status': 'no item'});
        }
        else {
            res.json({'status': 'success', 'found_items': items});
            console.log('successfully gotten found items');
        }
    })
});

// GET to update to db, ex: address is 'http://localhost:3000/update-lost-item' and 
// query params contains new object's json attributes
app.use('/update-found-item', (req, res) => {
    var updateId = parseInt(req.query.id);
    found_item.findOne({id: updateId}, (err, item) => {
        if (err) {
            res.json({'status': err});
        }
        else if (!item) {
            res.json({'status': 'no item'});
        }
        else {
        	if (req.query.posterId) {
        		item.posterId = parseInt(req.query.posterId);
        	}
        	if (req.query.category) {
        		item.category = req.query.category;
        	}
        	if (req.query.date) {
        		item.date = Date.parse(req.query.date);
        	}
            if (req.query.latitude) {
            	item.latitude = parseFloat(req.query.latitude);
            }
            if (req.query.longitude) {
            	item.longitude = parseFloat(req.query.longitude);
            }
            if (req.query.around) {
            	item.around = req.query.around;
            }
            item.save((err) => {
                if (err) {
                    res.json({'status': err});
                }
                else {
                    res.json({'status': 'success'});
                    console.log('successfully updated found item');
                }
            })
        }
    })
});

// GET to remove a found item
app.use('/remove-found-item', async (req, res) => {
	id = parseInt(req.query.id);
	const result = await found_item.deleteOne({"id" : id}).exec()
	if (result.deletedCount == 0) {
		console.log(id);
		console.log('no found item with id exists');
		res.json({'status': 'not an item'});
	} else {
		console.log('successfully removed found item with id ' + id);
		res.json({'status' : 'success'});
	}
});

// GET specific admin user
app.use('/get-admin', (req, res) => {
    var searchUsername = req.query.username;
    var reqPassword = req.query.password;
    console.log(searchUsername);
    admin.findOne({username: searchUsername}, (err, item) => {
        if (err) {
            res.json({'status': err});
        }
        else if (!item) {
            res.json({'status': 'no admin'});
        }
        else {
        	console.log('successfully gotten admin');
        	if (reqPassword.localeCompare(item.password) == 0) {
        		console.log('admin pass correct');
        		res.json({'status': 'success'});
        	} else {
        		console.log('admin pass incorrect');
        		res.json({'status': 'incorrect password'})
        	}
        }
    })
});



// GET to create a warning 
// ex: 'http://localhost:3000/warn?userId=2&message=hey' 
app.use('/warn', (req, res) => {
    var newWarning = new warning ({
        userId: parseInt(req.query.userId),
        seen: false,
        message: req.query.message,
    });

    newWarning.save( (err) => {
        if (err) {
            res.json({'status' : err});
            console.log(err)
        } 
        else {
            res.json({'status' : 'success'});
            console.log('successfully created warning');
        }
    })
});

// GET specific warnings for the user
app.use('/get-warnings', (req, res) => {
    var id = parseInt(req.query.userId);
    warning.find({userId: id}, (err, item) => {
        if (err) {
            res.json({'status': err});
            console.log(err);
        }
        else if (item.length == 0) {
            res.json({'status': 'no warnings'});
            console.log('no warnings found');
        }
        else {
            res.json({'status': 'success', 'warnings': item});
            console.log('successfully gotten warnings');
        }
    })
});

// GET to update warnings for a user to be seen
app.use('/see-warnings', (req, res) => {
    var id = parseInt(req.query.userId);
    warning.find({userId : id}, (err, allItems) => {
        if (err) {
            res.json({'status' : err});
        }
        else if (allItems.length == 0) {
            res.json({'status' : 'no warnings'});
        }
        else {
        	allItems.forEach(function (w) {
        		w.seen = true;
        		w.save((err) => {
	                if (err) {
	                    console.log(err);
	                } else {
	                    console.log('successfully updated warning');
	                }
	            });
        	});
            res.json({'status' : 'success', 'warnings' : allItems});
            console.log('successfully seen warnings');
        }
    });
});

// GET all warnings 
app.use('/get-all-warnings', (req, res) => {
    console.log("in /get-all-warnings");
    warning.find( (err, allWarns) => {
        if (err) {
            res.json({ status: err });
        } else if (allWarns.length == 0) {
            res.json({ status: 'no warnings' });
        } else {
            res.json({ 'status' : 'success',
                        'warns': allWarns });
            console.log('successfully got all warnings ');
        }
    })
});

// GET to create a ban
// ex: 'http://localhost:3000/ban' 
app.use('/ban', (req, res) => {
    var newBan = new ban ({
        userId: parseInt(req.query.userId),
        until: Date.parse(req.query.until),
        message: req.query.message,
    });

    newBan.save( (err) => {
        if (err) {
            res.json({'status' : err});
            console.log(err)
        } 
        else {
            res.json({'status' : 'success'});
            console.log('successfully created ban');
        }
    })
});

// GET to unban a user
app.use('/unban', async (req, res) => {
	id = parseInt(req.query.userId);
	const result = await ban.deleteOne({"userId" : id}).exec()
	if (result.deletedCount == 0) {
		console.log('user is not currently banned');
		res.json({'status': 'not currently banned'});
	} else {
		console.log('successfully unbanned user with id ' + id);
		res.json({'status' : 'success'});
	}
});

// GET a specific ban
app.use('/get-ban', (req, res) => {
    var id = parseInt(req.query.userId);
    ban.findOne({userId: id}, (err, item) => {
        if (err) {
            res.json({'status': err});
        }
        else if (!item) {
            res.json({'status': 'no ban'});
        }
        else {
            res.json({'status': 'success', 'ban': item});
            console.log('successfully gotten ban');
        }
    })
});

//GET all bans 
app.use('/get-all-bans', (req, res) => {
    console.log("in /get-all-bans");
    ban.find( (err, allBans) => {
        if (err) {
            res.json({ status: err });
        } else if (allBans.length == 0) {
            res.json({ status: 'no bans' });
        } else {
            res.json({ 'status' : 'success',
                        'bans': allBans });
            console.log('successfully got all bans ');
        }
    })
});

// GET to create a report 
// ex: 'http://localhost:3000/report' and get request
// with query params being report object json attributes 
app.use('/report', (req, res) => {
    var newReport = new report ({
        reporterId: parseInt(req.query.reporterId),
        violatorId: parseInt(req.query.violatorId),
        category: req.query.category,
        message: req.query.message,
    });

    newReport.save( (err) => {
        if (err) {
            res.json({'status' : err});
            console.log(err)
        } 
        else {
            res.json({'status' : 'success'});
            console.log('successfully created report');
        }
    })
});

// GET reports for a specific user
app.use('/get-reports', (req, res) => {
    var id = parseInt(req.query.userId);
    report.find({violatorId : id}, (err, item) => {
        if (err) {
            res.json({'status': err});
            console.log(err);
        }
        else if (item.length == 0) {
            res.json({'status': 'no reports'});
            console.log('no reports found');
        }
        else {
            res.json({'status': 'success', 'reports': item});
            console.log('successfully gotten reports');
        }
    })
});

// GET all reports
app.use('/all-reports', (req, res) => {
    report.find((err, allItems) => {
        if (err) {
            res.json({'status': err});
            console.log(err);
        }
        else if (allItems.length == 0) {
            res.json({'status': 'no reports'});
            console.log('no reports found');
        }
        else {
            res.json({'status': 'success', 'reports': allItems});
            console.log('successfully gotten all reports');
        }
    })
});

// GET to remove a report
app.use('/remove-report', async (req, res) => {
	id = parseInt(req.query.userId);
	const result = await lost_item.deleteOne({"violatorId" : id}).exec();
	if (result.deletedCount == 0) {
		console.log('no report with id exists');
		res.json({'status': 'not a report'});
	} else {
		console.log('successfully removed report with violatorId ' + id);
		res.json({'status' : 'success'});
	}
});

//Get back chats for a user
app.use('/get-user-chats', (req, res) => {
    console.log("in chat/ path to get all chats")
    userId = parseInt(req.query.id);
    Chat.find({$or:[{'initiatorId':userId}, {'receiverId':userId}]}, (err, allChats) => {
        if (err) {
            res.json({ 'status': err });
        } else if (allChats.length == 0) {
            res.json({ 'status': 'no chats' });
        } else {
            res.json({ 'status' : 'success',
                        'chats': allChats });
            console.log('successfully got all chats: ');
        }
    })
});
