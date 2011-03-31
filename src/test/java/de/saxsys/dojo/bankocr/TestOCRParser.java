/**
 * Copyright (C) Saxonia Systems AG
 */
package de.saxsys.dojo.bankocr;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link AccountParser}.
 * 
 * @author <a href="mailto:Kai.Zickmann@saxsys.de">Kai Zickmann</a>
 */
public class TestOCRParser {

    /**
     * Default constructor.
     */
    public TestOCRParser() {
        super();
    }

    /**
     * Delivers the file to the test resource for the given number.
     * 
     * @param number
     *            the number, <code>null</code> is not a valid value
     * @return the file
     */
    private File retriveFile(final String number) {
        final URL fileURL = getClass().getResource(number + ".txt");
        final File result = new File(fileURL.getFile());
        return result;
    }

    /**
     * Tests parsing the "0*" file.
     * 
     * @throws IOException
     *             should never occur
     */
    @Test
    public void testParsing000000000() throws IOException {
        final String numberToTest = "000000000";
        final File file = retriveFile(numberToTest);
        final String parsed = new AccountParser().parseFileContent(file);
        Assert.assertEquals("Parsing did not work.", numberToTest, parsed);
    }

    /**
     * Tests parsing the "1*" file.
     * 
     * @throws IOException
     *             should never occur
     */
    @Test
    public void testParsing111111111() throws IOException {
        final String numberToTest = "111111111";
        final File file = retriveFile(numberToTest);
        final String parsed = new AccountParser().parseFileContent(file);
        Assert.assertEquals("Parsing did not work.", numberToTest, parsed);
    }

    /**
     * Tests parsing the "2*" file.
     * 
     * @throws IOException
     *             should never occur
     */
    @Test
    public void testParsing222222222() throws IOException {
        final String numberToTest = "222222222";
        final File file = retriveFile(numberToTest);
        final String parsed = new AccountParser().parseFileContent(file);
        Assert.assertEquals("Parsing did not work.", numberToTest, parsed);
    }
}
