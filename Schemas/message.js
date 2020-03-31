var mongoose = require('mongoose');

mongoose.connect('mongodb+srv://Access:ilovelukeyeagley@cluster0-elsk0.mongodb.net/test?retryWrites=true&w=majority')

var Schema = mongoose.Schema; 

var messageSchema = new Schema({
    id: {type: Number, required: true, unique: true},
    senderId: {type: Number, required: true, unique: false},
    receiverId:  {type: Number, required: true, unique: false},
    time: {type: Date, required: true, unique: false, default: Date.now},
    text: {type: String, required: true, unique: false}, 
    chatId:  {type: Number, required: true, unique: false}
})

module.exports = mongoose.model('messages', messageSchema); 