package uk.co.sparktech.filedropalert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	
	public static final synchronized List<File> loadFilesInDirectory(File f) {
		if (!f.canRead()) {
			throw new RuntimeException("Cannot read directory, possible cause Permission denied " + f.getName());
		}
		String filesInDirectory[] = f.list();
		String path = f.getPath();
		List<File> files = new ArrayList<File>();
		
		for (String filename : filesInDirectory) {
			File newFile = new File(path + File.separator + filename);
			System.out.println(newFile.getPath());

			files.add(newFile);
		}
		
		return files;
	}

	public static boolean fileExists(File file) {
		return file.exists();
	}
}
