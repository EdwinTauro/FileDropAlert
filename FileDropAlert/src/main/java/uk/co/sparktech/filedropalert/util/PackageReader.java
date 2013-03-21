package uk.co.sparktech.filedropalert.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class PackageReader {

	public static List<String> read(String javaPackagePath) {
		String packagePath = javaPackagePath.replace(".", "/");
		
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    URL url = classLoader.getResource(packagePath);
	    String urlPath = url.getPath();
	    File urlFile = new File(urlPath);
	    List<String> files = new ArrayList<String>();
		recursiveFileLookup(urlFile, files);
	    
		List<String> classList = stripPathBackToPackage(packagePath, files);
		
		return classList;
	}
	
	private static void recursiveFileLookup(final File path, final List<String> files) {
		
		if (path.isDirectory()) {
			final File[] filesWithin = path.listFiles();
			
			for (File file : filesWithin) {
				recursiveFileLookup(file, files);	
			}
		} else {
			// Ensure it is a file
			if (path.isFile()) {
				files.add(path.getAbsolutePath());
			}
		}
	}
	
	private static List<String> stripPathBackToPackage(final String packagename, final List<String> files) {
		final String fileSystemPackageName = packagename.replace("/", System.getProperty("file.separator"));
		final List<String> classWithPackageInfo = new ArrayList<String>(files.size());
		
		for (String filepath : files) {
			int startIndex = filepath.indexOf(fileSystemPackageName);
			
			classWithPackageInfo.add(filepath.substring(startIndex, filepath.lastIndexOf('.')).replace(System.getProperty("file.separator"), "."));
		}
		
		return classWithPackageInfo;
	}
}
