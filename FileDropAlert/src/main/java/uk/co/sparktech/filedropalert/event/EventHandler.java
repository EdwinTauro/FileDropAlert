package uk.co.sparktech.filedropalert.event;

import java.util.List;

import uk.co.sparktech.filedropalert.action.ActionPayload;

public class EventHandler {
	
	private static EventHandler handler = new EventHandler();

	public void trigger(final ActionPayload payload, final List<String> actions) {
		new NewFileEvent().trigger(payload, actions);
	}

	public static EventHandler getInstance() {

		return handler;
	}
}
