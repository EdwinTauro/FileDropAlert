package uk.co.sparktech.filedropalert.action;

public class ActionController extends AbstractActionController {

	@Override
	public void action(ActionPayload payload, String action) {
		ActionFactory actionFactory = ActionFactory.getInstance();

		ActionProcessor processor = actionFactory.getProcessor(action);
		processor.processActionPayload(payload);

	}

}
