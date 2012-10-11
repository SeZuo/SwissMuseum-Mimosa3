package ch.sebastienzurfluh.client;

import ch.sebastienzurfluh.client.control.AppPresenter;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SwissMuseum___Mimosa3 implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		AppPresenter appPresenter = new AppPresenter(RootPanel.get());
		appPresenter.start();
	}
}
