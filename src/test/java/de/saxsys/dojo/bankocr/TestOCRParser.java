/**
 * Copyright (C) Saxonia Systems AG
 */
package de.saxsys.dojo.bankocr;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests {@link AccountParser}.
 * 
 * @author <a href="mailto:Kai.Zickmann@saxsys.de">Kai Zickmann</a>
 */
@RunWith(Parameterized.class)
public class TestOCRParser {

    /**
     * The number being tested in this test case.
     */
    private final String numberToTest;

    /**
     * Default constructor.
     * 
     * @param numberToTest
     *            the number being tested in this test case, <code>null</code>
     *            is not a valid value
     */
    public TestOCRParser(final String numberToTest) {
        super();
        this.numberToTest = numberToTest;
    }

    /**
     * Provides the test data.
     * 
     * @return the test data, never <code>null</code>
     */
    @Parameters
    public static Collection<String[]> data() {
        final Collection<String[]> result = new ArrayList<String[]>();
        result.add(new String[] { "000000000" });
        result.add(new String[] { "111111111" });
        result.add(new String[] { "222222222" });
        result.add(new String[] { "333333333" });
        result.add(new String[] { "444444444" });
        result.add(new String[] { "555555555" });
        result.add(new String[] { "666666666" });
        result.add(new String[] { "777777777" });
        result.add(new String[] { "888888888" });
        return result;
    }

    /**
     * Delivers the file to the test resource for the given number.
     * 
     * @param number
     *            the number, <code>null</code> is not a valid value
     * @return the file
     * @throws IOException
     *             should never occur
     */
    private AccountParser getParserFor(final String number) throws IOException {
        final URL fileURL = getClass().getResource(number + ".txt");
        final File fileDescriptor = new File(fileURL.getFile());
        final AccountParser result = new AccountParser(fileDescriptor);
        return result;
    }

    /**
     * Tests parsing the specified number.
     * 
     * @throws IOException
     *             should never occur
     */
    @Test
    public void testParsing() throws IOException {
        final AccountParser parser = getParserFor(numberToTest);
        final String parsed = parser.parseFileContent();
        Assert.assertEquals("Parsing did not work for " + numberToTest, numberToTest, parsed);
    }

}
