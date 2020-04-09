var mongoose = require('mongoose');

mongoose.connect('mongodb+srv://Access:ilovelukeyeagley@cluster0-elsk0.mongodb.net/test?retryWrites=true&w=majority');

var Schema = mongoose.Schema;

var adminSchema = new Schema({
    username: {type: String, required: true, unique: true},
    password: {type: String, required: true, unique: false},
})

module.exports = mongoose.model('admin', adminSchema);