package ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.edit.page;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.cms.client.model.CMSModel;
import ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.edit.resource.ResourceCreator;
import ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.edit.resource.ResourcePicker;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.TextArea;

public class Controls extends MenuBar {
	private ResourcePicker resourcePicker;
	private ResourceCreator resourceCreator;
	
	public Controls(CMSModel cmsModel, EventBus eventBus, TextArea insertImageInThisTextArea) {
		setStyleName("cms-pageEditor-controls");
		resourcePicker = new ResourcePicker(cmsModel, eventBus, insertImageInThisTextArea);
		resourceCreator = new ResourceCreator(cmsModel, eventBus, insertImageInThisTextArea);
		createImageMenu();
	}
	
	private void createImageMenu() {
		MenuBar menu = new MenuBar(true);
	    menu.setAutoOpen(false);
	    menu.setAnimationEnabled(true);
	    
	    menu.addItem("Nouveau", new Command() {
			

			@Override
			public void execute() {
				resourceCreator.center();
			}
	    });
	    menu.addItem("Choisir", new Command() {
			@Override
			public void execute() {
				resourcePicker.center();
			}
	    });
	    
	    addItem("<img src='resources/images/buttons/insert-image.png' />", true, menu);
	}
}
