package Database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Objects;

public class Room implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Hashtable<Integer, ChatLog> chatLogs = new Hashtable<Integer, ChatLog>();
	private String description;
	private String name;
	private String logo;
	private int roomID;
	private Hashtable<Integer, Role> userTable = new Hashtable<Integer, Role>(); //Object will be a subclass of Role
	private Boolean roomType = true; //private or public room, public as default
	private int last_chatLogID = 0;
	private Hashtable<Integer, Boolean> invitedUsers = new Hashtable<Integer, Boolean>();
	private ArrayList<Bot> bots = new ArrayList<Bot>();
	
	public ArrayList<Bot> getBots()
	{
		return bots;
	}

	public void setBots(ArrayList<Bot> bots)
	{
		this.bots = bots;
	}

	public int getLast_chatLogID()
	{
		return last_chatLogID;
	}

	public void setLast_chatLogID(int last_chatLogID)
	{
		this.last_chatLogID = last_chatLogID;
	}

	public Hashtable<Integer, Boolean> getInvitedUsers()
	{
		return invitedUsers;
	}

	public void setInvitedUsers(Hashtable<Integer, Boolean> invitedUsers)
	{
		this.invitedUsers = invitedUsers;
	}

	public void setRoomType(Boolean roomType)
	{
		this.roomType = roomType;
	}

	public Room() {
		this(-1, "<Default Room>", "<Default Description>", "<Default Logo>");
	}
	
	public Room(int roomID, String name, String description, String logo, Boolean roomType) {
		this.roomID = roomID;
		this.name = name;
		this.description = description;
		this.logo = logo;
		this.roomType = roomType;
	}
	
	public Room(int roomID, String name, String description, String logo) {
		this.roomID = roomID;
		this.name = name;
		this.description = description;
		this.logo = logo;
	}
	
	public Room(int roomID, String name, String description) {
		this.roomID = roomID;
		this.name = name;
		this.description = description;
		this.roomType = true;
	}
	
	public Room(int roomID, String name) {
		this.roomID = roomID;
		this.name = name;
	}
	
	public Room(int roomID) {
		this.roomID = roomID;
	}
	
	public void deleteRoom(int userID, RoomList rooms, UserList users) {
		Role deletersRole = userTable.get(userID);
		if (deletersRole == null) {
			return;
		}
		if (deletersRole.isDeleteRoomPermission()) {
			rooms.deleteRoom(roomID, users);
		}
	}
	
	public void lockChatLog(int userID, int roomID, int chatLogID, RoomList rooms) {
		Role lockersRole = userTable.get(userID);
		if (lockersRole.isLockChatLogPermission()) {
			Room room = rooms.getRoom(roomID);
			ChatLog chatLog = room.getChatLog(chatLogID);
			chatLog.lockChatLog();
		}
	}
	
	public void unlockChatLog(int userID, int roomID, int chatLogID, RoomList rooms) {
		Role lockersRole = userTable.get(userID);
		if (lockersRole.isLockChatLogPermission()) {
			Room room = rooms.getRoom(roomID);
			ChatLog chatLog = room.getChatLog(chatLogID);
			chatLog.unlockChatLog();
		}
	}
	
	public Hashtable<Integer, ChatLog> getChatLogs()
	{
		return chatLogs;
	}
	
	public void setChatLogs(Hashtable<Integer, ChatLog> chatLog)
	{
		this.chatLogs = chatLog;
	}
	
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String getLogo()
	{
		return logo;
	}
	public void setLogo(String logo)
	{
		this.logo = logo;
	}
	public int getRoomID()
	{
		return roomID;
	}
	public void setRoomID(int roomID)
	{
		this.roomID = roomID;
	}
	public Hashtable<Integer, Role> getUserTable()
	{
		return userTable;
	}
	public void setUserTable(Hashtable<Integer, Role> userTable)
	{
		this.userTable = userTable;
	}
	public Boolean getRoomType()
	{
		return roomType;
	}
	
	public void registerBot(Bot bot) {
		bots.add(bot);
		for (Bot nbot: bots) {
			//System.out.println("Bot: " + nbot.getName());
		}
	}
	
	public void notifyAllBots(String event, Object change) {
		for (Bot bot: bots) {
			bot.update(event, change);
		}
	}
	
	public void addUser(int userID, Role role, UserList userList, RoomList roomList) {
		User user = userList.getUser(userID);
		if (user == null) {
			return;
		}
		if (isPublic()) {
			userTable.put(userID, role);
			user.addRoom(roomList, roomID);
			//System.out.println("Adding user to room");
		}else {
			if (invitedUsers.get(userID) != null && invitedUsers.get(userID)) {
				//If the user exists in invitedUsers and the user's invited
				userTable.put(userID, role);
				user.addRoom(roomList, roomID);
				//System.out.println("Adding user to room");
			}
		}
	}
	
	public void addUser(int userID, Role role, UserList userList, RoomList roomList, String DM) { //Only used for DM rooms
		User user = userList.getUser(userID);
		if (user == null) {
			return;
		}
		if (isPublic()) {
			userTable.put(userID, role);
			//System.out.println("Adding user to room");
		}else {
			if (invitedUsers.get(userID) != null && invitedUsers.get(userID)) {
				//If the user exists in invitedUsers and the user's invited
				userTable.put(userID, role);
				//System.out.println("Adding user to room");
			}
		}
	}
	
	public void addUser(int userID, UserList userList, RoomList roomList) {
		//Giving new user the default of being a noob.
		User user = userList.getUser(userID);
		if (user == null) {
			return;
		}
		if (isPublic()) {
			userTable.put(userID, new Noob());
			user.addRoom(roomList, roomID);
		}else {
			if (invitedUsers.get(userID) != null && invitedUsers.get(userID)) {
				//If the user exists in invitedUsers and the user's invited
				userTable.put(userID, new Noob());
				user.addRoom(roomList, roomID);
			}
		}
	}
	
	public Role getUser(int userID) {
		return userTable.get(userID); 
	}
	
	public void removeUser(int userID, int targetUserID) {
		Role removersRole = userTable.get(userID);
		if (removersRole == null) {
			return;
		}
		if ((removersRole.isRemoveUserPermission()) || (userID == targetUserID)) {
			userTable.remove(targetUserID);
		}
	}
	
	public void deleteChat(int userID, int chatLogID, int chatID) {
		ChatLog relevantChatLog = chatLogs.get(chatLogID);
		if (relevantChatLog != null) {
			//Found the chatLog but now need to check if the user has permission to delete it.
			Role userRole = userTable.get(userID);
			Chat relevantChat = relevantChatLog.getChat(chatID);
			if (relevantChat != null && (userRole != null)) {
				//Okay, now we know that the chat with the ID exists. Now, check permission and delete it if permissions are met.
				if ((userRole.isDeleteChatPermission()) || (relevantChat.getSenderID() == userID)) {
	
					//Okay, user either has permission to delete any chat in the chat log
					//or the chat is the user's own chat which they are of course allowed to delete
					relevantChatLog.deleteChat(chatID);
				}
			}
		}
	}
	
	public Chat addChat(String message, int chatLogID, int senderID, String senderName) {
		ChatLog chatLog = chatLogs.get(chatLogID);
		if (userTable.get(senderID) != null && chatLog != null) { //If we know that the user is inside the room then we can add the chat to the chat log
			Chat chat = chatLog.addChat(message, senderID, senderName);
			if (chat == null) {
				return null;
			}
			notifyAllBots("<chatAdded> " + Integer.toString(chat.getChatID()), chatLog);
			return chat;

		} else {
			//System.out.println(senderID + " was null");
			if (chatLog == null) {
				//System.out.println(chatLogID + " was null");
				return null;
			}
			return null;
		}
	}
	
	public Chat addChat(String message, int chatLogID, int senderID, int receiverID) {
		ChatLog chatLog = chatLogs.get(chatLogID);
		if ((userTable.get(senderID) != null) && (chatLog != null) && (userTable.get(receiverID) != null)){ 
			//If we know that the sender and receiver are both  inside the room then we can add the chat to the chat log
			Chat chat = chatLog.addChat(message, senderID, receiverID);
			if (chat == null) {
				return null;
			}
			notifyAllBots("<chat added>", chat);
			return chat;
		}
		return null;
	}
	
	public Chat getChat(int chatLogID, int chatID) {
		ChatLog chatlog = chatLogs.get(chatLogID);
		if (chatlog != null) {
			return chatlog.getChat(chatID);
		}
		
		return null;
	}
	
	public void addChatLog(int userID, ChatLog chatLog) {
		Role addersRole = userTable.get(userID);
		if (addersRole != null && addersRole.isCreateChatLogPermission()) {
			last_chatLogID++;
			chatLogs.put(last_chatLogID, chatLog);
		}
	}
	
	public ChatLog addChatLog(int userID) {
		Role addersRole = userTable.get(userID);
		if (addersRole != null && addersRole.isCreateChatLogPermission()) {
			last_chatLogID++;
			ChatLog newChatLog = new ChatLog(last_chatLogID, "<Untitled ChatLog>");
			chatLogs.put(last_chatLogID, newChatLog);
			return newChatLog;
		}
		return null;
	}
	
	public ChatLog addChatLog(int userID, String name) {
		Role addersRole = userTable.get(userID);
		if (addersRole != null && addersRole.isCreateChatLogPermission()) {
			last_chatLogID++;
			ChatLog newChatLog = new ChatLog(last_chatLogID, name);
			chatLogs.put(last_chatLogID, newChatLog);
			return newChatLog;
		}
		return null;
	}
	
	public void deleteChatLog(int userID, int chatLogID) {
		Role addersRole = userTable.get(userID);
		//System.out.println("Deleting chat log");
		if (addersRole.isDeleteChatLogPermission()) {
			chatLogs.remove(chatLogID);
		}
	}
	
	public ChatLog getChatLog(int chatLogID) {
		return chatLogs.get(chatLogID);
	}
	
	public void giveRole(int userID, int targetUserID, Role role) {
		//Check if user has permission to give role
		//Then give target user the role
		Role settersRole = userTable.get(userID);
		if (settersRole.isGiveRolePermission() && 
				(userTable.get(targetUserID) != null)) {
			//Cool, the user has permission to give role and the user that's 
			//receiving role exists
			userTable.put(targetUserID, role); //Give the target the role.
		}
	}
	
	public void setRoomType(int userID, Boolean type) {
		//Check if user has permission to change room type.
		Role settersRole = userTable.get(userID);
		if (settersRole.isRoomTypePermission()) {
			//Set roomType if they do.
			roomType = type;
		}
	}
	
	public boolean isPublic() {
		return roomType; //Is the room public?
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public void inviteUser(int invitersUserID, int inviteesUserID) {
		//Check if the inviter has permission to invite
		Role invitersRole = userTable.get(invitersUserID);
		if (invitersRole.isInvitePermission()) {
			invitedUsers.put(inviteesUserID, true);
		}
	}
	
	public void uninviteUser(int invitersUserID, int inviteesUserID) {
		Role invitersRole = userTable.get(invitersUserID);
		if (invitersRole.isInvitePermission()) {
			invitedUsers.put(inviteesUserID, false);
		}
	}
	
	public Boolean isInvited(int userID) {		
		return invitedUsers.get(userID);
	}

	public String toString() {
		return name;
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(roomID);
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
		Room other = (Room) obj;
		return roomID == other.getRoomID();
	}
}
