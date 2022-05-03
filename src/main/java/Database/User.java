package Database;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Objects;

public class User implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;
	private int userID;
	private Hashtable<Integer, Room> rooms = new Hashtable<Integer, Room>();
	private Hashtable<Integer, Room> directMessages = new Hashtable<Integer, Room>();
	//RoomID, RoomObject
	//directMessages are all rooms with only 2 users.
	private String profilePic;
	private boolean status;
	private String profileData;
	private Hashtable<Integer, Boolean> blockedUsers = new Hashtable<Integer, Boolean>();
	 /*If the user's blocked, add true to the Hashtable for that user's ID and it 
	  * will be easy to see if a user has another user blocked
	*/
	//Same reason above, easy to find if someone's in your friends list.
		
	public Hashtable<Integer, Boolean> getBlockedUsers()
	{
		return blockedUsers;
	}

	public void setBlockedUsers(Hashtable<Integer, Boolean> blockedUsers)
	{
		this.blockedUsers = blockedUsers;
	}

	public void setUserID(int userID)
	{
		this.userID = userID;
	}

	public void setRooms(Hashtable<Integer, Room> rooms)
	{
		this.rooms = rooms;
	}

	public void setDirectMessages(Hashtable<Integer, Room> directMessages)
	{
		this.directMessages = directMessages;
	}
	
	public User(int userID) {
		this.userID = userID;
	}
	
	public User(int userID, String password) {
		this.userID = userID;
		this.password = password;
	}
	
	public User(int userID, String password, String userName) {
		this.userID = userID;
		this.password = password;
		this.userName = userName;
	}
	
	public User(int userID, String password, String userName, String profileData) {
		this.userID = userID;
		this.password = password;
		this.userName = userName;
		this.profileData = profileData;
	}
	
	public User(int userID, String password, String userName, String profileData, boolean status) {
		this.userID = userID;
		this.password = password;
		this.userName = userName;
		this.profileData = profileData;
		this.status = status;
	}
	
	public User(int userID, String password, String userName, String profileData, boolean status, String profilePic) {
		this.userID = userID;
		this.password = password;
		this.userName = userName;
		this.profileData = profileData;
		this.status = status;
		this.profilePic = profilePic;
	}
	
	public User() {
		this(-1, "<Default Password>", "<Default UserName>", "<Default ProfileData>", true, "<Default ProfilePic>");
	}
	
	public Room addRoom(RoomList roomList, int roomID) {
		Room addingRoom = roomList.getRoom(roomID);
		rooms.put(roomID, addingRoom);
		System.out.println("Adding " + roomID + " to " + userName + ".");
		return addingRoom;
	}
	
	public void removeRoom(int roomID) {
		System.out.println("Removing " + roomID + " from " + userName + ".");
		if (rooms.get(roomID) != null) {
			rooms.remove(roomID);
		}
	}
	 
	public Room startDirectMessage(RoomList roomList, UserList userList, int userID) {
		/*Okay, we ourselves can start a DM with whoever but others starting a DM with us 
		 * when we blocked them is bad, so we don't care who we're starting DMs with.
		 * 
		 * Okay, we also need to check if there is a room in our direct messages that 
		 * does not contain one user with the userID of the user we are starting DM with.
		 * If there is, we can just return that room.
		 * If not, we can just make another room and return that one.
		 * 
		*/
		
		//First, check if there's a room in our direct messages with user already
		User otherUser = userList.getUser(userID);
		Enumeration<Integer> e = directMessages.keys();
		while(e.hasMoreElements()) {
			int key = e.nextElement();
			Room roomInQuestion = directMessages.get(key); //Room we're checking
			Hashtable<Integer, Role> roomUsers = roomInQuestion.getUserTable();
			if ((roomUsers.get(userID) != null)) {
				//Okay, we found a DM with the user in it.
				//If the other user has not blocked us, we want to make sure they also have this room.
				otherUser.requestedDirectMessage(roomList, userList, this.userID, roomInQuestion);
				return roomInQuestion;
			}
		}
		//Okay, so the return did not pass meaning that there isn't a DM with the user in it.
		
		
		String otherUsersName = otherUser.getUserName();
		Room newDM = roomList.addRoom();
		String thisUsersName = this.userName;
		String discussionName = thisUsersName + ", " + otherUsersName;
		newDM.setName(discussionName);
		newDM.addUser(this.userID, new Admin(), userList, roomList, "DM");
		newDM.addUser(userID, new Noob(), userList, roomList, "DM");
		newDM.addChatLog(this.userID, new ChatLog(1, discussionName)); 
		//We can assign the chat log any arbitrary integer as a userID since we know
		// that the room will and should only have one ChatLog since it is a DM between
		// two users 
		directMessages.put(newDM.getRoomID(), newDM);
		otherUser.requestedDirectMessage(roomList, userList, this.userID, newDM); //Ask to start DM with yourself
		return newDM;
	}

	public void endDirectMessage(int userID) {
		//End DM with another user
		Enumeration<Integer> e = directMessages.keys();
		while(e.hasMoreElements()) {
			int key = e.nextElement();
			Room roomInQuestion = directMessages.get(key); //Room we're checking
			Hashtable<Integer, Role> roomUsers = roomInQuestion.getUserTable();
			if ((roomUsers.get(userID) != null)) {
				//Okay, we found a DM with the user in it.
				directMessages.remove(roomInQuestion.getRoomID());
			}
		}
	}
	
	public void requestedDirectMessage(RoomList roomList, UserList userList, int userID, Room DM) {
		//Check if the person requesting is not blocked by us.
		if (((blockedUsers.get(userID) == null) || (blockedUsers.get(userID) == false)) && (directMessages.get(DM.getRoomID()) == null)) {
			//Lets make sure we do not already have the room in our DMs
			
			//Room has already been made so we simply need to add it to our DMs
			directMessages.put(DM.getRoomID(), DM);
		}
	}
	
	public Hashtable<Integer, Room> getDirectMessages(){
		return directMessages;
	}
	
	public Hashtable<Integer, Room> getRooms(){
		return rooms;
	}
	
	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getProfilePic()
	{
		return profilePic;
	}

	public void setProfilePic(String profilePic)
	{
		this.profilePic = profilePic;
	}

	public boolean isStatus()
	{
		return status;
	}

	public void setStatus(boolean status)
	{
		this.status = status;
	}

	public String getProfileData()
	{
		return profileData;
	}

	public void setProfileData(String profileData)
	{
		this.profileData = profileData;
	}
	
	public void blockUser(int userID) {
		blockedUsers.put(userID, true);
		//Lets also close the direct messages with the user we are blocking
		endDirectMessage(userID);
		
	}
	
	public void unblockUser(int userID) {
		blockedUsers.put(userID, false);
	}
	
	public int getUserID() {
		return this.userID;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(blockedUsers, directMessages, password, profileData, profilePic, status, userID, userName);
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
		User other = (User) obj;
		
		//So, we need to check if the DMs are the same and if the blockedUsers are the same
		return blockedUsers.equals(other.getBlockedUsers()) && directMessages.equals(other.getDirectMessages())
				&& Objects.equals(password, other.getPassword()) && Objects.equals(profileData, other.getProfileData())
				&& Objects.equals(profilePic, other.getProfilePic()) && status == other.isStatus() && userID == other.getUserID()
				&& Objects.equals(userName, other.getUserName());
	}
	
	public String toString() {
		return userName;
	}
}
