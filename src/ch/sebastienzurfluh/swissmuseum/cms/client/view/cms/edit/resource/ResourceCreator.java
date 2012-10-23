package ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.edit.resource;

import java.util.Iterator;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.cms.client.model.CMSModel;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.DataReference;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.ResourceData;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observable;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observer;
import ch.sebastienzurfluh.swissmuseum.core.client.view.tilemenu.Tile;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.FlowPanel;

public class ResourceCreator extends PopupPanel implements Observer {
	private EventBus eventBus;
	private CMSModel cmsModel;
	private SimplePanel mainPanel;
	private FlowPanel titleLine;
	private TileDetailsButton detailsButton;
	private DataReference selectedTile;
	private SimplePanel currentSelectionPanel;
	
	/*
	 * Set to true while waiting for the model to acknowledge a new resource.
	 */
	private boolean waitForTheResourceToBeCreated = false;
	
	/**
	 * TextArea where the new image is to be added.
	 */
	private TextArea textArea;

	public ResourceCreator(CMSModel cmsModel, EventBus eventBus, final TextArea insertImageInThisTextArea) {
		super(false, true);
		this.cmsModel = cmsModel;
		this.eventBus = eventBus;
		this.textArea = insertImageInThisTextArea;
		
		FlowPanel resourcePickerPanel = new FlowPanel();
		
		Label title = new Label("Créer une image.");
		title.setStyleName("resourcePicker-title");
		
		detailsButton = new TileDetailsButton();
		
		titleLine = new FlowPanel();
		titleLine.add(title);
		titleLine.add(detailsButton);
		
		
		//TODO create the panel with data from cmsModel.getAllResources();
		Button cancel = new Button("Annuler", new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				ResourceCreator.this.hide();
			}
		});
		cancel.setStyleName("resourcePicker-button");
		
		Button ok = new Button("Choisir", new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				if(selectedTile != null)
					waitForTheResourceToBeCreated = true;
				
				
			}
		});
		ok.setStyleName("resourcePicker-button");
		
		
		currentSelectionPanel = new SimplePanel();
		
		
		FlowPanel buttonLine = new FlowPanel();
		buttonLine.add(ok);
		buttonLine.add(cancel);
		buttonLine.add(currentSelectionPanel);
		
		
		mainPanel = new SimplePanel();
		ResourceEditor resourceEditor = new ResourceEditor();
		resourceEditor.setStyleName("cms-resourceCreator-resourceEditor");
		mainPanel.setWidget(resourceEditor);
		mainPanel.setStyleName("resourcePicker-body");

		resourcePickerPanel.add(titleLine);
		resourcePickerPanel.add(mainPanel);
		resourcePickerPanel.add(buttonLine);
		
		
		setWidget(resourcePickerPanel);
		resourcePickerPanel.setWidth("100%");
		
		setGlassEnabled(true);
				
		cmsModel.allNeededResourcesObservable.subscribeObserver(this);
	}

	@Override
	public void notifyObserver(Observable source) {
		// si on attendait une confirmation, alors
		if(!waitForTheResourceToBeCreated)
			return;
		
		Iterator<ResourceData> iterator = cmsModel.getAllNeededResources().iterator();
		ResourceData lastResource = null;
		while (iterator.hasNext())
			lastResource = iterator.next();
		
		textArea.setText(textArea.getText().substring(0, textArea.getCursorPos()) +
				"[img]" + lastResource.getReference().getReferenceId() + "[/img]"
				+ textArea.getText().substring(textArea.getCursorPos()));
		
		hide();
	}

	/**
	 * @param tile currently selected by the user
	 */
	private void setSelectedTile(Tile tile) {
		System.out.println("ResourcePicker: setSelectedTile " + tile.toString());
		selectedTile = tile.getReference();
		currentSelectionPanel.setWidget(tile);
	}
}
