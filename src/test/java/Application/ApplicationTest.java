package Application;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ConcordClientModel;
import model.ViewTransitionalModel;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import Database.Chat;
import Database.ChatLog;
import Database.Room;
import Database.User;
import ServerClientModel.Server;
import ServerClientModel.ServerInterface;

@ExtendWith(ApplicationExtension.class)
public class ApplicationTest
{
	ConcordClientModel client;
	ViewTransitionalModel vtm;
	@Start  //Before
	public void start(Stage stage) throws Exception
	{
		ServerInterface server;
		try {
			server = (ServerInterface) Naming.lookup("rmi://127.0.0.1/CONCORD");
		} catch (ConnectException e) {
			Registry registry = LocateRegistry.createRegistry(1099);
			server = new Server();
			registry.rebind("CONCORD", server);
		}
		client = new ConcordClientModel();
		vtm = new ViewTransitionalModel(client);
		vtm.showLogin();
	}
	
	@Test
	public void testLogin(FxRobot robot) {
		/*
		robot.clickOn("#refreshDataButton");
		robot.clickOn("#signupButton");
		
		Assertions.assertThat(robot.lookup("#usernameTextFieldSignup") != null);
		Assertions.assertThat(robot.lookup("#passwordTextFieldSignup") != null);
		
		enterValue("#usernameTextFieldSignup", "Jake", robot);
		enterValue("#passwordTextFieldSignup", "From State Farm", robot);
		robot.clickOn("#signupFinishedButton");
		
		robot.clickOn("#loginButton");
		// If the user clicks on the login button, the scene does not change and it does not allow the user to login.
		Assertions.assertThat(robot.lookup("#errorMsg") != null);
		
		enterValue("#usernameTextField", "non_existing_user", robot);
		enterValue("#passwordTextField", "random_password", robot);
		robot.clickOn("#loginButton");
		Assertions.assertThat(robot.lookup("#errorMsg") != null);
		
		enterValue("#usernameTextField", "bob", robot);
        enterValue("#passwordTextField", "wrong_password", robot);
        robot.clickOn("#loginButton");
        Assertions.assertThat(robot.lookup("#errorMsg") != null);

        enterValue("#usernameTextField", "Jake", robot);
        enterValue("#passwordTextField", "From State Farm", robot);
        robot.clickOn("#loginButton");
        Assertions.assertThat(robot.lookup("#errorMsg") == null);
        Assertions.assertThat(robot.lookup("#mainFrame") != null);
        Assertions.assertThat(robot.lookup("#addButton") != null);
        Assertions.assertThat(robot.lookup("#exploreButton") != null);
        Assertions.assertThat(robot.lookup("#DMButton") != null); */
	}
	
	@Test
	public void testEverything(FxRobot robot) {
		//Functional testing to test everything
		
		//First, lets add a room of our own
		robot.clickOn("#refreshDataButton");
		robot.clickOn("#signupButton");
		enterValue("#usernameTextFieldSignup", "Jibraan", robot);
		enterValue("#passwordTextFieldSignup", "password123", robot);
		robot.clickOn("#signupFinishedButton");
		enterValue("#usernameTextField", "Jibraan", robot);
        enterValue("#passwordTextField", "password123", robot);
        robot.clickOn("#loginButton");
		Assertions.assertThat(robot.lookup("#errorMsg") == null);
        Assertions.assertThat(robot.lookup("#mainFrame") != null);
        Assertions.assertThat(robot.lookup("#addButton") != null);
        Assertions.assertThat(robot.lookup("#exploreButton") != null);
        Assertions.assertThat(robot.lookup("#DMButton") != null);  
        try
		{
			client.initializeDemo(); //Initialize our data in the server
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        robot.clickOn("#addButton");
		Assertions.assertThat(robot.lookup("#roomNameTextField") != null);
		Assertions.assertThat(robot.lookup("#roomDescriptionTextField") != null);
		Assertions.assertThat(robot.lookup("#roomLogoTextField") != null);
		Assertions.assertThat(robot.lookup("#privateRoomCheckbox") != null);
		Assertions.assertThat(robot.lookup("#createRoomButton") != null);
		Assertions.assertThat(robot.lookup("#cancelCreationButton") != null);
		enterValue("#roomNameTextField", "Jibraans Server", robot);
		robot.clickOn("#createRoomButton");
		selectListItem("#roomsList", 0, robot);
		wait(0.5);
		checkRoomName("Jibraans Server", robot);
		wait(0.5);
		robot.clickOn("#menuButton");
		robot.clickOn("#createChannelButton");
		Assertions.assertThat(robot.lookup("#channelNameTextField").query() != null);
		enterValue("#channelNameTextField", "Server Rules", robot);
		robot.clickOn("#createButton");
		selectListItem("#chatLogList", 0, robot);
		enterValue("#chatTextField", "Please do not spam!", robot);
		robot.clickOn("#sendChatButton");
		wait(0.5);
		ListView<Chat> chats = getChats(robot); 
		ObservableList<Chat> chatsList = chats.getItems();
		wait(0.5);
		assertTrue(chatsList.get(0).getMessage().equals("Please do not spam!"));
		
		selectListItem("#roomsList", 0, robot);
		checkRoomName("Jibraans Server", robot);
		robot.clickOn("#exploreButton");
		selectListItem("#exploreListView", 1, robot);
		robot.clickOn("#addRoomExploreButton");
		selectListItem("#roomsList", 1, robot);
		selectListItem("#roomsList", 0, robot);
		wait(0.5);
		checkRoomName("Jibraans Server", robot);
		wait(1.1); //Works sometimes and sometimes it doesn't
		selectListItem("#roomsList", 1, robot);
		wait(0.5);
		selectListItem("#roomsList", 1, robot);
		checkRoomName("Test Room", robot);
		selectListItem("#chatLogList", 0, robot);
		wait(0.5);
		chats = getChats(robot); 
		chatsList = chats.getItems();
		//System.out.println(chatsList.get(0));
		assertTrue(chatsList.get(0).getMessage().equals("Hey rob, how are you?"));
		assertTrue(chatsList.get(1).getMessage().equals("Good, how about you?"));
		enterValue("#chatTextField", "Whats up guys?", robot);
		robot.clickOn("#sendChatButton");
		wait(0.5);
		chats = getChats(robot); 
		chatsList = chats.getItems();
		assertTrue(chatsList.get(2).getMessage().equals("Whats up guys?"));
		selectListItem("#usersInRoomList", 0, robot);
		wait(0.5);
		checkLabelText("#profilePopupName", "Rob", robot);
		checkLabelText("#profilePopupAboutMe", "My name is Rob!", robot);
		wait(0.5);
		Node label = robot.lookup("#profilePopupName").query();
		closeStageFromNode(label);
		wait(0.5);
		//Testing DMs
//		robot.clickOn("#DMButton");
//		wait(0.5);
//		assertTrue(robot.lookup("#usersButton").query() != null);
//		robot.clickOn("#usersButton");
//		wait(0.5);
//		ListView<User> users = getUsers(robot);
//		ObservableList<User> usersList = users.getItems();
//		assertTrue(usersList.get(0).getUserName().equals("Rob"));
//		assertTrue(usersList.get(1).getUserName().equals("John"));
//		assertTrue(usersList.get(2).getUserName().equals("Jibraan"));
//		selectListItem("#usersListForDM", 0, robot);
//		selectListItem("#usersListForDM", 1, robot);
//		wait(0.5);
//		ListView<Room> dms = getDMs(robot);
//		ObservableList<Room> dmsList = dms.getItems();
//		
//		assertTrue(dmsList.get(0).getName().equals("Jibraan, John"));
//		assertTrue(dmsList.get(1).getName().equals("Jibraan, Rob"));
//		selectListItem("#DMList", 0, robot);
//		wait(0.5);
//		enterValue("#chatTextField", "Sup John?", robot);
//		wait(0.5);
//		robot.clickOn("#sendChatButton");
//		wait(0.5);
//		//ListView<Chat> chats = getChats(robot); 
//		//ObservableList<Chat> chatsList = chats.getItems();
//		chats = getChats(robot); 
//		chatsList = chats.getItems();
//		
//		assertTrue(chatsList.get(0).getMessage().equals("Sup John?"));
//		selectListItem("#DMList", 1, robot);
//		wait(0.5);
//		enterValue("#chatTextField", "Hey Rob, how've you been?", robot);
//		wait(0.5);
//		robot.clickOn("#sendChatButton");
//		wait(0.5);
//		chats = getChats(robot); 
//		chatsList = chats.getItems();
//		assertTrue(chatsList.get(0).getMessage().equals("Hey Rob, how've you been?"));
//		robot.clickOn("#editProfileButton");
//		enterValue("#usernameTextFieldEditProfile", "Jacob", robot);
//		enterValue("#passwordTextFieldEditProfile", "password123", robot);
//		enterValue("#profileDataTextFieldEditProfile", "My name's Jacob!", robot);
//		robot.clickOn("#saveButton");
//		wait(0.5);
//		checkLabelText("#usernameLabel", "Jacob", robot);
//		
//		//Testing bots
//		
//		selectListItem("#roomsList", 1, robot);
//		wait(0.5);
//		robot.clickOn("#menuButton");
//		robot.clickOn("#addBotButton");
//		selectListItem("#botsListView", 0, robot);
//		robot.clickOn("#registerBotButton");
//		robot.clickOn("#menuButton");
//		robot.clickOn("#addBotButton");
//		selectListItem("#botsListView", 1, robot);
//		robot.clickOn("#registerBotButton");
//		wait(0.5);
//		users = getUsersInRoom(robot);
//		usersList = users.getItems();
//		
//		assertTrue(usersList.get(0).getUserName().equals("[NOTIFICATION BOT]"));
//		assertTrue(usersList.get(1).getUserName().equals("[SPAM BOT]"));
//		selectListItem("#chatLogList", 0, robot);
//		enterValue("#chatTextField", "spam", robot);
//		robot.clickOn("#sendChatButton");
//		enterValue("#chatTextField", "spam", robot);
//		robot.clickOn("#sendChatButton");
//		enterValue("#chatTextField", "spam", robot);
//		robot.clickOn("#sendChatButton");
//		enterValue("#chatTextField", "spam", robot);
//		robot.clickOn("#sendChatButton");
//		enterValue("#chatTextField", "spam", robot);
//		robot.clickOn("#sendChatButton");
//		chats = getChats(robot); 
//		chatsList = chats.getItems();
//		int lastSpamIndex = chatsList.size() - 2;
//		//Last five chats were spam messages.
//		assertTrue(chatsList.get(lastSpamIndex).getMessage().equals("spam")); 
//		assertTrue(chatsList.get(lastSpamIndex - 1).getMessage().equals("spam")); 
//		assertTrue(chatsList.get(lastSpamIndex - 2).getMessage().equals("spam")); 
//		assertTrue(chatsList.get(lastSpamIndex - 3).getMessage().equals("spam")); 
//		assertTrue(chatsList.get(lastSpamIndex - 4).getMessage().equals("spam")); 
//		wait(0.5);
//		
//		//Bot should tell us to stop spamming
//		assertTrue(chatsList.get(lastSpamIndex + 1).getSenderName().equals("[SPAM BOT]"));
//		assertTrue(chatsList.get(lastSpamIndex + 1).getMessage().equals("Please stop spamming!")); 
//		
//		//Lets have notification bot add like two notifications and then show those notifications
//		enterValue("#chatTextField", "!addNotification Check me out on twitch @JibraanKhan91412", robot);
//		robot.clickOn("#sendChatButton");
//		enterValue("#chatTextField", "!addNotification Hey guys, don't forget about our soccer game this weekend!", robot);
//		robot.clickOn("#sendChatButton");
//		
//		enterValue("#chatTextField", "!showNotifications", robot);
//		robot.clickOn("#sendChatButton");
//		
//		chats = getChats(robot); 
//		chatsList = chats.getItems();
//		//Both notification messages being shown will be by the notification bot
//		int notificationIndex = chatsList.size() - 1;
//		assertTrue(chatsList.get(notificationIndex).getMessage().equals("Hey guys, don't forget about our soccer game this weekend!")); 
//		assertTrue(chatsList.get(notificationIndex - 1).getMessage().equals("Check me out on twitch @JibraanKhan91412")); 
//		assertTrue(chatsList.get(notificationIndex).getSenderName().equals("[NOTIFICATION BOT]"));
//		assertTrue(chatsList.get(notificationIndex - 1).getSenderName().equals("[NOTIFICATION BOT]"));
		wait(0.5);
		selectListItem("#roomsList", 0, robot);
		selectListItem("#chatLogList", 0, robot);
		wait(0.5);
		robot.clickOn("#menuButton");
		wait(0.5);
		robot.clickOn("#inviteUserButton");
		wait(0.5);
		selectListItem("#inviteUsersListView", 0, robot);
		wait(0.5);
		robot.clickOn("#inviteUserButton");
		wait(0.5);
		ListView<ChatLog> chatLogs = getChatLogs(robot);
		ObservableList<ChatLog> chatLogsList = chatLogs.getItems();
		
		System.out.println("About to check size\n" + chatLogsList);
		wait(0.5);
		assertTrue(chatLogsList.size() == 1); //Should have only one chatLog
		wait(0.5);
		robot.clickOn("#menuButton");
		wait(0.5);
		robot.clickOn("#deleteChatLogButton");
		chatLogs = getChatLogs(robot); //Get chatlogs again after deleting
		chatLogsList = chatLogs.getItems();
		
		assertTrue(chatLogsList.size() == 0); //Should not have anymore chatlogs
		
		ListView<Room> rooms = getRooms(robot);
		ObservableList<Room> roomsList = rooms.getItems();
		assertTrue(roomsList.size() == 2);
		wait(0.5);
		robot.clickOn("#menuButton");
		wait(0.5);
		robot.clickOn("#deleteRoomButton");
		wait(0.5);
		rooms = getRooms(robot);
		roomsList = rooms.getItems();
		assertTrue(roomsList.size() == 1);
		//assertTrue()
	}
	
	private void selectListItem(String listID, int index, FxRobot robot) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				@SuppressWarnings("unchecked")
				ListView<Room> listview = (ListView<Room>) robot.lookup(listID).query();
				listview.getSelectionModel().clearSelection();
				listview.scrollTo(index);
				listview.getSelectionModel().select(index);
			} 
		});
	}
	
	private void enterValue(String fieldID, String value, FxRobot robot) {
		robot.clickOn(fieldID);
		clearTextField(fieldID, robot);
		robot.write(value);
	}
	
	private void clearTextField(String fieldID, FxRobot robot) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				TextField textfield = (TextField) robot.lookup(fieldID).query();
				textfield.clear();
			}
		});
	}
	
	private void checkRoomName(String roomName, FxRobot robot) {
		Assertions.assertThat(robot.lookup("#menuButton")
				.queryAs(MenuButton.class)).hasText(roomName);
	}
	
	private void checkLabelText(String labelIdentifier, String text, FxRobot robot) {
		Assertions.assertThat(robot.lookup(labelIdentifier)
				.queryAs(Label.class)).hasText(text);
	}
	
	private void wait(double secs) {
		try
		{
			Thread.sleep((int) Math.round(secs * 1000));
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	ListView<Room> getRooms(FxRobot robot)
	{
	   return (ListView<Room>) robot.lookup("#roomsList")
	       .queryAll().iterator().next();
	}
	
	@SuppressWarnings("unchecked")
	ListView<ChatLog> getChatLogs(FxRobot robot)
	{
	   return (ListView<ChatLog>) robot.lookup("#chatLogList")
	       .queryAll().iterator().next();
	}
	
	@SuppressWarnings("unchecked")
	ListView<Chat> getChats(FxRobot robot)
	{
	   return (ListView<Chat>) robot.lookup("#chatsListView")
	       .queryAll().iterator().next();
	}
	
	@SuppressWarnings("unchecked")
	ListView<User> getUsersInRoom(FxRobot robot)
	{
	   return (ListView<User>) robot.lookup("#usersInRoomList")
	       .queryAll().iterator().next();
	}
	
	@SuppressWarnings("unchecked")
	ListView<User> getUsers(FxRobot robot)
	{
	   return (ListView<User>) robot.lookup("#usersListForDM")
	       .queryAll().iterator().next();
	}
	
	@SuppressWarnings("unchecked")
	ListView<Room> getDMs(FxRobot robot)
	{
	   return (ListView<Room>) robot.lookup("#DMList")
	       .queryAll().iterator().next();
	}
	private void closeStageFromNode(Node node) {
		if (getStageFromNode(node) != null) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					getStageFromNode(node).close();
				}
			});
		}
	}
	
	private Stage getStageFromNode(Node node) { 
		return (Stage) node.getScene().getWindow(); 
	}
}
