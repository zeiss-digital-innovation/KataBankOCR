package de.saxsys.dojo.bankocr;

import static de.saxsys.dojo.bankocr.TestUtils.createDummyFileFor;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

import org.junit.After;
import org.junit.Test;

/**
 * User Story 4
 * <p>
 * It turns out that often when a number comes back as ERR or ILL it is because
 * the scanner has failed to pick up on one pipe or underscore for one of the
 * figures. For example
 * </p>
 * 
 * <pre>
 *  _  _  _  _  _  _     _ 
 * |_||_|| || ||_   |  |  ||_ 
 *   | _||_||_||_|  |  |  | _|
 * </pre>
 * <p>
 * The 9 could be an 8 if the scanner had missed one |. Or the 0 could be an 8.
 * Or the 1 could be a 7. The 5 could be a 9 or 6. So your next task is to look
 * at numbers that have come back as ERR or ILL, and try to guess what they
 * should be, by adding or removing just one pipe or underscore. If there is
 * only one possible number with a valid checksum, then use that. If there are
 * several options, the status should be AMB. If you still can't work out what
 * it should be, the status should be reported ILL.
 * </p>
 * 
 * @author Sebastian Schmeck
 */
public class StoryFourTest {

	@After
	public void removeDummyFile() {
		TestUtils.removeDummyFile();
	}

	@Test
	public void readALineOfNineOnesAndGetOneCorrectionAtPositionOne()
			throws Exception {
		String str = "" + //
				"                           \n" + //
				"  |  |  |  |  |  |  |  |  |\n" + //
				"  |  |  |  |  |  |  |  |  |\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createDummyFileFor(str)), //
				contains("711111111"));
	}

	@Test
	public void readALineOfNineThreesAndGetOneCorrectionAtPositionFour()
			throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				" _| _| _| _| _| _| _| _| _|\n" + //
				" _| _| _| _| _| _| _| _| _|\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createDummyFileFor(str)), //
				contains("333393333"));
	}

	@Test
	public void readALineOfNineSevensAndGetOneCorrectionAtPositionSeven()
			throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				"  |  |  |  |  |  |  |  |  |\n" + //
				"  |  |  |  |  |  |  |  |  |\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createDummyFileFor(str)), //
				contains("777777177"));
	}

	@Test
	public void ifNumberIsInvalidButOnlyTheFourthSignMissesACharacterToAValidNumberTakeTheValidOne()
			throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				" _|| || || || || || || || |\n" + //
				"|_ |_||_||_||_||_||_||_||_|\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createDummyFileFor(str)), //
				contains("200800000"));
	}

	@Test
	public void readALineOfNineEightsResultsInThreeProposals() throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				"|_||_||_||_||_||_||_||_||_|\n" + //
				"|_||_||_||_||_||_||_||_||_|\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createDummyFileFor(str)), //
				contains("888888888 AMB [888886888, 888888880, 888888988]"));
	}

	@Test
	public void readALineOfNineFivesResultsInTwoProposals() throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				"|_ |_ |_ |_ |_ |_ |_ |_ |_ \n" + //
				" _| _| _| _| _| _| _| _| _|\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createDummyFileFor(str)), //
				hasItem("555555555 AMB [555655555, 559555555]"));
	}

	@Test
	public void readALineOfNineSixesResultsInTwoProposals() throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				"|_ |_ |_ |_ |_ |_ |_ |_ |_ \n" + //
				"|_||_||_||_||_||_||_||_||_|\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createDummyFileFor(str)), //
				contains("666666666 AMB [666566666, 686666666]"));
	}

	@Test
	public void readALineOfNineNinesResultsInThreeProposals() throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				"|_||_||_||_||_||_||_||_||_|\n" + //
				" _| _| _| _| _| _| _| _| _|\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createDummyFileFor(str)), //
				contains("999999999 AMB [899999999, 993999999, 999959999]"));
	}

	@Test
	public void readALineOfDifferentSignsResultsInThreeProposals()
			throws Exception {
		String str = "" + //
				"    _  _  _  _  _  _     _ \n" + //
				"|_||_|| || ||_   |  |  ||_ \n" + //
				"  | _||_||_||_|  |  |  | _|\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createDummyFileFor(str)), //
				contains("490067715 AMB [490067115, 490067719, 490867715]"));
	}

	@Test
	public void ifInvalidNumbersFoundThenAppendERR() throws Exception {
		String str = "" + //
				" _  _  _  _  _  _  _  _  _ \n" + //
				" _| _| _| _| _| _| _| _| _|\n" + //
				"|_ |_ |_ |_ |_ |_ |_ |_ |_ \n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createDummyFileFor(str)), //
				contains(endsWith(" ILL")));
	}

	@Test
	public void readLineWithUnknownSignAtPositionOneAndFindAValidAccountNumber()
			throws Exception {
		String str = ""//
				+ "    _  _     _  _  _  _  _ \n" //
				+ " _| _| _||_||_ |_   ||_||_|\n" //
				+ "  ||_  _|  | _||_|  ||_| _|\n\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createDummyFileFor(str)), //
				contains("123456789"));
	}

	@Test
	public void readLineWithUnknownSignAtPositionTwoAndFindAValidAccountNumber()
			throws Exception {
		String str = ""//
				+ " _     _  _  _  _  _  _    \n" //
				+ "| || || || || || || ||_   |\n" //
				+ "|_||_||_||_||_||_||_| _|  |\n\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createDummyFileFor(str)), //
				contains("000000051"));
	}

	@Test
	public void readLineWithUnknownSignAtPositionNineAndFindAValidAccountNumber()
			throws Exception {
		String str = ""//
				+ "    _  _  _  _  _  _     _ \n" //
				+ "|_||_|| ||_||_   |  |  | _ \n" //
				+ "  | _||_||_||_|  |  |  | _|\n\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createDummyFileFor(str)), //
				contains("490867715"));
	}
}
