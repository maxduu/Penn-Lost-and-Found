const express = require('express');
const Message = require('../Schemas/message.js'); 
const router = express.Router(); 


//Create new message
router.post('/', async (req, res) => {
    const newMessage = new Message({
        id: parseInt(req.body.id), 
        senderId: parseInt(req.body.senderId), 
        receiverId: parseInt(req.body.receiverId), 
        time: Date.parse(req.body.time), 
        text: req.body.text, 
        chatId: parseInt(req.body.id)
    }); 

    try {
        const savedMessage = await newMessage.save();
        console.log('successfully saved new message ' + req.body.id); 
    } catch (err) {
        res.json({ 'message': err }); 
    } 
}); 

//Get back a specific message
router.get('/:id', async (req, res) => {
    const tarMessgae = await Message.findOne( { id: req.params.id }, (err, message) => {
        if (err) {
            res.json({ 'status' : err }); 
        } else if (!message) {
            res.json({ 'status' : 'no chat' }); 
        } else {
            res.json({ 'status' : 'success', 
                        'message' : message }); 
        }
    }); 
    try {
        res.json(tarMessage); 
    } catch (err) {
        res.json({ 'error': err}); 
    }
    
}); 

//Get back all messages 
router.get('/', (req, res) => {
    Message.find( (err, allMessages) => {
        if (err) {
            res.json({ status: err }); 
        } else if (allMessages.length == 0) {
            res.json({ status: 'no messages' }); 
        } else {
            res.json({ 'status' : 'success', 
                        'messages': allMessages }); 
        }
    })
});

module.exports = router