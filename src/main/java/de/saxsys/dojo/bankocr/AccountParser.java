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
     * @param position
     *            the position of the digit to look up (0...9)
     * @return <code>true</code> if the line is set, <code>false</code>
     *         otherwise
     */
    private boolean isTopSet(final short position) {
        final int offset = 3 * position + 1;
        final boolean result = isCharacterSet(firstLine.charAt(offset));
        return result;
    }

    /**
     * Determines whether the upper line at the left side is set.
     * 
     * @param position
     *            the position of the digit to look up (0...9)
     * @return <code>true</code> if the line is set, <code>false</code>
     *         otherwise
     */
    private boolean isLeftTopSet(final short position) {
        final int offset = 3 * position;
        final boolean result = isCharacterSet(secondLine.charAt(offset));
        return result;
    }

    /**
     * Determines whether the upper line at the right side is set.
     * 
     * @param position
     *            the position of the digit to look up (0...9)
     * @return <code>true</code> if the line is set, <code>false</code>
     *         otherwise
     */
    private boolean isRightTopSet(final short position) {
        final int offset = 3 * position + 2;
        final boolean result = isCharacterSet(secondLine.charAt(offset));
        return result;
    }

    /**
     * Determines whether the middle line is set.
     * 
     * @param position
     *            the position of the digit to look up (0...9)
     * @return <code>true</code> if the line is set, <code>false</code>
     *         otherwise
     */
    private boolean isMiddleSet(final short position) {
        final int offset = 3 * position + 1;
        final boolean result = isCharacterSet(secondLine.charAt(offset));
        return result;
    }

    /**
     * Determines whether the bottom line at the left side is set.
     * 
     * @param position
     *            the position of the digit to look up (0...9)
     * @return <code>true</code> if the line is set, <code>false</code>
     *         otherwise
     */
    private boolean isLeftBottomSet(final short position) {
        final int offset = 3 * position;
        final boolean result = isCharacterSet(thirdLine.charAt(offset));
        return result;
    }

    /**
     * Parses the content of the given file and delivers the according account
     * number.
     * 
     * @return the account number (9 digits), never <code>null</code>
     */
    public String parseFileContent() {
        final StringBuilder result = new StringBuilder();
        for (short position = (short) 0; position < 9; position++) {
            result.append(parseDigit(position));
        }
        return result.toString();
    }

    /**
     * Parses the specified digit.
     * 
     * @param position
     *            the digit to parse (0...9)
     * @return the parsed digit, never <code>null</code>
     */
    public String parseDigit(final short position) {
        String result;
        if (isMiddleSet(position)) {
            result = parseMiddleIsSet(position);
        } else {
            result = parseMiddleIsUnset(position);
        }
        return result;
    }

    /**
     * Parses a digit where the middle line is set.
     * 
     * @param position
     *            the digit to parse (0...9)
     * @return the parsed digit, never <code>null</code>
     */
    private String parseMiddleIsSet(final short position) {
        String result;
        if (isRightTopSet(position)) {
            result = parseMiddleIsSetUpperRightIsSet(position);
        } else {
            result = parseMiddleIsSetUpperRightIsUnset(position);
        }
        return result;
    }

    /**
     * Parses a digit where the middle line and the upper right line are set.
     * 
     * @param position
     *            the digit to parse (0...9)
     * @return the parsed digit, never <code>null</code>
     */
    private String parseMiddleIsSetUpperRightIsSet(final short position) {
        String result = "4";
        if (isTopSet(position)) {
            if (isLeftTopSet(position)) {
                if (isLeftBottomSet(position)) {
                    result = "8";
                } else {
                    result = "9";
                }
            } else {
                if (isLeftBottomSet(position)) {
                    result = "2";
                } else {
                    result = "3";
                }
            }
        }
        return result;
    }

    /**
     * Parses a digit where the middle line is set, but the upper right line is
     * NOT set.
     * 
     * @param position
     *            the digit to parse (0...9)
     * @return the parsed digit, never <code>null</code>
     */
    private String parseMiddleIsSetUpperRightIsUnset(final short position) {
        String result;
        if (isLeftBottomSet(position)) {
            result = "6";
        } else {
            result = "5";
        }
        return result;
    }

    /**
     * Parses a digit where the middle line is NOT set.
     * 
     * @param position
     *            the digit to parse (0...9)
     * @return the parsed digit, never <code>null</code>
     */
    private String parseMiddleIsUnset(final short position) {
        String result = "1";
        if (isTopSet(position)) {
            if (isLeftTopSet(position)) {
                result = "0";
            } else {
                result = "7";
            }
        }
        return result;
    }

}
