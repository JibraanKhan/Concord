package Database;

import java.sql.Time;

public class Chat
{
	private int chatID;
	private int senderID;
	private int receiverID;
	private String message;
	private Time timeStamp;
	private Boolean pinned = false;
	
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
	
	public Chat(int chatID, String message, int senderID) {
		this.chatID = chatID;
		this.message = message;
		this.senderID = senderID;
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
	
	public Boolean isPinned() {
		return pinned;
	}
	
	public void setTimeStamp(Time timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public Time getTimeStamp() {
		return timeStamp;
	}
}
