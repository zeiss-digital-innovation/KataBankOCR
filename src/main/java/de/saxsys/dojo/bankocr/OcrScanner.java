package de.saxsys.dojo.bankocr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OcrScanner {

	private final static Pattern THREE_CHARS = Pattern.compile("[ |_]{3}");;

	public Iterable<String> read(Reader reader) {
		List<String> accountNumberList = new ArrayList<String>();

		// TODO BufferedReader Ressourcen freigeben und Fehler behandeln
		BufferedReader bufferedReader = new BufferedReader(reader);

		List<String> digitsOfOneLine = null;
		while (!(digitsOfOneLine = getDigitsOfOneLine(bufferedReader))
				.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (String digit : digitsOfOneLine) {
				sb.append(Digit.value(digit).intValue());
			}
			accountNumberList.add(sb.toString());
		}
		return accountNumberList;
	}

	private List<String> getDigitsOfOneLine(BufferedReader bufferedReader) {

		String firstLine = getNextLine(bufferedReader);
		if (null == firstLine || firstLine.isEmpty()) {
			return Collections.emptyList();
		}

		List<String> threeCharactersPartsOfLineOne = getThreeCharacterParts(firstLine);
		List<String> threeCharactersPartsOfLineTwo = getThreeCharacterParts(getNextLine(bufferedReader));
		List<String> threeCharactersPartsOfLineThree = getThreeCharacterParts(getNextLine(bufferedReader));
		getNextLine(bufferedReader);

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
