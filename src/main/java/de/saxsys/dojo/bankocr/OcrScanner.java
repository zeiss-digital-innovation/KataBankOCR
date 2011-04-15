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
			if (!findValidAccountNumber(signsOfOneLine, sb)) {
				sb.append(" ERR");
			}
		}
		return sb.toString();
	}

	private boolean findValidAccountNumber(List<ScannedSign> signsOfOneLine,
			StringBuilder sb) {

		for (int i = 0; i < signsOfOneLine.size(); i++) {
			if (!getValidNumberIfExists(sb, signsOfOneLine.get(i), i).isEmpty()) {
				break;
			}
		}
		return AccountNumberValidator.isValid(sb.toString());
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
