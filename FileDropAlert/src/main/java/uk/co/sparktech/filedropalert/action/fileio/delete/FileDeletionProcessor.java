package uk.co.sparktech.filedropalert.action.fileio.delete;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uk.co.sparktech.filedropalert.action.ActionPayload;
import uk.co.sparktech.filedropalert.action.ActionProcessor;
import uk.co.sparktech.filedropalert.action.ActionProcessorMapping;

public class FileDeletionProcessor implements ActionProcessor {
	private final static Logger LOG = LogManager.getLogger(FileDeletionProcessor.class.getName());

	static {
		ActionProcessorMapping.register("DELETE-FILE", FileDeletionProcessor.class);
	}
	
	@Override
	public void processActionPayload(final ActionPayload payload) {

		// Sleep for some time to give any previous action a chance to start and lock.
		// Wait till the lock is released.
		while (true) {			
			LOG.trace("Payload locked by " + payload.lockedBy() + " process. File " + payload.getName());
			
			try {
				Thread.sleep(5000);
				
				if (!payload.isLocked()) {
					break;
				}
			} catch (InterruptedException e) {
				LOG.error(e.getMessage());
			}
		}
		LOG.trace("Payload lock released. File " + payload.getName());
		
		File f = payload.getFile();
		final boolean succes = f.delete();
		
		if (!succes) {
			LOG.error("File Deletion failed. File " + payload.getName());
			throw new RuntimeException("File deletion failed. File " + f.getAbsolutePath());
		}
		LOG.info("File Deleted. File " + payload.getName());
		
		f = null;
	}

}
