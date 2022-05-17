package model;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import Database.Admin;
import Database.Bot;
import Database.Chat;
import Database.ChatLog;
import Database.Noob;
import Database.NotificationBot;
import Database.Role;
import Database.Room;
import Database.RoomList;
import Database.SpamBot;
import Database.User;
import Database.UserList;
import ServerClientModel.Client;
import ServerClientModel.ServerInterface;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class ConcordClientModel
{
	String password;
	String username;
	Client client;
	User user;
	ServerInterface server;
	StringProperty selectedRoom = new SimpleStringProperty();
	IntegerProperty selectedRoomID = new SimpleIntegerProperty();
	IntegerProperty selectedChatLogID = new SimpleIntegerProperty();
	StringProperty usernameTextProperty = new SimpleStringProperty();
	IntegerProperty selectedDMID = new SimpleIntegerProperty();
	ObservableList<Room> allRooms = FXCollections.observableArrayList();
	ObservableList<Room> myRooms = FXCollections.observableArrayList();
	ObservableList<Room> myDMs = FXCollections.observableArrayList();
	ObservableList<User> allUsers = FXCollections.observableArrayList();
	ObservableList<ChatLog> roomsChatLogs = FXCollections.observableArrayList();
	ObservableList<Chat> chatLogsChats = FXCollections.observableArrayList();
	ObservableList<Chat> DMsChats = FXCollections.observableArrayList();
	ObservableList<User> roomsUsers = FXCollections.observableArrayList();
	ObservableList<Bot> roomsBots = FXCollections.observableArrayList();
	ObservableList<String> allBots = FXCollections.observableArrayList();
	private void notifyAll(String msg, Collection<User> users) {
		//System.out.println("Notification message: " + msg);
		for (User user: users) {
			try
			{
				client.notifyUser(user.getUserID(), msg);
			} catch (RemoteException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	public User getUser()
	{
		return user;
	}
	
	public ObservableList<Room> getAllRooms(){
		return allRooms;
	}
	
	public ObservableList<String> getAllBots(){
		return allBots;
	}
	
	public ObservableList<Room> getMyRooms(){
		return myRooms;
	}
	
	public ObservableList<User> getAllUsers(){
		return allUsers;
	}
	
	public ObservableList<ChatLog> getRoomsChatLogs(){
		return roomsChatLogs;
	}
	
	public ObservableList<Chat> getChatLogsChats(){
		return chatLogsChats;
	}
	
	public ObservableList<User> getRoomsUsers(){
		return roomsUsers;
	}
	
	public ObservableList<Bot> getRoomsBots(){
		return roomsBots;
	}
	
	public ObservableList<Room> getMyDMs(){
		return myDMs;
	}
	
	public ObservableList<Chat> getDMsChats(){
		return DMsChats;
	}
	
	public void setUser(User user)
	{
		this.user = user;
	}

	public ServerInterface getServer()
	{
		return server;
	}

	public void setServer(ServerInterface server)
	{
		this.server = server;
	}

	public void setClient(Client client)
	{
		this.client = client;
	}

	public void setSelectedRoom(StringProperty selectedRoom)
	{
		this.selectedRoom = selectedRoom;
	}

	public void setSelectedRoomID(IntegerProperty selectedRoomID)
	{
		this.selectedRoomID = selectedRoomID;
	}

	public void setSelectedChatLogID(IntegerProperty selectedChatLogID)
	{
		this.selectedChatLogID = selectedChatLogID;
	}

	public void setUsernameTextProperty(StringProperty usernameTextProperty)
	{
		this.usernameTextProperty = usernameTextProperty;
	}

	public void setSelectedDMID(IntegerProperty selectedDMID)
	{
		this.selectedDMID = selectedDMID;
	}

	public void setAllRooms(ObservableList<Room> allRooms)
	{
		this.allRooms = allRooms;
	}

	public void setMyRooms(ObservableList<Room> myRooms)
	{
		this.myRooms = myRooms;
	}

	public void setMyDMs(ObservableList<Room> myDMs)
	{
		this.myDMs = myDMs;
	}

	public void setAllUsers(ObservableList<User> allUsers)
	{
		this.allUsers = allUsers;
	}

	public void setRoomsChatLogs(ObservableList<ChatLog> roomsChatLogs)
	{
		this.roomsChatLogs = roomsChatLogs;
	}

	public void setChatLogsChats(ObservableList<Chat> chatLogsChats)
	{
		this.chatLogsChats = chatLogsChats;
	}

	public void setDMsChats(ObservableList<Chat> dMsChats)
	{
		DMsChats = dMsChats;
	}

	public void setRoomsUsers(ObservableList<User> roomsUsers)
	{
		this.roomsUsers = roomsUsers;
	}

	public void setRoomsBots(ObservableList<Bot> roomsBots)
	{
		this.roomsBots = roomsBots;
	}

	public void setAllBots(ObservableList<String> allBots)
	{
		this.allBots = allBots;
	}
	
	public void loadAllRooms() {
		//This will load in all of the rooms from the server into the allRooms ArrayList
		
		if (server == null) {
			return;
		}
		
		try
		{
			allRooms.clear();
			Hashtable<Integer, Room> allRoomsHash = server.getRooms().getRooms();
			allRooms.addAll(allRoomsHash.values());
			//notifyOthers("<load all rooms>", allUsers);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void loadMyRooms() {
		//Loads all of my own rooms in which I am in.
		
		if (user == null || server == null) {
			return;
		}
		
		try
		{
			myRooms.clear();
			User necessaryUser = server.getUser(user.getUserID());
			Hashtable<Integer, Room> usersRooms = necessaryUser.getRooms();
			
			for (Room room: usersRooms.values()) {
				//System.out.println("Users room: " + room);
			}
			
			myRooms.addAll(usersRooms.values());
			//notifyOthers("<load all rooms>", allUsers);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void loadAllUsers() {
		
		if (server == null) {
			return;
		}
		
		try
		{
			allUsers.clear();
			
			Hashtable<Integer, User> allUsersHash = server.getUsers().getUsers();
			allUsers.addAll(allUsersHash.values());
			//notifyOthers("<load all users>", allUsers);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadRoomsUsers() {
		if (selectedRoomID == null || getSelectedRoomID() == -1 || getSelectedRoom() == null || selectedRoom == null) {
			return;
		}
		roomsUsers.clear();
		
		try
		{
			Room room = server.getRoom(getSelectedRoomID());
			if (room == null) { 
				return;
			}
			
			Set<Integer> userIds = room.getUserTable().keySet();
			
			for (int userID: userIds) {
				User user = server.getUser(userID);
				if (user != null) {
					roomsUsers.add(user);
				}
			}
			//notifyOthers("<load rooms users>", roomsUsers);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void loadRoomsChatLogs() {
		if (selectedRoomID == null || server == null) {
			//System.out.println("Room's not selected");
			return;
			//If a room isn't selected, we can't show its channels.
		}
		
		try
		{
			roomsChatLogs.clear();
			ArrayList<ChatLog> chatLogsForRoom = server.getAllChatLogs(getSelectedRoomID());
			if (chatLogsForRoom == null) {
				return;
			}
			//System.out.println(getSelectedRoomID());
			roomsChatLogs.addAll(chatLogsForRoom);
			//notifyOthers("<load all chatlogs>" + getSelectedRoomID(), roomsUsers);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadRoomsBots() {
		if (selectedRoomID == null || server == null) {
			//System.out.println("Room's not selected");
			return;
			//If a room isn't selected, we can't show its channels.
		}
		
		try
		{
			roomsBots.clear();
			Room room = server.getRoom(getSelectedRoomID());
			if (room == null) {
				return;
			}
			//System.out.println("Loading rooms bots");
			ArrayList<Bot> bots = room.getBots();
			if (bots == null) {
				return;
			}
			
			roomsBots.addAll(bots);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadAllBots() {
		allBots.clear();
		
		allBots.add("[SPAM BOT]");
		allBots.add("[NOTIFICATION BOT]");
	}
	
	public void loadChatLogsChats() {
		if (selectedRoom == null || selectedRoomID == null || getSelectedRoomID() == - 1 ||
			selectedChatLogID == null || getSelectedChatLogID() == -1) {
			return;
		}
		
		chatLogsChats.clear();
		try
		{
			ChatLog chatLog = server.getChatLog(getSelectedRoomID(), getSelectedChatLogID());
			
			if (chatLog == null) {
				return;
			}
			//System.out.println("Is chat log locked?: " + chatLog.isLocked());
			Hashtable<Integer, Chat> chats = chatLog.getChatLog();
			
			if (chats == null) {
				return;
			}
			Object[] chatsArray = chats.values().toArray();
			
			for (int i = chatsArray.length - 1; i >= 0; i--) {
				Chat chat = (Chat) chatsArray[i];
				chatLogsChats.add(chat);
			}
			
			//System.out.println("Loaded chats.");
			//chatLogsChats.addAll(chats.values());
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadMyDMs() {
		if (client == null) {
			return;
		}
		
		try
		{
			myDMs.clear();
			User myself = server.getUser(client.getUserID());
			Hashtable<Integer, Room> dms = myself.getDirectMessages();
			if (dms == null) {
				return;
			}
			
			myDMs.addAll(dms.values());
			Room selectedDM = dms.get(getSelectedDMID());
			if (selectedDM == null) {
				return;
			}
			Collection<Integer> usersInDM = selectedDM.getUserTable().keySet();
			for (int userID: usersInDM) {
				if (userID != client.getUserID()) {
					//client.notifyUser(userID, "<load all dms>"); //Notify the other user in the DM
				}
			}
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadDMsChats() {
		if (client == null || getSelectedDMID() == -1 || selectedDMID == null) {
			return;
		}
		
		
		try
		{
			DMsChats.clear();
			Hashtable<Integer, Room> dms = client.getDirectMessages();
			if (dms == null) {
				return;
			}
			
			Room selectedDM = dms.get(getSelectedDMID());
			if (selectedDM == null) {
				return;
			}
			
			ChatLog DM = selectedDM.getChatLog(1);
			
			if (DM == null) {
				return;
			}
			Hashtable<Integer, Chat> chats = DM.getChatLog();
			
			if (chats == null) {
				return;
			}
			
			Object[] chatsArray = chats.values().toArray();
			
			for (int i = chatsArray.length - 1; i >= 0; i--) {
				Chat chat = (Chat) chatsArray[i];
				DMsChats.add(chat);
			}
			
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public void startDM(int userID) {
		try
		{
			server.startDirectMessage(client.getUserID(), userID);
			notifyAll("<load all dms>", server.getUsers().getUsers().values());
			//loadMyDMs();
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Chat createChat(int roomID, int chatLogID, String message, ListView<Chat> chatList) {
		try
		{
			Chat chat = client.addChat(roomID, chatLogID, message);
			Room room = server.getRoom(getSelectedRoomID());
			if (room == null) { 
				return null;
			}
			
			Set<Integer> userIds = room.getUserTable().keySet();
			ArrayList<User> localUsers = new ArrayList<User>();
			for (int userID: userIds) {
				User user = server.getUser(userID);
				if (user != null) {
					localUsers.add(user);
				}
			}
			loadChatLogsChats();
			notifyAll("<load all chats>|" + Integer.toString(getSelectedChatLogID()), localUsers);
			List<Chat> chats = chatList.getItems();
			int index = chats.size();
			chatList.scrollTo(index);
			return chat;
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void sendDM(String msg) {
		if (selectedDMID == null || getSelectedDMID() == -1) {
			return;
		}
		
		try
		{
			client.addChat(getSelectedDMID(), 1, msg);
			Hashtable<Integer, Room> dms = client.getDirectMessages();
			if (dms == null) {
				return;
			}
			
			Room selectedDM = dms.get(getSelectedDMID());
			if (selectedDM == null) {
				return;
			}
			
			Set<Integer> userIDsInDM = selectedDM.getUserTable().keySet();
			ArrayList<User> usersInDM = new ArrayList<User>();
			for (int userID: userIDsInDM) {
				User user = server.getUser(userID);
				if (user != null) {
					usersInDM.add(user);
				}
			}
			notifyAll("<load dms chats>|" + Integer.toString(getSelectedDMID()), usersInDM);
			//loadDMsChats();
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createChatLog(int roomID, String chatLogName) {
		if (client == null) {
			return;
		}
		
		try
		{
			ChatLog chatlog = client.addChatLog(roomID, chatLogName);
			Room room = server.getRoom(roomID);
			if (room == null) {
				return;
			}
			Set<Integer> userIDsInRoom = room.getUserTable().keySet();
			ArrayList<User> usersInRoom = new ArrayList<User>();
			for (int userID: userIDsInRoom) {
				User user = server.getUser(userID);
				if (user != null) {
					usersInRoom.add(user);
				}
			}
			notifyAll("<load all chatlogs>|" + Integer.toString(getSelectedRoomID()), usersInRoom);
			//loadRoomsChatLogs();
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteChatLog(int roomID, int chatLogID) {
		if (client == null) {
			return;
		}
		
		try
		{
			//System.out.println("Deleting chat log");
			client.deleteChatLog(roomID, chatLogID);
			Room room = server.getRoom(roomID);
			if (room == null) {
				return;
			}
			Set<Integer> userIDsInRoom = room.getUserTable().keySet();
			ArrayList<User> usersInRoom = new ArrayList<User>();
			for (int userID: userIDsInRoom) {
				User user = server.getUser(userID);
				if (user != null) {
					usersInRoom.add(user);
				}
			}
			notifyAll("<load all chatlogs>|" + Integer.toString(getSelectedRoomID()), usersInRoom);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createRoom(String name, String description, String logo, boolean roomType) {
		if (client == null) {
			return;
		}
		try
		{
			Room newRoom = client.addRoom(name, description, logo, roomType);
			
			if (newRoom == null) {
				return;
			}
			
			selectedRoom.set(newRoom.getName());
			selectedRoomID.set(newRoom.getRoomID());
			
			client.addUserToRoom(newRoom.getRoomID(), new Admin()); //Also add user to room

			Collection<User> users = server.getUsers().getUsers().values();
			if (users == null) {
				return;
			}
			notifyAll("<load all rooms>", users);
			//loadAllRooms();
			loadMyRooms();
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteRoom(int roomID) {
		if (client == null) {
			return;
		}
		
		try
		{
			//loadAllUsers();
			Collection<User> users = server.getUsers().getUsers().values();
			if (users == null) {
				return;
			}
			notifyAll("<load all users>", users);
			//Delete the room
			client.deleteRoom(roomID); 
			selectedRoom.set("");
			selectedRoomID.set(-1);
			notifyAll("<load all rooms>", users);
			notifyAll("<load my rooms>", users);
			//loadAllRooms();
			//loadMyRooms();
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void registerBot(String botType) {
		try
		{
			Bot newBot = null;
			Room relevantRoom = server.getRoom(getSelectedRoomID());
			
			if (relevantRoom == null) {
				return;
			}
			
			if (botType.equals("[SPAM BOT]")) {
				//Lets make a new spam bot 
				newBot = new SpamBot(relevantRoom.getRoomID());
				//We also gotta add a user to the room with the same name is spam bot.
				
			} else if (botType.equals("[NOTIFICATION BOT]")) {
				newBot = new NotificationBot(relevantRoom.getRoomID());
			}
			
			if (newBot == null) {
				return;
			}
			//Check if the bot already exists in the room
			
			ArrayList<Bot> relevantRoomsBots = relevantRoom.getBots();
			//System.out.println("Performing checks on bots in room " + relevantRoomsBots);
			for (Bot bot: relevantRoomsBots) {
				//System.out.println(bot.getName() + ", " + newBot.getName());
				if (bot.getName().equals(newBot.getName())) {
					//System.out.println("Ok, returning");
					return; //The new bot already exists.
				}
			}

			
			//Add a user to the room with the same name as bot and set the bots userID to that user.
			User botUser = server.addUser(newBot.getName(), "botPass", "I AM " + newBot.getName());
			server.addUserToRoom(relevantRoom.getRoomID(), botUser, new Admin());
			newBot.setUserID(botUser.getUserID());
			server.registerBot(getSelectedRoomID(), newBot);
			System.out.println("New bots userID: " + newBot.getUserID());
			for (Integer userID: relevantRoom.getUserTable().keySet()) {
				System.out.println(userID + "\n" + server.getUser(userID));
			}
			Set<Integer> userIDsInRoom = relevantRoom.getUserTable().keySet();
			ArrayList<User> usersInRoom = new ArrayList<User>();
			for (int userID: userIDsInRoom) {
				User user = server.getUser(userID);
				if (user != null) {
					usersInRoom.add(user);
				}
			}
			notifyAll("<load all users>", usersInRoom);
			notifyAll("<load rooms users>", usersInRoom);
		} catch (RemoteException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public ConcordClientModel() throws RemoteException {
		//System.out.println("Initializing demo");
		
	}
	
	public ConcordClientModel(String password, String username) throws RemoteException {
		this.username = username;
		this.password = password;
		try
		{
			ServerInterface server = (ServerInterface) Naming.lookup("rmi://127.0.0.1/CONCORD");
			user = server.getUser(username, password);
			client = new Client(server, user);
			client.logOn(password);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//initializeDemo();
	}
	
	public void getNotified(String msg) {
		//System.out.println("Got notified: " + msg);
		if (msg.indexOf("<load all users>") != -1) {
			Platform.runLater(() -> loadAllUsers());
		} else if (msg.indexOf("<load my rooms>") != -1) {
			Platform.runLater(() -> loadMyRooms());
		} else if (msg.indexOf("<load all chats>|") != -1) {
			//Need to check if the chat logs match
			String strOccuring = "<load all chats>|";
			int strIndex = msg.indexOf(strOccuring);
			String chatlogID = msg.substring(strIndex + strOccuring.length(), msg.length());
			int chatLogID = Integer.parseInt(chatlogID);
			//System.out.println("Selected chatlog id:" + chatLogID);
			if (getSelectedChatLogID() == chatLogID) {
				Platform.runLater(() -> loadChatLogsChats());
			}
		} else if (msg.indexOf("<load all chatlogs>|") != -1) {
			//Need to check if rooms match
			String strOccuring = "<load all chatlogs>|";
			int strIndex = msg.indexOf(strOccuring);
			String roomIDStr = msg.substring(strIndex + strOccuring.length(), msg.length());
			int roomID = Integer.parseInt(roomIDStr);
			//System.out.println("Msg is:" + msg);
			//System.out.println("Room ID is:" + roomID);
			if (getSelectedRoomID() == roomID) {
				Platform.runLater(() -> loadRoomsChatLogs());
			}
		} else if (msg.indexOf("<load all rooms>") != -1) {
			Platform.runLater(() -> loadAllRooms());
		} else if (msg.indexOf("<load rooms users>") != -1) {
			Platform.runLater(() -> loadRoomsUsers());
		} else if (msg.indexOf("<load all dms>") != -1) {
			Platform.runLater(() -> loadMyDMs());
		} else if (msg.indexOf("<load dms chats>|") != -1) {
			String strOccuring = "<load dms chats>|";
			int strIndex = msg.indexOf(strOccuring);
			String dmIDString = msg.substring(strIndex + strOccuring.length(), msg.length());
			int dmID = Integer.parseInt(dmIDString);
			if (getSelectedDMID() == dmID) {
				Platform.runLater(() -> loadDMsChats());
			}
		}
	}
	
	public boolean logOn() {
		try
		{
			//System.out.println("Trying to login:\n" + username + ":" + password);
			server = (ServerInterface) Naming.lookup("rmi://127.0.0.1/CONCORD");
			if (user == null) {
				user = server.getUser(username, password);
				//System.out.println("Got user: " + user.getUserName());
			}
			
			//System.out.println("Is user null?" + user != null);
			//System.out.println("Is client null?" + client == null);
			if (client == null && user != null) {
				client = new Client(server, user);
				RoomList rooms = server.getRooms();
				Hashtable<Integer, Room> roomsHash = rooms.getRooms();
				
				Enumeration<Integer> e = roomsHash.keys();
				
				while (e.hasMoreElements()) {
					int key = e.nextElement();
					Room room = roomsHash.get(key);
					
					//System.out.println("Room:" + room);
				}
				
				server.addClient(client);
				Boolean loggedIn = client.logOn(password);
				//System.out.println("Adding client");
				//System.out.println(client);
				//Need to load all the data for the client.
				if (!loggedIn){
					//Don't want to load anything if the person hasn't logged in.
					return false;
				}
				client.addClientModel(this);
				//System.out.println("Adding client model");
				loadAllUsers();
				loadAllRooms();
				loadMyRooms();
				loadAllBots();
				selectedRoom.set("");
				selectedRoomID.set(-1); //No room is selected to start with
				usernameTextProperty.set(username);
				return loggedIn;
			}
			
			if (user == null || client == null) {
				return false;
			}
		} catch (RemoteException | MalformedURLException | NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	public User getUserFromServer() {
		int userID = user.getUserID();
		
		try
		{
			return server.getUser(userID);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void updateInfo(String username, String password, String profileData, Boolean status) {
		try
		{
			client.changeUsersPassword(password);
			client.changeUsersUsername(username);
			this.username = username;
			this.password = password;
			this.usernameTextProperty.set(username);
			client.setProfileData(profileData);
			client.setStatus(status);
			loadRoomsUsers();
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public StringProperty getUsernameTextProperty() {
		return usernameTextProperty;
	}
	
	public void logOut() {
		
		try
		{
			//need to flush all the data first
			myRooms.clear();
			allRooms.clear();
			allUsers.clear();
			roomsUsers.clear();
			roomsChatLogs.clear();
			chatLogsChats.clear();
			usernameTextProperty.set("");
			selectedRoom.set("");
			selectedRoomID.set(-1);
			client.logOut();
			client.removeClient();
			this.username = null;
			this.password = null;
			this.user = null;
			this.client = null;
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	public ObservableList<Room> getRooms(){
		return rooms;
	}
	
	public ObservableList<ChatLog> getChannels(){
		return channels;
	}
	
	public ObservableList<User> getUsers(){
		return users;
	}
	
	public ObservableList<Chat> getChats(){
		return chats;
	}
	
	public ObservableList<Room> getDMs(){
		return DMs;
	}
	*/
	public void setSelectedRoomID(int selectedRoomID) {
		this.selectedRoomID.set(selectedRoomID);
	}
	
	public void setSelectedDMID(int selectedDMID) {
		this.selectedDMID.set(selectedDMID);
	}
	
	public int getSelectedRoomID() {
		return selectedRoomID.get();
	}
	
	public int getSelectedDMID() {
		return selectedDMID.get();
	}
	
	public void setSelectedChatLogID(int selectedChatLogID) {
		this.selectedChatLogID.set(selectedChatLogID);
	}
	
	public int getSelectedChatLogID() {
		return selectedChatLogID.get();
	}
	
	public void setSelectedRoom(String selectedRoom) {
		this.selectedRoom.set(selectedRoom);
	}
	
	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public StringProperty getSelectedRoom() {
		return selectedRoom;
	}
	/*
	public void addRoom(String roomName, String roomDescription, String roomLogo, Boolean roomType) {
		Room new_room;
		try
		{
			new_room = client.addRoom(roomName, roomDescription, roomLogo, roomType);
			client.addUserToRoom(new_room.getRoomID(), new Admin());
			System.out.println("Adding room:" + new_room + ", ID: " + new_room.getRoomID()); 
			rooms.add(new_room);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public void deleteSelectedRoom() {
		if (selectedRoomID == null || getSelectedRoomID() == -1) {
			return;
		}
		
		try
		{
			Room room = client.getRoom(selectedRoomID.get());
			if (room == null) {
				return;
			}
			//System.out.println("Room's name: " + room.getName());
			//System.out.println("Room's ID:" + room.getRoomID());
			Role clientsRole = room.getUser(client.getUserID());
			if (clientsRole == null) {
				//System.out.println("No role for user");
				return;
			}
			if (!clientsRole.isDeleteRoomPermission()) {
				return;
			}

			//First, remove all users from the room
			/*
			for (User user:users) {
				client.removeUserFromRoom(selectedRoomID.get(), user.getUserID());
			}
			*/
			//Now delete room
			client.deleteRoom(getSelectedRoomID());
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Client getClient() {
		return client;
	}
	
	public void initializeDemo() throws RemoteException {
		//System.out.println("Initializing demo");
		
		if (server == null) {
			return;
		}
		//Populate the server with some dummy data.
		
		Room testRoom = server.addRoom("Test Room", "For testing purposes");
		User john = server.addUser("John", "john123", "My name is John!");
		Client johnsClient = new Client(server, john);
		
		User rob = server.addUser("Rob", "rob123", "My name is Rob!");
		Client robsClient = new Client(server, rob);
		
		server.addClient(johnsClient);
		server.addClient(robsClient);
		
		johnsClient.logOn("john123");
		robsClient.logOn("rob123");
		
		server.addUserToRoom(testRoom.getRoomID(), rob, new Noob());
		server.addUserToRoom(testRoom.getRoomID(), john, new Admin());
		
		ChatLog chats = server.addChatLog(testRoom.getRoomID(), john.getUserID(), "Chats");
		server.addChat(testRoom.getRoomID(), chats.getChatLogID(), john.getUserID(), "Hey rob, how are you?");
		server.addChat(testRoom.getRoomID(), chats.getChatLogID(), rob.getUserID(), "Good, how about you?");
	}
	
	public String toString() {
		return user.getUserName();
	}
	
	public String getUsername() {
		return username;
	}
	
	public ServerInterface getConnection() {
		try
		{
			return client.getConnection();
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
