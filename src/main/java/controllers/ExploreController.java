package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.ConcordClientModel;
import model.ViewTransitionalModel;

public class ExploreController extends BaseController
{

	@FXML
    private ListView<String> roomList;
    
	public ExploreController(String fxmlName, ViewTransitionalModel vtm, ConcordClientModel client)
	{
		super(fxmlName, vtm, client);
		// TODO Auto-generated constructor stub
	}

	@FXML
    void onAddButtonClicked(ActionEvent event) {
		vtm.closeStageFromNode(roomList);
		vtm.showMainFrame();
    }

    @FXML
    void onCancelButtonClicked(ActionEvent event) {
    	vtm.closeStageFromNode(roomList);
		vtm.showMainFrame();
    }
}
