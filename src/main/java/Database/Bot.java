package Database;

import ServerClientModel.ServerInterface;

public interface Bot
{
	public void update(String event, Object change); 
	//Event is what happened in the room
	//Change is the object that was modified or added to the room/the relevant object that the change
	//is concerned with.
	public String getName();
	public void setUserID(int userID);
	public int getUserID();
}
