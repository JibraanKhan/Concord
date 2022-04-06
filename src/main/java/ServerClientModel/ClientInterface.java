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

public interface ClientInterface extends Remote
{
	public Room addRoom() throws RemoteException;
	public Room addRoom(String name) throws RemoteException;
	public Room addRoom(String name, String description) throws RemoteException;
	public Room addRoom(String name, String description, String logo) throws RemoteException;
	public Room addRoom(String name, String description, String logo, boolean roomType) throws RemoteException;
	public void addRoom(Room room) throws RemoteException;
	public void deleteRoom(int roomID) throws RemoteException;
	public void addClient(Client c) throws RemoteException;
	public void storeDataDisk() throws RemoteException;
	public ServerInterface readDataFromDisk() throws RemoteException;
	public boolean logOn(String password) throws RemoteException;
	public void logOut() throws RemoteException;
	public int getUserID() throws RemoteException;
	public User getUser() throws RemoteException;
	public UserList getUsers() throws RemoteException;
	public RoomList getRooms() throws RemoteException;
	public void getNotified(String msg) throws RemoteException;
	public void notifyUser(int userToBeNotifiedID, String notification) throws RemoteException;
	public void endDirectMessage(int userID) throws RemoteException;
	public ArrayList<String> getNotifications() throws RemoteException;
	public void addRoom(int roomID) throws RemoteException;
	public Room startDirectMessage(int userID) throws RemoteException;
	public void setProfileData(String profileData) throws RemoteException;
	public void setStatus(boolean status) throws RemoteException;
	public void setProfilePic(String profilePic) throws RemoteException;
	public String getProfileData() throws RemoteException;
	public boolean getStatus() throws RemoteException;
	public String getProfilePic() throws RemoteException;
	public Hashtable<Integer, Room> getDirectMessages() throws RemoteException;
	public void updateUser(User user) throws RemoteException;
	public void blockUser(int targetUserID) throws RemoteException;
	public void unblockUser(int targetUserID) throws RemoteException;
	public User getUser(int userID) throws RemoteException;
	public String getPassword() throws RemoteException;
	public void setPassword(String pasword) throws RemoteException;
	public void addChat(int roomID, int chatLogID, String msg) throws RemoteException;
	public ChatLog addChatLog(int roomID, String chatLogName) throws RemoteException;
	public void deleteChatLog(int roomID, int chatLogID) throws RemoteException;
	public ChatLog getChatLog(int roomID, int chatLogID) throws RemoteException;
	public boolean isLoggedIn() throws RemoteException;
	public Boolean isInvited(int roomID) throws RemoteException;
	public void inviteUser(int roomID, int addUserID) throws RemoteException;
	public void uninviteUser(int roomID, int removeUserID) throws RemoteException;
	public void addUserToRoom(int roomID, Role role) throws RemoteException;
	public void removeUserFromRoom(int roomID, int targetUserID) throws RemoteException;
	public String getRoomDescription(int roomID) throws RemoteException;
	public void setRoomDescription(int roomID, String desc) throws RemoteException;
	public void setRoomLogo(int roomID, String logo) throws RemoteException;
	public String getRoomLogo(int roomID) throws RemoteException;
	public boolean getRoomType(int roomID) throws RemoteException;
	public void setRoomType(int roomID, Boolean type) throws RemoteException;
	public void deleteChat(int roomID, int chatLogID, int chatID) throws RemoteException;
	public void giveRole(int roomID, int targetUserID, Role role) throws RemoteException;
	public Chat getChat(int roomID, int chatLogID, int chatID) throws RemoteException;
	public ArrayList<User> getRoomOnlineUsers(int roomID) throws RemoteException;
	public ArrayList<User> getRoomOfflineUsers(int roomID) throws RemoteException;
	public void deleteAllMessagesByUser(int roomID, int chatLogID) throws RemoteException;
	public Hashtable<Integer, Role> getAllRoomMembers(int roomID) throws RemoteException;
	public void chatLogReply(String msg, int receiverID, int roomID, int chatLogID) throws RemoteException;
	public ArrayList<Chat> getPinnedMsgs(int roomID, int chatLogID) throws RemoteException;
	public Room getRoom(int roomID) throws RemoteException;
	public Role getRole(int roomID) throws RemoteException;
	public void pinMsg(int roomID, int chatLogID, int chatID) throws RemoteException;
	public void unpinMsg(int roomID, int chatLogID, int chatID) throws RemoteException;
}