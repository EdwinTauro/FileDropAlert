package uk.co.sparktech.filedropalert.action;

import java.util.Map;

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
		
		final Map<String, Class<?>> registeredActions = ActionProcessorMapping.REGISTERED_DEFAULT_ACTIONS;
		
		if (!registeredActions.containsKey(action.toUpperCase())) {
			throw new RuntimeException("Action not implemented. Action " + action);
		} else {
			Class<?> c = registeredActions.get(action.toUpperCase());


			try {
				Object instance = c.newInstance();
				
				if (instance instanceof ActionProcessor) {
					processor = (ActionProcessor) instance;
				} else {
					throw new RuntimeException("Invalid ActionProcessor implementation. Action " + action);
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage() + ".. for Action " + action);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage() + ".. for Action " + action);
			}
		}

		return processor; 
	}
}
