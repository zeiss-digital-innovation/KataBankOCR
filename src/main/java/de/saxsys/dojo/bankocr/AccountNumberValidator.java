package de.saxsys.dojo.bankocr;

public class AccountNumberValidator {

	public boolean isValid(String accountNo) {

		int checksum = 0;
		char[] digits = accountNo.toCharArray();
		checksum += Integer.parseInt(String.valueOf(digits[8])) * 1;
		checksum += Integer.parseInt(String.valueOf(digits[7])) * 2;
		checksum += Integer.parseInt(String.valueOf(digits[6])) * 3;

		return 0 == (checksum % 11);
	}
}
