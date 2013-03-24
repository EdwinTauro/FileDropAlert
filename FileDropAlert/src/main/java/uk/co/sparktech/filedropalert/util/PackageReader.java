package uk.co.sparktech.filedropalert.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class PackageReader {
	private static final Logger LOG = LogManager.getLogger(PackageReader.class.getName());

	public static List<String> read(String javaPackagePath) {
		String packagePath = javaPackagePath.replace(".", "/");
		LOG.trace("Looking for Processor implementation in packagePath " + packagePath);
		
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    URL url = classLoader.getResource(packagePath);
	    String urlPath = url.getPath();
	    LOG.trace("Looking for Processor implementation in url " + urlPath);
		
	    File urlFile = new File(urlPath);
	    
	    List<String> files = new ArrayList<String>();
		recursiveFileLookup(urlFile, files);
		urlFile = null;
		List<String> classList = stripPathBackToPackage(packagePath, files);

		if (classList == null || classList.size() == 0) {
		    LOG.trace("This may be a jar file so will attempt to read from the jar.");
			
			try {
				
				classList = readFromJar(urlPath.replace("!/" + packagePath, "").replace("file:/", ""));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		
		return classList;
	}
	
	private static void recursiveFileLookup(final File path, final List<String> files) {
		
		if (path.isDirectory()) {
			LOG.trace(path.toString() + " is a directory");
			final File[] filesWithin = path.listFiles();
			
			for (File file : filesWithin) {
				recursiveFileLookup(file, files);	
			}
		} else {
			// Ensure it is a file
			if (path.isFile()) {
				LOG.trace(path.toString() + " is a file");
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
	
	private static List<String> readFromJar(final String urlPath) throws IOException {
		LOG.trace("Reading jar " + urlPath);
		final List<String> classList = new ArrayList<String>();
		
		File jarFileName = new File(urlPath);
		JarFile jarFile = new JarFile(jarFileName);
		Enumeration<JarEntry> entries = jarFile.entries();
		
		while (entries.hasMoreElements()) {
			JarEntry element = entries.nextElement();
			
			if (element.getName().contains(".class")) {
				classList.add(element.getName().replace(".class", "").replace("/", "."));
			}
		}
		jarFile.close();
		
		return classList; 
	}
}
