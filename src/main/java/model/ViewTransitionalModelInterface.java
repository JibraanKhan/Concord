package model;

import controllers.BaseController;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

public interface ViewTransitionalModelInterface
{
	public Parent loadView(BaseController controller);
	
	public Parent showStage(BaseController controller, boolean bind_close);
	
	public void closeStageFromNode(Node node);
}
