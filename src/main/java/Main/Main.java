package Main;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.ConnectException;
import ServerClientModel.Server;
import ServerClientModel.ServerInterface;
import controllers.LoginController;
import javafx.application.Application;
import javafx.application.Platform;
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
	ServerInterface server;
	Stage window;
	
	@Override
	public void start(Stage stage) throws Exception
	{
		window = stage;
		try {
			server = (ServerInterface) Naming.lookup("rmi://127.0.0.1/CONCORD");
			window.setOnCloseRequest(event -> closeProgram());
		} catch (ConnectException e) {
			Registry registry = LocateRegistry.createRegistry(1099);
			server = new Server();
			ServerInterface storedServer = server.readDataFromDisk();
			if (storedServer != null) {
				server = storedServer;
			}
			window.setOnCloseRequest(event -> closeProgram());
			registry.rebind("CONCORD", server);
		}
		ConcordClientModel client = new ConcordClientModel();
		ViewTransitionalModel vtm = new ViewTransitionalModel(client);
		vtm.addStage(stage);
		vtm.showLogin();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void closeProgram() {
		try
		{
			server.storeDataDisk();
			window.close();
			Platform.exit();
			System.exit(0);
			System.out.println("Closing window");
			
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
