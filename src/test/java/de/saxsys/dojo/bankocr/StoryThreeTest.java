package de.saxsys.dojo.bankocr;

import static de.saxsys.dojo.bankocr.TestUtils.createDummyFileFor;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Test;

/**
 * Your boss is keen to see your results. He asks you to write out a file of
 * your findings, one for each input file, in this format:
 * 
 * <pre>
 * 457508000
 * 664371495 ERR
 * 86110??36 ILL
 * </pre>
 * 
 * ie the file has one account number per row. If some characters are illegible,
 * they are replaced by a ?. In the case of a wrong checksum, or illegible
 * number, this is noted in a second column indicating status.
 * 
 * @author Sebastian Schmeck
 */
public class StoryThreeTest {

	@After
	public void removeDummyFile() {
		TestUtils.removeDummyFiles();
	}

	@Test
	public void useAQuestionMarkIfNoASignDoesMatchADigit() throws Exception {
		String str = "" + //
				"                           \n" + //
				"  | _|  |  |  |  |  |  |  |\n" + //
				"  |  |  |  |  |  |  |  |  |\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createDummyFileFor(str)), //
				contains(startsWith("1?1111111")));
	}

	@Test
	public void ifUnknownSignsFoundThenAppendILL() throws Exception {
		String str = "" + //
				"                           \n" + //
				"  | _|  |  |  |  |  |  |  |\n" + //
				"  |  |  |  |  |  |  |  |  |\n";
		OcrScanner scanner = new OcrScanner();
		assertThat(scanner.read(createDummyFileFor(str)), //
				contains(endsWith(" ILL")));
	}
}