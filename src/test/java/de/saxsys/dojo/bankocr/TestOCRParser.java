/**
 * Copyright (C) Saxonia Systems AG
 */
package de.saxsys.dojo.bankocr;

import java.io.File;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

import de.saxsys.dojo.bankocr.AccountParser;

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
     * Tests parsing the "000000000" file.
     */
    @Test
    public void testParsing000000000() {
        final URL fileURL = getClass().getResource("000000000.txt");
        final File file = new File(fileURL.getFile());
        final String parsed = new AccountParser().parseFileContent(file);
        Assert.assertEquals("Parsing did not work.", "000000000", parsed);
    }
}
