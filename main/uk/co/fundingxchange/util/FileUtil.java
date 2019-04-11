package uk.co.fundingxchange.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtil {
	private static final long serialVersionUID = 64751525812008465L;
	
	/**
	   * Get the record from file as list of strings.
	   *
	   * @param line each line of the file
	   * @param delimiter, e.g. ","
	   * @return house insurance input record record as list of strings
	   */
	public static List<String> getRecordFromLine(String line, String delimiter) {
	    List<String> values = new ArrayList<String>();
	    try (Scanner rowScanner = new Scanner(line)) {
	        rowScanner.useDelimiter(delimiter);
	        while (rowScanner.hasNext()) {
	            values.add(rowScanner.next().trim());
	        }
	    }
	    return values;
	}
	
	/**
	   * Read the given file in classpath.
	   *
	   * @param fileName in the classpath
	   * @return File the file
	   */
	public static File readFileFromClasspath(String fileName)
    {
		URL path = FileUtil.class.getResource(fileName);
		return new File(path.getFile());
    }
	
	/**
	   * Detects if the given string is a positive integer.
	   *
	   * @param str the given string
	   * @return true if it is a positive integer, false otherwise
	   */
	public static boolean isPositiveInteger(String str) {
		return str.chars().allMatch( Character::isDigit );
		// TODO: If not Java 8 or later, rewrite the above code
	}
	
	/**
	   * Detects if the given string is a a bit ("0" or "1").
	   *
	   * @param str the given string
	   * @return true if it is "0" or "1", false otherwise
	   */
	public static boolean isBit(String str) {
		return str.equals("0") || str.equals("1");
	}
}
