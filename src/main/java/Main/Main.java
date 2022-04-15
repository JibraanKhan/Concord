package Main;
import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.ConcordClientModel;
import model.ViewTransitionalModel;

public class Main extends Application
{

	/*
	 * 
	 * stage.setScene(scene);
		stage.show();
		
		MainController controller = loader.getController();
		MainModel model = new MainModel();
		controller.setModel(model);
	 */
	@Override
	public void start(Stage stage) throws Exception
	{
		/*
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../views/login-view.fxml"));
		loader.setController(new LoginController());
		
		BorderPane loginView = loader.load();
		
		
		Scene scene = new Scene(loginView);
		stage.setScene(scene);
		stage.show();
		
		LoginController loginController = loader.getController();
		ConcordClientModel model = new ConcordClientModel();
		ViewTransitionalModel vtm = new ViewTransitionalModel(loginView, model);
		loginController.setModel(vtm);
		*/
		ConcordClientModel client = new ConcordClientModel();
		ViewTransitionalModel vtm = new ViewTransitionalModel(client);
		vtm.showLogin();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
