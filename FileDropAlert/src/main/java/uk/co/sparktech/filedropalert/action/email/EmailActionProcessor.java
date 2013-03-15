package uk.co.sparktech.filedropalert.action.email;

import uk.co.sparktech.filedropalert.action.ActionPayload;
import uk.co.sparktech.filedropalert.action.ActionProcessor;

public final class EmailActionProcessor implements ActionProcessor  {

	@Override
	public final synchronized void processActionPayload(final ActionPayload payload) {

		// Delegating the email sending business to another thread as this 
		// could be a long process.
		Runnable t = new Runnable() {
			
			@Override
			public void run() {
				SendMail.mail(payload);
			}
		};
		
		new Thread(t).start();  
	}
}
