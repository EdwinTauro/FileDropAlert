package uk.co.sparktech.filedropalert.util.property;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class AppPropertyReader {
	private final static Logger LOG = LogManager.getLogger(AppPropertyReader.class.getName());

	private static final String PROPERTY_FILENAME = "filedropalert.properties";

	private static String FILE_DROP_ALERT_PROPERTY_KEY = "filedropalert.properties";
	
	private static AppPropertyReader appPropertyReader = new AppPropertyReader();

	private static Properties properties = new Properties();
	
	public static enum FILEDROPALERT {
		CHECKING_INTERVAL
	}
	
	static {

		File f = new File(PROPERTY_FILENAME);
		
		if (!f.exists()) {
			LOG.info("File " + PROPERTY_FILENAME + ", not found on app path. \n Looking for property set against -D" + FILE_DROP_ALERT_PROPERTY_KEY + " at command line.");
			
			f = new File(System.getProperty(FILE_DROP_ALERT_PROPERTY_KEY));
			
			if (!f.exists()) {
				LOG.error("Stopping the application");
			}
		}
		
		FileInputStream inStream = null;
		try {
			inStream = new FileInputStream(f);
			properties.load(inStream);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			f = null;
		}		
		
	}
	
	private AppPropertyReader() {
		// Default Constructor
	}
	
	public static final AppPropertyReader getInstance() {
		
		return appPropertyReader;
	}
	
	public String getValue(FILEDROPALERT type) {
		String key = null; 

		if (type.equals(FILEDROPALERT.CHECKING_INTERVAL)) {
			key = "filedropalert.interval";
		}
		String value = getValue(key);
		
		if (value == null) {
			throw new RuntimeException("Invalid value defined for Key. " + key);
		}
		key = null;
		
		return value;
	}

	
	protected String getValue(String key) {
		return (String) properties.get(key);		
	}
}
