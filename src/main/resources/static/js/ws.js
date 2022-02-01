'use strict';

var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#text');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');
var roomId = document.location.pathname.substr(document.location.pathname.lastIndexOf("message") + 8);

var stompClient = null;
var username = null;

function onConnected() {
    stompClient.subscribe('/topic/public', onMessageReceived);
    connectingElement.classList.add('hidden');
}

function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

function send(event) {
    if (!stompClient) {
        username = document.querySelector('#name').textContent.trim();

        if (username) {
            var socket = new SockJS('/chat-websocket');
            stompClient = Stomp.over(socket);

            stompClient.connect({}, onConnected(), onError());
        }
        event.preventDefault();
    } else {
        var messageContent = messageInput.value.trim();
        var current_datetime = new Date();

        if (messageContent && stompClient) {
            var chatMessage = {
                //todo DTO fields. Change accordingly.
                author: username,
                text: messageInput.value,
                room: roomId,
                creationDate: current_datetime.getFullYear() + "-" + ("0" + (current_datetime.getMonth() + 1)).slice(-2) +
                    "-" + ("0" + current_datetime.getDate()).slice(-2) + " " + ("0" + current_datetime.getHours()).slice(-2) +
                    ":" + ("0" + current_datetime.getMinutes()).slice(-2) + ":" + ("0" + current_datetime.getSeconds())
            }
            //todo add here your mapping. Look for proper parameter names
            stompClient.send("rest/rooms/" + roomId + "/messages", {}, JSON.stringify(chatMessage));

            messageInput = '';
        }
        event.preventDefault();
    }
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    //todo DTO fields. Change accordingly. '0' is my bot room number
    if ((message.room === roomId && message.room !== '0') || (message.author === username && message.room === roomId)) {
        var messageElement = document.createElement('li');

        messageElement.classList.add('chat-message');
        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.author + ' (' + message.creationDate + ')');
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);

        var textElement = document.createElement('p');
        textElement.innerHTML = message.text;

        messageElement.appendChild(textElement);

        messageArea.appendChild(messageElement);
    }
}

messageForm.addEventListener('submit', send, true);
document.querySelector('#send').click();