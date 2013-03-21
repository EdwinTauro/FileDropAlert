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

		// Sleep for some time to give any other previous action to start. Wait till the lock is released.
		{
			LOG.debug("Payload locked by " + payload.lockedBy() + " waiting. File " + payload.getName());
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		} while (payload.isLocked());
		File f = payload.getFile();
		final boolean succes = f.delete();
		
		if (!succes) {
			throw new RuntimeException("File deletion failed. File " + f.getAbsolutePath());
		}
		LOG.info("File Deleted. File " + payload.getName());
		
		f = null;
	}

}
