<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello</title>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@stomp/stompjs@5.0.0/bundles/stomp.umd.js"></script>
    <style>
        .user-block {
            width: 100%; height: 2em; background: red; display: flex
        }
        .tick {
            width: 20%; height: 100%; background: blue; cursor: pointer;
        }
        .user_data {
            width: 80%; background: white; line-height: 2em; padding: 0 4px
        }
    </style>
    <script type="text/javascript">
        window.onunload = disconnect

        let stompClient = null;

        function setConnected(connected) {
            document.getElementById('connect').disabled = connected;
            document.getElementById('disconnect').disabled = !connected;
            document.getElementById('conversationDiv').style.visibility
                = connected ? 'visible' : 'hidden';
            document.getElementById('response').innerHTML = '';
        }

        function connect() {
            stompClient = new StompJs.Client({
                brokerURL: 'ws://localhost:8080/game',
                debug: function (str) {
                    console.log(str);
                }
            });

            stompClient.onConnect = function(frame) {
                // setConnected(true);
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/online', function(messageOutput) {
                    resolveOnlineUserMessage(JSON.parse(messageOutput.body));
                });
                stompClient.subscribe('/topic/lobby/${lobbyId}', function(message) {
                    resolveLobbyUserMessage(JSON.parse(message.body))
                })
                stompClient.onStompError = function (frame) {
                    // Will be invoked in case of error encountered at Broker
                    // Bad login/passcode typically will cause an error
                    // Complaint brokers will set `message` header with a brief message. Body may contain details.
                    // Compliant brokers will terminate the connection after any error
                    console.log('Broker reported error: ' + frame.headers['message']);
                    console.log('Additional details: ' + frame.body);
                };
                stompClient.publish({
                    destination: "/ws/online",
                    body: JSON.stringify({ type: "JOIN", userId: "${user.id}", username: "${user.name}" })
                })
            }
            stompClient.activate();
        }

        function disconnect() {
            if(stompClient != null) {
                stompClient.publish({
                    destination: "/ws/online",
                    body: JSON.stringify({ type: "LEAVE", userId: "${user.id}", username: "${user.name}" })
                })
                stompClient.publish({
                    destination: "/topic/lobby/${lobbyId}",
                    body: JSON.stringify({ type: "LEAVE", userId: "${user.id}", username: "${user.name}" })
                })
                stompClient.deactivate();
            }
            console.log("Disconnected");
        }

        function sendInvite(recepientId) {
            if (stompClient != null) {
                stompClient.publish({
                    destination: "/ws/invite",
                    body: JSON.stringify({ senderName: "${user.name}", recepientId: recepientId, lobbyId: "${lobbyId}"})
                })
            }
        }

        function resolveOnlineUserMessage(messageOutput) {
            switch (messageOutput.type) {
                case "JOIN": addUserOnline(messageOutput); break;
                case "LEAVE": removeUserOnline(messageOutput); break;
            }
        }

        function resolveLobbyUserMessage(messageOutput) {
            switch (messageOutput.type) {
                case "JOIN": addUserToLobby(messageOutput); break;
                case "LEAVE": removeLobbyUser(messageOutput); break;
            }
        }

        function clearEverything() {
            let list = document.querySelector("#online-users")
            for (let el of list) {
                el.remove()
            }
        }

        function addUserToLobby(userData) {
            let list = document.querySelector("#lobby-panel")
            if (!isUserAlreadyInList(userData, list.children, 'lobby-user-')) {
                let block = createLobbyUserBlock(userData)
                list.append(block)
            }
        }

        function addUserOnline(userData) {
            let list = document.querySelector("#online-users")
            if (!isUserAlreadyInList(userData, list.children, 'user-')) {
                let block = createBlock(userData)
                list.append(block)
            }
        }

        function isUserAlreadyInList(userData, list, prefix) {
            for (let element of list) {
                if (element.id === prefix + userData.userId) {
                    return true
                }
            }
            return false
        }

        function removeUserOnline(userData) {
            let block = document.querySelector("#user-" + userData.userId)
            block.remove()
        }

        function removeLobbyUser(userData) {
            let block = document.querySelector("#lobby-user-" + userData.userId)
            block.remove()
        }

        function leaveLobby(lobbyId) {
            if (stompClient != null) {
                fetch("/lobby/leave?lobbyId=" + lobbyId, {
                    method: "POST",
                    redirect: "follow"
                }).then(res => {
                    if (res.redirected) {
                        window.location = res.url
                    }
                })
            }
        }

        function createBlock(userData) {
            let outer = document.createElement("div")
            outer.id = "user-" + userData.userId
            outer.classList.add("user-block")
            let tick = document.createElement("div")
            tick.classList.add("tick")
            tick.onclick = () => sendInvite(userData.userId)
            let inner = document.createElement("div")
            inner.classList.add("user_data")
            inner.innerHTML = userData.username

            outer.append(tick)
            outer.append(inner)

            return outer
        }

        function createLobbyUserBlock(userData) {
            let outer = document.createElement("div")
            outer.id = "lobby-user-" + userData.userId
            outer.classList.add("user-block")
            let name = document.createElement("div")
            name.style.background = "white"
            name.style.lineHeight = "2em"
            name.style.padding = "0 4 px"
            name.style.flexGrow = "7"
            name.innerHTML = userData.username

            let status = document.createElement("div")
            status.style.background = "red"
            status.style.flexGrow = "1"

            outer.append(name)
            outer.append(status)

            return outer
        }


    </script>
</head>
<body onload="connect()">
    <h1>Hello, ${user.name} You're gay!</h1>

    <div style="display: flex; justify-content: center">
        <div style="padding: 0.5em">
            <div id="online-users" style="background: grey; width: 10em; height: 20em; padding: 4px">
                <#list onlineUsers as ou>
                    <div id="user-${ou.id}" class="user-block">
                        <div class="tick" onclick="sendInvite(${ou.id})"></div>
                        <div class="user_data">
                            ${ou.name}
                        </div>
                    </div>
                </#list>
            </div>
        </div>

        <div style="padding: 0.5em; width: 45em">
            <div id="lobby-panel" style="background: grey; width: 100%; height: 20em; padding: 4px;">
                <#list lobbyUsers as lobbyUs>
                    <div id="lobby-user-${lobbyUs.userId}" class="user-block" >
                        <div style="background: white; line-height: 2em; padding: 0 4px; flex-grow: 7">
                            ${lobbyUs.username}
                        </div>
                        <div style="background: ${lobbyUs.ready?then('green', 'red')}; flex-grow: 1"></div>
                    </div>
                </#list>
            </div>
        </div>
    </div>

    <div style="display: flex; justify-content: center">
        <div style="padding: 0.5em">
            <div id="chat" style="background: grey; width: 35em; height: 20em; padding: 4px">

            </div>
        </div>

        <div style="padding: 0.5em; width: 20em">
            <div id="config-panel" style="background: grey; width: 100%; height: 20em; padding: 4px">
                <div class="user-block" style="background: white">
                    <div>
                        <label for="x">Select hero:</label>
                        <select name="x" id="x">
                            <option>A</option>
                            <option>B</option>
                            <option>C</option>
                        </select>
                    </div>
                </div>
                <div class="user-block" style="background: white">
                    <div>
                        <label for="y">Select song:</label>
                        <select name="y" id="y">
                            <option>A</option>
                            <option>B</option>
                            <option>C</option>
                        </select>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div style="display: flex; justify-content: center">
        <button type="button">Begin</button>
        <button type="button" onclick="leaveLobby('${lobbyId}')" >Leave</button>
    </div>
</body>
</html>
