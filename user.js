
var mongoose = require('mongoose');

mongoose.connect('mongodb+srv://Access:ilovelukeyeagley@cluster0-elsk0.mongodb.net/test?retryWrites=true&w=majority');

var Schema = mongoose.Schema;

var userSchema = new Schema({
    id: {type: Number, required: true, unique: true},
    username: {type: String, required: true, unique: true},
    password: {type: String, required: true, unique: false},
    last_login: {type: Date, required: true, unique: false},
    lost_items: {type: [lostItemSchema], required: true, unique: true},
    found_items: {type: [foundItemSchema], required: true, unique: true},
    status: {type: Number, required: true, unique: false}
})

module.exports = mongoose.model('user', userSchema);
