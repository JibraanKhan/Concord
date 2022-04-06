package ServerClientModel;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Objects;

import Database.Chat;
import Database.ChatLog;
import Database.Role;
import Database.Room;
import Database.RoomList;
import Database.User;
import Database.UserList;

public class Server extends UnicastRemoteObject implements ServerInterface
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Hashtable<Integer, Client> clients = new Hashtable<Integer, Client>();
	Hashtable<Integer, Boolean> userLogins = new Hashtable<Integer, Boolean>();
	RoomList rooms = new RoomList();
	UserList users = new UserList();
	Hashtable<Integer, String> id_passwords = new Hashtable<Integer, String>();
	Registry registry = LocateRegistry.createRegistry(1202);

	private static final String SERIALIZED_FILE_NAME = "ConcordData.xml";

	public Server() throws RemoteException {
		registry.rebind("SomethingElse", this);
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
		rooms.deleteRoom(userID, roomID);
	}
	
	@Override
	public void addClient(Client c) throws RemoteException
	{
		//Add client to clients and adds the user's password and userID to the id_passwords hash map 
		User clientUser = c.getUser();
		int userID = clientUser.getUserID();
		clients.put(userID, c);
		id_passwords.put(clientUser.getUserID(), clientUser.getPassword());
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

	@Override
	public boolean logOn(int userID, String password) throws RemoteException
	{
		//Authenticates client
		userLogins.put(userID, true);
		if (id_passwords.get(userID) == password) {
			return true;
		}else {
			return false;
		}
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
		encoder.writeObject(this);
		encoder.close();
	}

	@Override
	public ServerInterface readDataFromDisk() throws RemoteException
	{
		XMLDecoder decoder=null;
		
		try {
			decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(SERIALIZED_FILE_NAME)));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File " + SERIALIZED_FILE_NAME + " not found");
		}
		ServerInterface server=(ServerInterface)decoder.readObject();
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
		Client clientToBeNotified = clients.get(userID);
		try
		{
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

	@Override
	public void addChat(int roomID, int chatLogID, int userID, String msg) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		room.addChat(msg, chatLogID, userID);
	}

	@Override
	public ChatLog addChatLog(int roomID, int userID, String chatLogName) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		return room.addChatLog(userID, chatLogName);
	}

	@Override
	public void deleteChatLog(int roomID, int userID, int chatLogID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		room.deleteChatLog(userID, chatLogID);
	}

	@Override
	public ChatLog getChatLog(int roomID, int chatLogID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		return room.getChatLog(chatLogID);
	}

	@Override
	public void addUserToRoom(int roomID, User user, Role role) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		room.addUser(user.getUserID(), role, users, rooms);
	}

	@Override
	public void removeUserFromRoom(int roomID, int removerUserID, int userID) throws RemoteException
	{
		Room room = rooms.getRoom(roomID);
		room.removeUser(removerUserID, userID);
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
	
	public Hashtable<Integer, Client> getClients() throws RemoteException
	{
		return clients;
	}

	public void setClients(Hashtable<Integer, Client> clients) throws RemoteException
	{
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

	public Hashtable<Integer, String> getId_passwords() throws RemoteException
	{
		return id_passwords;
	}

	public void setId_passwords(Hashtable<Integer, String> id_passwords) throws RemoteException
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
		ChatLog chatLog = room.getChatLog(chatLogID);
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
}
