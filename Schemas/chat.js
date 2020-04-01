var mongoose = require('mongoose');

mongoose.connect('mongodb+srv://Access:ilovelukeyeagley@cluster0-elsk0.mongodb.net/test?retryWrites=true&w=majority')

var Schema = mongoose.Schema; 

var chatSchema = new Schema({
    id:  {type: Number, required: true, unique: true},
    initiatorId: {type: Number, required: true, unique: false},
    receiverId:  {type: Number, required: true, unique: false},
    item: {type: String, required: true, unique: false},
    lastActive: {type: Date, required: true, unique: false, default: Date.now},
    messages: [{type: Number, required: false, unique: false}]
})

module.exports = mongoose.model('chats', chatSchema); 