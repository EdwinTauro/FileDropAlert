package uk.co.sparktech.filedropalert.action;

import java.util.List;

public abstract class AbstractActionController implements Action {

	@Override
	public final void process(ActionPayload payload) {
		List<String> actions = getActions();
		
		processAction(payload);
	}
	
	private List<String> getActions() {
		// TODO Auto-generated method stub
		return null;
	}

	protected abstract void processAction(ActionPayload payload);
	
}
