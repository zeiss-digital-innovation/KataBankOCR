package de.saxsys.dojo.bankocr;

public class AccountNumberValidator {

	private static final int MAGIC_VALIDATION_NUMBER = 11;

	public static boolean isValid(String accountNo) {

		if (accountNo.contains("?")) {
			return false;
		}

		int checksum = 0;
		char[] digits = accountNo.toCharArray();
		for (int i = 0; i < digits.length; i++) {
			checksum += getIntValueAtIndex(digits, i) * (digits.length - i);
		}
		return 0 == (checksum % MAGIC_VALIDATION_NUMBER);
	}

	private static int getIntValueAtIndex(char[] digits, int index) {
		return Integer.parseInt(String.valueOf(digits[index]));
	}
}
