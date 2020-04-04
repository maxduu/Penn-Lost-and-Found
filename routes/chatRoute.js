const express = require('express');
const Chat = require('../Schemas/chat.js'); 
const router = express.Router(); 


//Create new chat
router.use('/post', async (req, res) => {
    console.log("in chat/ path for posting");
    const newChat = new Chat({
        id: parseInt(req.query.id), 
        initiatorId: parseInt(req.query.initiatorId), 
        receiverId: parseInt(req.query.receiverId), 
        item: req.query.item, 
        lastActive: Date.parse(req.query.lastActive), 
        messages: req.query.messages
    }); 
    try {
        const savedChat = await newChat.save()
    } catch (err) {
        res.json({ 'message': err }); 
    } 
}); 

//Update a specific chat's info
router.use('/update', (req, res) => {
    console.log("in chat/update path");
    var updateId = parseInt(req.query.id);
    console.log("updateId is " + updateId); 
    Chat.findOne( { id: updateId }, (err, chat) => {
        console.log('inside findOne');
        if (err) {
            console.log(err); 
            res.json({ 'status': err}); 
        } else if (!chat) {
            console.log("no such chat"); 
            res.json({ 'status': 'no such chat'}); 
        } else {
            //updates messages if in query
            console.log("found chat " + req.query.id); 
            if (req.query.message != null) {
                var messageId = parseInt(req.query.message); 
                console.log("found message " + messageId); 
                if (messageId) {
                    chat.messages.push(messageId);    
                } else {
                    res.json({ message: 'Invalid messageId'})
                } 
            }
            //updates date if in query
            if (req.query.lastActive != null) {
                var newDate = Date.parse(req.query.lastActive);
                if (newDate) {
                    chat.lastActive = newDate;
                    console.log("found lastActive " +newDate); 
                } else {
                    res.json({ message: 'Invalid date'})
                }
            }
            chat.save( (err) => {
                if (err) {
                    res.json({ 'status': err}); 
                } else {
                    console.log("saved chat");
                    res.json({ 'status' : 'success' }); 
                }
            })
        }
    })
});

//Get back all chats
router.use('/get-all', (req, res) => {
    console.log("in chat/ path to get all chats")
    Chat.find( (err, allChats) => {
        if (err) {
            res.json({ status: err }); 
        } else if (allChats.length == 0) {
            res.json({ status: 'no chats' }); 
        } else {
            res.json({ 'status' : 'success', 
                        'chats': allChats }); 
            console.log('successfully got all chats: '); 
        }
    })
});

module.exports = router 