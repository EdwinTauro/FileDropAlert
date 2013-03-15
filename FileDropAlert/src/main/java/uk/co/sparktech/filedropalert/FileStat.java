package uk.co.sparktech.filedropalert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileStat {
	File m_rootFile;
	List<File> m_fileStatuses = new ArrayList<File>();

	public boolean isNewFile(File f) {
		
		return !m_fileStatuses.contains(f);
	}

	public void add(File f) {
		m_fileStatuses.add(f);
	}
	
	public void add(List<File> files) {
		m_fileStatuses.addAll(files);		
	}

	public void root(File f) {
		m_rootFile = f;
	}

	public void remove(File f) {
		final boolean result = m_fileStatuses.remove(f);
		
		if (!result) {
			System.out.println("File does not exsit to be removed. File " + f.getParent());
		}
	}

	public List<File> refreshList() {
		final List<File> missingFiles = new ArrayList<File>();
		
		for (File file : m_fileStatuses) {
			
			if (!file.exists()) {
				missingFiles.add(file);
			}
		}
		
		return missingFiles;
	}

	public void remove(List<File> files) {
		
		for (File file : files) {
			remove(file);
		}
	}
}
