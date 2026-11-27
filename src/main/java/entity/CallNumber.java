package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A Library of Congress call number, split into its parts. For example.
 * PN6728 .P4 S245515 2013" has class letters "PN", class number 6728,
 * cutters ["P4", "S245515"] and remainder "2013"
 */

public class CallNumber {
  // Matches the start of a call number
  //  ([A-Z]{1,3})        class letters, eg "PN"
  //  \s*                 optional space, e.g. "PN 6728" or "PN6728"
  //  (\d+(\.\d+)?)?      optional class number, e.g. "76" or "76.73"
  //  (.*)                everything after, parsed below as cutters

  private static final Pattern CLASS_PART =
          Pattern.compile("^([A-Z]{1,3})\\s*(\\d+(\\.\\d+)?)?(.*)$");

  // Matches one cutter at the front of the text
  //   \.?\s*              optional dot and space, e.g."P4" or " P4"
  //   ([A-Z])             the cutter letter
  //   (\d+)               the cutter digits
  //   (.*)                everything after, checked for another cutter

  private static final Pattern CUTTER =
          Pattern.compile("^\\.?\\s*([A-Z])(\\d+)(.*)$");
  // LC call numbers have at most three cutters.
  private static final int MAX_CUTTERS = 3;

  private final String code;             // "PN6728 .P4 S245515 2013"
  private final String classLetters;     // "PN"
  private final double classNumber;      // 6728
  private final boolean hasClassNumber;  // false for class-only values like "DA"
  private final List<String> cutters;            // ["P4", "S245515"]
  private final String remainder;        // "2013"

  /**
   * Parses a call number into its part
   *
   * @param text the call number, e.g. ""PN6728 .P4 S245515 2013" or "DA"
   * @throws IllegalArgumentException if text is missing or not a valid call number
   */


  public CallNumber(String text) {
    // Step 1: reject null or blank input
    if (text == null || text.trim().isEmpty()) {
      throw new IllegalArgumentException("Call number is missing.");
    }

    // Step 2: clean up the text. Trim the ends, make it uppercase,
    // and collapse repeated spaces into one
    final String normalized = text.trim().toUpperCase().replaceAll("\\s+", " ");

    // Step 3: match the class letters and number; reject anything else
    final Matcher classMatch = CLASS_PART.matcher(normalized);
    if (!classMatch.matches()) {
      throw new IllegalArgumentException(
              "Not a valid call number: " + text + " (example: PN6728 .P4 S245515 2013)");
    }
    this.code = normalized;

    // Step 4: save the class letters
    this.classLetters = classMatch.group(1);

    // Step 5: turn the class number into a double. Class-only values like "DA" have no number so use 0
    this.hasClassNumber = classMatch.group(2) != null;
    if (hasClassNumber) {
      this.classNumber = Double.parseDouble(classMatch.group(2));
    } else {
      this.classNumber = 0;
    }

    // Step 6: take the cutters off the front of the rest, one at a time, until there are none left
    // ( or we reach the maximum )
    this.cutters = new ArrayList<>();
    String rest = classMatch.group(4).trim();
    Matcher cutterMatch = CUTTER.matcher(rest);
    while (cutters.size() < MAX_CUTTERS && cutterMatch.matches()) {
      cutters.add (cutterMatch.group(1) + cutterMatch.group(2));
      rest = cutterMatch.group(3).trim();
      cutterMatch = CUTTER.matcher(rest);
    }

    // Step 7; whatever is left (year, volume, copy is the remainder
    this.remainder = rest;

  }

  /**
   * Returns the cleaned up call number
   *
   * @return the call number in uppercase with single spaces
   *
   */
  public String getCode() {
    return code;
  }

  /**
   * Returns the class letters
   *
   * @return the class letters, e.g. "PN"
   */
  public String getClassLetters () {
    return classLetters;
  }

  /**
   * Returns the class number, or 0 if the call number has none
   *
   * @return the class e.g 6728
   */
  public double getClassNumber() {
    return classNumber;
  }

  /**
   * Return whether this call number has a class number
   *
   * @return false for class-only class numbers like "DA" or "FC"
   */
  public boolean hasClassNumber() {
    return hasClassNumber;
  }

  /**
   * Returns a copy of the cutters, so callers can't change this call number
   *
   * @return the cutters in order e.g ["P4", "S245515"]
   *
   */
  public List<String> getCutters() {
    return new ArrayList<>(cutters);
  }

  /**
   * Returns whatever follows the cutters such as a year or volume
   *
   * @return the remainder, or an empty string if there is none
   */
  public String getRemainder() {
    return remainder;
  }

  @Override
  public String toString(){
    return code;
  }
}
