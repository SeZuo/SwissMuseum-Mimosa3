package ch.sebastienzurfluh.swissmuseum.cms.client.model;

import java.util.Collection;
import java.util.LinkedList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import ch.sebastienzurfluh.swissmuseum.cms.client.control.eventbus.events.Action;
import ch.sebastienzurfluh.swissmuseum.core.client.model.Model;
import ch.sebastienzurfluh.swissmuseum.core.client.model.ModelWrapper;
import ch.sebastienzurfluh.swissmuseum.core.client.model.io.IOConnector;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.Data;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.DataReference;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.MenuData;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.ResourceData;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observable;

/**
 * {@code CMSModel} gives an extended version of the model for the CMSView purposes.
 * 
 * Changes to the CMSModel will also change the original Model. This permits to switch from one to
 * the other without overhead.
 * 
 * {@code CMSModel} follows the ACID principle (Atomicty, Consistency, Isolation, Durability).
 *
 *
 * @author Sebastien Zurfluh
 *
 */
public class CMSModel extends ModelWrapper {
	private IOConnector connector;
	
	public CMSModel(Model model) {
		super(model);
		
		try {
			connector = (IOConnector) model.getConnector();
		} catch (Exception e) {
			System.out.println("The connection to the database is read-only." +
					" You won't be able to commit the changes.");
		}
	}
	
	private Action currentIntentAction = Action.NONE;
	private DataReference currentIntentReference = DataReference.NONE;
	public Observable currentIntentObservable = new Observable();
	
	private Collection<ResourceData> allResources = new LinkedList<ResourceData>();
	public Observable allResourcesObservable = new Observable();
	
	/**
	 * <p>This method is limited to the use of the CMS</p>
	 * 
	 * <p>Loads all the resources from the connector into the observable object allResources.
	 */
	public void loadAllResources() {
		connector.asyncRequestAllResourceData(
				new AsyncCallback<Collection<ResourceData>>() {
					@Override
					public void onSuccess(Collection<ResourceData> resourceList) {
						setAllResources(resourceList);
					}

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Model:asyncRequestResourceData" +
								" Cannot get data from the connector");
					};
				});
		allResourcesObservable.notifyObservers();
	}
	
	
	/**
	 * @param allResources is a collection containing all the resources
	 */
	public void setAllResources(Collection<ResourceData> allResources) {
		this.allResources = allResources;
		
		currentIntentObservable.notifyObservers();
	}
	
	/**
	 * @return allResources is a collection containing all the resources
	 */
	public Collection<ResourceData> getAllResources() {
		return allResources;
	}
	
	/**
	 * <p>Provide the last user intent.</p>
	 * 
	 * <p>CAUTION: because of the asynchronous data loading, this function HAS to be called before
	 * a call to load. The intent will only be taken into account if new data is provided.<br />
	 * Therefore you cannot use this function to switch between edit and browse mode on the current
	 * page.</p>
	 * 
	 * 
	 * @param intentAction
	 * @param reference for the intent.
	 */
	public void setCurrentIntent(Action intentAction, DataReference reference) {
		this.currentIntentAction = intentAction;
		this.currentIntentReference = reference;
		
		currentIntentObservable.notifyObservers();
	}
	
	public Action getCurrentIntentAction() {
		return currentIntentAction;
	}
	
	public DataReference getCurrentIntentReference() {
		return currentIntentReference;
	}
	
	public void createResource(final ResourceData resourceData) {
		connector.createResource(resourceData, new AsyncCallback<DataReference>() {
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("CMSModel: resource creation failed.");
			}

			@Override
			public void onSuccess(DataReference result) {
				//TODO specify intent to edit the new entry
				CMSModel.this.load(result);
			}
		});
	}
	
	public void createPage(Data data) {
		connector.createPage(data, new AsyncCallback<DataReference>() {
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("CMSModel: resource creation failed.");
			}

			@Override
			public void onSuccess(DataReference result) {
				//TODO specify intent to edit the new entry
				CMSModel.this.load(result);
			}
		});
	}
	
	public void createGroup(MenuData groupMenu) {
		connector.createGroup(groupMenu, new AsyncCallback<DataReference>() {
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("CMSModel: resource creation failed.");
			}

			@Override
			public void onSuccess(DataReference result) {
				//TODO specify intent to edit the new entry
				CMSModel.this.load(result);
			}
		});
	}
}
