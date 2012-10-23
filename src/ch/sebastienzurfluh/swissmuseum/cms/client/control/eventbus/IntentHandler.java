package ch.sebastienzurfluh.swissmuseum.cms.client.control.eventbus;

import ch.sebastienzurfluh.swissmuseum.cms.client.control.eventbus.events.IntentEvent;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.AbstractEvent;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.AbstractEvent.EventType;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBusListener;
import ch.sebastienzurfluh.swissmuseum.cms.client.model.CMSModel;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.DataReference;

/**
 * Handles user intents (defined in {@Action} class).
 *
 *
 * @author Sebastien Zurfluh
 *
 */
public class IntentHandler implements EventBusListener {
	private CMSModel cmsModel;
	private EventBus eventBus;

	public IntentHandler(CMSModel cmsModel, EventBus eventBus) {
		this.cmsModel = cmsModel;
		this.eventBus = eventBus;
		
		eventBus.addListener(this);
	}

	@Override
	public EventType getEventType() {
		return EventType.INTENT;
	}

	@Override
	public void notify(AbstractEvent e) {
		
		
		if (e instanceof IntentEvent) {
			IntentEvent intent = (IntentEvent) e;
			
			System.out.println("IntentHandler: " +
					intent.getAction() + " " + intent.getReference() + "");
			
			cmsModel.setCurrentIntent(intent.getAction(), intent.getReference());
			
			switch(intent.getAction()) {
			case MODIFY:
				System.out.println("IntentHandler: MODIFY loading.");
				if(intent.getReference().equals(DataReference.ALL_RESOURCES))
					cmsModel.loadAllResources();
				else
					cmsModel.load(intent.getReference());
				break;
			default:
				// Only modify needs a specific action as we need to load the corresponding data.
				break;
			}
		}
	}

}
