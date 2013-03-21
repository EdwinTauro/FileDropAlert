package uk.co.sparktech.util.reflection;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.co.sparktech.filedropalert.action.ActionProcessor;
import uk.co.sparktech.filedropalert.util.PackageReader;


public class PackageReaderTest {

	@Test
	public void doTest() {
		List<String> classList = new PackageReader().read("uk.co.sparktech.filedropalert.action");
	}
	

}
