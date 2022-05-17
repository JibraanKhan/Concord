package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Database.User;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.ConcordClientModel;
import model.ViewTransitionalModel;

public class ProfilePopupController extends BaseController implements Initializable
{
	@FXML
    private Label name;
	private StringProperty profileName = new SimpleStringProperty();
	private StringProperty profileData = new SimpleStringProperty();

    @FXML
    private Label profile;
    
	public ProfilePopupController(String fxmlName, ViewTransitionalModel vtm, ConcordClientModel client, User user)
	{
		super(fxmlName, vtm, client);
		this.profileName.set(user.getUserName());
		this.profileData.set(user.getProfileData());
		// TODO Auto-generated constructor stub
	}
	
	public StringProperty getProfileName() {
		return profileName;
	}

	public StringProperty getProfileData() {
		return profileData;
	}
	
	public void setProfileName(String profileName) {
		this.profileName.set(profileName);
	}
	
	public void setProfileData(String profileData) {
		this.profileData.set(profileData);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		name.setText(profileName.get());
		profile.setText(profileData.get());
		Bindings.bindBidirectional(name.textProperty(), profileName);
		Bindings.bindBidirectional(profile.textProperty(), profileData);
	}

	@Override
	public void clearSelections()
	{
		// TODO Auto-generated method stub
		
	}
}
