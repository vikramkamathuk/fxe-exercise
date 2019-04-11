package uk.co.fundingxchange.houseinsurance;

import static org.junit.Assert.*;

import org.junit.Test;

public class HouseInsuranceServiceImplTest {

	@Test
	public void testGetCalculatedPremium() {
		testInvalidInput();
		testNumBedRooms();
		testThatchedRoof();
		testSubsidenceArea();
		testHighFloodRiskArea();
		testMediumFloodRiskArea();
		testLowOrNoFloodRiskArea();
	}
	
	private void testInvalidInput() {
		HouseInsuranceService houseInsuranceService = new HouseInsuranceServiceImpl();
		Double premium = houseInsuranceService.getCalculatedPremium("IG4 8XF", -4, false);
		assertNull(premium);
	}
	
	private void testNumBedRooms() {
		HouseInsuranceService houseInsuranceService = new HouseInsuranceServiceImpl();
		// Houses having more than 4 bed rooms not to be insured
		Double premium = houseInsuranceService.getCalculatedPremium("IG4 8XF", 5, false);
		assertNull(premium);
	}
	
	private void testThatchedRoof() {		
		HouseInsuranceService houseInsuranceService = new HouseInsuranceServiceImpl();
		// expect null for hasThatchedRoof = true
		// represents not to be insured
		Double premium = houseInsuranceService.getCalculatedPremium("IG4 8XF", 4, true);
		assertNull(premium);
	}
	
	private void testSubsidenceArea() {
		HouseInsuranceService houseInsuranceService = new HouseInsuranceServiceImpl();
		// for the given demo, any postcode containing "SS" represents subsidence area
		Double premium = houseInsuranceService.getCalculatedPremium("IG4 8SS", 4, false);
		// expect null for subsidence area
		// // represents not to be insured
		assertNull(premium);
		premium = houseInsuranceService.getCalculatedPremium("EC1A 1BB", 4, false);
		// expect non-null for non-subsidence area without thatchedRoof
		assertNotNull(premium);
		// expect null for non-subsidence area with thatchedRoof
		premium = houseInsuranceService.getCalculatedPremium("EC1A 1BB", 4, true);
		assertNull(premium);
	}
	
	private void testHighFloodRiskArea() {
		HouseInsuranceService houseInsuranceService = new HouseInsuranceServiceImpl();
		// for the given demo, any postcode starting with "HR" represents High Risk flood area
		Double premium = houseInsuranceService.getCalculatedPremium("HR4 1BB", 3, false);
		assertNull(premium);
	}
	
	private void testMediumFloodRiskArea() {
		HouseInsuranceService houseInsuranceService = new HouseInsuranceServiceImpl();
		// for the given demo, any postcode starting with "MR" represents Medium Risk flood area
		Double premium = houseInsuranceService.getCalculatedPremium("MR4 1BB", 3, false);
		// expect a premium of 126.50
		assertEquals(126.50, premium, 0.001);
	}
	
	private void testLowOrNoFloodRiskArea() {
		HouseInsuranceService houseInsuranceService = new HouseInsuranceServiceImpl();
		// for the given demo, any postcode not starting with "HR" or "MR" represents Low or No Flood Risk area
		Double premium = houseInsuranceService.getCalculatedPremium("EC1A 1BB", 3, false);
		// expect a premium of 126.50
		assertEquals(110.00, premium, 0.001);
	}

}
