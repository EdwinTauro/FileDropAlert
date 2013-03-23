package uk.co.sparktech.filedropalert.action.email;

import uk.co.sparktech.filedropalert.action.ActionPayload;
import uk.co.sparktech.filedropalert.action.ActionProcessor;
import uk.co.sparktech.filedropalert.action.ActionProcessorMapping;

public final class EmailActionProcessor implements ActionProcessor  {
	private static final String ACTION_PROCESS_NAME = "EMAIL"; 
	static {
		ActionProcessorMapping.register(ACTION_PROCESS_NAME, EmailActionProcessor.class);
	}
	
	@Override
	public final void processActionPayload(final ActionPayload payload) {
		payload.lockPayload(ACTION_PROCESS_NAME);
		SendMail.mail(payload);
		payload.unlockPayload();
	}
}
