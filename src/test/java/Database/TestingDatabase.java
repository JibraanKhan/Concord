package Database;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestingDatabase
{
	RoomList roomList;
	UserList userList;
	Room myServer;
	ChatLog memes;
	ChatLog announcements;
	ChatLog chat;
	Role noob;
	Role admin;
	Role moderator;
	Role userCreated;
	Boolean[] userCreatedPermissions;
	User ashley;
	User john;
	User ron;
	User stacy;
	Room dm_stacy_john;
	
	@BeforeEach
	void setUp() throws Exception
	{
		/*Creating the list of rooms and users we need for concord.
		 * We need these lists as they will cover all of the users and rooms
		 * inside of the server.
		 */
		roomList = new RoomList(); 
		userList = new UserList();
		
		myServer = roomList.addRoom("My Server", "This is my own server, please join at your own risk.", null, true);
		//My own server is created now (my own room)
		//Roles
		noob = new Noob();
		admin = new Admin();
		moderator = new Mod();
		userCreatedPermissions = new Boolean[] {false, false, false, true, false, false, false, false};
		//Someone that can only give roles to others.
		userCreated = new UserCreatedRole(userCreatedPermissions, "Role Giver");
		//Users creation
		ashley = userList.addUser("Ashley", "Hello, my name is Ashley and I am a weeb.\nNice to meet you!", true);
		john = userList.addUser("John", "Yo, I go by John. I like skateboarding and hanging out with others!\nHit me up if there is a party going on.", false);
		ron = userList.addUser("Ron", "Hey man, I really don't like sunny weather. I just wish I could avoid social contact.\nPlease leave me alone.", true);
		stacy = userList.addUser("Stacy", "Heya there, my name is Stacy but my friends call me Stace.\nI really like meeting new people, so let me know if there are any major events going on!", false);
		//Add users to Room
		myServer.addUser(ashley.getUserID(), admin);
		myServer.addUser(john.getUserID(), moderator);
		myServer.addUser(ron.getUserID(), noob);
		myServer.addUser(stacy.getUserID(), userCreated);
		//Set RoomType by Ashley
		myServer.setRoomType(ashley.getUserID(), false); //Private
		//ChatLog creation by ashley
		memes = myServer.addChatLog(ashley.getUserID(), "Memes");
		announcements = myServer.addChatLog(ashley.getUserID(), "Announcements");
		chat = myServer.addChatLog(ashley.getUserID(), "Chat");
		//DM Between Stacy and John
		dm_stacy_john = stacy.startDirectMessage(roomList, userList, john.getUserID());
		//Send messages from everyone into server.
		myServer.addChat("What did the cat say to the dog?\nMeow", memes.getChatLogID(), ashley.getUserID());
		myServer.addChat("Yo yo guys, there's a huge skateboarding event going on tomorrow. You guys should definitely drop by!", announcements.getChatLogID(), john.getUserID());
		myServer.addChat("Ok, which one of you guys talked to me?", chat.getChatLogID(), ron.getUserID());
		myServer.addChat("Hey guys... like, what's your favorite coffee from Starbucks? I personally like the venti caramel macchiatto with light ice and whipped cream on top sprinkled with some honey glazed almonds.", chat.getChatLogID(), stacy.getUserID());
		//DMs between stacy and john
		dm_stacy_john.addChat("Hey John, what homework did we have due yesterday?", 1, stacy.getUserID(), john.getUserID());
		dm_stacy_john.addChat("Umm.... was it not the DeMorgan's law truth table problems? Agh, I don't remember!", 1, john.getUserID(), stacy.getUserID());
	}

	@Test
	void test()
	{
		//Lets test the above stuff, we should have two rooms: the DM and the Server
		assertEquals(myServer, roomList.getRoom(1));
		assertEquals(dm_stacy_john, roomList.getRoom(2));
		//Okay, both rooms have been added to the roomList. We are done testing RoomList.
		
		//Lets test UserList
		assertEquals(ashley, userList.getUser(1));
		assertEquals(john, userList.getUser(2));
		assertEquals(ron, userList.getUser(3));
		assertEquals(stacy, userList.getUser(4));
		//Okay, all the users have been added t the userList. We are done testing UserList.
		
		//Lets test of myServer's methods work (Lets test Room)
		//All the chatlogs have been added appropriately.
		assertEquals(memes, myServer.getChatLog(1));
		assertEquals(announcements, myServer.getChatLog(2));
		assertEquals(chat, myServer.getChatLog(3));
		//All the users have been added to myServer
		assertTrue(myServer.getUser(ashley.getUserID()) == admin);
		assertTrue(myServer.getUser(john.getUserID()) == moderator);
		assertTrue(myServer.getUser(ron.getUserID()) == noob);
		assertTrue(myServer.getUser(stacy.getUserID()) == userCreated);
		//Set john to admin before deleting ashley
		myServer.giveRole(ashley.getUserID(), john.getUserID(), admin);
		assertTrue(myServer.getUser(john.getUserID()) == admin);
		myServer.removeUser(1, 1); //remove ashley by ashley
		assertTrue(myServer.getUser(ashley.getUserID()) == null);
		//removing ashley shouldn't affect other users being in the server.
		assertTrue(myServer.getUser(ron.getUserID()) == noob);
		assertTrue(myServer.getUser(stacy.getUserID()) == userCreated);
		//Check if the ChatLog has the Chats added
		assertEquals("What did the cat say to the dog?\nMeow", myServer.getChat(memes.getChatLogID(), 1).getMessage());
		assertEquals("Yo yo guys, there's a huge skateboarding event going on tomorrow. You guys should definitely drop by!", myServer.getChat(announcements.getChatLogID(), 1).getMessage());
		assertEquals("Ok, which one of you guys talked to me?", myServer.getChat(chat.getChatLogID(), 1).getMessage());
		assertEquals("Hey guys... like, what's your favorite coffee from Starbucks? I personally like the venti caramel macchiatto with light ice and whipped cream on top sprinkled with some honey glazed almonds.", myServer.getChat(chat.getChatLogID(), 2).getMessage());
		myServer.deleteChat(stacy.getUserID(), chat.getChatLogID(), 2);
		assertTrue(myServer.getChat(chat.getChatLogID(), 2) == null);
		//Lets test the User class thoroughly by testing each method that is not a getter/setter
		myServer.giveRole(stacy.getUserID(), ron.getUserID(), admin);
		assertTrue(myServer.getUser(ron.getUserID()) == admin);
		myServer.giveRole(stacy.getUserID(), ron.getUserID(), noob); //Back to noob for some testing purposes.
		
		/*
		 * We have so far tested:
		 * 	RoomList
		 * 	UserList
		 * 	Room
		 * Next up is:
		 * 	Roles
		 * 		Methods: deleteRoom
		 * 				 removeUser
		 * 				 deleteChatLog
		 * 				 addChatLog
		 * 				 giveRole
		 * 				 setRoomType
		 * 			
		 */
		//Noob
		myServer.deleteRoom(ron.getUserID(), roomList); //Should not actually delete the server.
		assertEquals(myServer, roomList.getRoom(1));
		myServer.removeUser(ron.getUserID(), john.getUserID());
		assertEquals(admin, myServer.getUser(john.getUserID()));
		myServer.deleteChatLog(ron.getUserID(), memes.getChatLogID());
		assertEquals(memes, myServer.getChatLog(memes.getChatLogID()));
		ChatLog RonsChatLog = myServer.addChatLog(ron.getUserID(), "Rons Club");
		assertTrue(RonsChatLog == null);
		myServer.giveRole(ron.getUserID(), ron.getUserID(), admin);
		assertTrue(myServer.getUser(ron.getUserID()) != admin);
		myServer.setRoomType(ron.getUserID(), true); //Making it public
		assertEquals(true, myServer.isPublic());
		myServer.deleteChat(ron.getUserID(), memes.getChatLogID(), 1);
		assertEquals("What did the cat say to the dog?\nMeow", memes.getChat(1).getMessage());
		
		//UserCreatedRole
		myServer.deleteRoom(stacy.getUserID(), roomList); //Should not actually delete the server.
		assertEquals(myServer, roomList.getRoom(1));
		myServer.removeUser(stacy.getUserID(), john.getUserID());
		assertEquals(admin, myServer.getUser(john.getUserID()));
		myServer.deleteChatLog(stacy.getUserID(), memes.getChatLogID());
		assertEquals(memes, myServer.getChatLog(memes.getChatLogID()));
		ChatLog StacysChatLog = myServer.addChatLog(stacy.getUserID(), "Stacy's Club");
		assertTrue(StacysChatLog == null);
		myServer.giveRole(stacy.getUserID(), ron.getUserID(), moderator);
		assertTrue(myServer.getUser(ron.getUserID()) == moderator);
		myServer.setRoomType(stacy.getUserID(), true); //Making it public
		assertEquals(true, myServer.isPublic());
		myServer.deleteChat(stacy.getUserID(), memes.getChatLogID(), 1);
		assertEquals("What did the cat say to the dog?\nMeow", memes.getChat(1).getMessage());
		
		//Moderator
		
		myServer.deleteRoom(ron.getUserID(), roomList); //Should not actually delete the server.
		assertEquals(myServer, roomList.getRoom(1));
		myServer.removeUser(ron.getUserID(), stacy.getUserID());
		assertEquals(null, myServer.getUser(stacy.getUserID()));
		myServer.deleteChatLog(ron.getUserID(), memes.getChatLogID());
		assertEquals(memes, myServer.getChatLog(memes.getChatLogID()));
		ChatLog RonsSecondChatLog = myServer.addChatLog(ron.getUserID(), "Ron's Club");
		assertTrue(RonsSecondChatLog == null);
		myServer.giveRole(ron.getUserID(), ron.getUserID(), userCreated);
		assertTrue(myServer.getUser(ron.getUserID()) == moderator);
		myServer.setRoomType(ron.getUserID(), true); //Making it public
		assertEquals(true, myServer.isPublic());
		myServer.deleteChat(ron.getUserID(), memes.getChatLogID(), 1);
		assertEquals(null, myServer.getChat(memes.getChatLogID(), 1));
		
		//Admin
		myServer.deleteRoom(john.getUserID(), roomList); //Should not actually delete the server.
		assertEquals(null, roomList.getRoom(1));
		myServer.giveRole(john.getUserID(), ron.getUserID(), noob);
		assertTrue(myServer.getUser(ron.getUserID()) == noob);
		myServer.removeUser(john.getUserID(), ron.getUserID());
		assertEquals(null, myServer.getUser(ron.getUserID()));
		myServer.deleteChatLog(john.getUserID(), memes.getChatLogID());
		assertEquals(null, myServer.getChatLog(memes.getChatLogID()));
		ChatLog JohnsSecondChatLog = myServer.addChatLog(john.getUserID(), "John's Club");
		assertTrue(JohnsSecondChatLog == myServer.getChatLog(JohnsSecondChatLog.getChatLogID()));
		myServer.setRoomType(true); 
		assertEquals(true, myServer.isPublic());
		myServer.deleteChat(john.getUserID(), memes.getChatLogID(), 1);
		assertEquals(null, myServer.getChat(memes.getChatLogID(), 1));
		
		//Testing blocked users and DMs
		
		ashley.blockUser(stacy.getUserID());
		Room dm_ashley_stacy = ashley.startDirectMessage(roomList, userList, stacy.getUserID());
		ChatLog only_chatlog = dm_ashley_stacy.getChatLog(1);
	}

}
