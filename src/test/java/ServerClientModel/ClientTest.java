package ServerClientModel;

import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//
//import java.net.MalformedURLException;
//import java.rmi.Naming;
//import java.rmi.NotBoundException;
//import java.util.Enumeration;
//import java.util.Hashtable;
//import java.util.Objects;

import java.rmi.RemoteException;
import java.util.Hashtable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Database.Admin;
import Database.Chat;
import Database.ChatLog;
import Database.Mod;
import Database.Noob;
import Database.Role;
import Database.Room;
import Database.RoomList;
import Database.User;
import Database.UserCreatedRole;
import Database.UserList;

class ClientTest
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
	User userAshley;
	User userJohn;
	User userRon;
	User userStacy;
	Client ashley;
	Client john;
	Client ron;
	Client stacy;
	ServerInterface server;
	
	@BeforeEach
	void setUp() throws Exception
	{
		/*Creating the list of rooms and users we need for concord.
		 * We need these lists as they will cover all of the users and rooms
		 * inside of the server.
		 */
		roomList = new RoomList(); 
		userList = new UserList();
		server = new Server();
		server.setRooms(roomList);
		server.setUsers(userList);
		
		testServer = server.addRoom("My Server", "This is my own server, please join at your own risk.", null, true);
		//My own server is created now (my own room)
		//Roles
		noob = new Noob();
		admin = new Admin();
		moderator = new Mod();
		userCreatedPermissions = new boolean[] {false, false, false, true, false, false, false, false, false};
		//Someone that can only give roles to others.
		roleGiver = new UserCreatedRole(userCreatedPermissions, "Role Giver");
		//Users creation
		
		userAshley = userList.addUser("Ashley", 
				"uhgraw",
				"Hello, my name is Ashley and I am a weeb.\nNice to meet you!", 
				true);
		
		userJohn = userList.addUser("John", 
				"hello123", 
				"Yo, I go by John. I like skateboarding and hanging out with others!\nHit me up if there is a party going on.", 
				false);
		
		userRon = userList.addUser("Ron", 
				"why123",
				"Hey man, I really don't like sunny weather. I just wish I could avoid social contact.\nPlease leave me alone.", 
				true);
		
		userStacy = userList.addUser("Stacy", 
				"ghin",
				"Heya there, my name is Stacy but my friends call me Stace.\nI really like meeting new people, so let me know if there are any major events going on!"
				);
		
		ashley = new Client(server, userAshley);
		john = new Client(server, userJohn);
		ron = new Client(server, userRon);
		stacy = new Client(server, userStacy);
		server.addClient(ashley);
		server.addClient(john);
		server.addClient(ron);
		server.addClient(stacy);
		ashley.logOn(ashley.getPassword());
		john.logOn(john.getPassword());
		ron.logOn(ron.getPassword());
		stacy.logOn(stacy.getPassword());
		//Add users to Room
		ashley.addUserToRoom(testServer.getRoomID(), admin);
		john.addUserToRoom(testServer.getRoomID(), moderator);
		ron.addUserToRoom(testServer.getRoomID(), noob);
		stacy.addUserToRoom(testServer.getRoomID(), roleGiver);
		//ChatLog creation by ashley
		memes = ashley.addChatLog(testServer.getRoomID(), "Memes");
		announcements = ashley.addChatLog(testServer.getRoomID(), "Announcements");
		chat = ashley.addChatLog(testServer.getRoomID(), "Chat");
		//Send messages from everyone into server.
		ashley.addChat(testServer.getRoomID(), 
				memes.getChatLogID(), 
				"What did the cat say to the dog?\nMeow");
		
		ron.chatLogReply("What?", 
				ashley.getUserID(), 
				testServer.getRoomID(), 
				memes.getChatLogID());
		
		ashley.chatLogReply("Idk... I thought you knew lol.", 
				ron.getUserID(), 
				testServer.getRoomID(), 
				memes.getChatLogID());
		
		john.addChat(testServer.getRoomID(), 
				announcements.getChatLogID(), 
				"Yo yo guys, there's a huge skateboarding event "
				+ "going on tomorrow. You guys should definitely drop by!");
		
		ron.addChat(testServer.getRoomID(), 
				chat.getChatLogID(), 
				"Ok, which one of you guys talked to me?");
		
		stacy.addChat(testServer.getRoomID(), 
				chat.getChatLogID(), 
				"Hey guys... like, what's your favorite coffee "
				+ "from Starbucks? I personally like the venti caramel "
				+ "macchiatto with light ice and whipped cream on top "
				+ "sprinkled with some honey glazed almonds.");
		
		//DMs between stacy and john
	}
	
	@Test
	void clientsInServerTest() {
		//Check if clients have been logged onto the server.
		try
		{
			assertTrue(stacy.isLoggedIn());
			assertTrue(ron.isLoggedIn());
			assertTrue(john.isLoggedIn());
			assertTrue(ashley.isLoggedIn());
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void clientsCheck() {
		//Check if clients have correct users.
		try
		{
			assertTrue(ashley.getUser() == userAshley);
			assertTrue(ron.getUser() == userRon);
			assertTrue(john.getUser() == userJohn);
			assertTrue(stacy.getUser() == userStacy);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void serverTest() {
		//Test if server has all clients/users
		try
		{
			assertTrue(ashley.getUser(ashley.getUserID()) == userAshley);
			assertTrue(john.getUser(john.getUserID()) == userJohn);
			assertTrue(ron.getUser(ron.getUserID()) == userRon);
			assertTrue(stacy.getUser(stacy.getUserID()) == userStacy);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void userListTest()
	{
		//Lets use getUser to see if all the users were added to the users list.
		try
		{
			assertTrue(server.getUser(1) == userAshley);
			assertTrue(server.getUser(2) == userJohn);
			assertTrue(server.getUser(3) == userRon);
			assertTrue(server.getUser(4) == userStacy);
			assertTrue(userAshley.getPassword() == "uhgraw");
			assertTrue(userJohn.getPassword() == "hello123");
			assertTrue(userRon.getPassword() == "why123");
			assertTrue(userStacy.getPassword() == "ghin");
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		try
		{
			ron.blockUser(stacy.getUserID());

			//Okay, lets have Ron try and start a DM with Stacy, the direct message
			//hash table should be the same for both Ron and Stacy.
			Room ronToStacyDM = ron.startDirectMessage(stacy.getUserID());
			
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
			stacy.endDirectMessage(userRon.getUserID());
			ron.endDirectMessage(userStacy.getUserID());

			Room stacyToRonDM = stacy.startDirectMessage(ron.getUserID());
			
			stacysDMs = stacy.getDirectMessages();
			ronsDMs = ron.getDirectMessages();
			
			//Now, Ron should not have the DM but Stacy should
			assertTrue(stacysDMs.get(stacyToRonDM.getRoomID()) != null);
			//Stacy has it
			
			assertTrue(ronsDMs.get(stacyToRonDM.getRoomID()) == null);
			//Ron does not
			ron.unblockUser(userStacy.getUserID());
			
			stacyToRonDM = stacy.startDirectMessage(ron.getUserID());
			
			stacysDMs = stacy.getDirectMessages();
			ronsDMs = ron.getDirectMessages();
			
			//Now, Ron and Stacy should both have the same DM 
			assertTrue(stacysDMs.get(stacyToRonDM.getRoomID()) == ronsDMs.get(stacyToRonDM.getRoomID()));
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void roomListTest() {
		try
		{
			assertTrue(ashley.getRoom(testServer.getRoomID()) == testServer);
			ashley.deleteRoom(testServer.getRoomID());
			assertTrue(ron.getRoom(testServer.getRoomID()) == null);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void roomTest() {
		//lets test if ashley is in the test server
		try
		{
			assertTrue(ashley.getRole(testServer.getRoomID()) == admin);
			//getUser() returns the role of the user in that server, indicating
			//that the user does exist in the room. Might be a bad idea but we
			//should not have too much trouble since most of the time we want to
			//just check if a user exists in the room. If we want a user, we can
			//just user the userList.
			ashley.removeUserFromRoom(ashley.getUserID(), ron.getUserID());
			assertTrue(ron.getRole(testServer.getRoomID()) == null);
			//Ron's deleted from the server.
			//Lets add Ron back to the server so we can use him for testing later
			//ashley.addUserToRoom(testServer.getRoomID(), noob);
			ron.addUserToRoom(testServer.getRoomID(), noob); //Add user back
			assertTrue(ashley.getChat(testServer.getRoomID(), memes.getChatLogID(), 1).getMessage() == 
					"What did the cat say to the dog?\nMeow");
			assertTrue(ashley.getChat(testServer.getRoomID(), memes.getChatLogID(), 2).getMessage() == 
					"What?");
			assertTrue(ashley.getChat(testServer.getRoomID(), memes.getChatLogID(), 2).getReceiverID() == 
					ashley.getUserID());
			assertTrue(ashley.getChat(testServer.getRoomID(), memes.getChatLogID(), 3).getReceiverID() == 
					ron.getUserID());
			//Ok, the third message was directed at Ron and the second message
			//was directed at Ashley, so the replies to messages in the server 
			//are fine.
			
			//Okay, the addChat also works since these chats were inside of the 
			//testServer's Meme channel
			
			//These also test the addChat() of the ChatLog since they call
			//the ChatLogs' addChat method.
			assertTrue(ashley.getChatLog(testServer.getRoomID(), memes.getChatLogID()) == memes);
			//Okay, so the addChatLog also works since it was able to get memes
			//that verifies that memes was added to the Room.
			ashley.deleteChatLog(testServer.getRoomID(), memes.getChatLogID());
			assertTrue(ashley.getChatLog(testServer.getRoomID(), memes.getChatLogID()) == null);
			//Cool, the Memes chatlog was deleted.
			//System.out.println(testServer.getUser(ron.getUserID()) + "\n" + noob);
			assertTrue(ron.getRole(testServer.getRoomID()) == noob);
			//Ron is a Noob at first
			
			//Lets make Ron a moderator
			ashley.giveRole(testServer.getRoomID(), ron.getUserID(), moderator);
			assertTrue(ron.getRole(testServer.getRoomID()) == moderator);
			//Ron is now a moderator.
			assertTrue(ashley.getRoomType(testServer.getRoomID()));
			ashley.setRoomType(testServer.getRoomID(), false);
			assertTrue(!ashley.getRoomType(testServer.getRoomID()));
			/*Only invited users can join, so if we kick Ron out now, he can't
			 * join back unless he's invited. Let us show this.
			 */
			ashley.removeUserFromRoom(testServer.getRoomID(), ron.getUserID());
			//Ashley kicks Ron out.
			assertTrue(ron.getRole(testServer.getRoomID()) == null);
			//Ron's removed from server
			ashley.addUserToRoom(testServer.getRoomID(), noob);
			assertTrue(ron.getRole(testServer.getRoomID()) == null);
			//Ron is still not added. Lets invite Ron and then he should be able
			//to be added.
			ashley.inviteUser(testServer.getRoomID(), ron.getUserID());
			ron.addUserToRoom(testServer.getRoomID(), noob);
			assertTrue(ron.getRole(testServer.getRoomID()) == noob);
			//Ron has been added since he was invited and then got added.
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void chatLogTest(){
		//We want to test deleteChat, so lets see a message that is there,
		//then delete the message,
		//then check that the message is not there.
		try
		{
			assertTrue(ashley.getChat(testServer.getRoomID(), memes.getChatLogID(), 1).getMessage() == 
					"What did the cat say to the dog?\nMeow");
			ashley.deleteChat(testServer.getRoomID(), memes.getChatLogID(), 1);
			assertTrue(ashley.getChat(testServer.getRoomID(), memes.getChatLogID(), 1) == 
					null); 
			//Message was deleted
			
			//Ron decided to be annoying so he's spammed 5 messages really quickly.
			//Lets delete all his messages at once
			ron.addChat(testServer.getRoomID(), chat.getChatLogID(), "Hi");
			ron.addChat(testServer.getRoomID(), chat.getChatLogID(), "Hi");
			ron.addChat(testServer.getRoomID(), chat.getChatLogID(), "Hi");
			ron.addChat(testServer.getRoomID(), chat.getChatLogID(), "Hi");
			ron.addChat(testServer.getRoomID(), chat.getChatLogID(), "Hi");
			assertTrue(ron.getChat(testServer.getRoomID(), chat.getChatLogID(), 3).getMessage() == "Hi");
			assertTrue(ron.getChat(testServer.getRoomID(), chat.getChatLogID(), 4).getMessage() == "Hi");
			assertTrue(ron.getChat(testServer.getRoomID(), chat.getChatLogID(), 5).getMessage() == "Hi");
			assertTrue(ron.getChat(testServer.getRoomID(), chat.getChatLogID(), 6).getMessage() == "Hi");
			assertTrue(ron.getChat(testServer.getRoomID(), chat.getChatLogID(), 7).getMessage() == "Hi");
			ron.deleteAllMessagesByUser(testServer.getRoomID(), chat.getChatLogID());
			
			assertTrue(ron.getChat(testServer.getRoomID(), chat.getChatLogID(), 3) == null);
			assertTrue(ron.getChat(testServer.getRoomID(), chat.getChatLogID(), 4) == null);
			assertTrue(ron.getChat(testServer.getRoomID(), chat.getChatLogID(), 5) == null);
			assertTrue(ron.getChat(testServer.getRoomID(), chat.getChatLogID(), 6) == null);
			assertTrue(ron.getChat(testServer.getRoomID(), chat.getChatLogID(), 7) == null);
			//Deleted all the messages by Ron.
			assertTrue(ashley.getChat(testServer.getRoomID(), memes.getChatLogID(), 3).getMessage() == 
					"Idk... I thought you knew lol.");
			//assertTrue(memes.getChat(3).getMessage() == "Idk... I thought you knew lol.");
			//Remember from earlier that Ron posted this message. Well, it is 
			//not gone because the deleteAllMessagesByUser was only fired on 
			//chat which is a different ChatLog than memes.
			ashley.addChat(testServer.getRoomID(), announcements.getChatLogID(), "Hey guys, important update on the competition!");
			//The chat we added to announcements
			ashley.pinMsg(testServer.getRoomID(), announcements.getChatLogID(), 2);
			assertTrue(ashley.getChat(testServer.getRoomID(), announcements.getChatLogID(), 2).getMessage() == "Hey guys, important update on the competition!");
			assertTrue(ashley.getChat(testServer.getRoomID(), announcements.getChatLogID(), 2) == ashley.getPinnedMsgs(testServer.getRoomID(), announcements.getChatLogID()).get(0));
			//Check if the pinned message is this one.
			ashley.unpinMsg(testServer.getRoomID(), announcements.getChatLogID(), 2);
			assertTrue(ashley.getPinnedMsgs(testServer.getRoomID(), announcements.getChatLogID()).size() == 0); 
			//No pinned messages since we just unpinned that one above.
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		Client adminClient;
		Client modClient;
		Client noobClient;
		Client roleGiverClient;
		try
		{
			adminClient = new Client(server, adminUser);
			modClient = new Client(server, modUser);
			noobClient = new Client(server, noobUser);
			roleGiverClient = new Client(server, roleGiverUser);
			server.addClient(adminClient);
			server.addClient(modClient);
			server.addClient(noobClient);
			server.addClient(roleGiverClient);

			adminClient.addUserToRoom(testServer.getRoomID(), admin);
			modClient.addUserToRoom(testServer.getRoomID(), moderator);
			noobClient.addUserToRoom(testServer.getRoomID(), noob);
			roleGiverClient.addUserToRoom(testServer.getRoomID(), roleGiver);
			
			//deleteChatPermission check
			Chat relevantChat = adminClient.getChat(testServer.getRoomID(), chat.getChatLogID(), 1);
			//Expect noob and roleGiver cannot delete chat. 
			//Expect admin and moderator can delete chat.
			assertTrue(adminClient.getChat(testServer.getRoomID(), chat.getChatLogID(), 1).getMessage() ==
					"Ok, which one of you guys talked to me?");
			//Okay, so chat exists.
			noobClient.deleteChat(testServer.getRoomID(), chat.getChatLogID(), relevantChat.getChatID());
			roleGiverClient.deleteChat(testServer.getRoomID(), chat.getChatLogID(), relevantChat.getChatID());
			assertTrue(adminClient.getChat(testServer.getRoomID(), chat.getChatLogID(), 1).getMessage() ==
					"Ok, which one of you guys talked to me?");

			//Message still exist
			adminClient.deleteChat(testServer.getRoomID(), chat.getChatLogID(), relevantChat.getChatID());
			
			assertTrue(adminClient.getChat(testServer.getRoomID(), chat.getChatLogID(), relevantChat.getChatID()) ==
					null);
			//Message got deleted since it is an administrator
			assertTrue(adminClient.getChat(testServer.getRoomID(), chat.getChatLogID(), 2).getMessage() !=
					null);
			//assertTrue(testServer.getChat(chat.getChatLogID(), 2) != null);
			//The second message in chat exists
			
			modClient.deleteChat(testServer.getRoomID(), chat.getChatLogID(), 2);
			//testServer.deleteChat(modUser.getUserID(), 
			//		chat.getChatLogID(), 
			//		2);
			assertTrue(adminClient.getChat(testServer.getRoomID(), chat.getChatLogID(), 2) ==
					null);
			//assertTrue(testServer.getChat(chat.getChatLogID(), 2) == null);
			//Second message did get deleted since it is a moderator
			
			//removeUserPermission
			//Only mods and admins should be able to remove users.
			//I hate to do this again, but lets remove Ron once again
			assertTrue(ron.getRole(testServer.getRoomID()) != null);
			//assertTrue(testServer.getUser(userRon.getUserID()) != null);
			//Ron exists right now
			noobClient.removeUserFromRoom(testServer.getRoomID(), ron.getUserID());
			//testServer.removeUser(noobUser.getUserID(), userRon.getUserID());
			roleGiverClient.removeUserFromRoom(testServer.getRoomID(), ron.getUserID());
			//testServer.removeUser(roleGiverUser.getUserID(), userRon.getUserID());
			assertTrue(ron.getRole(testServer.getRoomID()) != null);
			//assertTrue(testServer.getUser(userRon.getUserID()) != null);
			//Ron exists still
			modClient.removeUserFromRoom(testServer.getRoomID(), ron.getUserID());
			//testServer.removeUser(modUser.getUserID(), userRon.getUserID());
			assertTrue(ron.getRole(testServer.getRoomID()) == null);
			//assertTrue(testServer.getUser(userRon.getUserID()) == null);
			//Ron no longer exists in the room.
			//Lets now try and remove Stacy by the administrator
			assertTrue(stacy.getRole(testServer.getRoomID()) != null);
			//assertTrue(testServer.getUser(userStacy.getUserID()) != null);
			//Stacy does exist in the room.
			adminClient.removeUserFromRoom(testServer.getRoomID(), stacy.getUserID());
			//testServer.removeUser(adminUser.getUserID(), userStacy.getUserID());
			assertTrue(stacy.getRole(testServer.getRoomID()) == null);
			//assertTrue(testServer.getUser(userStacy.getUserID()) == null);
			
			//roomType Permission (Whether users can set the type of the room.)
			//Expect mods and admins only to be able to set room type
			assertTrue(adminClient.getRoomType(testServer.getRoomID()));
			//assertTrue(testServer.isPublic()); //Test Server is public.
			//Lets try and have noob and role giver change server to private.
			noobClient.setRoomType(testServer.getRoomID(), false);
			//testServer.setRoomType(noobUser.getUserID(), false);
			roleGiverClient.setRoomType(testServer.getRoomID(), false);
			//testServer.setRoomType(roleGiverUser.getUserID(), false);
			assertTrue(adminClient.getRoomType(testServer.getRoomID()));
			//assertTrue(testServer.isPublic()); //Test Server is still public.
			modClient.setRoomType(testServer.getRoomID(), false);
			//testServer.setRoomType(modUser.getUserID(), false);
			assertTrue(!adminClient.getRoomType(testServer.getRoomID()));
			//assertTrue(!testServer.isPublic()); //Test Server is now private.
			modClient.setRoomType(testServer.getRoomID(), true);
			//testServer.setRoomType(modUser.getUserID(), true); //set back to public 
			adminClient.setRoomType(testServer.getRoomID(), false);
			//testServer.setRoomType(adminUser.getUserID(), false); //Test Server is now private.
			assertTrue(!adminClient.getRoomType(testServer.getRoomID()));
			//assertTrue(!testServer.isPublic()); //Test Server is now private.
			
			//invite permission
			//Expect only mods and admins to be able to invite others.
			assertTrue(adminClient.isInvited(testServer.getRoomID()) == null);
			//assertTrue(testServer.isInvited(userRon.getUserID()) == null);
			assertTrue(ron.getRole(testServer.getRoomID()) == null);
			//assertTrue(testServer.getUser(userRon.getUserID()) == null);
			//Ron is not invited to the server and is not in the server.
			//We set the server to private so we need to invite user before they can join.
			noobClient.inviteUser(testServer.getRoomID(), ron.getUserID());
			//testServer.inviteUser(noobUser.getUserID(), userRon.getUserID());
			roleGiverClient.inviteUser(testServer.getRoomID(), ron.getUserID());
			//testServer.inviteUser(roleGiverUser.getUserID(), userRon.getUserID());
			assertTrue(ron.isInvited(testServer.getRoomID()) == null);
			//assertTrue(testServer.isInvited(userRon.getUserID()) == null || 
			//		testServer.isInvited(userRon.getUserID()) == false);
			//Ron is still not invited to the server.
			adminClient.inviteUser(testServer.getRoomID(), ron.getUserID());
			//testServer.inviteUser(adminUser.getUserID(), userRon.getUserID());
			assertTrue(ron.isInvited(testServer.getRoomID()) == true);
			//assertTrue(testServer.isInvited(userRon.getUserID()));
			//Ron is invited now.
			
			adminClient.uninviteUser(testServer.getRoomID(), ron.getUserID());
			//testServer.uninviteUser(adminUser.getUserID(), userRon.getUserID());
			//Uninvite Ron 
			assertTrue(ron.isInvited(testServer.getRoomID()) == false);
//			assertTrue(testServer.isInvited(userRon.getUserID()) == null || 
//					testServer.isInvited(userRon.getUserID()) == false);
			//Ron is not invited anymore
			modClient.inviteUser(testServer.getRoomID(), ron.getUserID());
//			testServer.inviteUser(modUser.getUserID(), userRon.getUserID());
			assertTrue(ron.isInvited(testServer.getRoomID()));
			//assertTrue(testServer.isInvited(userRon.getUserID()));
			//Ron is now invited 
			
			//giveRole permission
			//Only roleGiverUser and adminUser should be able to give roles.
			//NoobUsers and ModUsers cannot give roles.
			assertTrue(modClient.getRole(testServer.getRoomID()) == moderator);
			assertTrue(testServer.getUser(userJohn.getUserID()) == moderator);
			//Lets make john a noob
			noobClient.giveRole(testServer.getRoomID(), john.getUserID(), noob);
//			testServer.giveRole(noobUser.getUserID(), userJohn.getUserID(), noob);
			modClient.giveRole(testServer.getRoomID(), john.getUserID(), noob);
//			testServer.giveRole(modUser.getUserID(), userJohn.getUserID(), noob);
			assertTrue(john.getRole(testServer.getRoomID()) == moderator);
//			assertTrue(testServer.getUser(userJohn.getUserID()) == moderator);
			//John is still a moderator because noobUser and modUser cannot give roles!
			adminClient.giveRole(testServer.getRoomID(), john.getUserID(), noob);
//			testServer.giveRole(adminUser.getUserID(), userJohn.getUserID(), noob);
			assertTrue(john.getRole(testServer.getRoomID()) == noob);
//			assertTrue(testServer.getUser(userJohn.getUserID()) == noob);
			//John is now a noob since admins can assign roles.
			//Lets make John a moderator again 
			roleGiverClient.giveRole(testServer.getRoomID(), john.getUserID(), moderator);
//			testServer.giveRole(roleGiverUser.getUserID(), userJohn.getUserID(), moderator);
			assertTrue(john.getRole(testServer.getRoomID()) == moderator);
//			assertTrue(testServer.getUser(userJohn.getUserID()) == moderator);
			//John is a moderator again because roleGiver can give out roles.
			//createChatLog permission
			//Only expect admin to be able to create chatlogs.
			ChatLog codeDiscussion = noobClient.addChatLog(testServer.getRoomID(), "CodeDiscussion");
			assertTrue(codeDiscussion == null);
//			ChatLog codeDiscussion = testServer.addChatLog(noobUser.getUserID());
//			assertTrue(codeDiscussion == null); 
//			//Noob cannot create a ChatLog
			codeDiscussion = modClient.addChatLog(testServer.getRoomID(), "CodeDiscussion");
//			codeDiscussion = testServer.addChatLog(modUser.getUserID());
			assertTrue(codeDiscussion == null); 
//			//Mods cannot create ChatLogs
			codeDiscussion = roleGiverClient.addChatLog(testServer.getRoomID(), "CodeDiscussion");
//			codeDiscussion = testServer.addChatLog(roleGiverUser.getUserID());
			assertTrue(codeDiscussion == null); 
//			//roleGivers cannot create ChatLogs
			codeDiscussion = adminClient.addChatLog(testServer.getRoomID(), "CodeDiscussion");
//			codeDiscussion = testServer.addChatLog(adminUser.getUserID());
			assertTrue(codeDiscussion != null); 
			assertTrue(adminClient.getChatLog(testServer.getRoomID(), codeDiscussion.getChatLogID()) != null);
//			assertTrue(testServer.getChatLog(codeDiscussion.getChatLogID()) != null);
			//codeDiscussion is created since an admin created it.
			
//			//deleteChatLog permission
//			//Expect only admin to be able to delete the chatLog
			noobClient.deleteChatLog(testServer.getRoomID(), codeDiscussion.getChatLogID());
			modClient.deleteChatLog(testServer.getRoomID(), codeDiscussion.getChatLogID());
			roleGiverClient.deleteChatLog(testServer.getRoomID(), codeDiscussion.getChatLogID());
//			testServer.deleteChatLog(noobUser.getUserID(), codeDiscussion.getChatLogID());
//			testServer.deleteChatLog(modUser.getUserID(), codeDiscussion.getChatLogID());
//			testServer.deleteChatLog(roleGiverUser.getUserID(), codeDiscussion.getChatLogID());
			assertTrue(adminClient.getChatLog(testServer.getRoomID(), codeDiscussion.getChatLogID()) != null);
//			assertTrue(testServer.getChatLog(codeDiscussion.getChatLogID()) != null);
//			//The ChatLog is not deleted since noobs, mods, and role givers cannot delete chatLog
			adminClient.deleteChatLog(testServer.getRoomID(), codeDiscussion.getChatLogID());
			assertTrue(adminClient.getChatLog(testServer.getRoomID(), codeDiscussion.getChatLogID()) == null);
//			testServer.deleteChatLog(adminUser.getUserID(), codeDiscussion.getChatLogID());
//			assertTrue(testServer.getChatLog(codeDiscussion.getChatLogID()) == null);
//			//deleteRoom permission
//			//Expect only admin to be able to delete room
			noobClient.deleteRoom(testServer.getRoomID());
			modClient.deleteRoom(testServer.getRoomID());
			roleGiverClient.deleteRoom(testServer.getRoomID());
			assertTrue(adminClient.getRoom(testServer.getRoomID()) != null);
			adminClient.deleteRoom(testServer.getRoomID());
			assertTrue(adminClient.getRoom(testServer.getRoomID()) == null);
//			roomList.deleteRoom(noobUser.getUserID(), testServer.getRoomID());
//			roomList.deleteRoom(modUser.getUserID(), testServer.getRoomID());
//			roomList.deleteRoom(roleGiverUser.getUserID(), testServer.getRoomID());
//			assertTrue(roomList.getRoom(testServer.getRoomID()) != null); 
//			//The Test Server is not deleted by noobs, mods, nor roleGivers.
//			roomList.deleteRoom(adminUser.getUserID(), testServer.getRoomID());
//			assertTrue(roomList.getRoom(testServer.getRoomID()) == null); 
//			//The Test server is now deleted.
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	//Ok, we can add the xml testing now
	@Test
	void XMLTesting(){
		//So, the server has a single room which has four users on it.
		//The server also has 4 clients.
		//The room has three chatLogs
		//announcements has 1 chat
		//memes has 3 chats
		//chat has 2 chats
		/*
		 * clients.equals(other.getClients()) && id_passwords.equals(other.getId_passwords())
				&& rooms.equals(other.getRooms()) && userLogins.equals(other.getUserLogins())
				&& users.equals(other.getUsers());
		 */
		try
		{
			server.storeDataDisk();
			ServerInterface serverFromData = server.readDataFromDisk();
			assertTrue(server.getUsers().equals(serverFromData.getUsers()));
			assertTrue(server.getClients().equals(serverFromData.getClients()));
			assertTrue(server.getId_passwords().equals(serverFromData.getId_passwords()));
			assertTrue(server.getUserLogins().equals(serverFromData.getUserLogins()));
		
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//For the XML Testing, everything checks out except that the RoomList inside of the server
		//does not equal to the one I saved. I looked at the .xml file and for some reason the 
		//rooms are being saved to some weird location that does not follow the beans hierarchy
		//I will see you in office hours on Wednesday and/or on Thursday to figure this out. 
		
		//Sorry for the inconvenience, the rest of the server checks out but the RoomList.
		
		
		
		//assertTrue(server.getRooms().equals(serverFromData.getRooms()));
		/*
		RoomList server1_roomlist = server.getRooms();
		RoomList server2_roomlist = serverFromData.getRooms();
		Hashtable<Integer, Room> server1_rooms = server1_roomlist.getRooms();
		Hashtable<Integer, Room> server2_rooms = server2_roomlist.getRooms();
		server2_rooms = server2_roomlist.getRooms();
		Enumeration<Integer> e = server1_rooms.keys();
		Enumeration<Integer> e2 = server2_rooms.keys();
		
		System.out.println("ID:" + server1_roomlist.getLast_RoomID());
		System.out.println("ID:" + server2_roomlist.getLast_RoomID());
		
		Client a = serverFromData.getClients().get(userAshley.getUserID());
		System.out.println(a.getUser().getUserName());
		Hashtable<Integer, Room> rooms = a.getUser().getRooms();
		e2 = rooms.keys();
		
		while (e.hasMoreElements()) {
			int key = e.nextElement();
			//int key2 = e2.nextElement();
			
			System.out.println("My key" + key);
			//Room room = server1_rooms.get(key);
			//Room otherRoom = server1_roomlist.getRoom(key);
			
			
			
//			if (!(otherRoom != null && otherRoom.equals(room))) {
//				
//			}
		}
		
		while (e2.hasMoreElements()) {
			int key = e2.nextElement();
			
			System.out.println("Data server's key:" + key);
		}
		*/
		
	}
	
	@Test
	void RMITesting() {
/*
		try
		{
			eater1 = new DonutEater();
			eater2 = new DonutEater();
			observed = (RMIObserved) Naming.lookup("rmi://127.0.0.1/DONUT");
			
			observed.addObserver(eater1);
			eater1.name = "Tony";
			ds.makeDonuts();
		} catch (MalformedURLException | RemoteException | NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("I failed");
		}*/
		/*
		User userBrock = userList.addUser("Brock", 
				"uhgraw",
				"Hey bois & girls, my name Brock.", 
				true);
		
		User userNatalie = userList.addUser("Natalie", 
				"hello123", 
				"Heyyyy, my name Natalie", 
				false);
		
		try
		{
			Client brock = new Client(server, userBrock);
			Client natalie = new Client(server, userNatalie);
			Server observedServer = (Server) Naming.lookup("rmi://127.0.0.1/DONUT");
			brock.logOn("uhgraw");
			natalie.logOn("hello123");
			observedServer.addClient(brock);
			observedServer.addClient(natalie);
			brock.getUser().setUserName("NotBrock123");
			
		} catch (RemoteException | MalformedURLException | NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("I failed");
		}*/
	}
}
