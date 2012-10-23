package ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.edit.resource;

import java.util.Collection;
import java.util.LinkedList;

import ch.sebastienzurfluh.swissmuseum.core.client.view.tilemenu.Tile;
import ch.sebastienzurfluh.swissmuseum.core.client.view.tilemenu.Tile.TileMode;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;

public class TileDetailsButton extends FocusPanel implements ClickHandler {
	public enum State {
		DETAILED, ICON_ONLY;
	}
	private State currentState;
	
	private Image toIconOnlyButton = new Image("resources/images/buttons/view-list-icons.png");
	private Image toDetailedButton = new Image("resources/images/buttons/view-list-compact.png");

	private Collection<Tile> tileList = new LinkedList<Tile>();

	public TileDetailsButton() {
		
		
		toIconOnlyButton.setStyleName("resourcePicker-button");
		toIconOnlyButton.getElement().setAttribute("style", "background: #FCFCFC");
		toDetailedButton.setStyleName("resourcePicker-button");
		toDetailedButton.getElement().setAttribute("style", "background: #FCFCFC");
		
		
		toDetailedButton.setAltText("Afficher les détails.");
		toIconOnlyButton.setAltText("Masquer les détails.");
		
		setState(State.ICON_ONLY);
		
		addClickHandler(this);
	}
	
	@Override
	public void onClick(ClickEvent event) {
		switch(currentState) {
		case DETAILED:
			setState(State.ICON_ONLY);
			for (Tile tile : tileList) {
				tile.setMode(TileMode.ICON_ONLY);
			}
			break;
		case ICON_ONLY:
			setState(State.DETAILED);
			for (Tile tile : tileList) {
				tile.setMode(TileMode.DETAILED);
			}
			break;
		}
	}
	
	/**
	 * Update the list of tiles
	 * @param tileList of the tiles this button will have access to.
	 */
	public void updateTileList(Collection<Tile> tileList) {
		this.tileList = tileList;
	}

	public void setState(State state) {
		switch(state) {
		case DETAILED:
			setWidget(toIconOnlyButton);
			currentState = State.DETAILED;
			break;
		case ICON_ONLY:
			setWidget(toDetailedButton);
			currentState = State.ICON_ONLY;
			break;
		}
		currentState = state;
	}
}