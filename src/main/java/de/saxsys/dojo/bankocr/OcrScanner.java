package de.saxsys.dojo.bankocr;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Sebastian Schmeck
 */
public class OcrScanner {

	public List<String> read(File accountFile) {

		final List<String> accountNumberResults = new ArrayList<String>();
		final AccountFileReader reader = new AccountFileReader(accountFile);

		List<ScannedSign> signsOfALine = null;
		while (!(signsOfALine = reader.getDigitsOfOneLine()).isEmpty()) {

			StringBuilder sb = new StringBuilder();
			for (ScannedSign sign : signsOfALine) {
				sb.append(AccountDigit.value(sign).character());
			}
			accountNumberResults.add( //
					getEvaluatedNumber(signsOfALine, sb.toString()));
		}

		return writeAccountNumbers( //
				accountNumberResults, accountFile.getName() + ".result");
	}

	private String getEvaluatedNumber(List<ScannedSign> signs, String number) {

		if (!AccountNumberValidator.isValid(number.toString())) {

			List<String> validNumbers = findValidAccountNumbers(signs, number);
			if (validNumbers.isEmpty()) {
				number += " ILL";
			} else if (1 == validNumbers.size()) {
				number = validNumbers.get(0);
			} else {
				Collections.sort(validNumbers);
				number += " AMB " + validNumbers;
			}
		}

		return number;
	}

	private List<String> findValidAccountNumbers(List<ScannedSign> signs,
			String invalidNumber) {

		List<String> validNumbers = new ArrayList<String>();
		for (int i = 0; i < signs.size(); i++) {

			String validAccountNumber = getValidNumberIfExists(invalidNumber,
					signs.get(i), i);

			if (!validAccountNumber.isEmpty()) {
				validNumbers.add(validAccountNumber);
			}
		}
		return writeAccountNumbers(validNumbers, "result.txt");
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

	private List<String> writeAccountNumbers(
			final List<String> accountNumberResults, String filename) {

		FileWriter fw = null;
		try {
			fw = new FileWriter(filename);
			for (String number : accountNumberResults) {
				fw.write(number);
				fw.append(System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			throw new RuntimeException("some trouble", e);
		} finally {
			if (fw != null)
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return accountNumberResults;
	}
}
