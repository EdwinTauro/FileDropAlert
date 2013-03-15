package uk.co.sparktech.filedropalert;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainFileDropAlert {
	private static final Logger LOG = LogManager.getLogger(MainFileDropAlert.class.getName());

	private FileStat m_fileStat = new FileStat();
	
	public static void main(String[] args) {
		if (args.length == 0 || args.length < 2) {
			throw new RuntimeException("Command line argument required. Provide path and action to be taken.");
		}
		LOG.trace("Entering application.");

		String path = args[0];
		String[] actions = Arrays.copyOfRange(args, 1, args.length);
		new MainFileDropAlert().start(path, actions);
		
		LOG.trace("Exiting application.");
	}

	public void start(String pathname, String... action) {
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

	private void monitor(String... actions) {
		List<String> actionList = Arrays.asList(actions);
		new MonitorDaemon(m_fileStat, actionList).start();
	}
}
