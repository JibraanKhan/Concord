package model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import Database.User;
import Main.Main;
import ServerClientModel.ServerInterface;
import controllers.BaseController;
import controllers.ChatListController;
import controllers.CreateChannelPopupController;
import controllers.DMController;
import controllers.EditProfilePopupController;
import controllers.ExploreController;
import controllers.LoginController;
import controllers.MainFrameController;
import controllers.ProfilePopupController;
import controllers.RegisterPopupController;
import controllers.RoomNamePopupController;
import controllers.RoomViewController;
import controllers.UserListController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewTransitionalModel implements ViewTransitionalModelInterface
{

	BorderPane mainFrame;
	ConcordClientModel client;
	BorderPane dmsFrame;
	ArrayList<Stage> stages = new ArrayList<Stage>();
	
	public void addStage(Stage stage) {
		stages.add(stage);
	}
	public ViewTransitionalModel(ConcordClientModel client) {
		this.client = client;
	}
	
	public void showLogin() {
		if (mainFrame != null) {
			closeStageFromNode(mainFrame);
			mainFrame = null;
			//If we logout, we need to close the mainframe and just work with the login page then.
		}
		BaseController loginController = new LoginController("login-view.fxml", this, client);
		showStage(loginController, true);
	}
	
	public void showMainFrame() {
		if (mainFrame != null) { 
			return; 
		}
		BaseController mainFrameController = new MainFrameController("main-frame.fxml", this, client);
		mainFrame = (BorderPane) showStage(mainFrameController, true);
	}
	
	public void showRoomView() {
		showMainFrame();
		
		BaseController roomViewController = new RoomViewController("room-view.fxml", this, client);
		Node roomView = loadView(roomViewController);
		mainFrame.setCenter(roomView);
	}
	
	public void showDMs() {
		if (dmsFrame == null) {
			BaseController DMsController = new DMController("dm-view.fxml", this, client);
			dmsFrame = (BorderPane) loadView(DMsController);
		}
		showMainFrame();
		
		mainFrame.setCenter(dmsFrame);
	}
	
	public void showChatList() {
		showDMs();
		BaseController chatListController = new ChatListController("chat-list-view.fxml", this, client);
		Node chatListNode = loadView(chatListController);
		dmsFrame.setCenter(chatListNode);
	}
	
	public void showUsersList() {
		showDMs();
		BaseController usersListController = new UserListController("users-list-view.fxml", this, client);
		Node usersListNode = loadView(usersListController);
		dmsFrame.setCenter(usersListNode);
	}
	
	public void showRoomNamePopup() {
		BaseController roomNamePopupController = new RoomNamePopupController("room-name-popup.fxml", this, client);
		showStage(roomNamePopupController, false);
	}
	
	public void showUserProfilePopup(User user) {
		BaseController profilePopupController = new ProfilePopupController("profile-popup.fxml", this, client, user);
		showStage(profilePopupController, false);
	}
	
	public void showRegisterUserPopup() {
		BaseController registerPopupController = new RegisterPopupController("register-popup.fxml", this, client);
		showStage(registerPopupController, false);
	}
	
	public void showEditProfilePopup() {
		BaseController editProfilePopupController = new EditProfilePopupController("editProfilePopup.fxml", this, client);
		showStage(editProfilePopupController, false);
	}
	
	public void showCreateChannelPopup() {
		BaseController createChannelPopupController = new CreateChannelPopupController("createChannelPopupView.fxml", this, client);
		showStage(createChannelPopupController, false);
	}
	
	public void showExplore() {
		BaseController explorePopupController = new ExploreController("explore-view.fxml", this, client);
		showStage(explorePopupController, false);
	}
	
	@Override
	public Parent showStage(BaseController controller, boolean bind_close)
	{
		Parent parent = loadView(controller);
		
		Stage stage = new Stage();
		stages.add(stage);
		stage.setScene(new Scene(parent, 800, 500));
		stage.show();
		if (bind_close) {
			stage.setOnCloseRequest(event -> closeApplication()); //To shut down entire app.
		}else {
			stage.setOnCloseRequest(event -> closeWindow(stage)); //When shutting down small windows
		}
		return parent;
	}
	
	private void closeApplication() {
		
		try
		{
			ServerInterface server = (ServerInterface) Naming.lookup("rmi://127.0.0.1/CONCORD");
			server.storeDataDisk();
			for (Stage stage: stages) {
				System.out.println(stage);
				stage.close();
			} // First close all the stages
			Platform.exit();
			System.exit(0);
			System.out.println("Closing application");
		} catch (RemoteException | MalformedURLException | NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void removeStage(Stage window) {
		stages.remove(window);
	}
	
	private void closeWindow(Stage window) {
		window.close();
		removeStage(window);
		System.out.println("Closing window");
	}
	
	@Override
	public Parent loadView(BaseController controller)
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../views/" + controller.getFxmlName()));
		loader.setController(controller);
		Parent parent;
		try
		{
			parent = loader.load();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return parent;
	}
	
	public void closeStageFromNode(Node node) {
		if (getStageFromNode(node) != null) {
			getStageFromNode(node).close();
		}
	}
	
	private Stage getStageFromNode(Node node) { 
		return (Stage) node.getScene().getWindow(); 
	}
	
}
