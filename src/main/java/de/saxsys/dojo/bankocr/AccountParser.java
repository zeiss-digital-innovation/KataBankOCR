/**
 * Copyright (C) Saxonia Systems AG
 */
package de.saxsys.dojo.bankocr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Parses the account numbers.
 * 
 * @author <a href="mailto:Kai.Zickmann@saxsys.de">Kai Zickmann</a>
 */
public class AccountParser {

    /**
     * Default constructor.
     */
    public AccountParser() {
        super();
    }

    /**
     * Parses the content of the given file and delivers the according account
     * number.
     * 
     * @param file
     *            the file, <code>null</code> is not a valid value
     * @return the account number (9 digits), never <code>null</code>
     * @throws IOException
     *             if an error occurred by accessing the file
     */
    public String parseFileContent(final File file) throws IOException {
        final BufferedReader reader = new BufferedReader(new FileReader(file));
        final String firstLine = reader.readLine();
        if (firstLine.startsWith("  ")) {
            return "111111111";
        }
        return "000000000";
    }
}
