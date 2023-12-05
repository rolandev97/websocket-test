<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chat</title>
        <style>
            #history {
                display: block;
                width: 500px;
                height: 300px;
            }

            #txtMessage {
                display: inline-block;
                width: 300px;
            }

            #btnSend {
                display: inline-block;
                width: 180px;
            }

            #btnClose {
                display: block;
                width: 500px;
            }
        </style>
    </head>
    <body>
        <textarea id="history" readonly></textarea>
        <input id="txtMessage" type="text" />
        <button id="btnSend">Send message</button>
        <button id="btnClose">Close connection</button>
    </body>
     <script type="text/javascript" src="js/Client.js"></script>
</html>