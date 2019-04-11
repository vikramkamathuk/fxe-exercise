package uk.co.subsidencewatch;

/**
 * This class encapsulates the checking of known subsidence areas.
 */
public interface SubsidenceAreaChecker {
  /**
   * Check the postcode against areas of known high subsidence risk.
   *
   * @param postcode the postcode to check against known subsidence areas
   * @return True if the postcode is in a known subsidence area, otherwise false. If the postcode is
   *         unknown then null is returned
   * @throws SubsidenceAreaCheckerTechnicalFailureException if any technical fault occurs
   */
  public Boolean isPostcodeInSubsidenceArea(String postcode) throws SubsidenceAreaCheckerTechnicalFailureException;
}
