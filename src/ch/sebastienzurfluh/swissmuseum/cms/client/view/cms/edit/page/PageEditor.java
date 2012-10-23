/*
 * Copyright Zak Linder
 */

package ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.edit.page;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.cms.client.model.CMSModel;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.Data;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Zak Linder
 */
public class PageEditor extends FlowPanel {
	private TextBox titleBox = new TextBox();
	private TextArea subtitleBox = new TextArea();
	private StretchyTextArea contentBox = new StretchyTextArea();
	
	/**
	 * Create a blank page editor.
	 */
	public PageEditor(CMSModel model, EventBus eventBus) {
		add(createNewLine("Titre", titleBox));
		add(createNewLine("Sous-titre", subtitleBox));
		
		
		FlowPanel contentBoxAndControls = new FlowPanel();
		MenuBar controls = new Controls(model , eventBus, contentBox);
		contentBoxAndControls.add(controls);
		contentBoxAndControls.add(contentBox);
		
		add(createNewLine("Contenu", contentBoxAndControls));
		
		contentBox.setVisibleLines(15);
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
	 * @param menuData
	 */
	public PageEditor(Data data, CMSModel cmsModel, EventBus eventBus) {
		this(cmsModel, eventBus);
		
		titleBox.setText(data.getPageTitle());
		subtitleBox.setText(data.getPageContentHeader());
		contentBox.setText(data.getPageContentBody());
	}
}
