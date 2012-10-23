package ch.sebastienzurfluh.swissmuseum.cms.client.control;

import ch.sebastienzurfluh.swissmuseum.core.client.control.Config;
import ch.sebastienzurfluh.swissmuseum.core.client.model.Model.ViewMode;

public class CMSConfig extends Config {
	/**
	 * TEST_MODE should be enabled if you want to use the offline test database.
	 */
	public static final boolean TEST_MODE = false;
	
	/**
	 * How do you wanna start the app: BROWSE or EDIT?
	 */
	public static final ViewMode START_VIEW_MODE = ViewMode.EDIT;
}
