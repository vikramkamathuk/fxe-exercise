package uk.co.fundingxchange.houseinsurance;

import java.io.File;

import uk.co.floodwatch.FloodAreaChecker;
import uk.co.floodwatch.FloodAreaCheckerTechnicalFailureException;
import uk.co.floodwatch.FloodRisk;
import uk.co.floodwatch.PostcodeNotFoundException;
import uk.co.fundingxchange.util.HouseInsuranceInputProcessor;
import uk.co.fundingxchange.util.PropertiesFileReader;
import uk.co.subsidencewatch.SubsidenceAreaChecker;
import uk.co.subsidencewatch.SubsidenceAreaCheckerTechnicalFailureException;


public class HouseInsuranceServiceImpl implements HouseInsuranceService {

	private static PropertiesFileReader propertiesFile = new PropertiesFileReader("resources" + File.separator + "house-insurance.properties");
	
	/**
	   * Calculate the premium or return null if house is not to be insured.
	   *
	   * @param postcode String - the postcode to check against known subsidence areas flood risk
	   * @param numBedrooms int - number of bed rooms
	   * @param hasThatchedRoof boolean - whether or not there is thatched roof
	   * @return calculated premium or null if house is not to be insured 
	   */
	@Override
	public Double getCalculatedPremium(String postcode, int numBedrooms,
			boolean hasThatchedRoof) throws HouseInsuranceServiceTechnicalFailureException {
		
		HouseInsuranceInput input = HouseInsuranceInputProcessor.validateAndConvertToHouseInsuranceInput(postcode, numBedrooms, hasThatchedRoof);				
		
		if (!input.isValid() || input.isHasThatchedRoof())
			return null;
		
		if (updatePremiumByNumBedRooms(input) == null)
			return null; 
		
		// @TODO: Temporarily this Lambda expression is passed as a mock implementation of 'SubsidenceAreaChecker'
		// Edit this when real implementation class is available
		Double premium = updatePremiumBySubsidenceArea(input, 
				pc -> {
					if (pc == null || pc.isEmpty())
						return null;
					else if (pc.contains("SS")) 
						return true;
					else 
						return false;
				  });
		if (premium == null)
			return null;
		
		// @TODO: Temporarily this Lambda expression is passed as a mock implementation of 'FloodAreaChecker'
		// Edit this when implementation class is available
		premium = updatePremiumByFloodArea(input, 
				pc -> {
					if (pc == null || pc.isEmpty() || pc.contains("HR")) {
						return FloodRisk.HIGH_RISK;
					} else if(pc.startsWith("MR")) {
						return FloodRisk.MEDIUM_RISK;
					} else 
						return FloodRisk.LOW_RISK;
				});
		
		return premium;
	}		
	
	/**
	   * Calculate the premium based on number of bed rooms or return null if house is not to be insured.
	   *
	   * @param input HouseInsuranceInput
	   * @return calculated premium or null if house is not to be insured 
	   */
	private Double updatePremiumByNumBedRooms(HouseInsuranceInput input) {
		int numBedRooms = input.getNumBedrooms();
		int bedRoomsUpperLimit = propertiesFile.getInt("bed.rooms.upper.limit");
		if (numBedRooms > bedRoomsUpperLimit || numBedRooms < 0) 
			return null; 
		else 
			// Currently numBedRooms otherwise has no impact on premium
			return 0.0; 
	}
	
	/**
	   * Calculate the premium based subsidence area or return null if house is not to be insured.
	   *
	   * @param input HouseInsuranceInput
	   * @param subsidenceAreaChecker SubsidenceAreaChecker
	   * @return calculated premium or null if house is not to be insured 
	   */
	private Double updatePremiumBySubsidenceArea(HouseInsuranceInput input, SubsidenceAreaChecker subsidenceAreaChecker) {
		String postcode = input.getPostcode();
		try {						
			Boolean inSubsidenceArea = subsidenceAreaChecker.isPostcodeInSubsidenceArea(postcode);
			// if post code not found (i.e. inSubsidenceArea == null), return null
			// stands for 'not to be insured'
			if (inSubsidenceArea == null || inSubsidenceArea) 
				return null;
			else 
				return 0.0;							
		} catch (SubsidenceAreaCheckerTechnicalFailureException ste) {
			throw new RuntimeException("Technical Failure while checking for subsidence area for the postcode " + postcode);
		}
	}
	
	/**
	   * Calculate the premium based flood area or return null if house is not to be insured.
	   *
	   * @param input HouseInsuranceInput
	   * @param floodAreaChecker FloodAreaChecker
	   * @return calculated premium or null if house is not to be insured 
	   */
	private Double updatePremiumByFloodArea(HouseInsuranceInput input, FloodAreaChecker floodAreaChecker) {
		Double premium = null; 
		String postcode = input.getPostcode();		
		double baseAnnualPremium = propertiesFile.getDouble("base.annual.premium");	
		double extraPercentagePremium = propertiesFile.getDouble("extra.percentage.premium");
		try {
			 FloodRisk floodRisk = floodAreaChecker.evaluateFloodRisk(postcode);
			 switch (floodRisk) {
			 	case NO_RISK: 
			 	case LOW_RISK: premium = baseAnnualPremium; break;
			 	case MEDIUM_RISK: premium = baseAnnualPremium * (1 + extraPercentagePremium / 100); break;
			 	case HIGH_RISK: 
			 	default: premium = null;
			 }
			 return premium;
		} catch(PostcodeNotFoundException pcne) {
			throw new RuntimeException("Postcode " + postcode + "not found!");
		} catch (FloodAreaCheckerTechnicalFailureException fcte) {
			 throw new RuntimeException("Technical Failure while checking for flood area for the postcode " + postcode);
		}
	}
}
