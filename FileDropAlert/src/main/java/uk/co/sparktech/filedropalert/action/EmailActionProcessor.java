package uk.co.sparktech.filedropalert.action;

import uk.co.sparktech.filedropalert.action.email.SendMail;

public class EmailActionProcessor extends AbstractActionController
		implements ActionProcessor  {

	
	@Override
	protected void processAction(ActionPayload payload) {

		SendMail.mail(payload);
	}

	@Override
	public void processActionPayload(ActionPayload payload) {
		processAction(payload);
	}

}
