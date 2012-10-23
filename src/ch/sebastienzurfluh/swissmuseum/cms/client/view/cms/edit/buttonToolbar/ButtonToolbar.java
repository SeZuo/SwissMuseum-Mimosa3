package ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.edit.buttonToolbar;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

public class ButtonToolbar extends HorizontalPanel {
	public ButtonToolbar() {
		setStyleName("cms-toolbar");
		
		Image revertButton = new Image("resources/images/buttons/revert.png");
		revertButton.setStyleName("cms-toolbar-button");
		add(revertButton);
		
		Image saveButton = new Image("resources/images/buttons/save.png");
		saveButton.setStyleName("cms-toolbar-button");
		add(saveButton);
		
		//TODO
	}
}
