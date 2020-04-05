const express = require('express');
const Message = require('../Schemas/message.js'); 
const router = express.Router(); 


//Create new message
router.use('/post', async (req, res) => {
    console.log("inside message/post")
    const newMessage = new Message({
        id: parseInt(req.query.id), 
        senderId: parseInt(req.query.senderId), 
        receiverId: parseInt(req.query.receiverId), 
        time: Date.parse(req.query.time), 
        text: req.query.text, 
        chatId: parseInt(req.query.id)
    }); 

    try {
        const savedMessage = await newMessage.save();
        console.log('successfully saved new message ' + savedMessage.text); 
        res.json({'status': 'success'});
    } catch (err) {
        res.json({ 'message': err }); 
    } 
}); 


//Get back all messages 
router.use('/get-all', (req, res) => {
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