package Database;

import java.util.Hashtable;

public class RoomList
{
	private Hashtable<Integer, Room> rooms = new Hashtable<Integer, Room>();
	private int last_RoomID = 0;
	
	public void addRoom(Room room) {
		last_RoomID++;
		rooms.put(last_RoomID, room);
	}
	
	public Room addRoom(String name, String description, String logo, Boolean roomType) {
		last_RoomID++;
		Room newRoom = new Room(last_RoomID, name, description, logo, roomType);
		rooms.put(last_RoomID, newRoom);
		return newRoom;
	}
	
	public Room addRoom(String name, String description, String logo) {
		last_RoomID++;
		Room newRoom = new Room(last_RoomID, name, description, logo);
		rooms.put(last_RoomID, newRoom);
		return newRoom;
	}
	
	public Room addRoom(String name, String description) {
		last_RoomID++;
		Room newRoom = new Room(last_RoomID, name, description);
		rooms.put(last_RoomID, newRoom);
		return newRoom;
	}
	
	public Room addRoom(String name) {
		last_RoomID++;
		Room newRoom = new Room(last_RoomID, name);
		rooms.put(last_RoomID, newRoom);
		return newRoom;
	}
	
	public Room addRoom() {
		last_RoomID++;
		Room newRoom = new Room(last_RoomID);
		rooms.put(last_RoomID, newRoom);
		return newRoom;
	}
	
	public Room getRoom(int roomID) {
		return rooms.get(roomID);
	}
	
	public void deleteRoom(int userID, int roomID) {
		rooms.get(roomID).deleteRoom(userID, this);
	}
	
	public void deleteRoom(int roomID) {
		rooms.remove(roomID);
	}
}
