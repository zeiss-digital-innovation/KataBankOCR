package de.saxsys.dojo.bankocr;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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
					sb.toString());
			if (validNumbers.isEmpty()) {
				sb.append(" ERR");
			} else if (1 == validNumbers.size()) {
				sb.delete(0, sb.length()).append(validNumbers.get(0));
			} else {
				sb.append(" AMB ");
				Collections.sort(validNumbers);
				sb.append(validNumbers);
			}
		}
		return sb.toString();
	}

	private List<String> findValidAccountNumber(
			List<ScannedSign> signsOfOneLine, String invalidAccountNumber) {

		List<String> validNumbers = new ArrayList<String>();
		for (int i = 0; i < signsOfOneLine.size(); i++) {
			String validAccountNumber = getValidNumberIfExists(
					invalidAccountNumber.toString(), signsOfOneLine.get(i), i);
			if (!validAccountNumber.isEmpty()) {
				validNumbers.add(validAccountNumber);
			}
		}
		return validNumbers;
	}

	private String getValidNumberIfExists(String invalidAccountNumber,
			ScannedSign sign, int signIndex) {

		for (AccountDigit digit : AccountDigit
				.valuesWithOneCharacterDifference(sign)) {

			String possibleAccountNumber = new StringBuilder(
					invalidAccountNumber).replace(signIndex, signIndex + 1,
					digit.character()).toString();

			if (AccountNumberValidator.isValid(possibleAccountNumber)) {
				return possibleAccountNumber;
			}
		}

		return "";
	}
}
