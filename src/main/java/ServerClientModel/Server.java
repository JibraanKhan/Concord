package ServerClientModel;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Objects;

import Database.Bot;
import Database.Chat;
import Database.ChatLog;
import Database.Role;
import Database.Room;
import Database.RoomList;
import Database.User;
import Database.UserList;

public class Server extends UnicastRemoteObject implements ServerInterface, Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Hashtable<Integer, ClientInterface> clients = new Hashtable<Integer, ClientInterface>();
	Hashtable<Integer, Boolean> userLogins = new Hashtable<Integer, Boolean>();
	RoomList rooms = new RoomList();
	UserList users = new UserList();
	Hashtable<String, String> id_passwords = new Hashtable<String, String>();
	

	private static final String SERIALIZED_FILE_NAME = "ConcordData.xml";

	public Server() throws RemoteException {
	}
	
	public void addRoom(Room room) throws RemoteException
	{
		rooms.addRoom(room);
	}
	
	public Room addRoom(String name, String description, String logo, boolean roomType) throws RemoteException
	{
		return rooms.addRoom(name, description, logo, roomType);
	}
	
	public Room addRoom(String name, String description, String logo) throws RemoteException
	{
		return rooms.addRoom(name, description, logo);
	}
	
	public Room addRoom(String name, String description) throws RemoteException
	{
		return rooms.addRoom(name, description);
	}
	
	public Room addRoom(String name) throws RemoteException
	{
		return rooms.addRoom(name);
	}
	
	public Room addRoom() throws RemoteException
	{
		return rooms.addRoom();
	}
	
	public void deleteRoom(int userID, int roomID) throws RemoteException
	{
		rooms.deleteRoom(userID, roomID, users);
	}
	
	@Override
	public void addClient(ClientInterface c) throws RemoteException
	{
		//Add client to clients and adds the user's password and userID to the id_passwords hash map 
		User clientUser = c.getUser();
		//System.out.println(clientUser + ":" + clientUser.getUserID() + ":" + clientUser.getUserName() + ":" + clientUser.getPassword());
		int userID = clientUser.getUserID();
		clients.put(userID, c);
		id_passwords.put(clientUser.getUserName(), clientUser.getPassword());
		if (getUser(clientUser.getUserName(), clientUser.getPassword()) == null) {
			addUser(clientUser);
		}
		//System.out.println(clientUser.getUserName() + "\n\t" + clientUser.getPassword());
	}

	@Override
	public void registerBot(int roomID, Bot botToRegister) throws RemoteException{
		Room room = rooms.getRoom(roomID);
		if (room == null) {
			return;
		}
		
		room.registerBot(botToRegister);
	}
	
	@Override
	public void setProfileData(int userID, String profileData) {
		User user = users.getUser(userID);
		if (user == null) { 
			return;
		}
		
		user.setProfileData(profileData);
	}
	
	@Override
	public void setStatus(int userID, boolean status) {
		User user = users.getUser(userID);
		if (user == null) { 
			return;
		}
		
		user.setStatus(status);
	}
	
	public void removeClient(int userID) throws RemoteException
	{
		clients.remove(userID);
	}
	
	@Override
	public void updateUser(User user) throws RemoteException
	{
		int userID = user.getUserID();
		// Updates the user inside of UserList
		User otherUser = users.getUser(userID);
		if (otherUser != null) {
			users.setUser(user, userID);
		}
	}

	public void changeUsersPassword(String username, String old_password, String new_password) throws RemoteException
	{
		if (id_passwords.get(username) != null) {
			
			id_passwords.put(username, new_password);
			User user = users.getUser(username, old_password);
			if (user != null) {
				//System.out.println("Changing password");
				user.setPassword(new_password);
			}
		}
	}
	
	public void changeUsersUsername(String old_username, String new_username, String password) throws RemoteException
	{
		//System.out.println("The old username is:" + old_username);
		if (id_passwords.get(old_username) != null) {
			//System.out.println("Ok, the username exists!");
			id_passwords.put(new_username, password);
			id_passwords.remove(old_username); //Remove because we are using different key above
			//System.out.println(old_username + ":" + password);
			User user = users.getUser(old_username, password);
			if (user != null) {
				//System.out.println("Changing username");
				user.setUserName(new_username);
			}
		}
	}
	
	@Override
	public boolean logOn(String username, String password) throws RemoteException
	{
		//Authenticates client
		//System.out.println("Password is:" + password);
		//System.out.println("Logging in id_password is:" + id_passwords.get(username));
		if ((id_passwords.get(username) != null) && (id_passwords.get(username).equals(password))) {
			//System.out.println("Inside if statement");
			User user = users.getUser(username, password);
			//System.out.println("Special: " + username + "\n\t" + password);
			//System.out.println(username + "'s user: " + user);
			if (user != null) {
				//System.out.println(username + " just logged in.");
				userLogins.put(user.getUserID(), true);
				return true;
			}
			return false;
		}else {
//			if (users.getUser(username) != null) {
//				//System.out.println("Password, " + password + ", is wrong for " + userID + ": " +users.getUser(userID).getUserName() +"." + id_passwords.get(userID));
//				//System.out.println(id_passwords.get(userID) + ", " + password + ", " + id_passwords.get(userID) == password);
//				if (id_passwords.get(username) == null) {
//					//System.out.println("\t" + users.getUser(userID).getUserID() + ": " + users.getUser(userID).getUserName() + " does not have a password set.");
//				} else {
//					//System.out.println(userID + ", " + id_passwords.get(userID));
//					//System.out.println(id_passwords.get(userID).equals("uhgraw"));
//				}
//			}
			return false;
		}
	}

	public void eraseData() throws RemoteException{
		clients.clear();
		userLogins.clear();
		rooms = new RoomList();
		users = new UserList();
		id_passwords.clear();
	}
	
	@Override
	public void storeDataDisk() throws RemoteException
	{
		XMLEncoder encoder=null;
		
		try{
		encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream(SERIALIZED_FILE_NAME)));
		}catch(FileNotFoundException fileNotFound){
			System.out.println("ERROR: While Creating or Opening the File " + SERIALIZED_FILE_NAME);
		}
		userLogins = new Hashtable<Integer, Boolean>(); //Do not want to store userLogins since all users will be considered logged off when server logs back in.
		encoder.writeObject(this);
		encoder.close();
	}

	@Override
	public Server readDataFromDisk() throws RemoteException
	{
		XMLDecoder decoder=null;
		
		try {
			decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(SERIALIZED_FILE_NAME)));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File " + SERIALIZED_FILE_NAME + " not found");
		}
		Server server= (Server) decoder.readObject();
		return server;
	}

	@Override
	public void logOut(int userID) throws RemoteException
	{
		userLogins.put(userID, false);
	}

	@Override
	public void notifyUser(int userID, String notification) throws RemoteException
	{
		ClientInterface clientToBeNotified = clients.get(userID);
		if (clientToBeNotified == null) {
			return;
		}
		try
		{
			//System.out.println("Notifying user from server:\n " + clientToBeNotified + "\n" + notification);
			clientToBeNotified.getNotified(notification);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public User getUser(int userID) throws RemoteException
	{
		return users.getUser(userID);
	}

	public Room startDirectMessage(int user1ID, int user2ID) throws RemoteException {
		User user1 = users.getUser(user1ID);
		if (user1 == null) {
			return null;
		}
		return user1.startDirectMessage(rooms, users, user2ID);
	}
	
	@Override
	public Chat addChat(int roomID, int chatLogID, int userID, String msg) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		if (room == null) {
			System.out.println("Room was null");
			return null;
		}
		User user = users.getUser(userID);
		if (user == null) {
			System.out.println("User was null");
			return null;
		}
		Chat chat = room.addChat(msg, chatLogID, userID, user.getUserName());
		return chat;
	}

	@Override
	public ChatLog addChatLog(int roomID, int userID, String chatLogName) throws RemoteException
	{
		//System.out.println("Adding chatlog.");
		//System.out.println(roomID + ":" + userID + ":" + chatLogName);
		Room room = rooms.getRoom(roomID);
		if (room == null) {
			return null;
		}
		ChatLog addedChatLog = room.addChatLog(userID, chatLogName);
		//System.out.println(addedChatLog);
		return addedChatLog;
	}

	@Override
	public void deleteChatLog(int roomID, int userID, int chatLogID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		if (room == null) {
			return;
		}
		room.deleteChatLog(userID, chatLogID);
	}

	@Override
	public ChatLog getChatLog(int roomID, int chatLogID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		return room.getChatLog(chatLogID);
	}

	public ArrayList<ChatLog> getAllChatLogs(int roomID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		//System.out.println("Room is:" + room);
		if (room == null) {
			return null;
		}
		Hashtable<Integer, ChatLog> chatlogsHash = room.getChatLogs();
		ArrayList<ChatLog> chatlogs = new ArrayList<ChatLog>();
		
		Enumeration<Integer> e = chatlogsHash.keys();
		
		while (e.hasMoreElements()) {
			int key = e.nextElement();
			ChatLog currentChatLog = chatlogsHash.get(key);
			chatlogs.add(currentChatLog);
		}
		
		return chatlogs;
	}
	
	public void addUser(User user) {
		users.addUser(user.getUserName(), user.getPassword(), user.getProfileData(), user.isStatus(), user.getProfilePic());
	}
	
	public User addUser() { // Maybe add parameters for user information? 
		User user = users.addUser();
		return user;
	}
	
	public User addUser(String password) { // Maybe add parameters for user information?  
		User user = users.addUser(password);
		return user;
	}
	
	public User addUser(String userName, String password) { // Maybe add parameters for user information?  
		User user = users.addUser(userName, password);
		return user;
	}
	
	public User addUser(String userName, String password, String profileData) { // Maybe add parameters for user information?  
		User user = users.addUser(userName, password, profileData);
		return user;
	}
	
	public User addUser(String userName, String password, String profileData, boolean status) { // Maybe add parameters for user information?  
		User user = users.addUser(userName, password, profileData, status);
		//System.out.println("Added user for " + userName + ":" + user);
		//System.out.println("User exists for " + userName + ":" + users.getUser(userName, password));
		return user;
	}
	
	public User addUser(String userName, String password, String profileData, boolean status, String profilePic) { // Maybe add parameters for user information?  
		User user = users.addUser(userName, password, profileData, status, profilePic);
		return user;
	}
	@Override
	public void addUserToRoom(int roomID, User user, Role role) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		if (room == null) {
			return;
		}
		room.addUser(user.getUserID(), role, users, rooms);
	}

	@Override
	public void removeUserFromRoom(int roomID, int removerUserID, int userID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		if (room == null) {
			//Its ok, we just didn't find a room with the user in it.
			return;
		}
		
		room.removeUser(removerUserID, userID);
		User user = users.getUser(userID);
		if (user == null) {
			//Its ok, we just couldn't find a user with the userID 
			return;
		}
		//System.out.println("User that's being removed from room: " + user);
		user.removeRoom(roomID);
	}
	
	@Override
	public void inviteUser(int roomID, int userID, int addUserID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		room.inviteUser(userID, addUserID);
	}

	@Override
	public void uninviteUser(int roomID, int userID, int removeUserID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		room.uninviteUser(userID, removeUserID);
	}

	@Override
	public String getRoomDescription(int roomID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		
		return room.getDescription();
	}

	@Override
	public void setRoomDescription(int roomID, String desc) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		room.setDescription(desc);
	}

	@Override
	public void setRoomLogo(int roomID, String logo) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		room.setLogo(logo);
	}

	@Override
	public String getRoomLogo(int roomID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		return room.getLogo();
	}

	@Override
	public boolean getRoomType(int roomID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		return room.getRoomType();
	}

	@Override
	public void setRoomType(int roomID, int userID, Boolean type) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		room.setRoomType(userID, type);
	}

	@Override
	public void deleteChat(int roomID, int userID, int chatLogID, int chatID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		room.deleteChat(userID, chatLogID, chatID);
	}

	@Override
	public void giveRole(int roomID, int giverID, int userID, Role role) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		room.giveRole(giverID, userID, role);
	}

	@Override
	public ArrayList<User> getRoomOnlineUsers(int roomID) throws RemoteException
	{
		ArrayList<User> onlineUsers = new ArrayList<User>();
		Enumeration<Integer> onlineUserIDs = userLogins.keys();
		while (onlineUserIDs.hasMoreElements()) {
			int userID = onlineUserIDs.nextElement();
			try
			{
				onlineUsers.add(clients.get(userID).getUser());
			} catch (RemoteException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return onlineUsers;
	}

	@Override
	public ArrayList<User> getRoomOfflineUsers(int roomID) throws RemoteException
	{
		ArrayList<User> offlineUsers = new ArrayList<User>();
		Enumeration<Integer> clientsInServerUserIDs = clients.keys();
		while (clientsInServerUserIDs.hasMoreElements()) {
			int userID = clientsInServerUserIDs.nextElement();
			if (userLogins.get(userID) == null) { //If user is not logged in.
				try
				{
					offlineUsers.add(clients.get(userID).getUser());
				} catch (RemoteException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return offlineUsers;
	}

	public boolean isLoggedIn(int userID) throws RemoteException
	{	
		if (userLogins.get(userID) != null) {
			return true;
		}else {
			if (users.getUser(userID) != null) {
				//System.out.println("User isn't logged in " + users.getUser(userID).getUserName());
			}else {
				//System.out.println("User doesn't exist.");
			}
			
		}
		
		return false;
	}
	
	@Override
	public Hashtable<Integer, Role> getAllRoomMembers(int roomID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		return room.getUserTable();
	}

	@Override
	public void setChatLogLocked(int roomID, int userID, int chatLogID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		room.lockChatLog(userID, roomID, chatLogID, rooms);
	}
	
	@Override
	public void setChatLogUnlocked(int roomID, int userID, int chatLogID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		room.unlockChatLog(userID, roomID, chatLogID, rooms);
	}

	@Override
	public void chatLogReply(String msg, int replierID, int receiverID, int roomID, int chatLogID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		room.addChat(msg, chatLogID, replierID, receiverID);
	}

	@Override
	public void pinMsg(int roomID, int chatLogID, int chatID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		ChatLog chatLog = room.getChatLog(chatLogID);
		chatLog.pinMessage(chatID);
	}
	
	@Override
	public void unpinMsg(int roomID, int chatLogID, int chatID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		ChatLog chatLog = room.getChatLog(chatLogID);
		chatLog.unpinMessage(chatID);
	}

	@Override
	public ArrayList<Chat> getPinnedMsgs(int roomID, int chatLogID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		ChatLog chatLog = room.getChatLog(chatLogID);
		return chatLog.getPinned();
	}

	@Override
	public Room getRoom(int roomID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		
		return room;
	}

	@Override
	public Role getRole(int roomID, int userID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		return room.getUser(userID);
	}
	
	public Hashtable<Integer, ClientInterface> getClients() throws RemoteException
	{
		return clients;
	}

	public void setClients(Hashtable<Integer, ClientInterface> clients) throws RemoteException
	{
		//Server.clients = clients;
		this.clients = clients;
	}

	public Hashtable<Integer, Boolean> getUserLogins() throws RemoteException
	{
		return userLogins;
	}

	public void setUserLogins(Hashtable<Integer, Boolean> userLogins) throws RemoteException
	{
		this.userLogins = userLogins;
	}

	public RoomList getRooms() throws RemoteException
	{
		return rooms;
	}

	public void setRooms(RoomList rooms) throws RemoteException
	{
		this.rooms = rooms;
	}

	public UserList getUsers() throws RemoteException
	{
		return users;
	}

	public void setUsers(UserList users) throws RemoteException
	{
		this.users = users;
	}

	public Hashtable<String, String> getId_passwords() throws RemoteException
	{
		return id_passwords;
	}

	public void setId_passwords(Hashtable<String, String> id_passwords) throws RemoteException
	{
		this.id_passwords = id_passwords;
	}

	public static String getSerializedFileName() throws RemoteException
	{
		return SERIALIZED_FILE_NAME;
	}

	@Override
	public void blockUser(int userID, int targetUserID) throws RemoteException
	{
		User user = users.getUser(userID);
		user.blockUser(targetUserID);
	}

	@Override
	public void unblockUser(int userID, int targetUserID) throws RemoteException
	{
		User user = users.getUser(userID);
		user.unblockUser(targetUserID);
	}
	
	public Chat getChat(int roomID, int chatLogID, int chatID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		if (room == null) {
			return null;
		}
		
		ChatLog chatLog = room.getChatLog(chatLogID);
		
		
		if (chatLog == null) {
			return null;
		}
		return chatLog.getChat(chatID);
	}

	@Override
	public void deleteAllMessagesByUser(int userID, int roomID, int chatLogID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		ChatLog chatLog = room.getChatLog(chatLogID);
		
		chatLog.deleteAllMessagesByUser(userID);
	}
	
	public Boolean isInvited(int userID, int roomID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		return room.isInvited(userID);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(clients, id_passwords, rooms, userLogins, users);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Server other = (Server) obj;
		try
		{
			return clients.equals(other.getClients()) && id_passwords.equals(other.getId_passwords())
					&& rooms.equals(other.getRooms()) && userLogins.equals(other.getUserLogins())
					&& users.equals(other.getUsers());
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public User getUser(String username, String password) throws RemoteException
	{
		// TODO Auto-generated method stub
		return users.getUser(username, password);
	}

	@Override
	public boolean getStatus(int userID) throws RemoteException
	{
		User user = users.getUser(userID);
		if (user == null) { 
			return false;
		}
		
		return user.isStatus();
	}
}
