package ServerClientModel;

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

public class Client extends UnicastRemoteObject implements ClientInterface
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
		return connection.getUsers();
	}
	
	public RoomList getRooms() throws RemoteException{
		return connection.getRooms();
	}
	
	public void setRooms(RoomList rooms) throws RemoteException{
		connection.setRooms(rooms);
	}
		
	public void getNotified(String msg) throws RemoteException{
		notifications.add(msg);
	}
	
	public void notifyUser(int userToBeNotifiedID, String notification) throws RemoteException{
		connection.notifyUser(userToBeNotifiedID, notification);
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
		connection.logOut(user.getUserID());
	}

	@Override
	public void addRoom(int roomID) throws RemoteException
	{
		// TODO Auto-generated method stub
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
		return connection;
	}

	public void setConnection(ServerInterface connection) throws RemoteException
	{
		this.connection = connection;
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
		return connection.getUser(userID);
	}

	public String getPassword() throws RemoteException
	{
		return user.getPassword();
	}
	
	public Chat getChat(int roomID, int chatLogID, int chatID) throws RemoteException
	{
		return connection.getChat(roomID, chatLogID, chatID);
	}
	
	public void setPassword(String password) throws RemoteException
	{
		user.setPassword(password);
	}
	
	@Override
	public void addChat(int roomID, int chatLogID, String msg) throws RemoteException
	{
		connection.addChat(roomID, chatLogID, user.getUserID(), msg);
	}

	@Override
	public ChatLog addChatLog(int roomID, String chatLogName) throws RemoteException
	{
		return connection.addChatLog(roomID, user.getUserID(), chatLogName);
	}

	@Override
	public void deleteChatLog(int roomID, int chatLogID) throws RemoteException
	{
		connection.deleteChatLog(roomID, user.getUserID(), chatLogID);
	}

	@Override
	public ChatLog getChatLog(int roomID, int chatLogID) throws RemoteException
	{
		return connection.getChatLog(roomID, chatLogID);
	}

	@Override
	public Boolean isInvited(int roomID) throws RemoteException
	{
		return connection.isInvited(user.getUserID(), roomID);
	}
	
	@Override
	public void inviteUser(int roomID, int addUserID) throws RemoteException
	{
		connection.inviteUser(roomID, user.getUserID(), addUserID);
	}

	@Override
	public void uninviteUser(int roomID, int removeUserID) throws RemoteException
	{
		connection.uninviteUser(roomID, user.getUserID(), removeUserID);
	}

	@Override
	public void addUserToRoom(int roomID, Role role) throws RemoteException
	{
		connection.addUserToRoom(roomID, user, role);
	}

	@Override
	public void removeUserFromRoom(int roomID, int targetUserID) throws RemoteException
	{
		connection.removeUserFromRoom(roomID, user.getUserID(), targetUserID);
	}

	@Override
	public String getRoomDescription(int roomID) throws RemoteException
	{
		return connection.getRoomDescription(roomID);
	}

	@Override
	public void setRoomDescription(int roomID, String desc) throws RemoteException
	{
		connection.setRoomDescription(roomID, desc);
	}

	@Override
	public void setRoomLogo(int roomID, String logo) throws RemoteException
	{
		connection.setRoomLogo(roomID, logo);
	}

	@Override
	public String getRoomLogo(int roomID) throws RemoteException
	{
		return connection.getRoomLogo(roomID);
	}

	@Override
	public boolean getRoomType(int roomID) throws RemoteException
	{
		return connection.getRoomType(roomID);
	}

	@Override
	public void setRoomType(int roomID, Boolean type) throws RemoteException
	{
		connection.setRoomType(roomID, user.getUserID(), type);
	}

	@Override
	public void deleteChat(int roomID, int chatLogID, int chatID) throws RemoteException
	{
		connection.deleteChat(roomID, user.getUserID(), chatLogID, chatID);
	}

	@Override
	public void giveRole(int roomID, int targetUserID, Role role) throws RemoteException
	{
		connection.giveRole(roomID, user.getUserID(), targetUserID, role);
	}

	@Override
	public ArrayList<User> getRoomOnlineUsers(int roomID) throws RemoteException
	{
		return connection.getRoomOnlineUsers(roomID);
	}

	@Override
	public ArrayList<User> getRoomOfflineUsers(int roomID) throws RemoteException
	{
		return connection.getRoomOfflineUsers(roomID);
	}

	@Override
	public Hashtable<Integer, Role> getAllRoomMembers(int roomID) throws RemoteException
	{
		return connection.getAllRoomMembers(roomID);
	}

	@Override
	public void chatLogReply(String msg, int receiverID, int roomID, int chatLogID) throws RemoteException
	{
		connection.chatLogReply(msg, user.getUserID(), receiverID, roomID, chatLogID);
	}

	@Override
	public void pinMsg(int roomID, int chatLogID, int chatID) throws RemoteException
	{
		connection.pinMsg(roomID, chatLogID, chatID);
	}
	
	@Override
	public void unpinMsg(int roomID, int chatLogID, int chatID) throws RemoteException
	{
		connection.unpinMsg(roomID, chatLogID, chatID);
	}

	@Override
	public ArrayList<Chat> getPinnedMsgs(int roomID, int chatLogID) throws RemoteException
	{
		return connection.getPinnedMsgs(roomID, chatLogID);
	}

	@Override
	public Room getRoom(int roomID) throws RemoteException
	{
		return connection.getRoom(roomID);
	}

	@Override
	public Role getRole(int roomID) throws RemoteException
	{
		return connection.getRole(roomID, user.getUserID());
	}

	@Override
	public void addClient(Client c) throws RemoteException
	{
		connection.addClient(c);
	}

	@Override
	public void storeDataDisk() throws RemoteException
	{
		connection.storeDataDisk();
	}

	@Override
	public ServerInterface readDataFromDisk() throws RemoteException
	{
		return connection.readDataFromDisk();
	}

	@Override
	public Room addRoom() throws RemoteException
	{
		return connection.addRoom();
	}

	@Override
	public Room addRoom(String name) throws RemoteException
	{
		return connection.addRoom(name);
	}

	@Override
	public Room addRoom(String name, String description) throws RemoteException
	{
		return connection.addRoom(name, description);
	}

	@Override
	public Room addRoom(String name, String description, String logo) throws RemoteException
	{
		return connection.addRoom(name, description, logo);
	}

	@Override
	public Room addRoom(String name, String description, String logo, boolean roomType) throws RemoteException
	{
		return connection.addRoom(name, description, logo, roomType);
	}

	@Override
	public void addRoom(Room room) throws RemoteException
	{
		connection.addRoom(room);
	}

	@Override
	public void deleteRoom(int roomID) throws RemoteException
	{
		connection.deleteRoom(user.getUserID(), roomID);
	}

	@Override
	public void blockUser(int targetUserID) throws RemoteException
	{
		connection.blockUser(user.getUserID(), targetUserID);
	}

	@Override
	public void unblockUser(int targetUserID) throws RemoteException
	{
		connection.unblockUser(user.getUserID(), targetUserID);
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
		connection.deleteAllMessagesByUser(user.getUserID(), roomID, chatLogID);
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
