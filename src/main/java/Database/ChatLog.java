package Database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Objects;

public class ChatLog implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Hashtable<Integer, Chat> chatLog = new Hashtable<Integer,Chat>(); //Instead made it a HashTable to make accessibility easier O(1) because of it
	private int chatLogID;
	private String chatLogName;
	private int last_ChatID = 0;
	private boolean chatLogLocked = false; //If you want to lock a chatLog so that messages cannot be added.
	
	public ChatLog() {
		this(-1, "<Default ChatLog>");
	}
	
	public ChatLog(int chatLogID, String chatLogName, Boolean chatLogLocked) {
		this.chatLogID = chatLogID;
		this.chatLogName = chatLogName;
		this.chatLogLocked = chatLogLocked;
	}
	
	public ChatLog(int chatLogID, String chatLogName) {
		this.chatLogID = chatLogID;
		this.chatLogName = chatLogName;
	}
	
	public ChatLog(int chatLogID) {
		this.chatLogID = chatLogID;
	}
	
	public void lockChatLog() {
		chatLogLocked = true;
	}
	
	public void unlockChatLog() {
		chatLogLocked = false;
	}
	
	public boolean isLocked() {
		return chatLogLocked;
	}

	public void setChatLogName(String name) {
		chatLogName = name;
	}
	
	
	public String getChatLogName() {
		return chatLogName;
	}
		
	public Chat addChat(String message, int senderID, String senderName) {
		if (!chatLogLocked) {
			last_ChatID++;
			//System.out.println("Ok, adding chat:\t" + message);
			Chat newChat = new Chat(last_ChatID, message, senderID, senderName);
			chatLog.put(last_ChatID, new Chat(last_ChatID, message, senderID, senderName));
			return newChat;
		}else {
			System.out.println("ChatLog is locked");
			return null;
		}
	}
	
	public Chat addChat(String message, int senderID, int receiverID) {
		if (!chatLogLocked) {
			last_ChatID++;
			Chat newChat = new Chat(last_ChatID, message, senderID, receiverID);
			chatLog.put(last_ChatID, newChat);
			return newChat;
		}else {
			//System.out.println("ChatLog is locked");
			return null;
		}
	}
	
	public void deleteChat(int chatID) {
		chatLog.remove(chatID);
	}
	
	public void deleteAllMessagesByUser(int userID) {
		Enumeration<Integer> e = chatLog.keys();
		
		while (e.hasMoreElements()) {
			int key = e.nextElement();
			Chat specificChat = chatLog.get(key);
			if (specificChat.getSenderID() == userID) {
				chatLog.remove(key);
			}
		}
	}
	
	public void pinMessage(int chatID) {
		Chat unpinned_chat = chatLog.get(chatID);
		unpinned_chat.pin();
	}
	
	public void unpinMessage(int chatID) {
		Chat pinned_chat = chatLog.get(chatID);
		pinned_chat.unpin();
	}
	
	public Chat getChat(int chatID) {
		return chatLog.get(chatID);
	}
	
	public void setChatLogID(int chatLogID) {
		this.chatLogID = chatLogID;
	}
	
	public int getChatLogID() {
		return chatLogID;
	}
	
	public ArrayList<Chat> getPinned() { //Return all the chats that are pinned.
		ArrayList<Chat> pinned = new ArrayList<Chat>();
		Enumeration<Integer> e = chatLog.keys();
		
		while (e.hasMoreElements()) {
			int key = e.nextElement();
			Chat currentChat = chatLog.get(key);
			if (currentChat.isPinned()) {
				pinned.add(currentChat);
			}
		}
		
		return pinned;
	}

	public Hashtable<Integer, Chat> getChatLog()
	{
		return chatLog;
	}

	public void setChatLog(Hashtable<Integer, Chat> chatLog)
	{
		this.chatLog = chatLog;
	}

	public int getLast_ChatID()
	{
		return last_ChatID;
	}

	public void setLast_ChatID(int last_ChatID)
	{
		this.last_ChatID = last_ChatID;
	}

	public boolean isChatLogLocked()
	{
		return chatLogLocked;
	}

	public void setChatLogLocked(boolean chatLogLocked)
	{
		this.chatLogLocked = chatLogLocked;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(chatLogID);
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
		ChatLog other = (ChatLog) obj;
		return chatLogID == other.getChatLogID();
	}
	
	public String toString() {
		return chatLogName;
	}
}
