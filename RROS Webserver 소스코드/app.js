var fs = require('fs');
var express = require('express');
var http = require('http');
var static = require('serve-static');
var path = require('path');
var bodyParser = require('body-parser');
var cookieParser = require('cookie-parser');
var expressSession = require('express-session');

var expressErrorHandler = require('express-error-handler');
var database = require('./database');
var pool = database.pool;
var config = require('./config');
var user = require('./routes/user')
var app = express();
var route_loader = require('./routes/route_loader');



app.set('port', config.server_port);


app.set('views', __dirname + '/views');
app.set('view engine', 'ejs');
console.log('뷰 엔진 설정됨.');

app.use(static(path.join(__dirname, 'public')));


app.use(bodyParser.urlencoded({
    extended: false
}));
app.use(bodyParser.json());

app.use(cookieParser());
app.use(expressSession({
    secret: 'key',
    resave: true,
    saveUninitailized: true
}));





route_loader.init(app);



var errorHandler = expressErrorHandler({
    static: {
        '404': './public/404.html'
    }
});

app.use(expressErrorHandler.httpError(404));
app.use(errorHandler);

var server = http.createServer(app).listen(app.get('port'), function () {
    console.log('익스프레스로 웹 서버 생성 : ' + app.get('port'));


});



module.exports = app;
