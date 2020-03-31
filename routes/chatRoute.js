const express = require('express');
const Chat = require('../Schemas/chat.js'); 
const router = express.Router(); 


//Create new chat
router.use('/post', async (req, res) => {
    console.log("in chat/ path for posting");
    const newChat = new Chat({
        id: parseInt(req.body.id), 
        initiatorId: parseInt(req.body.initiatorId), 
        receiverId: parseInt(req.body.receiverId), 
        item: req.body.item, 
        lastActive: Date.parse(req.body.lastActive), 
        messages: req.body.messages
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
    Chat.findOne({ id: updateId }), (err, chat) => {
        console.log('inside findOne');
        if (err) {
            console.log(err); 
            res.json({ 'status': err}); 
        } else if (!chat) {
            console.log("no such chat"); 
            res.json({ 'status': 'no such chat'}); 
        } else {
            //updates messages if in body
            console.log("found chat " + id); 
            if (req.body.message != null) {
                var messsageId = parse(req.body.message); 
                console.log("found message " + messsageId); 
                if (messsageId) {
                    chat.messages.push(messsageId);    
                } else {
                    res.json({ message: 'Invalid messageId'})
                }
            }
            //updates date if in body
            if (req.body.lastActive != null) {
                var newDate = Date.parse(req.body.lastActive);
                if (newDate) {
                    chat.lastActive = newDate;
                    console.log("found lastActive " +newDate.toString); 
                } else {
                    res.json({ message: 'Invalid date'})
                }
            }
            chat.save( (err) => {
                if (err) {
                    res.json({ 'status': err}); 
                } else {
                    console.log("saved chat " + updateId + " with text " + 
                        messsageId + " at time " + newDate.toString);
                    res.json({ 'status' : 'success' }); 
                }
            })
        }
    }
})

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
            console.log(allChats)
        }
    })
});

module.exports = router 