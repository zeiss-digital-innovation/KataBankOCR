package de.saxsys.dojo.bankocr;

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
	UNKNOWN("?", "", "", "");

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

	public static AccountDigit value(ScannedSign sign) {

		for (AccountDigit d : AccountDigit.values()) {
			if ((d.firstLine + d.secondLine + d.thirdLine).equals( //
					sign.asString())) {
				return d;
			}
		}
		return UNKNOWN;
	}

	public String character() {
		return character;
	}

}