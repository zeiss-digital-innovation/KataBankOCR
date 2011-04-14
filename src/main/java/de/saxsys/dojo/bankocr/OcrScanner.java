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
				sb.append(Digit.value(digit).character());
			}

			if (sb.toString().contains("?")) {
				sb.append(" ILL");
			} else if (!new AccountNumberValidator().isValid(sb.toString())) {
				sb.append(" ERR");
			}
			accountNumberList.add(sb.toString());
		}
		return accountNumberList;
	}
}
