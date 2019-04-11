package uk.co.fundingxchange.util;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Test;

import uk.co.fundingxchange.houseinsurance.HouseInsuranceInput;

public class HouseInsuranceInputProcessorTest {
	
	@Test
    public void testConvertCsvToHouseInsuranceInputList() {
		String csvFileName = "/resources/test-input.txt";
		csvFileName = csvFileName.replaceAll("/", File.separator);
        List<HouseInsuranceInput> inputList = HouseInsuranceInputProcessor.convertCsvToHouseInsuranceInputList(csvFileName);
        
        assertEquals(inputList.size(), 17);
        assertTrue(inputList.get(0).isValid());
        assertFalse(inputList.get(11).isValid());
        assertFalse(inputList.get(13).isValid());
        assertFalse(inputList.get(14).isValid());
        assertEquals(inputList.get(16).getPostcode(), "EC1A 1DD");
    }
	
	@Test
    public void testValidateAndConvertToHouseInsuranceInput() {
		HouseInsuranceInput input = HouseInsuranceInputProcessor.validateAndConvertToHouseInsuranceInput("W1A 0AX", 3, true);
        assertTrue(input.isValid());
        assertEquals(input.getPostcode(), "W1A 0AX");
        assertEquals(input.getNumBedrooms(), 3);
        assertTrue(input.isHasThatchedRoof());
        assertTrue(input.getInputStr().contains("W1A 0AX"));
    }

}
