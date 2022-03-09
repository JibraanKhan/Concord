package Database;

import java.util.Hashtable;

public class Room
{
	private Hashtable<Integer, ChatLog> chatLogs = new Hashtable<Integer, ChatLog>();
	private String description;
	private String name;
	private String logo;
	private int roomID;
	private Hashtable<Integer, Role> userTable = new Hashtable<Integer, Role>(); //Object will be a subclass of Role
	private Boolean roomType; //private or public room
	private int last_chatLogID = 0;
	
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
	}
	
	public Room(int roomID, String name) {
		this.roomID = roomID;
		this.name = name;
	}
	
	public Room(int roomID) {
		this.roomID = roomID;
	}
	
	public void deleteRoom(int userID, RoomList rooms) {
		Role deletersRole = userTable.get(userID);
		if (deletersRole.isDeleteRoomPermission()) {
			rooms.deleteRoom(roomID);
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
	public void setRoomType(Boolean roomType)
	{
		this.roomType = roomType;
	}
	
	public void addUser(int userID, Role role) {
		userTable.put(userID, role);
	}
	
	public void addUser(int userID) {
		userTable.put(userID, new Noob());
	}
	
	public Role getUser(int userID) {
		return userTable.get(userID); 
	}
	
	public void removeUser(int userID, int targetUserID) {
		Role removersRole = userTable.get(userID);
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
			if (relevantChat != null) {
				//Okay, now we know that the chat with the ID exists. Now, check permission and delete it if permissions are met.
				if ((userRole.isDeleteChatPermission()) || (relevantChat.getSenderID() == userID)) {
					//Okay, user either has permission to delete any chat in the chat log
					//or the chat is the user's own chat which they are of course allowed to delete
					relevantChatLog.deleteChat(chatID);
				}
			}
		}
	}
	
	public void addChat(String message, int chatLogID, int senderID) {
		ChatLog chatLog = chatLogs.get(chatLogID);
		if (userTable.get(senderID) != null) { //If we know that the user is inside the room then we can add the chat to the chat log
			chatLog.addChat(message, senderID);
		}
	}
	
	public void addChat(String message, int chatLogID, int senderID, int receiverID) {
		ChatLog chatLog = chatLogs.get(chatLogID);
		if ((userTable.get(senderID) != null) && (userTable.get(receiverID) != null)){ 
			//If we know that the sender and receiver are both  inside the room then we can add the chat to the chat log
			chatLog.addChat(message, senderID, receiverID);
		}
	}
	
	public Chat getChat(int chatLogID, int chatID) {
		return chatLogs.get(chatLogID).getChat(chatID);
	}
	
	public void addChatLog(int userID, ChatLog chatLog) {
		Role addersRole = userTable.get(userID);
		if (addersRole.isCreateChatLogPermission()) {
			last_chatLogID++;
			chatLogs.put(last_chatLogID, chatLog);
		}
	}
	
	public ChatLog addChatLog(int userID) {
		Role addersRole = userTable.get(userID);
		if (addersRole.isCreateChatLogPermission()) {
			last_chatLogID++;
			ChatLog newChatLog = new ChatLog(last_chatLogID, "<Untitled ChatLog>");
			chatLogs.put(last_chatLogID, newChatLog);
			return newChatLog;
		}
		return null;
	}
	
	public void deleteChatLog(int userID, int chatLogID) {
		Role addersRole = userTable.get(userID);
		if (addersRole.isDeleteChatLogPermission()) {
			chatLogs.remove(chatLogID);
		}
	}
	
	public ChatLog getChatLog(int chatLogID) {
		return chatLogs.get(chatLogID);
	}
	
	public ChatLog addChatLog(int userID, String name) {
		Role addersRole = userTable.get(userID);
		if (addersRole.isCreateChatLogPermission()) {
			last_chatLogID++;
			ChatLog newChatLog = new ChatLog(last_chatLogID, name);
			chatLogs.put(last_chatLogID, newChatLog);
			return newChatLog;
		}
		return null;
	}
	
	public void giveRole(int userID, int targetUserID, Role role) {
		//Check if user has permission to give role
		//Then give target user the role
		Role settersRole = userTable.get(userID);
		if (settersRole.isGiveRolePermission()) {
			//Cool, the user has permission to give role.
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
}
