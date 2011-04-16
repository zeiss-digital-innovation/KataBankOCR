package de.saxsys.dojo.bankocr;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Sebastian Schmeck
 */
enum AccountDigit {

	ZERO("0", " _ ", "| |", "|_|"), //
	ONE("1", "   ", "  |", "  |"), //
	TWO("2", " _ ", " _|", "|_ "), //
	THREE("3", " _ ", " _|", " _|"), //
	FOUR("4", "   ", "|_|", "  |"), //
	FIVE("5", " _ ", "|_ ", " _|"), //
	SIX("6", " _ ", "|_ ", "|_|"), //
	SEVEN("7", " _ ", "  |", "  |"), //
	EIGHT("8", " _ ", "|_|", "|_|"), //
	NINE("9", " _ ", "|_|", " _|"), //
	UNKNOWN("?", "   ", "   ", "   ");

	final String firstLine;
	final String secondLine;
	final String thirdLine;
	final String character;

	AccountDigit(String number, String firstLine, String secondLine,
			String thirdLine) {
		this.firstLine = firstLine;
		this.secondLine = secondLine;
		this.thirdLine = thirdLine;
		this.character = number;
	}

	public String character() {
		return character;
	}

	public String asString() {
		return firstLine + secondLine + thirdLine;
	}

	public static AccountDigit value(ScannedSign sign) {

		for (AccountDigit d : AccountDigit.values()) {
			if (d.asString().equals(sign.asString())) {
				return d;
			}
		}

		return UNKNOWN;
	}

	public static Collection<AccountDigit> valuesWithOneCharacterDifference(
			ScannedSign sign) {

		final Collection<AccountDigit> matchingDigits = new ArrayList<AccountDigit>();
		for (AccountDigit digit : AccountDigit.values()) {
			if (onlyOneCharacterIsWrong(sign, digit)) {
				matchingDigits.add(digit);
			}
		}

		return matchingDigits;
	}

	private static boolean onlyOneCharacterIsWrong(ScannedSign sign,
			AccountDigit digit) {

		final String signString = sign.asString();
		final String digitString = digit.asString();
		int errors = 0;
		for (int i = 0; i < signString.length(); i++) {
			errors += (signString.charAt(i) != digitString.charAt(i)) ? 1 : 0;
		}

		return 1 == errors;
	}
}