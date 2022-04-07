package Database;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Objects;

public class RoomList implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Hashtable<Integer, Room> rooms = new Hashtable<Integer, Room>();
	private int last_RoomID = 0;
	
	public RoomList() {
		
	}
	
	public Room getRoom(int roomID) {
		return rooms.get(roomID);
	}
	
	public void setRoom(int roomID, Room room) {
		rooms.put(roomID, room);
	}
	
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

	public Hashtable<Integer, Room> getRooms()
	{
		return rooms;
	}

	public void setRooms(Hashtable<Integer, Room> rooms)
	{
		this.rooms = rooms;
	}

	public int getLast_RoomID()
	{
		return last_RoomID;
	}

	public void setLast_RoomID(int last_RoomID)
	{
		this.last_RoomID = last_RoomID;
	}

	public void deleteRoom(int userID, int roomID) {
		rooms.get(roomID).deleteRoom(userID, this);
	}
	
	public void deleteRoom(int roomID) {
		rooms.remove(roomID);
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(rooms);
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
		RoomList other = (RoomList) obj;
		
		Enumeration<Integer> e = rooms.keys();
		
		while (e.hasMoreElements()) {
			int key = e.nextElement();
			Room room = rooms.get(key);
			Room otherRoom = other.getRoom(key);
			if (!(otherRoom != null && otherRoom.equals(room))) {
				return false;
			}
		}
		return true;
	}
}
