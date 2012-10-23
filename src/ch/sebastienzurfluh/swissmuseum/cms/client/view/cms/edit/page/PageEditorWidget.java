package ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.edit.page;

import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlowPanel;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.cms.client.model.CMSModel;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observable;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observer;
import ch.sebastienzurfluh.swissmuseum.core.client.view.pagewidget.PageWidget;

public class PageEditorWidget extends FlowPanel implements Observer {
	private CMSModel cmsModel;
	
	private DecoratedTabPanel tabPanel;

	private PageWidget browse;

	private PageEditor edit;

	private EventBus eventBus;
	
	public PageEditorWidget(CMSModel cmsModel, EventBus eventBus) {
		this.eventBus = eventBus;
		this.cmsModel = cmsModel;
		
		setStyleName("cms-editorWidget");
		
		HTML titleLabel = new HTML("<span class='cms-tileMenu-title'>Page:</span>");
		tabPanel = new DecoratedTabPanel();
		tabPanel.setStyleName("cms-tabPanel");
		
		add(titleLabel);
		add(tabPanel);
		
		cmsModel.currentPageDataObservable.subscribeObserver(this);
	}

	public void setCreateState() {
		tabPanel.clear();
		tabPanel.add(new PageWidget(eventBus, cmsModel.getModel()), "Voir");
		tabPanel.add(new PageEditor(cmsModel, eventBus), "Editer");
		
		tabPanel.selectTab(1);
	}

	public void setModifyState() {
		switch(cmsModel.getCurrentIntentReference().getType()) {
		case PAGE:
			tabPanel.clear();
			// The previous page widget will not be destroyed and will continue to ask for data?
			cmsModel.currentPageDataObservable.unsubscribeObserver(browse);	// This line prevents an epic bug!
			browse = new PageWidget(eventBus, cmsModel.getModel());
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
		case PAGE:
			
			tabPanel.add(browse, "Voir");
			
			edit = new PageEditor(cmsModel.getCurrentPageData(), cmsModel, eventBus);
			tabPanel.add(edit, "Editer");
			
			tabPanel.selectTab(0);
			break;
		default:
			break;
		}
	}

}
