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
     * Tests parsing the "0*" file.
     * 
     * @throws IOException
     *             should never occur
     */
    @Test
    public void testParsing000000000() throws IOException {
        final URL fileURL = getClass().getResource("000000000.txt");
        final File file = new File(fileURL.getFile());
        final String parsed = new AccountParser().parseFileContent(file);
        Assert.assertEquals("Parsing did not work.", "000000000", parsed);
    }

    /**
     * Tests parsing the "1*" file.
     * 
     * @throws IOException
     *             should never occur
     */
    @Test
    public void testParsing111111111() throws IOException {
        final URL fileURL = getClass().getResource("111111111.txt");
        final File file = new File(fileURL.getFile());
        final String parsed = new AccountParser().parseFileContent(file);
        Assert.assertEquals("Parsing did not work.", "111111111", parsed);
    }
}
