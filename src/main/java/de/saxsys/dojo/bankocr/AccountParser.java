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
     * Determines whether a line is set within the given character.
     * 
     * @param character
     *            the character
     * @return <code>true</code> if the character is any line,
     *         <code>false</code> if it is no line (empty)
     */
    private boolean isCharacterSet(final char character) {
        final boolean result = (character != ' ');
        return result;
    }

    /**
     * Determines whether the top line is set.
     * 
     * @return <code>true</code> if the line is set, <code>false</code>
     *         otherwise
     */
    private boolean isTopSet() {
        final boolean result = isCharacterSet(firstLine.charAt(1));
        return result;
    }

    /**
     * Determines whether the upper line at the left side is set.
     * 
     * @return <code>true</code> if the line is set, <code>false</code>
     *         otherwise
     */
    private boolean isLeftTopSet() {
        final boolean result = isCharacterSet(secondLine.charAt(0));
        return result;
    }

    /**
     * Determines whether the upper line at the right side is set.
     * 
     * @return <code>true</code> if the line is set, <code>false</code>
     *         otherwise
     */
    private boolean isRightTopSet() {
        final boolean result = isCharacterSet(secondLine.charAt(2));
        return result;
    }

    /**
     * Determines whether the middle line is set.
     * 
     * @return <code>true</code> if the line is set, <code>false</code>
     *         otherwise
     */
    private boolean isMiddleSet() {
        final boolean result = isCharacterSet(secondLine.charAt(1));
        return result;
    }

    /**
     * Determines whether the bottom line at the left side is set.
     * 
     * @return <code>true</code> if the line is set, <code>false</code>
     *         otherwise
     */
    private boolean isLeftBottomSet() {
        final boolean result = isCharacterSet(thirdLine.charAt(0));
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
        if (isMiddleSet()) {
            result = parseMiddleIsSet();
        } else {
            result = parseMiddleIsUnset();
        }
        return result;
    }

    /**
     * Parses a digit where the middle line is set.
     * 
     * @return the account number (9 digits), never <code>null</code>
     */
    private String parseMiddleIsSet() {
        String result = "UNKNOWN";
        if (isRightTopSet()) {
            result = parseMiddleIsSetUpperRightIsSet();
        } else {
            result = parseMiddleIsSetUpperRightIsUnset();
        }
        return result;
    }

    /**
     * Parses a digit where the middle line and the upper right line are set.
     * 
     * @return the account number (9 digits), never <code>null</code>
     */
    private String parseMiddleIsSetUpperRightIsSet() {
        String result = "444444444";
        if (isTopSet()) {
            if (isLeftTopSet()) {
                if (isLeftBottomSet()) {
                    result = "888888888";
                } else {
                    result = "999999999";
                }
            } else {
                if (isLeftBottomSet()) {
                    result = "222222222";
                } else {
                    result = "333333333";
                }
            }
        }
        return result;
    }

    /**
     * Parses a digit where the middle line is set, but the upper right line is
     * NOT set.
     * 
     * @return the account number (9 digits), never <code>null</code>
     */
    private String parseMiddleIsSetUpperRightIsUnset() {
        String result = "INTERNAL_ERROR";
        if (isLeftBottomSet()) {
            result = "666666666";
        } else {
            result = "555555555";
        }
        return result;
    }

    /**
     * Parses a digit where the middle line is NOT set.
     * 
     * @return the account number (9 digits), never <code>null</code>
     */
    private String parseMiddleIsUnset() {
        String result = "111111111";
        if (isTopSet()) {
            if (isLeftTopSet()) {
                result = "000000000";
            } else {
                result = "777777777";
            }
        }
        return result;
    }

}
