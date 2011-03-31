/**
 * Copyright (C) Saxonia Systems AG
 */
package de.saxsys.dojo.bankocr;

import java.io.File;

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
     */
    public String parseFileContent(final File file) {
        return "000000000";
    }
}
