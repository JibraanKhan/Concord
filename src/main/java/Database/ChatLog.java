package Database;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class ChatLog
{
	private Hashtable<Integer, Chat> chatLog = new Hashtable<Integer,Chat>(); //Instead made it a HashTable to make accessibility easier O(1) because of it
	private int chatLogID;
	private String chatLogName;
	private int last_ChatID = 0;
	
	public ChatLog(int chatLogID, String chatLogName) {
		this.chatLogID = chatLogID;
		this.chatLogName = chatLogName;
	}
	
	public ChatLog(int chatLogID) {
		this.chatLogID = chatLogID;
	}
	
	public void setChatLogName(String name) {
		chatLogName = name;
	}
	
	public String getChatLogName() {
		return chatLogName;
	}
	
	public void addChat(String message, int senderID) {
		last_ChatID++;
		chatLog.put(last_ChatID, new Chat(last_ChatID, message, senderID));
	}
	
	public void addChat(String message, int senderID, int receiverID) {
		last_ChatID++;
		chatLog.put(last_ChatID, new Chat(last_ChatID, message, senderID, receiverID));
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
}
