package ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.edit.resource;

import java.util.LinkedList;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.cms.client.control.eventbus.events.Action;
import ch.sebastienzurfluh.swissmuseum.cms.client.control.eventbus.events.IntentEvent;
import ch.sebastienzurfluh.swissmuseum.cms.client.model.CMSModel;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.DataReference;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.MenuData;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.ResourceData;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observable;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observer;
import ch.sebastienzurfluh.swissmuseum.core.client.view.tilemenu.Tile;
import ch.sebastienzurfluh.swissmuseum.core.client.view.tilemenu.Tile.TileMode;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.FlowPanel;

public class ResourcePicker extends PopupPanel implements Observer {
	private EventBus eventBus;
	private CMSModel cmsModel;
	private SimplePanel mainPanel;
	private FlowPanel titleLine;
	private TileDetailsButton detailsButton;
	private DataReference selectedTile;
	private SimplePanel currentSelectionPanel;

	public ResourcePicker(CMSModel cmsModel, EventBus eventBus, final TextArea insertImageInThisTextArea) {
		super(false, true);
		this.cmsModel = cmsModel;
		this.eventBus = eventBus;
		
		FlowPanel resourcePickerPanel = new FlowPanel();
		
		Label title = new Label("Choisissez une image à insérer.");
		title.setStyleName("resourcePicker-title");
		
		detailsButton = new TileDetailsButton();
		
		titleLine = new FlowPanel();
		titleLine.add(title);
		titleLine.add(detailsButton);
		
		
		//TODO create the panel with data from cmsModel.getAllResources();
		Button cancel = new Button("Annuler", new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				ResourcePicker.this.hide();
			}
		});
		cancel.setStyleName("resourcePicker-button");
		
		Button ok = new Button("Choisir", new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				TextArea ta = insertImageInThisTextArea;
				if(selectedTile != null)
					ta.setText(ta.getText().substring(0, ta.getCursorPos()) +
							"[img]" + selectedTile.getReferenceId() + "[/img]"
							+ ta.getText().substring(ta.getCursorPos()));
				ResourcePicker.this.hide();
			}
		});
		ok.setStyleName("resourcePicker-button");
		
		
		currentSelectionPanel = new SimplePanel();
		
		
		FlowPanel buttonLine = new FlowPanel();
		buttonLine.add(ok);
		buttonLine.add(cancel);
		buttonLine.add(currentSelectionPanel);
		
		
		mainPanel = new SimplePanel();
		mainPanel.setWidget(new Label("Please wait"));
		mainPanel.setStyleName("resourcePicker-body");

		resourcePickerPanel.add(titleLine);
		resourcePickerPanel.add(mainPanel);
		resourcePickerPanel.add(buttonLine);
		
		
		setWidget(resourcePickerPanel);
		resourcePickerPanel.setWidth("100%");
		
		setGlassEnabled(true);
				
		cmsModel.allResourcesObservable.subscribeObserver(this);
	}
	
	@Override
	public void show() {
		super.show();
		
		eventBus.fireEvent(new IntentEvent(DataReference.ALL_RESOURCES, Action.MODIFY));
	}

	@Override
	public void notifyObserver(Observable source) {
		FlowPanel flowPanel = new FlowPanel();
		LinkedList<Tile> listOfTiles = new LinkedList<Tile>();
		for (ResourceData resourceData : cmsModel.getAllResources()) {
			Tile tile = createTileFromResource(resourceData);
			flowPanel.add(tile);
			listOfTiles.add(tile);
		}
		// Give space priority to the mainPanel
		detailsButton.updateTileList(listOfTiles);
		titleLine.add(detailsButton);
		mainPanel.setWidget(flowPanel);
	}
	
	/**
	 * @param resourceData of the resource
	 * @return a new tile formed with the given data.
	 */
	private Tile createTileFromResource(ResourceData resourceData) {
		MenuData menuFromResource = new MenuData(
				resourceData.getReference(),
				0,
				resourceData.getTitle(),
				resourceData.getDetails(),
				resourceData.getURL(),
				resourceData.getURL());
		Tile tile = new Tile(menuFromResource, TileMode.ICON_ONLY);
		tile.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ResourcePicker.this.setSelectedTile((Tile) event.getSource());
			}
		});
		return tile;
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
