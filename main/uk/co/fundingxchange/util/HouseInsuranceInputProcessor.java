package uk.co.fundingxchange.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import uk.co.fundingxchange.houseinsurance.HouseInsuranceInput;

public class HouseInsuranceInputProcessor {
	private static final long serialVersionUID = 789015258120026475L;
	
	/**
	   * Converts a CSV file to list of HouseInsuranceInput.
	   *
	   * @param csvFileName the csv file name in classpath
	   * @return list of HouseInsuranceInput
	   */
	public static List<HouseInsuranceInput> convertCsvToHouseInsuranceInputList(String csvFileName) {		
		List<HouseInsuranceInput> inputList = new ArrayList<>();
		File csvFile = FileUtil.readFileFromClasspath(csvFileName);
		try (Scanner scanner = new Scanner(csvFile);) {
		    while (scanner.hasNextLine()) {
		        List<String> record = FileUtil.getRecordFromLine(scanner.nextLine(), ",");
		        inputList.add(HouseInsuranceInputProcessor.convertRecordToHouseInsuranceInput(record));		      
		    }
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File '" + csvFileName + "' not found!");
		}
		
		return inputList;
	}
	
	/**
	   * Converts a house insurance input record (of strings) to HouseInsuranceInput.
	   *
	   * @param house insurance input record (of strings)
	   * @return HouseInsuranceInput
	   */
	private static HouseInsuranceInput convertRecordToHouseInsuranceInput(List<String> record) {
		HouseInsuranceInput houseInsuranceInput = null;
        String postcodeStr = "";
        String numBedroomsStr = "";
        String hasThatchedRoofStr = "";
        boolean valid = false;
        int countTokens = record.size();
        
        if (countTokens >= 1) {
        	postcodeStr = record.get(0);
        }
        if (countTokens >= 2) {
        	numBedroomsStr = record.get(1);
        }
        if (countTokens >= 3) {
        	hasThatchedRoofStr = record.get(2);
        }
        
        if (record.size() < 3) {		        	
        	// System.out.println("All three inputs - postcode, numBedrooms and hasThatchedRoof are required in " + record);
        } else if (postcodeStr.length() == 0 ||
        		numBedroomsStr.length() == 0 ||
        				hasThatchedRoofStr.length() == 0) {
        	// System.out.println("Invalid input i.e. blank string in " + record);
        } else if (!FileUtil.isPositiveInteger(numBedroomsStr)) {
        	// System.out.println("Invalid input for numBedrooms in " + record);
        } else if (!FileUtil.isBit(hasThatchedRoofStr)) {
        	//System.out.println("Invalid input for hasThatchedRoof in " + record 
        			// + ". Allowed values are 0 and 1. 1 -> true. 0 -> false.");
        } else {		    
        	valid = true;
        	int numBedrooms = Integer.valueOf(numBedroomsStr);
        	boolean hasThatchedRoof = hasThatchedRoofStr.equals("0") ? false : true;
        	houseInsuranceInput = new HouseInsuranceInput(postcodeStr, numBedrooms, hasThatchedRoof, valid, record.toString());
        }
        
        if (!valid) {
        	houseInsuranceInput = new HouseInsuranceInput("", -1, false, false, record.toString());
        }
        
        return houseInsuranceInput;
	}
	
	/**
	   * validate house insurance input fields and return HouseInsuranceInput.
	   *
	   * @param postcode (String) postcode
	   * @param numBedrooms (int) number of bed rooms
	   * @param hasThatchedRoof (boolean) whether it has hatched roof
	   * @return HouseInsuranceInput
	   */
	public static HouseInsuranceInput validateAndConvertToHouseInsuranceInput(String postcode, int numBedrooms, boolean hasThatchedRoof) {
		String input = "[" + postcode + ", " + numBedrooms + ", " + hasThatchedRoof + "]";
		boolean valid = false;
         if (postcode.length() == 0 ||
        		numBedrooms < 0) {
        	// System.out.println("Invalid input " + input);
        } else {		    
        	valid = true;            	
        }
        
        return new HouseInsuranceInput(postcode, numBedrooms, hasThatchedRoof, valid, input); 
	}
}
