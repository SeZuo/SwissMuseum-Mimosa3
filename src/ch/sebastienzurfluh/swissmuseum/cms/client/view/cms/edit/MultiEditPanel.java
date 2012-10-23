package ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.edit;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.cms.client.model.CMSModel;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observable;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observer;
import ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.edit.buttonToolbar.ButtonToolbar;
import ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.edit.menu.MenuEditorWidget;
import ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.edit.page.PageEditorWidget;
import ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.edit.resource.ResourceEditorWidget;

import com.google.gwt.user.client.ui.FlowPanel;


public class MultiEditPanel extends FlowPanel implements Observer {
	private MenuEditorWidget menuEdit;
	private PageEditorWidget pageEdit;
	private ResourceEditorWidget resourceEdit;
	
	private CMSModel cmsModel;
	private EventBus eventBus;
	
	 
	public MultiEditPanel(CMSModel cmsModel, EventBus eventBus) {
		this.cmsModel = cmsModel;
		this.eventBus = eventBus;
		
		setStyleName("multiEditPanel");
		
		menuEdit = new MenuEditorWidget(cmsModel);
		pageEdit = new PageEditorWidget(cmsModel, eventBus);
		resourceEdit = new ResourceEditorWidget(cmsModel);
		
		menuEdit.setSize("100%", "");
		
		add(menuEdit);
		add(pageEdit);
		add(resourceEdit);
		
		menuEdit.setVisible(false);
		pageEdit.setVisible(false);
		resourceEdit.setVisible(false);
		
		ButtonToolbar buttonToolbar = new ButtonToolbar();
		add(buttonToolbar);
		
		
		cmsModel.currentIntentObservable.subscribeObserver(this);
	}


	@Override
	public void notifyObserver(Observable source) {
		if(!source.equals(cmsModel.currentIntentObservable))
			return;
		
		System.out.println("MultiEditPanel: notified of intent " +
				cmsModel.getCurrentIntentAction() + " on " + cmsModel.getCurrentIntentReference());
		
		switch(cmsModel.getCurrentIntentReference().getType()) {
		case GROUP:
			menuEdit.setVisible(true);
			pageEdit.setVisible(false);
			resourceEdit.setVisible(false);
			break;
		case PAGE:
			menuEdit.setVisible(true);
			pageEdit.setVisible(true);
			resourceEdit.setVisible(true);
			break;
		default:
			return;
		}
		
		switch(cmsModel.getCurrentIntentAction()) {
		case CREATE:
			menuEdit.setCreateState();
			pageEdit.setCreateState();
			resourceEdit.setCreateState();
			break;
		case MODIFY:
			menuEdit.setModifyState();
			pageEdit.setModifyState();
			resourceEdit.setModifyState();
			break;
		case REMOVE:
			menuEdit.setRemoveState();
			pageEdit.setRemoveState();
			resourceEdit.setRemoveState();
			break;
		default:
			return;
		}
	}
}
