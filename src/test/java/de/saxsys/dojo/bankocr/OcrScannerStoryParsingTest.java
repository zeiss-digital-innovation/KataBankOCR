package de.saxsys.dojo.bankocr;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

import java.io.StringReader;

import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

/**
 * Tests Story One.
 * 
 * @author Sebastian Schmeck
 */
public class OcrScannerStoryParsingTest {

	@Test
	public void readALineOfNineZeros() throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				"| || || || || || || || || |\n" + //
				"|_||_||_||_||_||_||_||_||_|\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(new StringReader(str)), hasItem("000000000"));
	}

	@Test
	public void readALineOfNineOnes() throws Exception {
		String str = "" + //
				"                           \n" + //
				"  |  |  |  |  |  |  |  |  |\n" + //
				"  |  |  |  |  |  |  |  |  |\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(new StringReader(str)), hasItem("111111111"));
	}

	@Test
	public void readALineOfNineTwos() throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				" _| _| _| _| _| _| _| _| _|\n" + //
				"|_ |_ |_ |_ |_ |_ |_ |_ |_ \n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(new StringReader(str)), hasItem("222222222"));
	}

	@Test
	public void readALineOfNineThrees() throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				" _| _| _| _| _| _| _| _| _|\n" + //
				" _| _| _| _| _| _| _| _| _|\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(new StringReader(str)), hasItem("333333333"));
	}

	@Test
	public void readALineOfNineFours() throws Exception {
		String str = "" + //
				"                           \n" + //
				"|_||_||_||_||_||_||_||_||_|\n" + //
				"  |  |  |  |  |  |  |  |  |\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(new StringReader(str)), hasItem("444444444"));
	}

	@Test
	public void readALineOfNineFives() throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				"|_ |_ |_ |_ |_ |_ |_ |_ |_ \n" + //
				" _| _| _| _| _| _| _| _| _|\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(new StringReader(str)), hasItem("555555555"));
	}

	@Test
	public void readALineOfNineSix() throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				"|_ |_ |_ |_ |_ |_ |_ |_ |_ \n" + //
				"|_||_||_||_||_||_||_||_||_|\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(new StringReader(str)), hasItem("666666666"));
	}

	@Test
	public void readALineOfNineSevens() throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				"  |  |  |  |  |  |  |  |  |\n" + //
				"  |  |  |  |  |  |  |  |  |\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(new StringReader(str)), hasItem("777777777"));
	}

	@Test
	public void readALineOfNineEights() throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				"|_||_||_||_||_||_||_||_||_|\n" + //
				"|_||_||_||_||_||_||_||_||_|\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(new StringReader(str)), hasItem("888888888"));
	}

	@Test
	public void readALineOfNineNines() throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				"|_||_||_||_||_||_||_||_||_|\n" + //
				" _| _| _| _| _| _| _| _| _|\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(new StringReader(str)), hasItem("999999999"));
	}

	@Test
	public void readALineWithDigitsFromOneToNine() throws Exception {
		String str = "" + //
				"    _  _     _  _  _  _  _ \n" + //
				"  | _| _||_||_ |_   ||_||_|\n" + //
				"  ||_  _|  | _||_|  ||_| _|\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(new StringReader(str)), hasItem("123456789"));
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
		assertThat(scanner.read(new StringReader(str)),
				JUnitMatchers.hasItems("123456789", "000000000"));
	}
}
