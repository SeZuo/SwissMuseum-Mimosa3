package ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.edit.resource;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.events.ResourceType;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.ResourceData;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class ResourceEditor extends FlowPanel {
	private TextBox titleBox = new TextBox();
	private TextArea descriptionBox = new TextArea();
	private ListBox resourceTypeBox = new ListBox();
	private TextBox resourceURLBox = new TextBox();
	
	/**
	 * Create a blank menu editor.
	 */
	public ResourceEditor() {
		add(createNewLine("Titre", titleBox));
		add(createNewLine("Description", descriptionBox));
		add(createNewEnumLine("Type de la resource", resourceTypeBox));
		add(createNewLine("URL de la resource", resourceURLBox));
	}
	
	public void setResource(ResourceType type) {
		switch(type) {
		case IMAGE:
			resourceTypeBox.setTabIndex(1);
			break;
		default:
			resourceTypeBox.setTabIndex(0);
		}
	}
	
	private FlowPanel createNewEnumLine(String labelText, ListBox textBox) {
		for(ResourceType resourceType : ResourceType.values()) {
			resourceTypeBox.addItem(resourceType.toString());
		}
		return createNewLine(labelText, textBox);
	}

	private FlowPanel createNewLine(String labelText, Widget textBox) {
		FlowPanel newLine = new FlowPanel();
		Label label = new Label(labelText);
		label.setStyleName("cms-textboxLabel");
		newLine.add(label);
		newLine.add(textBox);
		
		return newLine;
	}

	/**
	 * Create a menu editor with default values given by the {@code MenuData}.
	 * @param resourceData
	 */
	public ResourceEditor(ResourceData resourceData) {
		this();
		
		if(resourceData.equals(ResourceData.NONE))
			return;
		
		titleBox.setText(
				resourceData.getTitle());
		descriptionBox.setText(resourceData.getDetails());
		
		int rankOfResourceType = 0;
		for(ResourceType resourceType : ResourceType.values()) {
			if(resourceType.equals(resourceData.getResourceType()))
				break;
			rankOfResourceType++;
		}
		resourceTypeBox.setItemSelected(rankOfResourceType, true);
		resourceURLBox.setText(resourceData.getURL());
	}
}
