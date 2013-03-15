package uk.co.sparktech.filedropalert.action;

import java.io.File;

public class ActionPayload {
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

}
