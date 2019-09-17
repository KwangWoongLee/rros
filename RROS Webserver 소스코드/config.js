var route_loader = require('./routes/route_loader');
var app = require('./app');

var config={};
config.server_port = process.env.PORT || 3000;
config.jsonrpc_api_path = '/api';
config.route_info = [
    {file:'./routes/user', path:'/process/login',method:'login',type:'post'},
    {file:'./routes/user', path:'/process/adduser',method:'adduser',type:'post'},
    {file:'./routes/user', path:'/process/search',method:'search',type:'get'}
];


module.exports = config;