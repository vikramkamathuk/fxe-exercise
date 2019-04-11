package uk.co.fundingxchange.houseinsurance;

import java.io.File;
import java.util.List;

import uk.co.fundingxchange.util.HouseInsuranceInputProcessor;

public class Main {
	public static void main(String[] args) {
		Main.houseInsuranceDemo();
	}
	
	private static void houseInsuranceDemo() {
		HouseInsuranceService houseInsuranceService = new HouseInsuranceServiceImpl();
		
		// Read the CSV file that contains 17 house insurance inputs
		// The inputs cover different cases for purpose of this demo
		String csvFileName = "/resources/csv-input.txt";
		csvFileName = csvFileName.replaceAll("/", File.separator);
		List<HouseInsuranceInput> inputs = HouseInsuranceInputProcessor.convertCsvToHouseInsuranceInputList(csvFileName);
		
		System.out.println("\n ================== House Insurance Demo ==================");
		
		System.out.println("For the demo purpose, a postcode containing: \n"
				+ "'SS' --> represents subsidence area \n"
				+ "'HR' --> represents High Risk flood area \n"
				+ "'MR' --> represents Medium Risk flood area \n");
		
		// Display the output in a tabular format
		// Postcode			# bedrooms			Has Thatched roof			Expected Output
		System.out.printf("%20s %20s %20s %33s %n", "Postcode", "# Bed Rooms", "Has Thatched Roof", "Output");
		System.out.printf("%20s %20s %20s %33s %n", "--------", "-----------", "-----------------", "------");
		for (HouseInsuranceInput input : inputs) {
			if (!input.isValid()) {
				System.out.printf("%20s %20s %20s %33s %n", input.getInputStr(), "Invalid input", "Invalid input", "Insurance is not available");
				continue;
			}
			String postcode = input.getPostcode();
			int numBedRooms = input.getNumBedrooms();
			boolean hasThatchedRoof =  input.isHasThatchedRoof();
			Double calculatedPremium = houseInsuranceService.getCalculatedPremium(postcode, numBedRooms, hasThatchedRoof);
			String premiumStr = (calculatedPremium != null) ? String.format("%.2f", calculatedPremium) : "";
			System.out.printf("%20s %20d %20s %33s %n", postcode, numBedRooms, (hasThatchedRoof ? "Yes" : "No"),  
					(calculatedPremium == null ? "Insurance is not available" : "Premium " + premiumStr));
		}
		
		System.out.println("\nNote: To test your own case, open 'csv-input.txt' under 'resources' folder and edit accordingly");
	}
}
