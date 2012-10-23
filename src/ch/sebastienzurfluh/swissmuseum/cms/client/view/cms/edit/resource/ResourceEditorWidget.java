package ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.edit.resource;

import java.util.Iterator;

import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.FlowPanel;

import ch.sebastienzurfluh.swissmuseum.cms.client.model.CMSModel;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.MenuData;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.ResourceData;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observable;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observer;
import ch.sebastienzurfluh.swissmuseum.core.client.view.tilemenu.Tile;
import ch.sebastienzurfluh.swissmuseum.core.client.view.tilemenu.Tile.TileMode;

public class ResourceEditorWidget extends FlowPanel implements Observer {
	private CMSModel cmsModel;
	
	private DecoratedTabPanel resourceList;

	private Tile view;

	private ResourceEditor edit;
	
	public ResourceEditorWidget(CMSModel cmsModel) {
		this.cmsModel = cmsModel;
		
		setStyleName("cms-editorWidget");
		
		HTML titleLabel = new HTML("<span class='cms-tileMenu-title'>Ressources:</span>");
		
		resourceList = new DecoratedTabPanel();
		resourceList.setStyleName("cms-tabPanel");
		
		add(titleLabel);
		add(resourceList);
		
		cmsModel.allNeededResourcesObservable.subscribeObserver(this);
	}
	
	/**
	 * @param resourceData of the resource
	 * @return a new tile formed with the given data.
	 */
	private static Tile createTileFromResource(ResourceData resourceData) {
		MenuData menuFromResource = new MenuData(
				resourceData.getReference(),
				0,
				resourceData.getTitle(),
				resourceData.getDetails(),
				resourceData.getURL(),
				resourceData.getURL());
		return new Tile(menuFromResource, TileMode.DETAILED);
	}

	public void setCreateState() {
		resourceList.clear();
		setVisible(false);
	}

	public void setModifyState() {
		resourceList.clear();
		//TODO
	}

	public void setRemoveState() {
		//TODO create this state
	}
	
	@Override
	public void notifyObserver(Observable source) {
		switch(cmsModel.getCurrentIntentReference().getType()) {
		case PAGE:
			// add only the new resource (the last of the collection)
			Iterator<ResourceData> iterator = cmsModel.getAllNeededResources().iterator();
			ResourceData lastResource = null;
			while (iterator.hasNext())
				lastResource = iterator.next();
			
			// we suppose cmsModel.getAllNeededResources() cannot be null as we've been notified
			// it's containing a new resource.
			createEditResourceTab(lastResource);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Adds an edit tab to the resource tabPanel associated with the given resource.
	 * @param resourceData : if NONE this doesn't do a thing.
	 */
	private void createEditResourceTab(ResourceData resourceData) {
		if(resourceData.equals(ResourceData.NONE))
			return;
		
		DecoratedTabPanel tabPanel = new DecoratedTabPanel();
		tabPanel.setStyleName("cms-tabPanel");
		
		view = createTileFromResource(resourceData);
		
		
		edit = new ResourceEditor(resourceData);
		
		
		tabPanel.add(view, "Voir");
		tabPanel.add(edit, "Editer");
		
		tabPanel.selectTab(0);
		
		String tabName = Integer.toString(resourceData.getReference().getReferenceId());
		if (tabName.equals("-1"))
			tabName = "Nouvelle ressource";
		
		resourceList.add(tabPanel, tabName);
	}
	
//	private void createNewResourceTab(ResourceData resourceData) {
//		DecoratedTabPanel tabPanel = new DecoratedTabPanel();
//		tabPanel.setStyleName("cms-tabPanel");
//		
//		browse = resourceData.equals(ResourceData.NONE) ?
//				new Tile(new MenuData(
//						DataReference.NONE,
//						0,
//						"Nothing to browse",
//						"no description",
//						"resources/images/pix/light_blue.gif",
//						"resources/images/pix/light_blue.gif")) :
//				createTileFromResource(resourceData);
//				
//		
//		edit = new ResourceEditor(resourceData);
//		
//		
//		tabPanel.add(browse, "Voir");
//		tabPanel.add(edit, "Editer");
//		
//		tabPanel.selectTab(0);
//		
//		String tabName = Integer.toString(resourceData.getReference().getReferenceId());
//		if (tabName.equals("-1"))
//			tabName = "Nouvelle ressource";
//		
//		resourceList.add(tabPanel, tabName);
//	}

}
