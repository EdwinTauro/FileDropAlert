package uk.co.sparktech.filedropalert.action;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract class AbstractActionController {
	
	private final static Logger LOG = LogManager.getLogger(AbstractActionController.class.getName());
	
	final ActionFactory m_actionFactory = ActionFactory.getInstance();
	
	public final synchronized void process(final ActionPayload payload, final List<String> actions) {
		
		// Delegating the control to a thread instance
		Runnable t = new Runnable() {
			
			@Override
			public void run() {
				
				for(String action : actions) {
					LOG.info("Triggering action. Action " + action + ", in Thread " + Thread.currentThread().getName());
					action(payload, action);
				}
				
			}
		};
		
		new Thread(t).start();
	}
	
	public abstract void action(ActionPayload payload, String action);
}
