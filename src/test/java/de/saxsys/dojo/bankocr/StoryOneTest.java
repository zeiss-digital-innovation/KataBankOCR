package de.saxsys.dojo.bankocr;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

import java.io.File;
import java.io.FileWriter;

import org.junit.After;
import org.junit.Test;

/**
 * You work for a bank, which has recently purchased an ingenious machine to
 * assist in reading letters and faxes sent in by branch offices. The machine
 * scans the paper documents, and produces a file with a number of entries which
 * each look like this:
 * 
 * <pre>
 *   _  _     _  _  _  _  _
 * | _| _||_||_ |_   ||_||_|
 * ||_  _|  | _||_|  ||_| _|
 * </pre>
 * 
 * Each entry is 4 lines long, and each line has 27 characters. The first 3
 * lines of each entry contain an account number written using pipes and
 * underscores, and the fourth line is blank. Each account number should have 9
 * digits, all of which should be in the range 0-9. A normal file contains
 * around 500 entries.
 * 
 * Your first task is to write a program that can take this file and parse it
 * into actual account numbers.
 * 
 * @author Sebastian Schmeck
 */
public class StoryOneTest {

	private static final String FILE_NAME = "testaccounts.txt";

	@After
	public void removeTestFile() {
		File file = new File(FILE_NAME);
		assertThat(file.delete(), is(true));
	}

	@Test
	public void readALineOfNineZeros() throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				"| || || || || || || || || |\n" + //
				"|_||_||_||_||_||_||_||_||_|\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createFileFor(str)), hasItem("000000000"));
	}

	@Test
	public void readALineOfNineOnes() throws Exception {
		String str = "" + //
				"                           \n" + //
				"  |  |  |  |  |  |  |  |  |\n" + //
				"  |  |  |  |  |  |  |  |  |\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createFileFor(str)), hasItem("111111111"));
	}

	@Test
	public void readALineOfNineTwos() throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				" _| _| _| _| _| _| _| _| _|\n" + //
				"|_ |_ |_ |_ |_ |_ |_ |_ |_ \n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createFileFor(str)), hasItem("222222222"));
	}

	@Test
	public void readALineOfNineThrees() throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				" _| _| _| _| _| _| _| _| _|\n" + //
				" _| _| _| _| _| _| _| _| _|\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createFileFor(str)), hasItem("333333333"));
	}

	@Test
	public void readALineOfNineFours() throws Exception {
		String str = "" + //
				"                           \n" + //
				"|_||_||_||_||_||_||_||_||_|\n" + //
				"  |  |  |  |  |  |  |  |  |\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createFileFor(str)), hasItem("444444444"));
	}

	@Test
	public void readALineOfNineFives() throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				"|_ |_ |_ |_ |_ |_ |_ |_ |_ \n" + //
				" _| _| _| _| _| _| _| _| _|\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createFileFor(str)), hasItem("555555555"));
	}

	@Test
	public void readALineOfNineSix() throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				"|_ |_ |_ |_ |_ |_ |_ |_ |_ \n" + //
				"|_||_||_||_||_||_||_||_||_|\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createFileFor(str)), hasItem("666666666"));
	}

	@Test
	public void readALineOfNineSevens() throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				"  |  |  |  |  |  |  |  |  |\n" + //
				"  |  |  |  |  |  |  |  |  |\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createFileFor(str)), hasItem("777777777"));
	}

	@Test
	public void readALineOfNineEights() throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				"|_||_||_||_||_||_||_||_||_|\n" + //
				"|_||_||_||_||_||_||_||_||_|\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createFileFor(str)), hasItem("888888888"));
	}

	@Test
	public void readALineOfNineNines() throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				"|_||_||_||_||_||_||_||_||_|\n" + //
				" _| _| _| _| _| _| _| _| _|\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createFileFor(str)), hasItem("999999999"));
	}

	@Test
	public void readALineWithDigitsFromOneToNine() throws Exception {
		String str = "" + //
				"    _  _     _  _  _  _  _ \n" + //
				"  | _| _||_||_ |_   ||_||_|\n" + //
				"  ||_  _|  | _||_|  ||_| _|\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createFileFor(str)), hasItem("123456789"));
	}

	@Test
	public void readTwoLinesOfDigits() throws Exception {
		String str = "" //
				+ "    _  _     _  _  _  _  _ \n" //
				+ "  | _| _||_||_ |_   ||_||_|\n" //
				+ "  ||_  _|  | _||_|  ||_| _|\n\n" //
				+ " _  _  _  _  _  _  _  _  _ \n" //
				+ "| || || || || || || || || |\n" //
				+ "|_||_||_||_||_||_||_||_||_|\n\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createFileFor(str)),
				contains("123456789", "000000000"));
	}

	@Test
	public void readThreeLinesOfDigits() throws Exception {
		String str = "" //
				+ " _  _  _  _  _  _  _  _  _ \n" //
				+ "|_ |_ |_ |_ |_ |_ |_ |_ |_ \n" //
				+ "|_||_||_||_||_||_||_||_||_|\n\n" //
				+ "    _  _     _  _  _  _  _ \n" //
				+ "  | _| _||_||_ |_   ||_||_|\n" //
				+ "  ||_  _|  | _||_|  ||_| _|\n\n" //
				+ " _  _  _  _  _  _  _  _  _ \n" //
				+ "| || || || || || || || || |\n" //
				+ "|_||_||_||_||_||_||_||_||_|\n\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createFileFor(str)),
				contains("666666666", "123456789", "000000000"));
	}

	private File createFileFor(String str) throws Exception {

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
}