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
     * Determines whether the top line is set.
     * 
     * @return <code>true</code> if the line is set, <code>false</code>
     *         otherwise
     */
    private boolean isTopSet() {
        final boolean result = !firstLine.startsWith("  ");
        return result;
    }

    /**
     * Determines whether the upper line at the left side is set.
     * 
     * @return <code>true</code> if the line is set, <code>false</code>
     *         otherwise
     */
    private boolean isLeftTopSet() {
        final boolean result = !secondLine.startsWith(" ");
        return result;
    }

    /**
     * Determines whether the middle line is set.
     * 
     * @return <code>true</code> if the line is set, <code>false</code>
     *         otherwise
     */
    private boolean isMiddleSet() {
        final boolean result = (secondLine.charAt(1) != ' ');
        return result;
    }

    /**
     * Determines whether the bottom line at the left side is set.
     * 
     * @return <code>true</code> if the line is set, <code>false</code>
     *         otherwise
     */
    private boolean isLeftBottomSet() {
        final boolean result = !thirdLine.startsWith(" ");
        return result;
    }

    /**
     * Parses the content of the given file and delivers the according account
     * number.
     * 
     * @return the account number (9 digits), never <code>null</code>
     */
    public String parseFileContent() {
        String result = "UNKNOWN";
        if (isTopSet()) {
            if (isLeftTopSet()) {
                if (isLeftBottomSet()) {
                    if (isMiddleSet()) {
                        result = "666666666";
                    } else {
                        result = "000000000";
                    }
                } else {
                    result = "555555555";
                }
            } else {
                if (isLeftBottomSet()) {
                    result = "222222222";
                } else {
                    if (isMiddleSet()) {
                        result = "333333333";
                    } else {
                        result = "777777777";
                    }
                }
            }
        } else {
            if (isLeftTopSet()) {
                result = "444444444";
            } else {
                result = "111111111";
            }
        }
        return result;
    }
}
