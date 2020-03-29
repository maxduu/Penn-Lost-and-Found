const express = require('express');
const Chat = require('../Schemas/chat.js'); 
const router = express.Router(); 


//Create new chat
router.post('/', async (req, res) => {
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

//Get back a specific chat
router.get('/:id', async (req, res) => {
    const chat = await Chat.findOne( { id: req.params.id }, (err, chat) => {
        if (err) {
            res.json({ 'status' : err }); 
        } else if (!chat) {
            res.json({ 'status' : 'no chat' }); 
        } else {
            res.json({ 'status' : 'success', 
                        'chat' : chat }); 
        }
    }); 
    try {
        res.json(chat); 
    } catch (err) {
        res.json({ message: err}); 
    }
    
}); 

//Update a specific chat's info
router.patch('/:id', (req, res) => {
    Chat.findOne({ 'id': req.query.id }), (err, chat) => {
        if (err) {
            res.json({ 'status': err}); 
        } else if (!chat) {
            res.json({ 'status': 'no such chat'}); 
        } else {
            //updates messages if in body
            if (req.body.message != null) {
                var messsageId = parseInt(req.body.message); 
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
                } else {
                    res.json({ message: 'Invalid date'})
                }
            }
            chat.save( (err) => {
                if (err) {
                    res.json({ 'status': err}); 
                } else {
                    res.json({ 'status' : 'success' }); 
                }
            })
        }
    }
})

//Get back all chats
router.get('/', (req, res) => {
    Chat.find( (err, allChats) => {
        if (err) {
            res.json({ status: err }); 
        } else if (allChats.length == 0) {
            res.json({ status: 'no chats' }); 
        } else {
            res.json({ 'status' : 'success', 
                        'chats': allChats }); 
            console.log('successfully got all chats'); 
        }
    })
});

module.exports = router 