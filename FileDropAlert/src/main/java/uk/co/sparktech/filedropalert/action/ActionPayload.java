package uk.co.sparktech.filedropalert.action;

import java.io.File;

public class ActionPayload {
	
	private boolean m_payloadLocked;
	
	private String m_actionProcessorName;
	
	private File m_file;
	
	public ActionPayload(File f) {
		m_file = f;
	}

	public String getPath() {
		
		return m_file.getPath();
	}

	public File getFile() {
		
		return m_file;
	}

	public String getName() {
		
		return m_file.getName();
	}

	public synchronized void lockPayload(String actionProcessorName) {
		m_actionProcessorName = actionProcessorName;
		m_payloadLocked = true;
	}
	
	public synchronized void unlockPayload() {
		m_payloadLocked = false;
	}

	public synchronized boolean isLocked() {
		return m_payloadLocked;
	}
	
	public String lockedBy() {
		return m_actionProcessorName;
	}

}
