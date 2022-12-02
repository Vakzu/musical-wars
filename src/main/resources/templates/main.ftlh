<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Main</title>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@stomp/stompjs@5.0.0/bundles/stomp.umd.js"></script>
    <script>
        window.onunload = disconnect

        let stompClient = null

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
                stompClient.subscribe('/user/' + ${user.id} + '/queue/invites', function(messageOutput) {
                    let msg = JSON.parse(messageOutput.body)
                    let result = confirm(msg.senderName + " invites you to join their lobby")
                    if (result) {
                        fetch("/lobby/join?lobbyId=" + msg.lobbyId, {
                            method: "POST"
                        }).then(res => {
                            if (res.redirected) {
                                window.location = res.url
                            }
                        })
                    }
                });
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
                stompClient.deactivate();
            }
            console.log("Disconnected");
        }
    </script>
</head>
<body onload="connect()">
<div>
    <a href="/lobby/create">Create lobby</a>
</div>
</body>
</html>