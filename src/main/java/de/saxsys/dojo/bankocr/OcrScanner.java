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

	private String getEvaluatedAccountNumberResult(List<ScannedSign> signsOfOneLine,
			StringBuilder sb) {
		if (sb.toString().contains("?")) {
			sb.append(" ILL");
		} else if (!AccountNumberValidator.isValid(sb.toString())) {
			if (!findAValidFirstNumber(signsOfOneLine, sb)) {
				sb.append(" ERR");
			}
		}
		return sb.toString();
	}

	private boolean findAValidFirstNumber(List<ScannedSign> signsOfOneLine,
			StringBuilder sb) {

		for (int i = 0; i < signsOfOneLine.size(); i++) {
			if (existsAValidCharacter(sb, signsOfOneLine, i)) {
				break;
			}
		}
		return AccountNumberValidator.isValid(sb.toString());
	}

	private boolean existsAValidCharacter(StringBuilder sb,
			List<ScannedSign> scannedSigns, int index) {
		for (AccountDigit digit : AccountDigit
				.valuesWithOneCharacterDifference(scannedSigns.get(index))) {

			String possibleAccountNumber = new StringBuffer(sb.toString())
					.replace(index, index + 1, digit.character()).toString();
			if (AccountNumberValidator.isValid(possibleAccountNumber)) {
				sb.replace(index, index + 1, digit.character());
				return true;
			}
		}
		return false;
	}
}
