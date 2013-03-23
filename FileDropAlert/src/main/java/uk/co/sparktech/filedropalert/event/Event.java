package uk.co.sparktech.filedropalert.event;

import java.util.List;

import uk.co.sparktech.filedropalert.action.ActionPayload;

public interface Event {

	boolean trigger(final ActionPayload payload, final List<String> actions);

}
