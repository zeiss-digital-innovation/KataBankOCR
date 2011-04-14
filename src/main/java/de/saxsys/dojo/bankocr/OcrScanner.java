package de.saxsys.dojo.bankocr;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OcrScanner {

	public List<String> read(File accountFile) {
		List<String> accountNumberList = new ArrayList<String>();
		List<String> digitsOfOneLine = null;
		AccountFileReader reader = new AccountFileReader(accountFile);
		while (!(digitsOfOneLine = reader.getDigitsOfOneLine()).isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (String digit : digitsOfOneLine) {
				Digit resolvedDigit = Digit.value(digit);
				if (null == resolvedDigit) {
					sb.append("?");
				} else {
					sb.append(resolvedDigit.intValue());
				}
			}
			accountNumberList.add(sb.toString());
		}
		return accountNumberList;
	}
}
