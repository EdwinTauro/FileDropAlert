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
	public final synchronized void processActionPayload(final ActionPayload payload) {

		// Delegating the email sending business to another thread as this 
		// could be a long process.
		Runnable t = new Runnable() {
			
			@Override
			public void run() {
				payload.lockPayload(ACTION_PROCESS_NAME);
				SendMail.mail(payload);
				payload.unlockPayload();
			}
		};
		
		new Thread(t).start();  
	}
}
