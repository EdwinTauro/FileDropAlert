package uk.co.sparktech.filedropalert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainFileDropAlert {
	private FileStat m_fileStat = new FileStat();
	
	public static void main(String[] args) {
		if (args.length == 0 || args.length < 2) {
			throw new RuntimeException("Command line argument required. Provide path and action to be taken.");
		}
		new MainFileDropAlert().start(args[0], args[1]);
	}

	public void start(String pathname, String action) {
		loadProperty();
		loadPathInfo(pathname);
		monitor(action);
	}

	private final void loadProperty() {
		
	}
	
	private final void loadPathInfo(String pathname) {
		File f = new File(pathname);
		
		if (f.isDirectory()) {
			List<File> files  = FileUtil.loadFilesInDirectory(f);
			m_fileStat.add(files);	
		} else if (f.isFile()) {
			m_fileStat.add(f);
		}
		
		m_fileStat.root(f);
	}

	private void monitor(String action) {
		List<String> actions = new ArrayList<String>();
		actions.add(action);
		new MonitorDaemon(m_fileStat, actions).start();
	}
}
