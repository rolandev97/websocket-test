package com.testwebsocket.controller;

import java.util.Hashtable;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

@ServerEndpoint(value = "/chat/{pseudo}",configurator = ChatRoom.EndpointConfigurator.class)
public class ChatRoom {
	
	private static ChatRoom singleton = new ChatRoom();
	
	public ChatRoom() {
		// TODO Auto-generated constructor stub
	}
	
      //Acquisition de notre unique instance ChatRoom 
     
	public static ChatRoom getInstance()
	{
		return ChatRoom.singleton;
	}
	
	//On maintient toutes les sessions utilisateurs dans une collection.
    
	private Hashtable<String, Session> sessions = new Hashtable<>();
	
	//On maintient toutes les sessions utilisateurs dans une collection.
     
	private void sendMessage(String message) 
	{
		System.out.println(message);
		
		// On envoie le message à tout le monde.
		for (Session session : sessions.values())
		{
			try {
				session.getBasicRemote().sendText(message);
			} catch (Exception e) {
				 System.out.println( "ERROR: cannot send message to " + session.getId() );
			}
		}
	}
	
	// Cette méthode est déclenchée à chaque connexion d'un utilisateur.
    
	@OnOpen
	private void open(Session session,@PathParam("pseudo") String pseudo) 
	{
		sendMessage("Admin >>> Connection Etablished for "+ pseudo);
		session.getUserProperties().put("pseudo", pseudo);
		sessions.put(session.getId(), session);
	}
	
    //Cette méthode est déclenchée à chaque déconnexion d'un utilisateur.
     
	@OnClose
	private void close(Session session)
	{
		String pseudo = (String)session.getUserProperties().get("pseudo");
		sessions.remove(session.getId());
		sendMessage( "Admin >>> Connection closed for " + pseudo );
	}
	
	
     // Cette méthode est déclenchée en cas d'erreur de communication.
     
	@OnError
	private void onError(Throwable error) 
	{
		System.out.println("Error :" + error.getMessage());
	}
	
	
     //Cette méthode est déclenchée à chaque réception d'un message utilisateur.
     
	@OnMessage
	private void handleMessage(String msg,Session session) 
	{
		String pseudo = (String) session.getUserProperties().get("pseudo");
		String fullMessgage = pseudo + " >>> "+msg;
		sendMessage(fullMessgage);
	}
	

      //Permet de ne pas avoir une instance différente par client.
      //ChatRoom est donc gérer en "singleton" et le configurateur utilise ce singleton. 
     
    public static class EndpointConfigurator extends ServerEndpointConfig.Configurator {
        @Override 
        @SuppressWarnings("unchecked")
        public <T> T getEndpointInstance(Class<T> endpointClass) {
            return (T) ChatRoom.getInstance();
        }
    }
}
