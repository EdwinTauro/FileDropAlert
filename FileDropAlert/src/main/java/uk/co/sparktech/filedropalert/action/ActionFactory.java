package uk.co.sparktech.filedropalert.action;

public class ActionFactory {

	private static final ActionFactory m_factory = new ActionFactory();
	
	private ActionFactory() {
		//Default consutrutor
	}
	
	public static ActionFactory getInstance() {
		return m_factory;
	}
	
	public ActionProcessor getProcessor(String action) {
		ActionProcessor processor = null;
		
		if (Action.Actions.EMAIL.toString().toLowerCase().equals(action.toLowerCase())) {
			processor = new EmailActionProcessor();
		} else {
			throw new RuntimeException("Action not implemented. Action " + action);
		}
		
		return processor; 
	}
}
