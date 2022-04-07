package Database;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Objects;

public class UserList implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Hashtable<Integer, User> users = new Hashtable<Integer, User>();
	private int last_userID = 0;
	
	public UserList() {
		
	}
	
	public User getUser(int userID) {
		return users.get(userID);
	}
	
	public User addUser() { // Maybe add parameters for user information?  
		last_userID++; //Since there will only be one userList, we can just keep incrementing the ID to give each one a unique ID.
		User user = new User(last_userID);
		users.put(last_userID, user);
		return user;
	}
	
	public User addUser(String password) { // Maybe add parameters for user information?  
		last_userID++; //Since there will only be one userList, we can just keep incrementing the ID to give each one a unique ID.
		User user = new User(last_userID, password);
		users.put(last_userID, user);
		return user;
	}
	
	public User addUser(String userName, String password) { // Maybe add parameters for user information?  
		last_userID++; //Since there will only be one userList, we can just keep incrementing the ID to give each one a unique ID.
		User user = new User(last_userID, password, userName);
		users.put(last_userID, user);
		return user;
	}
	
	public User addUser(String userName, String password, String profileData) { // Maybe add parameters for user information?  
		last_userID++; //Since there will only be one userList, we can just keep incrementing the ID to give each one a unique ID.
		User user = new User(last_userID, password, userName, profileData);
		users.put(last_userID, user);
		return user;
	}
	
	public User addUser(String userName, String password, String profileData, boolean status) { // Maybe add parameters for user information?  
		last_userID++; //Since there will only be one userList, we can just keep incrementing the ID to give each one a unique ID.
		User user = new User(last_userID, password, userName, profileData, status);
		users.put(last_userID, user);
		return user;
	}
	
	public User addUser(String userName, String password, String profileData, boolean status, String profilePic) { // Maybe add parameters for user information?  
		last_userID++; //Since there will only be one userList, we can just keep incrementing the ID to give each one a unique ID.
		User user = new User(last_userID, password, userName, profileData, status, profilePic);
		users.put(last_userID, user);
		return user;
	}
	
	public Hashtable<Integer, User> getUsers()
	{
		return users;
	}

	public void setUsers(Hashtable<Integer, User> users)
	{
		this.users = users;
	}

	public int getLast_userID()
	{
		return last_userID;
	}

	public void setLast_userID(int last_userID)
	{
		this.last_userID = last_userID;
	}

	public void setUser(User user, Integer userID) {
		users.put(userID, user);
	}
		
	@Override
	public int hashCode()
	{
		return Objects.hash(users);
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
		
		UserList other = (UserList) obj;
		Enumeration<Integer> e = users.keys();
		
		while (e.hasMoreElements()) {
			int key = e.nextElement();
			User user = users.get(key);
			User otherUser = other.getUser(key);
			if (!(otherUser != null && otherUser.equals(user))) {
				return false;
			}
		}
		
		return true;
	}
	
}
