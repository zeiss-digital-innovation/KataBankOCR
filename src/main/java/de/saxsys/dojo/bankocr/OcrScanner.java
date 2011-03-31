package de.saxsys.dojo.bankocr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OcrScanner {

	private final static Pattern THREE_CHARS = Pattern.compile("[ |_]{3}");;

	public Iterable<String> read(Reader reader) {
		StringBuilder sb = new StringBuilder();
	
		// TODO BufferedReader Ressourcen freigeben und Fehler behandeln
		for (String digit : getDigits(new BufferedReader(reader))) {
			sb.append(Digit.value(digit).intValue());
		}

		return Arrays.asList(sb.toString());
	}

	private List<String> getDigits(BufferedReader bufferedReader) {

		List<String> threeCharactersPartsOfLineOne = getThreeCharacterParts(getNextLine(bufferedReader));
		List<String> threeCharactersPartsOfLineTwo = getThreeCharacterParts(getNextLine(bufferedReader));
		List<String> threeCharactersPartsOfLineThree = getThreeCharacterParts(getNextLine(bufferedReader));

		List<String> digitList = new ArrayList<String>();
		for (int i = 0; i < 9; i++) {
			digitList.add(threeCharactersPartsOfLineOne.get(i)
					+ threeCharactersPartsOfLineTwo.get(i)
					+ threeCharactersPartsOfLineThree.get(i));
		}
		return digitList;
	}

	private List<String> getThreeCharacterParts(String nextLine) {
		final Matcher m = THREE_CHARS.matcher(nextLine);
		final List<String> threeCharParts = new ArrayList<String>();
		while (m.find()) {
			threeCharParts.add(m.group());
		}
		return threeCharParts;
	}

	private String getNextLine(BufferedReader bufferedReader) {
		String nextLine = "";
		try {
			nextLine = bufferedReader.readLine();
		} catch (IOException e) {
		}
		return nextLine;
	}

}
