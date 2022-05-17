package Database;

import java.io.Serializable;
import java.sql.Time;
import java.util.Objects;

public class Chat implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int chatID;
	private int senderID;
	private int receiverID;
	private String message;
	private Time timeStamp;
	private Boolean pinned = false;
	private String senderName;
	
	public Chat() {
		this(-1, "<Default Message>", -1, new Time(0));
	}
	
	public Chat(int chatID, String message, int senderID, int receiverID, Time timeStamp) {
		this.chatID = chatID;
		this.message = message;
		this.senderID = senderID;
		this.receiverID = receiverID;
		this.timeStamp = timeStamp;
	}
	
	public Chat(int chatID, String message, int senderID, Time timeStamp) {
		this.chatID = chatID;
		this.message = message;
		this.senderID = senderID;
		this.timeStamp = timeStamp;
	}
	
	public Chat(int chatID, String message, int senderID, int receiverID) {
		this.chatID = chatID;
		this.message = message;
		this.senderID = senderID;
		this.receiverID = receiverID;
	}
	
	public Chat(int chatID, String message, int senderID, String senderUsername) {
		this.chatID = chatID;
		this.message = message;
		this.senderID = senderID;
		this.senderName = senderUsername;
	}
	
	public void setChatID(int id) {
		chatID = id;
	}
	
	public void setSenderID(int id) {
		senderID = id;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setReceiverID(int id) {
		receiverID = id;
	}
	
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	
	public void pin() {
		pinned = true;
	}
	
	public void unpin() {
		pinned = false;
	}
	
	public int getChatID() {
		return chatID;
	}
	
	public int getSenderID() {
		return senderID;
	}
	
	public String getMessage() {
		return message;
	}
	
	public int getReceiverID() {
		return receiverID;
	}
	
	public String getSenderName() {
		return senderName;
	}
	
	public Boolean isPinned() {
		return pinned;
	}
	
	public void setTimeStamp(Time timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public Time getTimeStamp() {
		return timeStamp;
	}

	public Boolean getPinned()
	{
		return pinned;
	}

	public void setPinned(Boolean pinned)
	{
		this.pinned = pinned;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(chatID);
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
		Chat other = (Chat) obj;
		return chatID == other.getChatID();
	}
	
	public String toString() {
		return senderName + ": " + message;
	}
}
