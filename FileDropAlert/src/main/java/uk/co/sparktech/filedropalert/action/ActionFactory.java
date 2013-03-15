package uk.co.sparktech.filedropalert.action;

import uk.co.sparktech.filedropalert.action.email.EmailActionProcessor;

class ActionFactory {

	private static final ActionFactory m_factory = new ActionFactory();
	
	private ActionFactory() {
		//Default constructor
	}
	
	public static ActionFactory getInstance() {
		return m_factory;
	}
	
	public ActionProcessor getProcessor(String action) {
		ActionProcessor processor = null;
		
		if (Action.EMAIL.toString().toLowerCase().equals(action.toLowerCase())) {
			processor = new EmailActionProcessor();
		} else {
			throw new RuntimeException("Action not implemented. Action " + action);
		}
		
		return processor; 
	}
}
