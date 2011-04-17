package de.saxsys.dojo.bankocr;

import static de.saxsys.dojo.bankocr.TestUtils.createDummyFileFor;
import static de.saxsys.dojo.bankocr.TestUtils.getResultsFromFile;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

import java.util.List;

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
@SuppressWarnings("unchecked")
public class StoryOneTest {

	@After
	public void removeTestFile() {
		TestUtils.removeDummyFiles();
	}

	@Test
	public void readALineOfNineZeros() throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				"| || || || || || || || || |\n" + //
				"|_||_||_||_||_||_||_||_||_|\n";
		executeScannerFor(str);
		assertThat(getResultsFromFile(), hasItem(startsWith("000000000")));
	}

	@Test
	public void readALineOfNineTwos() throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				" _| _| _| _| _| _| _| _| _|\n" + //
				"|_ |_ |_ |_ |_ |_ |_ |_ |_ \n";
		executeScannerFor(str);
		assertThat(getResultsFromFile(), //
				hasItem(startsWith("222222222")));
	}

	private List<String> executeScannerFor(String str) throws Exception {
		return new OcrScanner().read(createDummyFileFor(str));
	}

	@Test
	public void readALineOfNineFours() throws Exception {
		String str = "" + //
				"                           \n" + //
				"|_||_||_||_||_||_||_||_||_|\n" + //
				"  |  |  |  |  |  |  |  |  |\n";
		executeScannerFor(str);
		assertThat(getResultsFromFile(), hasItem(startsWith("444444444")));
	}

	@Test
	public void readALineWithDigitsFromOneToNine() throws Exception {
		String str = "" + //
				"    _  _     _  _  _  _  _ \n" + //
				"  | _| _||_||_ |_   ||_||_|\n" + //
				"  ||_  _|  | _||_|  ||_| _|\n";
		executeScannerFor(str);
		assertThat(getResultsFromFile(), hasItem(startsWith("123456789")));
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
		executeScannerFor(str);
		assertThat(getResultsFromFile(),
				contains(startsWith("123456789"), startsWith("000000000")));
	}

	@Test
	public void readThreeLinesOfDigits() throws Exception {
		String str = "" //
				+ " _  _  _  _  _  _  _  _  _ \n" //
				+ "|_ |_||_ |_ |_ |_ |_ |_ |_ \n" //
				+ "|_||_||_||_||_||_||_||_||_|\n\n" //
				+ "    _  _     _  _  _  _  _ \n" //
				+ "  | _| _||_||_ |_   ||_||_|\n" //
				+ "  ||_  _|  | _||_|  ||_| _|\n\n" //
				+ " _  _  _  _  _  _  _  _  _ \n" //
				+ "| || || || || || || || || |\n" //
				+ "|_||_||_||_||_||_||_||_||_|\n\n";
		executeScannerFor(str);
		assertThat(getResultsFromFile(), contains( //
				startsWith("686666666"), //
				startsWith("123456789"), //
				startsWith("000000000")));
	}
}