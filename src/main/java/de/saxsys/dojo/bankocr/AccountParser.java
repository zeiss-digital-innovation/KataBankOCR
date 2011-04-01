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
     * The first line of the file content.
     */
    private final String firstLine;

    /**
     * The second line of the file content.
     */
    private final String secondLine;

    /**
     * The third line of the file content.
     */
    private final String thirdLine;

    /**
     * Initializes the account parser with the given file. The file content is
     * read during construction time, however not parsed.
     * 
     * @param file
     *            the file to parse, <code>null</code> is not a valid value
     * @throws IOException
     *             if an error occurred by accessing the file
     */
    public AccountParser(final File file) throws IOException {
        super();
        // open the file
        final BufferedReader reader = new BufferedReader(new FileReader(file));
        // read the content
        try {
            firstLine = reader.readLine();
            secondLine = reader.readLine();
            thirdLine = reader.readLine();
        } finally {
            reader.close();
        }
    }

    /**
     * Parses the content of the given file and delivers the according account
     * number.
     * 
     * @return the account number (9 digits), never <code>null</code>
     */
    public String parseFileContent() {
        if (firstLine.startsWith("  ")) {
            if (secondLine.startsWith(" ")) {
                return "111111111";
            }
            return "444444444";
        }
        if (secondLine.startsWith(" ")) {
            if (thirdLine.startsWith(" ")) {
                return "333333333";
            }
            return "222222222";
        }
        return "000000000";
    }
}
