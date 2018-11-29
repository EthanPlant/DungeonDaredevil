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
                players[i].x = data.x;
                players[i].y = data.y;
                players[i].dir = data.dir;
                players[i].state = data.state;
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
    players.push(new Player(socket.id, 0, 0, 0, 'STANDING'));
});

function Player(id, x, y, dir, state) {
    this.id = id;
    this.dir = dir;
    this.x = x;
    this.y = y;
    this.state = state
}