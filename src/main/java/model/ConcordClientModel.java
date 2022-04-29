package model;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import Database.Admin;
import Database.Chat;
import Database.ChatLog;
import Database.Noob;
import Database.Role;
import Database.Room;
import Database.RoomList;
import Database.User;
import Database.UserList;
import ServerClientModel.Client;
import ServerClientModel.ServerInterface;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConcordClientModel
{
	/*
	public ObservableList<Room> getAllRooms()
	{
		return allRooms;
	}

	public void setAllRooms(ObservableList<Room> allRooms)
	{
		this.allRooms = allRooms;
	}

	public ObservableList<User> getAllUsers()
	{
		return allUsers;
	}

	public void setAllUsers(ObservableList<User> allUsers)
	{
		this.allUsers = allUsers;
	}*/

	String password;
	String username;
	Client client;
	User user;
	ServerInterface server;
	StringProperty selectedRoom = new SimpleStringProperty();
	IntegerProperty selectedRoomID = new SimpleIntegerProperty();
	IntegerProperty selectedChatLogID = new SimpleIntegerProperty();
	StringProperty usernameTextProperty = new SimpleStringProperty();
	ObservableList<Room> allRooms = FXCollections.observableArrayList();
	ObservableList<Room> myRooms = FXCollections.observableArrayList();
	ObservableList<User> allUsers = FXCollections.observableArrayList();
	ObservableList<ChatLog> roomsChatLogs = FXCollections.observableArrayList();
	ObservableList<Chat> chatLogsChats = FXCollections.observableArrayList();
	ObservableList<User> roomsUsers = FXCollections.observableArrayList();
	/*ObservableList<Room> allRooms = FXCollections.observableArrayList();
	ObservableList<Room> rooms = FXCollections.observableArrayList();
	ObservableList<ChatLog> channels = FXCollections.observableArrayList();
	ObservableList<User> users = FXCollections.observableArrayList();
	ObservableList<User> allUsers = FXCollections.observableArrayList();
	ObservableList<Chat> chats = FXCollections.observableArrayList();
	ObservableList<Room> DMs = FXCollections.observableArrayList();*/
	
	public ObservableList<Room> getAllRooms(){
		return allRooms;
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
				System.out.println("Users room: " + room);
			}
			
			myRooms.addAll(usersRooms.values());
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
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void loadRoomsChatLogs() {
		if (selectedRoomID == null || server == null) {
			System.out.println("Room's not selected");
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
			System.out.println(getSelectedRoomID());
			roomsChatLogs.addAll(chatLogsForRoom);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			System.out.println("Is chat log locked?: " + chatLog.isLocked());
			Hashtable<Integer, Chat> chats = chatLog.getChatLog();
			
			if (chats == null) {
				return;
			}
			System.out.println("Loaded chats.");
			chatLogsChats.addAll(chats.values());
			
			for (Chat chat: chatLogsChats) {
				System.out.println(chat);
			}
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void createChat(int roomID, int chatLogID, String message) {
		try
		{
			client.addChat(roomID, chatLogID, message);
			loadChatLogsChats();
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
			client.addChatLog(roomID, chatLogName);
			loadRoomsChatLogs();
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
			loadRoomsChatLogs();
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
			
			loadAllRooms();
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
			loadAllUsers();
			
			//Delete the room
			client.deleteRoom(roomID); 
			selectedRoom.set("");
			selectedRoomID.set(-1);
			loadAllRooms();
			loadMyRooms();
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public boolean logOn() {
		try
		{
			System.out.println("Trying to login:\n" + username + ":" + password);
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
					
					System.out.println("Room:" + room);
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
				loadAllUsers();
				loadAllRooms();
				loadMyRooms();
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
	
	public int getSelectedRoomID() {
		return selectedRoomID.get();
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
			System.out.println("Room's name: " + room.getName());
			System.out.println("Room's ID:" + room.getRoomID());
			Role clientsRole = room.getUser(client.getUserID());
			if (clientsRole == null) {
				System.out.println("No role for user");
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
	/*
	public void initializeDemo() throws RemoteException {
		System.out.println("Initializing demo");
		try
		{
			ServerInterface server = (ServerInterface) Naming.lookup("rmi://127.0.0.1/CONCORD");
			User user = server.addUser("Jibraan", "password123");
			Client jibraansClient = new Client(server, user);
			server.addClient(jibraansClient);
			System.out.println("Adding user");
			RoomList rooms_List = server.getRooms();
			UserList users_List = server.getUsers();
			ArrayList<Room> roomsArrayList = new ArrayList<Room>();
			Room room1 = rooms_List.addRoom("John's Server", "My own personal server!", "http://my_logo.png", true);
			roomsArrayList.add(room1);
			rooms.addAll(roomsArrayList);
			ArrayList<User> usersArrayList = new ArrayList<User>();
			User tom = users_List.addUser("Tom", "luvmymom", "My name is Tom.", false);
			User john = users_List.addUser("John", "hello123", "My name is John.", false);
			server.addUserToRoom(room1.getRoomID(), tom, new Noob());
			server.addUserToRoom(room1.getRoomID(), john, new Admin());
			room1.addUser(tom.getUserID(), new Noob(), users_List, rooms_List);
			room1.addUser(john.getUserID(), new Admin(), users_List, rooms_List);
			usersArrayList.add(tom);
			usersArrayList.add(john);
			users.addAll(usersArrayList);
			
			ArrayList<ChatLog> channelsArrayList = new ArrayList<ChatLog>();
			ChatLog chatsChatLog = server.addChatLog(room1.getRoomID(), john.getUserID(), "Chats");
			channelsArrayList.add(chatsChatLog);
			
			channels.addAll(channelsArrayList);
			
			ArrayList<Chat> chatsArrayList = new ArrayList<Chat>();
			chatsChatLog.addChat("Hey!", tom.getUserID());
			chatsChatLog.addChat("Whats up?", john.getUserID());
			chatsArrayList.add(chatsChatLog.getChat(1));
			chatsArrayList.add(chatsChatLog.getChat(2));
			chats.addAll(chatsArrayList);
			ArrayList<Room> DMsArrayList = new ArrayList<Room>();
			tom.startDirectMessage(rooms_List, users_List, john.getUserID());
			
			Hashtable<Integer, Room> tomsDms = tom.getDirectMessages();
			
			Enumeration<Integer> e = tomsDms.keys();
			
			while (e.hasMoreElements()) {
				int key = e.nextElement();
				Room dm = tomsDms.get(key);
				DMsArrayList.add(dm);
			}
			DMs.addAll(DMsArrayList);
		} catch (MalformedURLException | RemoteException | NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
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
