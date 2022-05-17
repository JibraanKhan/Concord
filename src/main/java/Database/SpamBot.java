package Database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import ServerClientModel.ServerInterface;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.Instant;

public class SpamBot implements Bot, Serializable
{
	private static final long serialVersionUID = 1L;
	int roomID;
	Hashtable<Integer, ArrayList<Chat>> chats = new Hashtable<Integer, ArrayList<Chat>>();
	String name = "[SPAM BOT]";
	int userID;

	public Hashtable<Integer, ArrayList<Chat>> getChats()
	{
		return chats;
	}

	public void setChats(Hashtable<Integer, ArrayList<Chat>> chats)
	{
		this.chats = chats;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getUserID()
	{
		return userID;
	}

	public void setUserID(int userID)
	{
		this.userID = userID;
	}
	
	public SpamBot()
	{
		// TODO Auto-generated constructor stub
		
	}

	public SpamBot(int roomID) {
		this.roomID = roomID;
	}
	
	@Override
	public void update(String event, Object change)
	{
		//Gets triggered whenever any event takes place
		//If the event is a chat being added then we add it to chats and record the time.
		if (event.lastIndexOf("<chatAdded>") != -1) {
			String[] splitStr = event.split(" ");
			splitStr[0] = "";
			//System.out.println("Split string is:" + splitStr);
			String chatIDInString = String.join("", splitStr);
			int chatID = Integer.parseInt(chatIDInString);
			
			//System.out.println("Got notified <SPAM BOT>");
			ChatLog chatLog = (ChatLog) change;
			ServerInterface server;
			try
			{
				server = (ServerInterface) Naming.lookup("rmi://127.0.0.1/CONCORD");
				Chat chat = server.getChat(roomID, chatLog.getChatLogID(), chatID);
				//System.out.println("Room:" + roomID);
				//System.out.println("Chat log:" + chatLog);
				if (chat == null) {
					//System.out.println("Chat was null");
					return;
				}
				
				int userID = chat.getSenderID();		
				Room roomInvolved = server.getRoom(roomID);
				if (roomInvolved == null) {
					return;
				}
				ArrayList<Bot> roomsBots = roomInvolved.getBots();
				
				for (Bot bot: roomsBots) {
					if (userID == bot.getUserID()) { //Check that the message is not sent from a bot.
						return;
					}
				}
				
				ArrayList<Chat> chatsArrayList = chats.get(userID);
				if (chatsArrayList == null) {
					chatsArrayList = new ArrayList<Chat>();
				}
				chatsArrayList.add(chat);
				chats.put(userID, chatsArrayList);
				
				//System.out.println("Chat is: " + chat);
				//System.out.println("ChatLog is: " + chatLog);
				checkForSpam(userID, chatLog, chat);
			} catch (MalformedURLException | RemoteException | NotBoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public int getRoomID()
	{
		return roomID;
	}

	public void setRoomID(int roomID)
	{
		this.roomID = roomID;
	}

	public void checkForSpam(int userID, ChatLog chatLog, Chat chat) {
		//Check if the last five items have the same text. If so, it is probably spam.
		try
		{
			ServerInterface server = (ServerInterface) Naming.lookup("rmi://127.0.0.1/CONCORD");
			Room relevantRoom = server.getRoom(roomID);
			
			if (relevantRoom == null) {
				return;
			}
			
			Collection<Chat> chats = chatLog.getChatLog().values();
			Object[] chatsArr = chats.toArray();
			
			String concerningText = chat.getMessage();
			//int startingIndex = chatsArr.length - 1;
			int counter = 0;
			for (int i = 0; i < chatsArr.length - 1; i++) {
				Chat relChat = (Chat) chatsArr[i];
				//System.out.println("Chat is: " + relChat);
				
				if (relChat.getSenderID() == userID) {
					if (!relChat.getMessage().equals(concerningText)) {
						System.out.println(relChat.getMessage() + " " + concerningText + counter);
						return;
					}
					counter++;
				}
				//System.out.println(counter + " " + relChat);
				if (counter >= 5) {
					break;
				}
			}
			
			if (counter < 5) {
				return;
			}
			System.out.println("Telling them to stop");
			tellThemToStop(userID, chatLog.getChatLogID());
		} catch (MalformedURLException | RemoteException | NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void tellThemToStop(int userID, int chatLogID) {
		//Sends a message to the room letting them know they should stop spamming.
		ServerInterface server;
		try
		{
			server = (ServerInterface) Naming.lookup("rmi://127.0.0.1/CONCORD");
			Room roomInvolved = server.getRoom(roomID);
			//System.out.println("TELLING THEM TO STOP!");
			//System.out.println("NEED THEM TO STOP!");
			server.addChat(roomInvolved.getRoomID(), chatLogID, this.userID, "Please stop spamming!");
		} catch (MalformedURLException | RemoteException | NotBoundException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
}
