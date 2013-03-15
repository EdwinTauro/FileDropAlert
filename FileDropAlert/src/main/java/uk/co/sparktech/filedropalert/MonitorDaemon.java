package uk.co.sparktech.filedropalert;

import java.io.File;
import java.util.List;

import uk.co.sparktech.filedropalert.action.ActionFactory;
import uk.co.sparktech.filedropalert.action.ActionPayload;
import uk.co.sparktech.filedropalert.action.ActionProcessor;
import uk.co.sparktech.filedropalert.util.property.AppPropertyReader;
import uk.co.sparktech.filedropalert.util.property.AppPropertyReader.FILEDROPALERT;

public class MonitorDaemon extends Thread {

	private final AppPropertyReader PROPERTY = AppPropertyReader.getInstance();

	private final FileStat m_fileStat;
	
	private final List<String> m_actions;

	public MonitorDaemon(FileStat fileStat, List<String> actions) {
		m_fileStat = fileStat;
		m_actions = actions;
	}

	@Override
	public void run() {
		super.run();
		
		while (true) {
			try {
				System.out.println("Sleeping ... ");
				Thread.sleep(Integer.decode(PROPERTY.getValue(FILEDROPALERT.CHECKING_INTERVAL)));
				System.out.println("Woken up and now monitoring... ");

				// Ensure root path still exists
				File file = m_fileStat.m_rootFile;
				
				if (!FileUtil.fileExists(file)) {
					System.out.println("File move or deleted nothing to monitor, file " + file.getPath());
				} 
				else {
					// Check on original set of files if any missing remove from list
					List<File> missingFiles = m_fileStat.refreshList();
					m_fileStat.remove(missingFiles);

					//Load files from root path to compare with previous read 
					List<File> files = FileUtil.loadFilesInDirectory(file);
					
					for (File f : files) {
						boolean newFile = m_fileStat.isNewFile(f);
						
						if (newFile) {
							System.out.println("Found new file adding to list. File " + f.getPath());
							m_fileStat.add(f);
							
							ActionFactory actionFactory = ActionFactory.getInstance();
							ActionPayload payload = new ActionPayload(f);
							
							for(String action : m_actions) {
								System.out.println("Triggering action. Action " + action);
								ActionProcessor processor = actionFactory.getProcessor(action);
								processor.processActionPayload(payload);
							}
						}
					}
				}				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}
