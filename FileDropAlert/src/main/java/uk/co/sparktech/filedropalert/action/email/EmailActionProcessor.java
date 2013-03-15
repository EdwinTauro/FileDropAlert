package uk.co.sparktech.filedropalert.action.email;

import uk.co.sparktech.filedropalert.action.AbstractActionController;
import uk.co.sparktech.filedropalert.action.ActionPayload;
import uk.co.sparktech.filedropalert.action.ActionProcessor;

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
