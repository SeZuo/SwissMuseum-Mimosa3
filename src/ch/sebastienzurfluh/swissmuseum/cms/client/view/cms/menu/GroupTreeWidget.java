package ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.menu;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.swissmuseum.cms.client.model.CMSModel;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.MenuData;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observable;
import ch.sebastienzurfluh.swissmuseum.core.client.view.tilemenu.Tile;

public class GroupTreeWidget extends TreeWidget {

	public GroupTreeWidget(
			DataType type,
			EventBus eventBus,
			CMSModel model) {
		super(type, eventBus, model);
		
		model.allGroupsMenusChangesObservable.subscribeObserver(this);
		model.currentGroupMenuObservable.subscribeObserver(this);
	}
	
	/**
	 * Points to the currently focused tile.
	 */
	private Tile focusedTile = new Tile(MenuData.NONE);
	
	@Override
	public void notifyObserver(Observable source) {
		super.notifyObserver(source);
		
		if (source.equals(model.allGroupsMenusChangesObservable)) {
			// reload all the group pages
			entries.clear();
			if(model.getAllGroupMenus() == null) {
				this.setVisible(false);
			} else {
				this.setVisible(true);
				for(MenuData menuData : model.getAllGroupMenus()) {
					createNewTile(menuData);
				}
			}
		} else if (source.equals(model.currentGroupMenuObservable)) {
			// set the current focus on the right object
			if(model.getCurrentGroupMenu() == null) {
				this.setVisible(false);
			} else {
				this.setVisible(true);
				updateFocusedTile(model.getCurrentGroupMenu().getReference());
			}
		}
	}
}
