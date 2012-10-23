package ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.edit.resource;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;

public class WaitingPopup extends PopupPanel {
	private Image waitingImage = new Image("resources/images/icons/loading.gif");

	private WaitingPopup instance = null;
	
	private WaitingPopup() {
		add(waitingImage);
	}
	
	public WaitingPopup getWaitingPopup() {
		if(instance == null)
			instance = new WaitingPopup();
		return instance;
	}
}
