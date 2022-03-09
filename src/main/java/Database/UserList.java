package Database;

import java.util.Hashtable;

public class UserList
{
	private Hashtable<Integer, User> users = new Hashtable<Integer, User>();
	private int last_userID = 0;
	
	public User getUser(int userID) {
		return users.get(userID);
	}
	
	public User addUser() { // Maybe add parameters for user information?  
		last_userID++; //Since there will only be one userList, we can just keep incrementing the ID to give each one a unique ID.
		User user = new User(last_userID);
		users.put(last_userID, user);
		return user;
	}
	
	public User addUser(String userName) { // Maybe add parameters for user information?  
		last_userID++; //Since there will only be one userList, we can just keep incrementing the ID to give each one a unique ID.
		User user = new User(last_userID, userName);
		users.put(last_userID, user);
		return user;
	}
	
	public User addUser(String userName, String profileData) { // Maybe add parameters for user information?  
		last_userID++; //Since there will only be one userList, we can just keep incrementing the ID to give each one a unique ID.
		User user = new User(last_userID, userName, profileData);
		users.put(last_userID, user);
		return user;
	}
	
	public User addUser(String userName, String profileData, boolean status) { // Maybe add parameters for user information?  
		last_userID++; //Since there will only be one userList, we can just keep incrementing the ID to give each one a unique ID.
		User user = new User(last_userID, userName, profileData, status);
		users.put(last_userID, user);
		return user;
	}
	
	public User addUser(String userName, String profileData, boolean status, String profilePic) { // Maybe add parameters for user information?  
		last_userID++; //Since there will only be one userList, we can just keep incrementing the ID to give each one a unique ID.
		User user = new User(last_userID, userName, profileData, status, profilePic);
		users.put(last_userID, user);
		return user;
	}
}
