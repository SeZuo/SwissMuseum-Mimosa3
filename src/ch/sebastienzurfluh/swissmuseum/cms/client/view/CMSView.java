/*
 * Copyright 2012 Sebastien Zurfluh
 * 
 * This file is part of "Parcours".
 * 
 * "Parcours" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "Parcours" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with "Parcours".  If not, see <http://www.gnu.org/licenses/>.
 */

package ch.sebastienzurfluh.swissmuseum.cms.client.view;


import ch.sebastienzurfluh.swissmuseum.cms.client.model.CMSModel;
import ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.Header;
import ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.edit.MultiEditPanel;
import ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.menu.GroupTreeWidget;
import ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.menu.PageTreeWidget;
import ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.menu.TreeWidget;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.PageRequestEventHandler;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.ResourceRequestEventHandler;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.swissmuseum.core.client.view.AboutPanel;
import ch.sebastienzurfluh.swissmuseum.core.client.view.FooterWidget;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TabPanel;

/**
 * This view lets the user browse the booklet catalogue.
 *
 * @author Sebastien Zurfluh
 */
public class CMSView extends FlowPanel {
	// Shared handler for page requests
	private HorizontalPanel textsPanel;
	private TreeWidget pagePanel;
	private TreeWidget groupPanel;
	private EventBus eventBus;
	private CMSModel model;
	private MultiEditPanel editPanel;
	
	
	

	public CMSView(final EventBus eventBus,
			PageRequestEventHandler pageRequestEventHandler,
			ResourceRequestEventHandler resourceRequestHandler,
			CMSModel model) {
		assert eventBus != null;
		assert model != null;
		assert pageRequestEventHandler != null;
		assert resourceRequestHandler != null;
		
		this.eventBus = eventBus;
		this.model = model;
		
		setStyleName("cms-View");
		
		
		
		
		// Setup main panel
		textsPanel = new HorizontalPanel();
		textsPanel.setStyleName("cms-mainPanel");
		
		TabPanel resourceAndText = new TabPanel();
		resourceAndText.setStyleName("resourceAndTextPanel");
		
		resourceAndText.add(new AboutPanel(), new Header());
		
		resourceAndText.add(textsPanel, "Parcours");
		
		resourceAndText.selectTab(1);
		
		add(resourceAndText);
		
		FooterWidget footer = new FooterWidget();
		footer.setStyleName("footerWidget-big");
		add(footer);
	}
	
	/**
	 * Fill main sections and add main sections to main panel.
	 * 
	 * Call this after the panel has been attached.
	 */
	public void afterAttached() {
		pagePanel = new PageTreeWidget(DataType.PAGE, eventBus, model);
		groupPanel = new GroupTreeWidget(DataType.GROUP, eventBus, model);
		editPanel = new MultiEditPanel(model, eventBus);
		
		textsPanel.add(groupPanel);
		textsPanel.add(pagePanel);
		textsPanel.add(editPanel);
		
		textsPanel.setCellWidth(editPanel, "100%");
	}
}
