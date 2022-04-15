package model;

import java.util.ArrayList;
import java.util.List;

import Database.Room;
import ServerClientModel.Client;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConcordClientModel
{
	Client client;
	ObservableList<String> rooms = FXCollections.observableArrayList();
	StringProperty selectedRoom = new SimpleStringProperty();
	ObservableList<String> channels = FXCollections.observableArrayList();
	ObservableList<String> users = FXCollections.observableArrayList();
	ObservableList<String> chats = FXCollections.observableArrayList();
	ObservableList<String> DMs = FXCollections.observableArrayList();
	
	public ConcordClientModel() {
		initializeDemo();
		
	}
	
	public ObservableList<String> getRooms(){
		return rooms;
	}
	
	public ObservableList<String> getChannels(){
		return channels;
	}
	
	public ObservableList<String> getUsers(){
		return users;
	}
	
	public ObservableList<String> getChats(){
		return chats;
	}
	
	public ObservableList<String> getDMs(){
		return DMs;
	}
	
	public void setSelectedRoom(String selectedRoom) {
		this.selectedRoom.set(selectedRoom);
	}
	
	public StringProperty getSelectedRoom() {
		return selectedRoom;
	}
	
	public void addRoom(String roomName) {
		rooms.add(roomName);
	}
	
	public void initializeDemo() {
		ArrayList<String> roomsArrayList = new ArrayList<String>();
		roomsArrayList.add("My Server!");
		roomsArrayList.add("John's Server");
		rooms.addAll(roomsArrayList);
		
		ArrayList<String> usersArrayList = new ArrayList<String>();
		usersArrayList.add("Tom");
		usersArrayList.add("Brad");
		usersArrayList.add("John");
		usersArrayList.add("Stacy");
		usersArrayList.add("Jenna");
		usersArrayList.add("Chris");
		users.addAll(usersArrayList);
		
		ArrayList<String> channelsArrayList = new ArrayList<String>();
		channelsArrayList.add("Chat");
		channelsArrayList.add("Announcements");
		channelsArrayList.add("Promotions");
		channels.addAll(channelsArrayList);
		
		ArrayList<String> chatsArrayList = new ArrayList<String>();
		chatsArrayList.add("Hello, how are you?");
		chatsArrayList.add("I am fine thank you. How about you?");
		chatsArrayList.add("Why I feel amazing!");
		chats.addAll(chatsArrayList);
		
		ArrayList<String> DMsArrayList = new ArrayList<String>();
		DMsArrayList.add("Tom");
		DMsArrayList.add("Brad");
		DMsArrayList.add("John");
		DMs.addAll(DMsArrayList);
	}
}
