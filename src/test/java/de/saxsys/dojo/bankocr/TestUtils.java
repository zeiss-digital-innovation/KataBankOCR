package de.saxsys.dojo.bankocr;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

	public static void removeDummyFiles() {
		File file = new File(FILE_NAME);
		assertThat(file.delete(), is(true));
		file = new File(FILE_NAME + ".result");
		assertThat(file.delete(), is(true));
	}

	public static List<String> getResultsFromFile() {

		List<String> results = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(FILE_NAME + ".result"));

			String line = null;
			while (null != (line = reader.readLine())) {
				results.add(line);
			}

		} catch (IOException e) {
			throw new RuntimeException("some trouble", e);
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
			}
		}

		return results;
	}
}
