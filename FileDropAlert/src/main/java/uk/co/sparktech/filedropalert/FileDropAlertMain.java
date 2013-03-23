package uk.co.sparktech.filedropalert;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileDropAlertMain {
	private static final Logger LOG = LogManager.getLogger(FileDropAlertMain.class.getName());
	
	public static void main(String[] args) {
		if (args.length == 0 || args.length < 2) {
			throw new RuntimeException("Command line argument required. Provide path to monitor and action to be taken.");
		}
		LOG.trace("Entering application.");

		String path = args[0];
		String[] actions = Arrays.copyOfRange(args, 1, args.length);
		new FileDropAlert().start(path, actions);
		
		LOG.trace("Exiting application.");
	}

}
