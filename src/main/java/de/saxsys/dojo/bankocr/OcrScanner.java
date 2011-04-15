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
							signsOfOneLine, sb.toString()));
		}
		return accountNumberList;
	}

	private String getEvaluatedAccountNumberResult(
			List<ScannedSign> signsOfOneLine, String accountNumber) {

		if (accountNumber.contains("?")) {
			accountNumber += (" ILL");
		} else if (!AccountNumberValidator.isValid(accountNumber.toString())) {
			List<String> validNumbers = findValidAccountNumbers( //
					signsOfOneLine, accountNumber);
			if (validNumbers.isEmpty()) {
				accountNumber += (" ERR");
			} else if (1 == validNumbers.size()) {
				accountNumber = validNumbers.get(0);
			} else {
				accountNumber += (" AMB ");
				Collections.sort(validNumbers);
				accountNumber += (validNumbers.toString());
			}
		}

		return accountNumber;
	}

	private List<String> findValidAccountNumbers(
			List<ScannedSign> signsOfTheLine, String invalidAccountNumber) {

		List<String> validNumbers = new ArrayList<String>();
		for (int i = 0; i < signsOfTheLine.size(); i++) {

			String validAccountNumber = getValidNumberIfExists(
					invalidAccountNumber.toString(), signsOfTheLine.get(i), i);

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
