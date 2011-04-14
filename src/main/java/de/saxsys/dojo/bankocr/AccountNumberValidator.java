package de.saxsys.dojo.bankocr;

public class AccountNumberValidator {

	public boolean isValid(String accountNo) {

		int checksum = 0;
		char[] digits = accountNo.toCharArray();
		checksum += Integer.parseInt(String.valueOf(digits[8])) * 1;
		checksum += Integer.parseInt(String.valueOf(digits[7])) * 2;
		checksum += Integer.parseInt(String.valueOf(digits[6])) * 3;
		checksum += Integer.parseInt(String.valueOf(digits[5])) * 4;
		checksum += Integer.parseInt(String.valueOf(digits[4])) * 5;
		checksum += Integer.parseInt(String.valueOf(digits[3])) * 6;
		checksum += Integer.parseInt(String.valueOf(digits[2])) * 7;
		checksum += Integer.parseInt(String.valueOf(digits[1])) * 8;
		checksum += Integer.parseInt(String.valueOf(digits[0])) * 9;

		return 0 == (checksum % 11);
	}
}
