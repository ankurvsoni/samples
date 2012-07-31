var fs = require('fs')
    , http = require('http')
    , socketio = require('socket.io')
    , connect = require('connect');

console.log("===Stock Ticker Application===");

var server = connect.createServer(
                connect.static(__dirname)
             ).listen(8080, function() {
                console.log('Listening at: http://localhost:8080');
             });

var io = socketio.listen(server);

io.sockets.on('connection', function (socket) {
    socket.on('message', function (ticker) {
        console.log('Message Received: ', ticker);
        setInterval(getStockQuote, 5000, ticker, socket);
    });
});

function getStockQuote(ticker, socket) {
    var options = {
        host: 'finance.google.com',
        port: 80,
        path: '/finance/info?client=ig&q=' + ticker,
        headers: {
            Host: "www.google.com"
        }
    };

    http.get(options, function(res) {
      var data = '';
      res.on('data', function (chunk) {
            data += chunk;
      });
      res.on('end',function(){
        var obj = JSON.parse(data.substring(3));
        var lastPrice =  { "ticker" : ticker , "last" : obj[0].l };
        socket.emit('message', lastPrice);
      });
    }).on('error', function(e) {
      console.log("Got error: " + e.message);
    });
  }
