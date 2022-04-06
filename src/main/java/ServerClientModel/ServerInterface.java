package ServerClientModel;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;

import Database.Chat;
import Database.ChatLog;
import Database.Role;
import Database.Room;
import Database.RoomList;
import Database.User;
import Database.UserList;

public interface ServerInterface extends Remote
{
	public Room addRoom() throws RemoteException;
	public Room addRoom(String name) throws RemoteException;
	public Room addRoom(String name, String description) throws RemoteException;
	public Room addRoom(String name, String description, String logo) throws RemoteException;
	public Room addRoom(String name, String description, String logo, boolean roomType) throws RemoteException;
	public void addRoom(Room room) throws RemoteException;
	public void deleteRoom(int userID, int roomID) throws RemoteException;
	public void addClient(Client c) throws RemoteException;
	public void removeClient(int userID) throws RemoteException;
	public void updateUser(User user) throws RemoteException;
	public boolean logOn(int userID, String password) throws RemoteException;
	public void storeDataDisk() throws RemoteException;
	public void blockUser(int userID, int targetUserID) throws RemoteException;
	public void unblockUser(int userID, int targetUserID) throws RemoteException;
	public ServerInterface readDataFromDisk() throws RemoteException;
	public void logOut(int userID) throws RemoteException;
	public boolean isLoggedIn(int userID) throws RemoteException;
	public void notifyUser(int userID, String notification) throws RemoteException;
	public User getUser(int userID) throws RemoteException;
	public void addChat(int roomID, int chatLogID, int userID, String msg) throws RemoteException;
	public ChatLog addChatLog(int roomID, int userID, String chatLogName) throws RemoteException;
	public void deleteChatLog(int roomID, int userID, int chatLogID) throws RemoteException;
	public ChatLog getChatLog(int roomID, int chatLogID) throws RemoteException;
	public Boolean isInvited(int userID, int roomID) throws RemoteException;
	public void inviteUser(int roomID, int userID, int addUserID) throws RemoteException;
	public void uninviteUser(int roomID, int userID, int removeUserID) throws RemoteException;
	public void addUserToRoom(int roomID, User user, Role role) throws RemoteException;
	public void removeUserFromRoom(int roomID, int removerUserID, int userID) throws RemoteException;
	public String getRoomDescription(int roomID) throws RemoteException;
	public void setRoomDescription(int roomID, String desc) throws RemoteException;
	public void setRoomLogo(int roomID, String logo) throws RemoteException;
	public void deleteAllMessagesByUser(int userID, int roomID, int chatLogID) throws RemoteException;
	public String getRoomLogo(int roomID) throws RemoteException;
	public Hashtable<Integer, Client> getClients() throws RemoteException;
	public boolean getRoomType(int roomID) throws RemoteException;
	public void setRoomType(int roomID, int userID, Boolean type) throws RemoteException;
	public void deleteChat(int roomID, int userID, int chatLogID, int chatID) throws RemoteException;
	public void giveRole(int roomID, int giverID, int userID, Role role) throws RemoteException;
	public ArrayList<User> getRoomOnlineUsers(int roomID) throws RemoteException;
	public ArrayList<User> getRoomOfflineUsers(int roomID) throws RemoteException;
	public Hashtable<Integer, Boolean> getUserLogins() throws RemoteException;
	public Hashtable<Integer, Role> getAllRoomMembers(int roomID) throws RemoteException;
	public void setChatLogLocked(int roomID, int userID, int chatLogID) throws RemoteException;
	public Chat getChat(int roomID, int chatLogID, int chatID) throws RemoteException;
	public Hashtable<Integer, String> getId_passwords() throws RemoteException;
	public void setChatLogUnlocked(int roomID, int userID, int chatLogID) throws RemoteException;
	public void chatLogReply(String msg, int replierID, int receiverID, int roomID, int chatLogID) throws RemoteException;
	public ArrayList<Chat> getPinnedMsgs(int roomID, int chatLogID) throws RemoteException;
	public void setRooms(RoomList rooms) throws RemoteException;
	public RoomList getRooms() throws RemoteException;
	public void setUsers(UserList users) throws RemoteException;
	public UserList getUsers() throws RemoteException;
	public Room getRoom(int roomID) throws RemoteException;
	public Role getRole(int roomID, int userID) throws RemoteException;
	public void pinMsg(int roomID, int chatLogID, int chatID) throws RemoteException;
	public void unpinMsg(int roomID, int chatLogID, int chatID) throws RemoteException;
}
