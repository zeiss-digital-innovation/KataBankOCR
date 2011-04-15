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
							sb, signsOfOneLine));
		}
		return accountNumberList;
	}

	private String getEvaluatedAccountNumberResult(StringBuilder sb,
			List<ScannedSign> signsOfOneLine) {
		if (sb.toString().contains("?")) {
			sb.append(" ILL");
		} else if (!AccountNumberValidator.isValid(sb.toString())) {
			if (!findAValidFirstNumber(signsOfOneLine, sb, 0)) {
				sb.append(" ERR");
			}
		}
		return sb.toString();
	}

	private boolean findAValidFirstNumber(List<ScannedSign> signsOfOneLine,
			StringBuilder sb, int index) {
		ScannedSign sign = signsOfOneLine.get(0);
		if (AccountDigit.value(sign) == AccountDigit.ONE) {
			replace(sb, signsOfOneLine, 0);
			return AccountNumberValidator.isValid(sb.toString());
		}
		sign = signsOfOneLine.get(6);
		if (AccountDigit.value(sign) == AccountDigit.SEVEN) {
			replace(sb, signsOfOneLine, 6);
			return AccountNumberValidator.isValid(sb.toString());
		}
		sign = signsOfOneLine.get(3);
		if (AccountDigit.value(sign) == AccountDigit.ZERO) {
			replace(sb, signsOfOneLine, 3);
			return AccountNumberValidator.isValid(sb.toString());
		}
		return AccountNumberValidator.isValid(sb.toString());
	}

	private void replace(StringBuilder sb, List<ScannedSign> scannedSigns,
			int index) {
		for (AccountDigit digit : AccountDigit.values()) {

			if (onlyOneCharacterIsMissing(scannedSigns.get(index),
					digit.asString()))
				sb.replace(index, index + 1, digit.character());
			if (AccountNumberValidator.isValid(sb.toString())) {
				break;
			}
		}
	}

	private boolean onlyOneCharacterIsMissing(ScannedSign scannedSign,
			String asString) {
		int errors = 0;
		for (int i = 0; i < scannedSign.asString().length(); i++) {
			char charAt = asString.charAt(i);
			errors += (scannedSign.asString().charAt(i) != charAt) ? 1 : 0;
		}

		return 1 == errors;
	}
}
