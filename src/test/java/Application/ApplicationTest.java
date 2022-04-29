package Application;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ConcordClientModel;
import model.ViewTransitionalModel;

import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import ServerClientModel.Server;
import ServerClientModel.ServerInterface;

@ExtendWith(ApplicationExtension.class)
public class ApplicationTest
{
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
		ConcordClientModel client = new ConcordClientModel();
		//client.initializeDemo();
		ViewTransitionalModel vtm = new ViewTransitionalModel(client);
		vtm.showLogin();
	}
	
	@Test
	public void testLogin(FxRobot robot) {
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
        
        
        enterValue("#usernameTextField", "Jibraan", robot);
        enterValue("#passwordTextField", "password123", robot);
        robot.clickOn("#loginButton");
        Assertions.assertThat(robot.lookup("#errorMsg") == null);
        Assertions.assertThat(robot.lookup("#mainFrame") != null);
        Assertions.assertThat(robot.lookup("#addButton") != null);
        Assertions.assertThat(robot.lookup("#exploreButton") != null);
        Assertions.assertThat(robot.lookup("#DMButton") != null);    
	}
	
	private void selectListItem(String listID, int index, FxRobot robot) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ListView listview = (ListView) robot.lookup(listID).query();
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
}
