package ServerClientModel;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Objects;

import Database.Chat;
import Database.ChatLog;
import Database.Role;
import Database.Room;
import Database.RoomList;
import Database.User;
import Database.UserList;

public class Client extends UnicastRemoteObject implements ClientInterface, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	User user;
	ServerInterface connection;
	ArrayList<String> notifications = new ArrayList<String>();

	public Client() throws RemoteException
	{
		this(new Server(), new User());
	}
	
	public Client(ServerInterface server, User user) throws RemoteException
	{
		this.user = user;
		this.connection = server;
	}
	
	public UserList getUsers() throws RemoteException{
		if (isLoggedIn()) {
			return connection.getUsers();
		}
		return null;
	}
	
	public RoomList getRooms() throws RemoteException{
		if (isLoggedIn()) {
			return connection.getRooms();
		}
		return null;
	}
	
	public void setRooms(RoomList rooms) throws RemoteException{
		if (isLoggedIn()) {
			connection.setRooms(rooms);
		}
	}
		
	public void getNotified(String msg) throws RemoteException{
		notifications.add(msg);
	}
	
	public void notifyUser(int userToBeNotifiedID, String notification) throws RemoteException{
		if (isLoggedIn()) {
			connection.notifyUser(userToBeNotifiedID, notification);
		}
	}
	
	public ArrayList<String> getNotifications() throws RemoteException{
		return notifications;
	}

	public boolean isLoggedIn() throws RemoteException{
		return connection.isLoggedIn(user.getUserID());
	}
	
	@Override
	public boolean logOn(String password) throws RemoteException
	{
		return connection.logOn(user.getUserID(), password);
	}

	@Override
	public void logOut() throws RemoteException
	{
		if (isLoggedIn()) {
			connection.logOut(user.getUserID());
		}
	}

	@Override
	public Room startDirectMessage(int userID) throws RemoteException
	{
		return user.startDirectMessage(getRooms(), getUsers(), userID);
	}

	@Override
	public void setProfileData(String profileData) throws RemoteException
	{
		user.setProfileData(profileData);
	}

	@Override
	public void setStatus(boolean status) throws RemoteException
	{
		user.setStatus(status);
	}

	@Override
	public void setProfilePic(String profilePic) throws RemoteException
	{
		user.setProfilePic(profilePic);
	}

	@Override
	public String getProfileData() throws RemoteException
	{
		return user.getProfilePic();
	}

	@Override
	public boolean getStatus() throws RemoteException
	{
		return user.isStatus();
	}

	@Override
	public String getProfilePic() throws RemoteException
	{
		return user.getProfilePic();
	}

	@Override
	public Hashtable<Integer, Room> getDirectMessages() throws RemoteException
	{
		return user.getDirectMessages();
	}

	public ServerInterface getConnection() throws RemoteException
	{
		if (isLoggedIn()) {
			return connection;
		}
		return null;
	}

	public void setConnection(ServerInterface connection) throws RemoteException
	{
		if (isLoggedIn()) {
			this.connection = connection;
		}
	}

	public User getUser() throws RemoteException
	{
		return user;
	}
	
	public void setUser(User user) throws RemoteException
	{
		this.user = user;
	}

	@Override
	public void updateUser(User user) throws RemoteException
	{
		this.user = user;
	}

	@Override
	public User getUser(int userID) throws RemoteException
	{
		if (isLoggedIn()) {
			return connection.getUser(userID);
		}else {
			
		}
		return null;
	}

	public String getPassword() throws RemoteException
	{
		return user.getPassword();
	}
	
	public Chat getChat(int roomID, int chatLogID, int chatID) throws RemoteException
	{
		if (isLoggedIn()) {
			return connection.getChat(roomID, chatLogID, chatID);
		}else {
			
		}
		return null;
	}
	
	public void setPassword(String password) throws RemoteException
	{
		user.setPassword(password);
	}
	
	@Override
	public void addChat(int roomID, int chatLogID, String msg) throws RemoteException
	{
		if (isLoggedIn()) {
			connection.addChat(roomID, chatLogID, user.getUserID(), msg);
		}else {
			
		}
	}

	@Override
	public ChatLog addChatLog(int roomID, String chatLogName) throws RemoteException
	{
		if (isLoggedIn()) {
			return connection.addChatLog(roomID, user.getUserID(), chatLogName);
		}else {
			
		}
		return null;
	}

	@Override
	public void deleteChatLog(int roomID, int chatLogID) throws RemoteException
	{
		if (isLoggedIn()) {
			connection.deleteChatLog(roomID, user.getUserID(), chatLogID);
		}else {
			
		}
	}

	@Override
	public ChatLog getChatLog(int roomID, int chatLogID) throws RemoteException
	{
		if (isLoggedIn()) {
			return connection.getChatLog(roomID, chatLogID);
		}else {
			
		}
		return null;
	}

	@Override
	public Boolean isInvited(int roomID) throws RemoteException
	{
		if (isLoggedIn()) {
			return connection.isInvited(user.getUserID(), roomID);
		}else {
			
		}
		return false;
	}
	
	@Override
	public void inviteUser(int roomID, int addUserID) throws RemoteException
	{
		if (isLoggedIn()) {
			connection.inviteUser(roomID, user.getUserID(), addUserID);
		}else {
			
		}
	}

	@Override
	public void uninviteUser(int roomID, int removeUserID) throws RemoteException
	{
		if (isLoggedIn()) {
			connection.uninviteUser(roomID, user.getUserID(), removeUserID);
		}else {
			
		}
	}

	@Override
	public void addUserToRoom(int roomID, Role role) throws RemoteException
	{
		if (isLoggedIn()) {
			connection.addUserToRoom(roomID, user, role);
		}else {
			
		}
	}

	@Override
	public void removeUserFromRoom(int roomID, int targetUserID) throws RemoteException
	{
		if (isLoggedIn()) {
			connection.removeUserFromRoom(roomID, user.getUserID(), targetUserID);
		}else {
			
		}
	}

	@Override
	public String getRoomDescription(int roomID) throws RemoteException
	{
		if (isLoggedIn()) {
			return connection.getRoomDescription(roomID);
		}else {
			
		}
		return null;
	}

	@Override
	public void setRoomDescription(int roomID, String desc) throws RemoteException
	{
		if (isLoggedIn()) {
			connection.setRoomDescription(roomID, desc);
		}else {
			
		}
	}

	@Override
	public void setRoomLogo(int roomID, String logo) throws RemoteException
	{
		if (isLoggedIn()) {
			connection.setRoomLogo(roomID, logo);
		}else {
			
		}
	}

	@Override
	public String getRoomLogo(int roomID) throws RemoteException
	{
		if (isLoggedIn()) {
			return connection.getRoomLogo(roomID);
		}else {
			
		}
		return null;
	}

	@Override
	public boolean getRoomType(int roomID) throws RemoteException
	{
		if (isLoggedIn()) {
			return connection.getRoomType(roomID);
		}else {
			
		}
		return false;
	}

	@Override
	public void setRoomType(int roomID, Boolean type) throws RemoteException
	{
		if (isLoggedIn()) {
			connection.setRoomType(roomID, user.getUserID(), type);
		}else {
			
		}
	}

	@Override
	public void deleteChat(int roomID, int chatLogID, int chatID) throws RemoteException
	{
		if (isLoggedIn()) {
			connection.deleteChat(roomID, user.getUserID(), chatLogID, chatID);
		}else {
			
		}
	}

	@Override
	public void giveRole(int roomID, int targetUserID, Role role) throws RemoteException
	{
		if (isLoggedIn()) {
			connection.giveRole(roomID, user.getUserID(), targetUserID, role);
		}else {
			
		}
	}

	@Override
	public ArrayList<User> getRoomOnlineUsers(int roomID) throws RemoteException
	{
		if (isLoggedIn()) {
			return connection.getRoomOnlineUsers(roomID);
		}else {
			
		}
		return null;
	}

	@Override
	public ArrayList<User> getRoomOfflineUsers(int roomID) throws RemoteException
	{
		if (isLoggedIn()) {
			return connection.getRoomOfflineUsers(roomID);
		}else {
			
		}
		return null;
	}

	@Override
	public Hashtable<Integer, Role> getAllRoomMembers(int roomID) throws RemoteException
	{
		if (isLoggedIn()) {
			return connection.getAllRoomMembers(roomID);
		}else {
			
		}
		return null;
	}

	@Override
	public void chatLogReply(String msg, int receiverID, int roomID, int chatLogID) throws RemoteException
	{
		if (isLoggedIn()) {
			connection.chatLogReply(msg, user.getUserID(), receiverID, roomID, chatLogID);
		}else {
			
		}
	}

	@Override
	public void pinMsg(int roomID, int chatLogID, int chatID) throws RemoteException
	{
		if (isLoggedIn()) {
			connection.pinMsg(roomID, chatLogID, chatID);
		}else {
			
		}
	}
	
	@Override
	public void unpinMsg(int roomID, int chatLogID, int chatID) throws RemoteException
	{
		if (isLoggedIn()) {
			connection.unpinMsg(roomID, chatLogID, chatID);
		}else {
			
		}
	}

	@Override
	public ArrayList<Chat> getPinnedMsgs(int roomID, int chatLogID) throws RemoteException
	{
		if (isLoggedIn()) {
			return connection.getPinnedMsgs(roomID, chatLogID);
		}else {
			
		}
		return null;
	}

	@Override
	public Room getRoom(int roomID) throws RemoteException
	{
		if (isLoggedIn()) {
			return connection.getRoom(roomID);
		}else {
			
		}
		return null;
	}

	@Override
	public Role getRole(int roomID) throws RemoteException
	{
		if (isLoggedIn()) {
			return connection.getRole(roomID, user.getUserID());
		}else {
			
		}
		return null;
	}

	@Override
	public void addClient(ClientInterface c) throws RemoteException
	{
		if (isLoggedIn()) {
			connection.addClient(c);
		}else {
			
		}
	}

	@Override
	public void storeDataDisk() throws RemoteException
	{
		if (isLoggedIn()) {
			connection.storeDataDisk();
		}else {
			
		}
	}

	@Override
	public ServerInterface readDataFromDisk() throws RemoteException
	{
		if (isLoggedIn()) {
			return connection.readDataFromDisk();
		}else {
			
		}
		return null;
	}

	@Override
	public Room addRoom() throws RemoteException
	{
		if (isLoggedIn()) {
			return connection.addRoom();
		}else {
			
		}
		return null;
	}

	@Override
	public Room addRoom(String name) throws RemoteException
	{
		if (isLoggedIn()) {
			return connection.addRoom(name);
		}else {
			
		}
		return null;
	}

	@Override
	public Room addRoom(String name, String description) throws RemoteException
	{
		if (isLoggedIn()) {
			return connection.addRoom(name, description);
		}else {
			
		}
		return null;
	}

	@Override
	public Room addRoom(String name, String description, String logo) throws RemoteException
	{
		if (isLoggedIn()) {
			return connection.addRoom(name, description, logo);
		}else {
			
		}
		return null;
	}

	@Override
	public Room addRoom(String name, String description, String logo, boolean roomType) throws RemoteException
	{
		if (isLoggedIn()) {
			return connection.addRoom(name, description, logo, roomType);
		}else {
			
		}
		return null;
	}

	@Override
	public void addRoom(Room room) throws RemoteException
	{
		if (isLoggedIn()) {
			connection.addRoom(room);
		}else {
			
		}
	}

	@Override
	public void deleteRoom(int roomID) throws RemoteException
	{
		if (isLoggedIn()) {
			connection.deleteRoom(user.getUserID(), roomID);
		}else {
			
		}
	}

	@Override
	public void blockUser(int targetUserID) throws RemoteException
	{
		if (isLoggedIn()) {
			connection.blockUser(user.getUserID(), targetUserID);
		}else {
			
		}
	}

	@Override
	public void unblockUser(int targetUserID) throws RemoteException
	{
		if (isLoggedIn()) {
			connection.unblockUser(user.getUserID(), targetUserID);
		}else {
			
		}
	}

	@Override
	public void endDirectMessage(int userID) throws RemoteException
	{
		user.endDirectMessage(userID);
	}
	
	public int getUserID() throws RemoteException
	{
		return user.getUserID();	
	}
	
	public void deleteAllMessagesByUser(int roomID, int chatLogID) throws RemoteException
	{
		if (isLoggedIn()) {
			connection.deleteAllMessagesByUser(user.getUserID(), roomID, chatLogID);
		}else {
			
		}
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(connection, notifications, user);
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
		Client other = (Client) obj;
		
		try
		{
			return  Objects.equals(notifications, other.getNotifications())
					&& Objects.equals(user, other.getUser());
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
