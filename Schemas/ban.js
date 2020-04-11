var mongoose = require('mongoose');

mongoose.connect('mongodb+srv://Access:ilovelukeyeagley@cluster0-elsk0.mongodb.net/test?retryWrites=true&w=majority')

var Schema = mongoose.Schema; 

var banSchema = new Schema({
    userId: {type: Number, required: true, unique: true},
    until: {type: Date, required: true, unique: false, default: Date.now},
    message: {type: String, required: true, unique: false}, 
})

module.exports = mongoose.model('ban', banSchema); 