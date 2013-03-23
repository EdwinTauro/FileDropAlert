package uk.co.sparktech.filedropalert.action;

public class ActionController extends AbstractActionController {

	@Override
	public void action(final ActionPayload payload, final String action) {
		
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				
				ActionProcessor processor = m_actionFactory.getProcessor(action);
				processor.processActionPayload(payload);				
			}
		};
		new Thread(runnable).start();

	}

}
