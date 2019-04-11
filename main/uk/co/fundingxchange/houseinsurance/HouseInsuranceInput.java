package uk.co.fundingxchange.houseinsurance;

public class HouseInsuranceInput {
	private static final long serialVersionUID = 4662015258120026547L;
	
	private String postcode;
	private int numBedrooms;
	private boolean hasThatchedRoof;
	private boolean valid;
	private String inputStr;

	public HouseInsuranceInput(String postcode, int numBedrooms,
			boolean hasThatchedRoof, boolean valid, String inputStr) {
		this.postcode = postcode;
		this.numBedrooms = numBedrooms;
		this.hasThatchedRoof = hasThatchedRoof;
		this.valid = valid;
		this.inputStr = inputStr;
	}

	public String getPostcode() {
		return postcode;
	}
	
	public int getNumBedrooms() {
		return numBedrooms;
	}
	
	public boolean isHasThatchedRoof() {
		return hasThatchedRoof;
	}
	
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	public void setNumBedrooms(int numBedrooms) {
		this.numBedrooms = numBedrooms;
	}
	
	public void setHasThatchedRoof(boolean hasThatchedRoof) {
		this.hasThatchedRoof = hasThatchedRoof;
	}		

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getInputStr() {
		return inputStr;
	}

	public void setInputStr(String inputStr) {
		this.inputStr = inputStr;
	}

	@Override
	public String toString() {
		return "HouseInsuranceInput [postcode=" + postcode + ", numBedrooms="
				+ numBedrooms + ", hasThatchedRoof=" + hasThatchedRoof
				+ ", valid=" + valid + ", inputStr=" + inputStr + "]";
	}

}
