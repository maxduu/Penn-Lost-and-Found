var mongoose = require('mongoose');

mongoose.connect('mongodb+srv://Access:ilovelukeyeagley@cluster0-elsk0.mongodb.net/test?retryWrites=true&w=majority')

var Schema = mongoose.Schema; 

var warningSchema = new Schema({
    userId: {type: Number, required: true, unique: false},
    seen: {type: Boolean, required: true, unique: false, default: true},
    message: {type: String, required: true, unique: false}, 
})

module.exports = mongoose.model('warning', warningSchema); 