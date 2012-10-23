package ch.sebastienzurfluh.swissmuseum.cms.client.view.cms;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class Header extends FlowPanel {
	public Header() {
		Image header = new Image("resources/images/logos/mimosa3_3.png");
		header.setStyleName("cms-logo");
		add(header);
	}
}
