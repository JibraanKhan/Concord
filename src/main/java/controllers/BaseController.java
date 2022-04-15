package controllers;

import model.ConcordClientModel;
import model.ViewTransitionalModel;

public abstract class BaseController
{
	String fxmlName;
	ViewTransitionalModel vtm;
	ConcordClientModel client;
	
	public BaseController(String fxmlName, ViewTransitionalModel vtm, ConcordClientModel client) {
		this.fxmlName = fxmlName;
		this.vtm = vtm;
		this.client = client;
	}
	
	public String getFxmlName() {
		return fxmlName;
	}
}
