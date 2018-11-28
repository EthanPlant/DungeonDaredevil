const app = require('express');
const server = require('http').Server(app);
const io = require('socket.io')(server);
let players = [];

server.listen(8080, () => {
    console.log('Server is now running');
});

io.on('connection', (socket) => {
    console.log('Player connected');
    socket.emit('socketID', {id: socket.id});
    socket.emit('getPlayers', players);
    socket.broadcast.emit('newPlayer', {id: socket.id});
    socket.on('playerMoved', (data) => {
        data.id = socket.id;
        socket.broadcast.emit('playerMoved', data);

        for (let i = 0; i < players; i++) {
            if (players[i].id == data.id) {
                players[i].x = x;
                players[i].y = y;
            }
        }
    });
    socket.on('disconnect', () => {
        console.log('Player disconected');
        socket.broadcast.emit('playerDisconnected', {id: socket.id});
        for (let i = 0; i < players.length; i++) {
            if (players[i] == socket.id) {
                players.splice(i, 1);
            }
        }
    });
    players.push(new Player(socket.id, 0, 0));
});

function Player(id, x, y) {
    this.id = id;
    this.x = x;
    this.y = y;
}