package model;

import java.io.IOException;

import Main.Main;
import controllers.BaseController;
import controllers.ChatListController;
import controllers.DMController;
import controllers.ExploreController;
import controllers.LoginController;
import controllers.MainFrameController;
import controllers.ProfilePopupController;
import controllers.RoomNamePopupController;
import controllers.RoomViewController;
import controllers.UserListController;
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
	
	public ViewTransitionalModel(ConcordClientModel client) {
		this.client = client;
	}
	
	public void showLogin() {
		BaseController loginController = new LoginController("login-view.fxml", this, client);
		showStage(loginController);
	}
	
	public void showMainFrame() {
		if (mainFrame != null) { 
			return; 
		}
		BaseController mainFrameController = new MainFrameController("main-frame.fxml", this, client);
		mainFrame = (BorderPane) showStage(mainFrameController);
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
		showStage(roomNamePopupController);
	}
	
	public void showUserProfilePopup(String profileName) {
		BaseController profilePopupController = new ProfilePopupController("profile-popup.fxml", this, client, profileName);
		showStage(profilePopupController);
	}
	
	public void showExplore() {
		BaseController explorePopupController = new ExploreController("explore-view.fxml", this, client);
		showStage(explorePopupController);
	}
	
	@Override
	public Parent showStage(BaseController controller)
	{
		Parent parent = loadView(controller);
		
		Stage stage = new Stage();
		stage.setScene(new Scene(parent));
		stage.show();
		
		return parent;
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
