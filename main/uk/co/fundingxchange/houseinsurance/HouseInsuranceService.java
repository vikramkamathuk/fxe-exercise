package uk.co.fundingxchange.houseinsurance;

public interface HouseInsuranceService {
	
  // changing the return type to Double
  // public int getCalculatedPremium(String postcode, int numBedrooms, boolean hasThatchedRoof);
	
  public Double getCalculatedPremium(String postcode, int numBedrooms, boolean hasThatchedRoof) throws HouseInsuranceServiceTechnicalFailureException;
}