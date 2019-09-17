var mysql = require('mysql');
var database = {};
database.pool = mysql.createPool({
    connectionLimit:10,
    host:'rros.cnt3bjp8k3if.ap-northeast-2.rds.amazonaws.com',
    user:'root',
    password:'12345678',
    database:'rrosdb',
    debug:false
});

module.exports = database;