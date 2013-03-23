package uk.co.sparktech.filedropalert.event;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uk.co.sparktech.filedropalert.action.ActionController;
import uk.co.sparktech.filedropalert.action.ActionPayload;

public final class NewFileEvent implements Event {
	private final static Logger LOG = LogManager.getLogger(NewFileEvent.class.getName());
	
	@Override
	public boolean trigger(final ActionPayload payload, final List<String> actions) {

		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				ActionController controller = new ActionController();
				
				if (!isFileChanging(payload.getFile())) {
					controller.process(payload, actions);
				}
				
			}
		};
		new Thread(runnable).start();

		return false;
	}

	private boolean isFileChanging(final File f) {
		
		if (f.isFile() && f.canRead()) {
			
			// Check at-least a few times before deciding that the file has stopped changing.
			for (int count = 0; count <= 3; count ++) {
				LOG.trace("Checking if file length and modified date have stopped changing for count " + count + " time. File " + f.getName());
			
				 while (true) {
					long lastLength = f.length();
					long lastModified = f.lastModified();
					
					// We assume 5 seconds is enough time to figure the file is finished changing.
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	
					long currentLength = f.length();
					long currentModified = f.lastModified();
	
					if (lastLength == currentLength || lastModified == currentModified) {
						LOG.trace("File length and modified date have stopped changing. File " + f.getName());
						break;
					}
				}
			}
		}
		
		return false;
	}
	
}
