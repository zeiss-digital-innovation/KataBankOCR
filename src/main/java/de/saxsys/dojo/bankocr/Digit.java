package de.saxsys.dojo.bankocr;

enum Digit {

	ZERO(0, " _ ", "| |", "|_|"), //
	ONE(1, "   ", "  |", "  |"), //
	TWO(2, " _ ", " _|", "|_ "), //
	THREE(3, " _ ", " _|", " _|"), //
	FOUR(4, "   ", "|_|", "  |"), //
	FIVE(5, " _ ", "|_ ", " _|"), //
	SIX(6, " _ ", "|_ ", "|_|"), //
	SEVEN(7, " _ ", "  |", "  |"), //
	EIGHT(8, " _ ", "|_|", "|_|"), //
	NINE(9, " _ ", "|_|", " _|");

	final String firstLine;
	final String secondLine;
	final String thirdLine;
	final int number;

	Digit(int number, String firstLine, String secondLine, String thirdLine) {
		this.firstLine = firstLine;
		this.secondLine = secondLine;
		this.thirdLine = thirdLine;
		this.number = number;
	}

	public static Digit value(String digit) {

		for (Digit d : Digit.values()) {
			if ((d.firstLine + d.secondLine + d.thirdLine).equals(digit)) {
				return d;
			}
		}
		return null;
	}

	public int intValue() {
		return number;
	}

}