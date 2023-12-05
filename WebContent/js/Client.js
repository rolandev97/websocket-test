window.addEventListener("load",function(event){
	
	let pseudo = prompt("Entrer votre nom :");
	let ws = new WebSocket("ws://localhost:8080/testwebsocket/" + pseudo);
	
	let txtHistory = document.getElementById( "history" );
    let txtMessage = document.getElementById( "txtMessage" );
    txtMessage.focus();
    
    ws.addEventListener( "open", function( evt ) {
        console.log( "Connection established" );
    });
    
    ws.addEventListener( "message", function( evt ) {
        let message = evt.data;
        console.log( "Receive new message: " + message );
        txtHistory.value += message + "\n";
    });
    
    ws.addEventListener( "close", function( evt ) {
        console.log( "Connection closed" );
    });
    
    let btnSend = document.getElementById( "btnSend" );
    btnSend.addEventListener( "click", function( clickEvent ) {
    	alert("ok");
        ws.send( txtMessage.value );
        txtMessage.value = "";
        txtMessage.focus();
    });
    
    let btnClose = document.getElementById( "btnClose" );
    btnClose.addEventListener( "click", function( clickEvent ) {
        ws.close();
    });
    
    
});