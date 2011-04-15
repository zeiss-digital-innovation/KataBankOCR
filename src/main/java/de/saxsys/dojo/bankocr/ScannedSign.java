package de.saxsys.dojo.bankocr;

public class ScannedSign {

	private final String firstLine;
	private final String secondLine;
	private final String thirdLine;

	public ScannedSign(String firstLine, String secondLine, String thirdLine) {
		this.firstLine = firstLine;
		this.secondLine = secondLine;
		this.thirdLine = thirdLine;
	}

	public String asString() {
		return firstLine + secondLine + thirdLine;
	}
}
