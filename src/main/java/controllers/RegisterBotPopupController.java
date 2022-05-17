package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Database.Bot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.ConcordClientModel;
import model.ViewTransitionalModel;

public class RegisterBotPopupController extends BaseController implements Initializable
{
	String selectedBot;
	public RegisterBotPopupController(String fxmlName, ViewTransitionalModel vtm, ConcordClientModel client)
	{
		super(fxmlName, vtm, client);
		// TODO Auto-generated constructor stub
	}
	
	@FXML
    private ListView<String> botsListView;

    @FXML
    void onCancelButtonClicked(ActionEvent event) {
    	vtm.closeStageFromNode(botsListView);
    }

    @FXML
    void onRegisterButtonClicked(ActionEvent event) {
    	if (selectedBot == null) {
    		vtm.closeStageFromNode(botsListView);
    		return;
    	}
    	
    	client.registerBot(selectedBot);
    	vtm.closeStageFromNode(botsListView);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		// TODO Auto-generated method stub
		client.loadAllBots();
		botsListView.setItems(client.getAllBots());
		
		botsListView.getSelectionModel().selectedItemProperty().addListener((observer, oldValue, newValue) -> {
			selectedBot = newValue;
		});
	}

	@Override
	public void clearSelections()
	{
		botsListView.getSelectionModel().clearSelection();
	}

}
