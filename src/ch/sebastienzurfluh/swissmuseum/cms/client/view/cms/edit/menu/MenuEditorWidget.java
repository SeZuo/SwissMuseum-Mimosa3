package ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.edit.menu;

import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlowPanel;

import ch.sebastienzurfluh.swissmuseum.cms.client.model.CMSModel;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observable;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observer;
import ch.sebastienzurfluh.swissmuseum.core.client.view.tilemenu.Tile;
import ch.sebastienzurfluh.swissmuseum.core.client.view.tilemenu.Tile.TileMode;

public class MenuEditorWidget extends FlowPanel implements Observer {
	private CMSModel cmsModel;
	
	private DecoratedTabPanel tabPanel;

	private Tile browse;

	private MenuEditor edit;
	
	public MenuEditorWidget(CMSModel cmsModel) {
		this.cmsModel = cmsModel;
		
		setStyleName("cms-editorWidget");
		
		HTML titleLabel = new HTML("<span class='cms-tileMenu-title'>Menu:</span>");
		tabPanel = new DecoratedTabPanel();
		tabPanel.setStyleName("cms-tabPanel");
		
		add(titleLabel);
		add(tabPanel);
		
		cmsModel.currentGroupMenuObservable.subscribeObserver(this);
		cmsModel.currentPageDataObservable.subscribeObserver(this);
	}

	public void setCreateState() {
		tabPanel.clear();
		tabPanel.add(new Tile(cmsModel.getCurrentGroupMenu(), TileMode.DETAILED), "Voir");
		tabPanel.add(new MenuEditor(), "Editer");
		
		tabPanel.selectTab(1);
	}

	public void setModifyState() {
		switch(cmsModel.getCurrentIntentReference().getType()) {
		case GROUP:
		case PAGE:
			tabPanel.clear();
			break;
		default:
			break;
		}
	}

	public void setRemoveState() {
		tabPanel.clear();
		tabPanel.add(new Label("Panneau de confirmation."));
		//TODO create this state
	}
	
	@Override
	public void notifyObserver(Observable source) {
		switch(cmsModel.getCurrentIntentReference().getType()) {
		case GROUP:
			// check if the right data was made available
			if(!source.equals(cmsModel.currentGroupMenuObservable))
				return;
			browse = new Tile(cmsModel.getCurrentGroupMenu(), TileMode.DETAILED);
			tabPanel.add(browse, "Voir");
			
			edit = new MenuEditor(cmsModel.getCurrentGroupMenu());
			tabPanel.add(edit, "Editer");
			
			tabPanel.selectTab(0);
			break;
		case PAGE:
			// check if the right data was made available
			if(!source.equals(cmsModel.currentPageDataObservable))
				return;
			browse = new Tile(cmsModel.getCurrentPageData().getMenu(), TileMode.DETAILED);
			tabPanel.add(browse, "Voir");
			
			edit = new MenuEditor(cmsModel.getCurrentPageData().getMenu());
			tabPanel.add(edit, "Editer");
			
			tabPanel.selectTab(0);
			break;
		default:
			break;
		}
	}

}
