package de.saxsys.dojo.bankocr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountFileReader {

	private final static Pattern THREE_CHARS = Pattern.compile("[ |_]{3}");;

	private final BufferedReader bufferedReader;

	public AccountFileReader(File accountFile) {
		try {
			bufferedReader = new BufferedReader(new FileReader(accountFile));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("some trouble", e);
		}
	}

	public List<ScannedSign> getDigitsOfOneLine() {

		String firstLine = getNextLine();
		if (firstLine.isEmpty()) {
			return cleanUpAndReturnEmptyList();
		}

		List<String> threeCharactersPartsOfLineOne = getThreeCharacterParts(firstLine);
		List<String> threeCharactersPartsOfLineTwo = getThreeCharacterParts(getNextLine());
		List<String> threeCharactersPartsOfLineThree = getThreeCharacterParts(getNextLine());
		getNextLine();

		List<ScannedSign> digitList = new ArrayList<ScannedSign>();
		for (int i = 0; i < 9; i++) {
			digitList.add(new ScannedSign( //
					threeCharactersPartsOfLineOne.get(i), //
					threeCharactersPartsOfLineTwo.get(i), //
					threeCharactersPartsOfLineThree.get(i)));
		}
		return digitList;
	}

	private List<ScannedSign> cleanUpAndReturnEmptyList() {
		if (null != bufferedReader)
			try {
				bufferedReader.close();
			} catch (IOException e) {
				throw new RuntimeException("some trouble", e);
			}
		return Collections.emptyList();
	}

	private List<String> getThreeCharacterParts(String nextLine) {
		final List<String> threeCharParts = new ArrayList<String>();
		final Matcher m = THREE_CHARS.matcher(nextLine);
		while (m.find()) {
			threeCharParts.add(m.group());
		}
		return threeCharParts;
	}

	private String getNextLine() {

		try {
			if (null != bufferedReader) {
				String line = bufferedReader.readLine();
				return (null == line) ? "" : line;
			}
		} catch (IOException e) {
			throw new RuntimeException("some trouble", e);
		}
		return "";
	}
}
