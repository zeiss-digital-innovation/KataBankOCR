package de.saxsys.dojo.bankocr;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileWriter;

public class TestUtils {

	private static final String FILE_NAME = "testaccounts.txt";

	public static File createDummyFileFor(String str) throws Exception {
		FileWriter writer = null;
		try {
			writer = new FileWriter(FILE_NAME);
			writer.write(str);
		} finally {
			if (null != writer)
				writer.close();
		}
		return new File(FILE_NAME);
	}

	public static void removeDummyFile() {
		File file = new File(FILE_NAME);
		assertThat(file.delete(), is(true));
	}
}
