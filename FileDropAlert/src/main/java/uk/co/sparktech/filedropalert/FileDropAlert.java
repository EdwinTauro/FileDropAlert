package uk.co.sparktech.filedropalert;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uk.co.sparktech.filedropalert.action.ActionProcessor;
import uk.co.sparktech.filedropalert.action.ActionProcessorMapping;
import uk.co.sparktech.filedropalert.util.PackageReader;

public class FileDropAlert {
	private static final Logger LOG = LogManager.getLogger(FileDropAlert.class.getName());

	static {
		List<String> classList = PackageReader.read(ActionProcessor.class.getPackage().getName());
		findActionProcessorImplementationClassFromList(classList, "processor");
	
		LOG.info(ActionProcessorMapping.registeredActions());
	}

	private static List<Class<?>> findActionProcessorImplementationClassFromList(final List<String> files, String hint) {
		final List<Class<?>> actionProcessorClasses = new ArrayList<Class<?>>(files.size());
		
		for (String filepath : files) {
			try {
				if (hint != null && !filepath.toLowerCase().contains(hint)) {
					continue;
				}
				
				Class<?> c = Class.forName(filepath);
				
				if (c.isInterface()) {
					continue;
				}
				Type[] types = c.getGenericInterfaces();
				boolean matchFound = false;
				for (Type type : types) {
					if (type.equals(ActionProcessor.class)) {
						matchFound = true;
						break;
					}
				}
				
				if (matchFound) {
					actionProcessorClasses.add(c);
				}
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return actionProcessorClasses;
	}
	
	private FileStat m_fileStat = new FileStat();
	
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
