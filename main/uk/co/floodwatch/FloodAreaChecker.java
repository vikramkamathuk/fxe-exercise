package uk.co.floodwatch;

/**
 * This class encapsulates the checking of known Flood risk areas.
 */
public interface FloodAreaChecker {
  /**
   * Check the postcode and assess the flood risk for that area.
   *
   * @param postcode the postcode to assess the flood risk for
   * @return FloodRisk enumerating the perceived flood risk for the given postcode
   * @throws FloodAreaCheckerTechnicalFailureException if any technical fault occurs
   * @throws PostcodeNotFoundException if the postcode is unknown
   */
	
   public FloodRisk evaluateFloodRisk(String postcode) throws PostcodeNotFoundException, FloodAreaCheckerTechnicalFailureException;
}
