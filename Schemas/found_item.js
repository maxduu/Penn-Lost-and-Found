
var mongoose = require('mongoose');

mongoose.connect('mongodb+srv://Access:ilovelukeyeagley@cluster0-elsk0.mongodb.net/test?retryWrites=true&w=majority');

var Schema = mongoose.Schema;

var foundItemSchema = new Schema({
    id: {type: Number, required: true, unique: true},
    posterId: {type: Number, required: true, unique: false},
    category: {type: String, required: true, unique: false},
    date: {type: Date, required: true, unique: false},
    latitude: {type: Number, required: false, unique: false},
    longitude: {type: Number, required: false, unique: false},
    around: {type: String, required: false, unique: false}
})

module.exports = mongoose.model('found_item', foundItemSchema);
