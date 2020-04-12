var mongoose = require('mongoose');

mongoose.connect('mongodb+srv://Access:ilovelukeyeagley@cluster0-elsk0.mongodb.net/test?retryWrites=true&w=majority');

var Schema = mongoose.Schema;

var reportSchema = new Schema({
    reporterId: {type: Number, required: true, unique: false},
    violatorId: {type: Number, required: true, unique: false},
    category: {type: String, required: true, unique: false},
    message: {type: String, required: true, unique: false}
})

module.exports = mongoose.model('report', reportSchema);