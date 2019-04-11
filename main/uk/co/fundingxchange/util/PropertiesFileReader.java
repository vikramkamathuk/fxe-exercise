package uk.co.fundingxchange.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileReader {
	
	private static ClassLoader objClassLoader = PropertiesFileReader.class.getClassLoader();
	private Properties commonProperties = new Properties();
	private String propertiesFilename; 
	
	public PropertiesFileReader(String propertiesFilename) {
		this.propertiesFilename = propertiesFilename;
	}
    
    public String readKey(String key) {
        if (propertiesFilename != null && !propertiesFilename.trim().isEmpty()
                && key != null && !key.trim().isEmpty()) {
            /* try-with-resource in JDK 1.7 or above */
            try(
                    FileInputStream objFileInputStream = new FileInputStream(objClassLoader.getResource(propertiesFilename).getFile());
               ){
                /* Load file into commonProperties */
                commonProperties.load(objFileInputStream);
                /* Get the value of key */
                return String.valueOf(commonProperties.get(key));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
    
    public int getInt(String key) {    	
    	String value = readKey(key);
    	String errorMessage = value + " is not a valid positive integer in properties file " + propertiesFilename + "for key " + key + "!";
    	try {
    		int valueInt = Integer.parseInt(value);
    		if (valueInt < 0) {
    			throw new RuntimeException(errorMessage);
    		}
    		return valueInt;
    	} catch(NumberFormatException nfe) {
    		throw new RuntimeException(errorMessage);
    	}
    }
    
    public double getDouble(String key) {    	
    	String value = readKey(key);
    	String errorMessage = value + " is not a valid positive value in properties file " + propertiesFilename + "for key " + key  + "!";
    	try {
    		double valueDouble = Double.parseDouble(value);
    		if (valueDouble < 0) {
    			throw new RuntimeException(errorMessage);
    		}
    		return valueDouble;
    	} catch(NumberFormatException nfe) {
    		throw new RuntimeException(errorMessage);
    	}
    }
}
