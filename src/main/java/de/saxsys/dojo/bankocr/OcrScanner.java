package de.saxsys.dojo.bankocr;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OcrScanner {

	public List<String> read(File accountFile) {
		List<String> accountNumberList = new ArrayList<String>();
		List<ScannedSign> signsOfOneLine = null;
		AccountFileReader reader = new AccountFileReader(accountFile);
		while (!(signsOfOneLine = reader.getDigitsOfOneLine()).isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (ScannedSign sign : signsOfOneLine) {
				sb.append(AccountDigit.value(sign).character());
			}
			accountNumberList.add( //
					getEvaluatedAccountNumberResult( //
							signsOfOneLine, sb));
		}
		return accountNumberList;
	}

	private String getEvaluatedAccountNumberResult(
			List<ScannedSign> signsOfOneLine, StringBuilder sb) {
		if (sb.toString().contains("?")) {
			sb.append(" ILL");
		} else if (!AccountNumberValidator.isValid(sb.toString())) {
			List<String> validNumbers = findValidAccountNumber(signsOfOneLine,
					sb);
			if (validNumbers.isEmpty()) {
				sb.append(" ERR");
			} else {
				return validNumbers.get(0);
			}
		}
		return sb.toString();
	}

	private List<String> findValidAccountNumber(
			List<ScannedSign> signsOfOneLine, StringBuilder sb) {

		List<String> validNumbers = new ArrayList<String>();
		for (int i = 0; i < signsOfOneLine.size(); i++) {
			String validAccountNumber = getValidNumberIfExists(sb,
					signsOfOneLine.get(i), i);
			if (!validAccountNumber.isEmpty()) {
				validNumbers.add(validAccountNumber);
				break;
			}
		}
		// return AccountNumberValidator.isValid(sb.toString());
		return validNumbers;
	}

	private String getValidNumberIfExists(StringBuilder sb, ScannedSign sign,
			int index) {
		for (AccountDigit digit : AccountDigit
				.valuesWithOneCharacterDifference(sign)) {

			String possibleAccountNumber = new StringBuffer(sb.toString())
					.replace(index, index + 1, digit.character()).toString();
			if (AccountNumberValidator.isValid(possibleAccountNumber)) {
				sb.replace(index, index + 1, digit.character());
				return sb.toString();
			}
		}
		return "";
	}
}
