package Database;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;

import ServerClientModel.ServerInterface;

public class NotificationBot implements Bot, Serializable
{
	private static final long serialVersionUID = 1L;
	int roomID;
	String name = "[NOTIFICATION BOT]";
	int userID;
	ArrayList<String> notifications = new ArrayList<String>();
	
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
	
	public NotificationBot()
	{
		// TODO Auto-generated constructor stub
		
	}

	public NotificationBot(int roomID) {
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
				//System.out.println("Found room");
				Role sendersRole = roomInvolved.getUser(userID);
				if (sendersRole == null) {
					return;
				}
				
				//System.out.println("Sender has role");
				//System.out.println("Senders role is:" + sendersRole.getRoleName());
				if (!sendersRole.getRoleName().equals("Admin")) {
					return; //Only want admins to be able to add and request notifications to show up.
				}
				//System.out.println("Ok, sender is admin");
				ArrayList<Bot> roomsBots = roomInvolved.getBots();
				
				for (Bot bot: roomsBots) {
					if (userID == bot.getUserID()) { //Check that the message is not sent from a bot.
						return;
					}
				}
				
				//System.out.println("Chat is: " + chat);
				//System.out.println("ChatLog is: " + chatLog);
				checkMessage(userID, chatLog, chat);
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

	public ArrayList<String> getNotifications()
	{
		return notifications;
	}

	public void setNotifications(ArrayList<String> notifications)
	{
		this.notifications = notifications;
	}

	public void checkMessage(int userID, ChatLog chatLog, Chat chat) {
		//System.out.println("Checking message");
		//System.out.println(userID);
		//System.out.println(chatLog);
		//System.out.println(chat);
		String msg = chat.getMessage();
		String[] msgSplit = msg.split(" ");
		String command = msgSplit[0];
		
		if (command.equals("!addNotification")) {
			msgSplit[0] = "";
			String notificationToAdd = String.join(" ", msgSplit).strip();
			//System.out.println("Adding notification: " + notificationToAdd);
			notifications.add(notificationToAdd);
		} else if (command.equals("!showNotifications")) {
			showNotifications(chatLog.getChatLogID());
		}
	}
	
	public void showNotifications(int chatLogID) {
		try
		{
			//System.out.println("Showing notifications");
			ServerInterface server = (ServerInterface) Naming.lookup("rmi://127.0.0.1/CONCORD");
			for (String notification: notifications) {
				server.addChat(roomID, chatLogID, userID, notification);
			}
		} catch (MalformedURLException | RemoteException | NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
