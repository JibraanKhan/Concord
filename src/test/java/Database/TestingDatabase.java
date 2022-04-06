package Database;

import static org.junit.Assert.assertTrue;

import java.util.Hashtable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestingDatabase
{
	RoomList roomList;
	UserList userList;
	Room testServer;
	ChatLog memes;
	ChatLog announcements;
	ChatLog chat;
	Role noob;
	Role admin;
	Role moderator;
	Role roleGiver;
	boolean[] userCreatedPermissions;
	User ashley;
	User john;
	User ron;
	User stacy;
	
	@BeforeEach
	void setUp() throws Exception
	{
		/*Creating the list of rooms and users we need for concord.
		 * We need these lists as they will cover all of the users and rooms
		 * inside of the server.
		 */
		roomList = new RoomList(); 
		userList = new UserList();
		
		testServer = roomList.addRoom("My Server", "This is my own server, please join at your own risk.", null, true);
		//My own server is created now (my own room)
		//Roles
		noob = new Noob();
		admin = new Admin();
		moderator = new Mod();
		userCreatedPermissions = new boolean[] {false, false, false, true, false, false, false, false};
		//Someone that can only give roles to others.
		roleGiver = new UserCreatedRole(userCreatedPermissions, "Role Giver");
		//Users creation
		
		ashley = userList.addUser("Ashley", "uhgraw",
				"Hello, my name is Ashley and I am a weeb.\nNice to meet you!", 
				true);
		
		john = userList.addUser("John", "hello123", "Yo, I go by John. I like skateboarding and hanging out with others!\nHit me up if there is a party going on.", 
				false);
		
		ron = userList.addUser("Ron", 
				"why123",
				"Hey man, I really don't like sunny weather. I just wish I could avoid social contact.\nPlease leave me alone.", 
				true);
		
		stacy = userList.addUser("Stacy", 
				"ghin",
				"Heya there, my name is Stacy but my friends call me Stace.\nI really like meeting new people, so let me know if there are any major events going on!"
				);
		
		//Add users to Room
		testServer.addUser(ashley.getUserID(), admin, userList, roomList);
		testServer.addUser(john.getUserID(), moderator, userList, roomList);
		testServer.addUser(ron.getUserID(), noob, userList, roomList);
		testServer.addUser(stacy.getUserID(), roleGiver, userList, roomList);
		//ChatLog creation by ashley
		memes = testServer.addChatLog(ashley.getUserID(), "Memes");
		announcements = testServer.addChatLog(ashley.getUserID(), "Announcements");
		chat = testServer.addChatLog(ashley.getUserID(), "Chat");
		//Send messages from everyone into server.
		testServer.addChat("What did the cat say to the dog?\nMeow", 
				memes.getChatLogID(), 
				ashley.getUserID());
		
		testServer.addChat("What?", 
				memes.getChatLogID(), 
				ron.getUserID(), 
				ashley.getUserID()); //Receiver is specified, making it a reply
		
		testServer.addChat("Idk... I thought you knew lol.", 
				memes.getChatLogID(), 
				ashley.getUserID(), 
				ron.getUserID());//Receiver is specified, making it a reply
		
		testServer.addChat("Yo yo guys, there's a huge skateboarding event "
				+ "going on tomorrow. You guys should definitely drop by!", 
				announcements.getChatLogID(), 
				john.getUserID());
		
		testServer.addChat("Ok, which one of you guys talked to me?", 
				chat.getChatLogID(), 
				ron.getUserID());
		testServer.addChat("Hey guys... like, what's your favorite coffee "
				+ "from Starbucks? I personally like the venti caramel "
				+ "macchiatto with light ice and whipped cream on top "
				+ "sprinkled with some honey glazed almonds.", 
				chat.getChatLogID(), 
				stacy.getUserID());
		//DMs between stacy and john
	}

	@Test
	void userListTest()
	{
		//Lets use getUser to see if all the users were added to the users list.
		assertTrue(userList.getUser(1) == ashley);
		assertTrue(userList.getUser(2) == john);
		assertTrue(userList.getUser(3) == ron);
		assertTrue(userList.getUser(4) == stacy);
	}
	
	@Test
	void userTest() {
		Hashtable<Integer, Room> ronsDMs;
		Hashtable<Integer, Room> stacysDMs;
		//Variables to be used later in the method to keep track of DMs
		//addRoom got tested when users were added to the room. 
		//Lets test doing direct messages with users.
		//Okay, so lets do a direct message between Stacy and John 
		
		
		/*
		Room stacyToJohn = stacy.startDirectMessage(roomList, userList, john.getUserID());
		stacysDMs = stacy.getDirectMessages();
		johnsDMs = john.getDirectMessages();
		//They should now have a DM with themselves so their list of DMs 
		//should be the same
		assertTrue(stacysDMs.get(stacyToJohn.getRoomID()) ==
				johnsDMs.get(stacyToJohn.getRoomID())); 
		//Should be the same room
		
		//Lets close the DM from stacy's sides
		stacy.endDirectMessage(john.getUserID());
		
		assertTrue(stacysDMs.get(stacyToJohn.getRoomID()) == null);
		assertTrue(johnsDMs.get(stacyToJohn.getRoomID()) == stacyToJohn);
		//Stacy does not have the DM open anymore but John does.
		
		//startDirectMessage() method also tests room.addUser, room.addChatLog
		//and the requestedDirectMessage() method of user.
		*/
		/*
		 * Okay, lets have ron block stacy and then see how this works with
		 * the DMs.
		 * 
		 * What I expect to see:
		 * 	Stacy cannot start a DM with Ron but Ron is allowed to 
		 *  start a DM with Stacy since Ron blocked Stacy and Stacy has not
		 */
		ron.blockUser(stacy.getUserID());
		
		//Okay, lets have Ron try and start a DM with Stacy, the direct message
		//hash table should be the same for both Ron and Stacy.
		Room ronToStacyDM = ron.startDirectMessage(roomList, 
				userList, 
				stacy.getUserID());
		
		stacysDMs = stacy.getDirectMessages();
		ronsDMs = ron.getDirectMessages();
		
		assertTrue(stacysDMs.get(ronToStacyDM.getRoomID()) == 
				ronsDMs.get(ronToStacyDM.getRoomID())); 
		//They should both have the same room in their DMs
		
		/*Lets close this room for both and then try starting a DM from 
		 * Stacy to Ron. The reason we want to close this DM is because 
		 * we want to see if Ron will have a new room created. He already
		 * has this room and so startDirectMessage() will simply return
		 * that already made room since that is how it is designed to
		 * prevent duplication of DM rooms with the same user. 
		 */
		
		stacy.endDirectMessage(ron.getUserID());
		ron.endDirectMessage(stacy.getUserID());
		
		Room stacyToRonDM = stacy.startDirectMessage(roomList, 
				userList, 
				ron.getUserID());
		
		stacysDMs = stacy.getDirectMessages();
		ronsDMs = ron.getDirectMessages();
		
		//Now, Ron should not have the DM but Stacy should
		assertTrue(stacysDMs.get(stacyToRonDM.getRoomID()) != null);
		//Stacy has it
		
		assertTrue(ronsDMs.get(stacyToRonDM.getRoomID()) == null);
		//Ron does not
		ron.unblockUser(stacy.getUserID());
		
		stacyToRonDM = stacy.startDirectMessage(roomList, 
				userList, 
				ron.getUserID());
		
		stacysDMs = stacy.getDirectMessages();
		ronsDMs = ron.getDirectMessages();
		
		//Now, Ron and Stacy should both have the same DM 
		assertTrue(stacysDMs.get(stacyToRonDM.getRoomID()) == ronsDMs.get(stacyToRonDM.getRoomID()));
	}
	
	@Test
	void roomListTest() {
		assertTrue(roomList.getRoom(testServer.getRoomID()) == testServer);
		roomList.deleteRoom(testServer.getRoomID());
		assertTrue(roomList.getRoom(testServer.getRoomID()) == null);
	}
	
	@Test
	void roomTest() {
		//lets test if ashley is in the test server
		assertTrue(testServer.getUser(ashley.getUserID()) == admin);
		//getUser() returns the role of the user in that server, indicating
		//that the user does exist in the room. Might be a bad idea but we
		//should not have too much trouble since most of the time we want to
		//just check if a user exists in the room. If we want a user, we can
		//just user the userList.
		testServer.removeUser(ashley.getUserID(), ron.getUserID());
		assertTrue(testServer.getUser(ron.getUserID()) == null);
		//Ron's deleted from the server.
		//Lets add Ron back to the server so we can use him for testing later
		testServer.addUser(ron.getUserID(), noob, userList, roomList);
		
		assertTrue(testServer.getChat(memes.getChatLogID(), 1).getMessage() == 
				"What did the cat say to the dog?\nMeow"); //First chat in Memes
		assertTrue(testServer.getChat(memes.getChatLogID(), 2).getMessage() == 
				"What?"); //Second chat in Memes
		assertTrue(testServer.getChat(memes.getChatLogID(), 2).getReceiverID() == 
				ashley.getUserID());
		assertTrue(testServer.getChat(memes.getChatLogID(), 3).getReceiverID() == 
				ron.getUserID()); 
		//Ok, the third message was directed at Ron and the second message
		//was directed at Ashley, so the replies to messages in the server 
		//are fine.
		
		//Okay, the addChat also works since these chats were inside of the 
		//testServer's Meme channel
		
		//These also test the addChat() of the ChatLog since they call
		//the ChatLogs' addChat method.
		assertTrue(testServer.getChatLog(memes.getChatLogID()) == memes);
		//Okay, so the addChatLog also works since it was able to get memes
		//that verifies that memes was added to the Room.
		
		testServer.deleteChatLog(ashley.getUserID(), memes.getChatLogID());
		assertTrue(testServer.getChatLog(memes.getChatLogID()) == null);
		//Cool, the Memes chatlog was deleted.
		//System.out.println(testServer.getUser(ron.getUserID()) + "\n" + noob);
		assertTrue(testServer.getUser(ron.getUserID()) == noob);
		//Ron is a Noob at first
		
		//Lets make Ron a moderator
		testServer.giveRole(ashley.getUserID(), ron.getUserID(), moderator);
		assertTrue(testServer.getUser(ron.getUserID()) == moderator);
		//Ron is now a moderator.
		assertTrue(testServer.isPublic()); //Room is currently public
		testServer.setRoomType(ashley.getUserID(), false);
		assertTrue(!testServer.isPublic()); //Room is now private.
		/*Only invited users can join, so if we kick Ron out now, he can't
		 * join back unless he's invited. Let us show this.
		 */
		
		testServer.removeUser(ashley.getUserID(), ron.getUserID()); 
		//Ashley kicks Ron out.
		assertTrue(testServer.getUser(ron.getUserID()) == null); 
		//Ron's removed from server
		testServer.addUser(ron.getUserID(), userList, roomList);
		assertTrue(testServer.getUser(ron.getUserID()) == null); 
		//Ron is still not added. Lets invite Ron and then he should be able
		//to be added.
		testServer.inviteUser(ashley.getUserID(), ron.getUserID());
		testServer.addUser(ron.getUserID(), noob, userList, roomList);
		assertTrue(testServer.getUser(ron.getUserID()) == noob); 
		//Ron has been added since he was invited and then got added.
	}
	
	@Test
	void chatLogTest(){
		//We want to test deleteChat, so lets see a message that is there,
		//then delete the message,
		//then check that the message is not there.
		assertTrue(memes.getChat(1).getMessage() == "What did the cat say to the dog?\nMeow");
		memes.deleteChat(1);
		assertTrue(memes.getChat(1) == null);
		//Message was deleted
		
		//Ron decided to be annoying so he's spammed 5 messages really quickly.
		//Lets delete all his messages at once
		chat.addChat("Hi", ron.getUserID());
		chat.addChat("Hi", ron.getUserID());
		chat.addChat("Hi", ron.getUserID());
		chat.addChat("Hi", ron.getUserID());
		chat.addChat("Hi", ron.getUserID());
		assertTrue(chat.getChat(3).getMessage() == "Hi");
		assertTrue(chat.getChat(4).getMessage() == "Hi");
		assertTrue(chat.getChat(5).getMessage() == "Hi");
		assertTrue(chat.getChat(6).getMessage() == "Hi");
		assertTrue(chat.getChat(7).getMessage() == "Hi");
		chat.deleteAllMessagesByUser(ron.getUserID());
		assertTrue(chat.getChat(3) == null);
		assertTrue(chat.getChat(4) == null);
		assertTrue(chat.getChat(5) == null);
		assertTrue(chat.getChat(6) == null);
		assertTrue(chat.getChat(7) == null);
		//Deleted all the messages by Ron.
		assertTrue(memes.getChat(3).getMessage() == "Idk... I thought you knew lol.");
		//Remember from earlier that Ron posted this message. Well, it is 
		//not gone because the deleteAllMessagesByUser was only fired on 
		//chat which is a different ChatLog than memes.
		testServer.addChat("Hey guys, important update on the competition!", 
				announcements.getChatLogID(), 
				john.getUserID());
		Chat pinnedChat = announcements.getChat(2);
		//The chat we added to announcements
		pinnedChat.pin(); //pin the message
		assertTrue(pinnedChat.getMessage() == "Hey guys, important update on the competition!");
		assertTrue(pinnedChat == announcements.getPinned().get(0));
		//Check if the pinned message is this one.
		pinnedChat.unpin();
		assertTrue(announcements.getPinned().size() == 0); 
		//No pinned messages since we just unpinned that one above.
	}
	
	@Test
	void checkPermissionsAndRole() {
		//All of the chats and changes in Concord have been done by the 
		//four users, so we will initialize new users for the room that
		//are clearly admins, moderators, noobs, and roleGiver.
		
		User adminUser = userList.addUser("Admin");
		User modUser = userList.addUser("Moderator");
		User noobUser = userList.addUser("Noob");
		User roleGiverUser = userList.addUser("Role Giver");
		testServer.addUser(adminUser.getUserID(), admin, userList, roomList);
		testServer.addUser(modUser.getUserID(), moderator, userList, roomList);
		testServer.addUser(noobUser.getUserID(), noob, userList, roomList);
		testServer.addUser(roleGiverUser.getUserID(), roleGiver, userList, roomList);
		
		//deleteChatPermission check
		Chat relevantChat = chat.getChat(1);
		//Expect noob and roleGiver cannot delete chat. 
		//Expect admin and moderator can delete chat.
		assertTrue(testServer.getChat(chat.getChatLogID(), 
				relevantChat.getChatID()).getMessage() == 
				"Ok, which one of you guys talked to me?");
		//Okay, so chat exists.
		testServer.deleteChat(noobUser.getUserID(), 
				chat.getChatLogID(), 
				relevantChat.getChatID());
		testServer.deleteChat(roleGiverUser.getUserID(), 
				chat.getChatLogID(), 
				relevantChat.getChatID());
		
		assertTrue(testServer.getChat(chat.getChatLogID(), 
				relevantChat.getChatID()).getMessage() == 
				"Ok, which one of you guys talked to me?"); 
		//Message still exist
		
		testServer.deleteChat(adminUser.getUserID(), 
				chat.getChatLogID(), 
				relevantChat.getChatID());
		
		assertTrue(testServer.getChat(chat.getChatLogID(), 
				relevantChat.getChatID()) == null); 
		//Message got deleted since it is an administrator
		
		assertTrue(testServer.getChat(chat.getChatLogID(), 2) != null);
		//The second message in chat exists
		
		testServer.deleteChat(modUser.getUserID(), 
				chat.getChatLogID(), 
				2);
		
		assertTrue(testServer.getChat(chat.getChatLogID(), 2) == null);
		//Second message did get deleted since it is a moderator
		
		//removeUserPermission
		//Only mods and admins should be able to remove users.
		//I hate to do this again, but lets remove Ron once again
		assertTrue(testServer.getUser(ron.getUserID()) != null);
		//Ron exists right now
		testServer.removeUser(noobUser.getUserID(), ron.getUserID());
		testServer.removeUser(roleGiverUser.getUserID(), ron.getUserID());
		assertTrue(testServer.getUser(ron.getUserID()) != null);
		//Ron exists still
		testServer.removeUser(modUser.getUserID(), ron.getUserID());
		assertTrue(testServer.getUser(ron.getUserID()) == null);
		//Ron no longer exists in the room.
		//Lets now try and remove Stacy by the administrator
		assertTrue(testServer.getUser(stacy.getUserID()) != null);
		//Stacy does exist in the room.
		testServer.removeUser(adminUser.getUserID(), stacy.getUserID());
		assertTrue(testServer.getUser(stacy.getUserID()) == null);
		
		//roomType Permission (Whether users can set the type of the room.)
		//Expect mods and admins only to be able to set room type
		assertTrue(testServer.isPublic()); //Test Server is public.
		//Lets try and have noob and role giver change server to private.
		testServer.setRoomType(noobUser.getUserID(), false);
		testServer.setRoomType(roleGiverUser.getUserID(), false);
		assertTrue(testServer.isPublic()); //Test Server is still public.
		
		testServer.setRoomType(modUser.getUserID(), false);
		assertTrue(!testServer.isPublic()); //Test Server is now private.
		testServer.setRoomType(modUser.getUserID(), true); //set back to public 
		
		testServer.setRoomType(adminUser.getUserID(), false); //Test Server is now private.
		assertTrue(!testServer.isPublic()); //Test Server is now private.
		
		//invite permission
		//Expect only mods and admins to be able to invite others.
		assertTrue(testServer.isInvited(ron.getUserID()) == null);
		assertTrue(testServer.getUser(ron.getUserID()) == null);
		//Ron is not invited to the server and is not in the server.
		//We set the server to private so we need to invite user before they can join.
		testServer.inviteUser(noobUser.getUserID(), ron.getUserID());
		testServer.inviteUser(roleGiverUser.getUserID(), ron.getUserID());
		assertTrue(testServer.isInvited(ron.getUserID()) == null || 
				testServer.isInvited(ron.getUserID()) == false);
		//Ron is still not invited to the server.
		testServer.inviteUser(adminUser.getUserID(), ron.getUserID());
		assertTrue(testServer.isInvited(ron.getUserID()));
		//Ron is invited now.
		
		testServer.uninviteUser(adminUser.getUserID(), ron.getUserID());
		//Uninvite Ron 
		assertTrue(testServer.isInvited(ron.getUserID()) == null || 
				testServer.isInvited(ron.getUserID()) == false);
		//Ron is not invited anymore
		testServer.inviteUser(modUser.getUserID(), ron.getUserID());
		assertTrue(testServer.isInvited(ron.getUserID()));
		//Ron is now invited 
		
		//giveRole permission
		//Only roleGiverUser and adminUser should be able to give roles.
		//NoobUsers and ModUsers cannot give roles.
		assertTrue(testServer.getUser(john.getUserID()) == moderator);
		//Lets make john a noob
		testServer.giveRole(noobUser.getUserID(), john.getUserID(), noob);
		testServer.giveRole(modUser.getUserID(), john.getUserID(), noob);
		assertTrue(testServer.getUser(john.getUserID()) == moderator);
		//John is still a moderator because noobUser and modUser cannot give roles!
		testServer.giveRole(adminUser.getUserID(), john.getUserID(), noob);
		assertTrue(testServer.getUser(john.getUserID()) == noob);
		//John is now a noob since admins can assign roles.
		//Lets make John a moderator again 
		testServer.giveRole(roleGiverUser.getUserID(), john.getUserID(), moderator);
		assertTrue(testServer.getUser(john.getUserID()) == moderator);
		//John is a moderator again because roleGiver can give out roles.
		//createChatLog permission
		//Only expect admin to be able to create chatlogs.
		ChatLog codeDiscussion = testServer.addChatLog(noobUser.getUserID());
		assertTrue(codeDiscussion == null); 
		//Noob cannot create a ChatLog
		codeDiscussion = testServer.addChatLog(modUser.getUserID());
		assertTrue(codeDiscussion == null); 
		//Mods cannot create ChatLogs
		codeDiscussion = testServer.addChatLog(roleGiverUser.getUserID());
		assertTrue(codeDiscussion == null); 
		//roleGivers cannot create ChatLogs
		codeDiscussion = testServer.addChatLog(adminUser.getUserID());
		assertTrue(codeDiscussion != null); 
		assertTrue(testServer.getChatLog(codeDiscussion.getChatLogID()) != null);
		//codeDiscussion is created since an admin created it.
		
		//deleteChatLog permission
		//Expect only admin to be able to delete the chatLog
		testServer.deleteChatLog(noobUser.getUserID(), codeDiscussion.getChatLogID());
		testServer.deleteChatLog(modUser.getUserID(), codeDiscussion.getChatLogID());
		testServer.deleteChatLog(roleGiverUser.getUserID(), codeDiscussion.getChatLogID());
		assertTrue(testServer.getChatLog(codeDiscussion.getChatLogID()) != null);
		//The ChatLog is not deleted since noobs, mods, and role givers cannot delete chatLog
		testServer.deleteChatLog(adminUser.getUserID(), codeDiscussion.getChatLogID());
		assertTrue(testServer.getChatLog(codeDiscussion.getChatLogID()) == null);
		//deleteRoom permission
		//Expect only admin to be able to delete room
		roomList.deleteRoom(noobUser.getUserID(), testServer.getRoomID());
		roomList.deleteRoom(modUser.getUserID(), testServer.getRoomID());
		roomList.deleteRoom(roleGiverUser.getUserID(), testServer.getRoomID());
		assertTrue(roomList.getRoom(testServer.getRoomID()) != null); 
		//The Test Server is not deleted by noobs, mods, nor roleGivers.
		roomList.deleteRoom(adminUser.getUserID(), testServer.getRoomID());
		assertTrue(roomList.getRoom(testServer.getRoomID()) == null); 
		//The Test server is now deleted.
	}
}
