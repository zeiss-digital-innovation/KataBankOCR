package de.saxsys.dojo.bankocr;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

import java.io.File;
import java.io.FileWriter;

import org.junit.After;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

/**
 * Tests Story One.
 * 
 * @author Sebastian Schmeck
 */
public class OcrScannerStoryParsingTest {

	private static final String FILE_NAME = "testaccounts.txt";

	@After
	public void removeTestFile() {
		File file = new File(FILE_NAME);
		assertThat(file.delete(), is(true));;
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
				+ "|_||_||_||_||_||_||_||_||_|\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createFileFor(str)),
				JUnitMatchers.hasItems("123456789", "000000000"));
	}
	
	private File createFileFor(String str) throws Exception {
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(FILE_NAME);
			writer.write(str);		
		} finally {
			if (null != writer) writer.close();
		}
		return new File(FILE_NAME);
	}
}
