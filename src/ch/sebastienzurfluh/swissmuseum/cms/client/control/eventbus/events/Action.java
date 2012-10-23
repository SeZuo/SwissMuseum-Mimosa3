package ch.sebastienzurfluh.swissmuseum.cms.client.control.eventbus.events;


/**
 * {@code Action}s are fired when the caller wants to interact with the DataBase (write mode).
 *
 *
 * @author Sebastien Zurfluh
 *
 */
public enum Action {
	NONE,
	/**
	 * This action notifies that the caller commands to create new elements with the current data.
	 */
	CREATE,
	/**
	 * Notifies that the caller commands to modify an existing entry with the current data.
	 */
	MODIFY,
	/**
	 * Delete some data from the DB.
	 */
	REMOVE;
	
	/* Things to do or not. 

	 	/**
		 * Saves the modifications for ever (for instance to the DB).
		 * /
		SAVE,
		/**
		 * Discard the visible changes done yet. {@code DISCARD} only discards changes that are not
		 * committed yet.
	 	 * /
		DISCARD,
		/**
		 * Reverts the last committed change to the DB.
		 * / 
		REVERT
	*/
}