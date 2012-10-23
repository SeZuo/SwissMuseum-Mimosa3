package ch.sebastienzurfluh.swissmuseum.cms.client.control.eventbus.events;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.AbstractEvent;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.DataReference;

/**
 * Fired when the user is requesting the means to take an {@code Action} on the DB.
 *
 *
 * @author Sebastien Zurfluh
 *
 */
public class IntentEvent extends AbstractEvent {
	private DataReference reference;
	private Action action;

	/**
	 * @param type of the data
	 * @param action to take on the data
	 */
	public IntentEvent(DataReference reference, Action action) {
		assert permission(reference, action);
		
		System.out.println("IntentEvent: " + action + " " + reference + "");
		
		this.reference = reference;
		this.action = action;
	}
	
	public DataReference getReference() {
		return reference;
	}
	
	public Action getAction() {
		return action;
	}

	@Override
	public EventType getType() {
		return EventType.INTENT;
	}
	
	/**
	 * @param reference of the data
	 * @param action to take on the data
	 * @return true if the action authorised on the given data type.
	 */
	private boolean permission(DataReference reference, Action action) {
		switch (reference.getType()) {
		case SUPER:
		case NONE:
			// no actions are authorised to SUPER and NONE
			return false;
		case GROUP:
		case PAGE:
		case RESOURCE:
		default:
			return true;
		}
	}
}
