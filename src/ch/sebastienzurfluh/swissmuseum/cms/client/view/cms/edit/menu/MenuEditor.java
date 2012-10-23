package ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.edit.menu;

import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.MenuData;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class MenuEditor extends FlowPanel {
	private TextBox titleBox = new TextBox();
	private TextArea descriptionBox = new TextArea();
	private TextBox thumbImgURLBox = new TextBox();
	private TextBox imgURLBox = new TextBox();
	
	/**
	 * Create a blank menu editor.
	 */
	public MenuEditor() {
		add(createNewLine("Titre", titleBox));
		add(createNewLine("Description", descriptionBox));
		add(createNewLine("Image réduite", thumbImgURLBox));
		add(createNewLine("Image de titre", imgURLBox));
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
	public MenuEditor(MenuData menuData) {
		this();
		
		if(menuData.equals(MenuData.NONE))
			return;
		
		titleBox.setText(
				menuData.getTitle());
		descriptionBox.setText(menuData.getDescription());
		thumbImgURLBox.setText(menuData.getSquareImgURL());
		imgURLBox.setText(menuData.getRectangleImgURL());
	}
}
