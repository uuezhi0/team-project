package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

public class CallNumberTest {

    @Test
  void parsesFullCallNumber() {
    CallNumber cn = new CallNumber("PN6728 .P4 S245515 2013");
    assertEquals("PN", cn.getClassLetters());
    assertEquals(6728.0, cn.getClassNumber());
    assertTrue(cn.hasClassNumber());
    assertEquals(List.of("P4", "S245515"), cn.getCutters());
    assertEquals("2013", cn.getRemainder());
  }

  @Test
  void parsesDecimalClassNumberAndCutterWithoutSpace() {
    CallNumber cn = new CallNumber("QA76.73.J38 2018");
    assertEquals("QA", cn.getClassLetters());
    assertEquals(76.73, cn.getClassNumber());
    assertEquals(List.of("J38"), cn.getCutters());
    assertEquals("2018", cn.getRemainder());
  }

  @Test
  void parsesSpaceBetweenLettersAndNumber() {
    CallNumber cn = new CallNumber("QA 76 .J38");
    assertEquals("QA", cn.getClassLetters());
    assertEquals(76.0, cn.getClassNumber());
    assertEquals(List.of("J38"), cn.getCutters());
    assertEquals("", cn.getRemainder());
  }

  @Test
  void parsesClassOnlyValueFromFloorData() {
    CallNumber cn = new CallNumber("DA");
    assertEquals("DA", cn.getClassLetters());
    assertFalse(cn.hasClassNumber());
    assertEquals(0.0, cn.getClassNumber());
    assertTrue(cn.getCutters().isEmpty());
  }

  @Test
  void cleansUpLowercaseAndExtraSpaces() {
    CallNumber cn = new CallNumber("  ps3537   ");
    assertEquals("PS3537", cn.getCode());
    assertEquals("PS", cn.getClassLetters());
    assertEquals(3537.0, cn.getClassNumber());
  }

  @Test
  void getCuttersReturnsCopy() {
    CallNumber cn = new CallNumber("PN6728 .P4 S245515 2013");
    cn.getCutters().clear();
    assertEquals(2, cn.getCutters().size());
  }

  @Test
  void rejectsNull() {
    assertThrows(IllegalArgumentException.class, () -> new CallNumber(null));
  }

  @Test
  void rejectsBlank() {
    assertThrows(IllegalArgumentException.class, () -> new CallNumber("   "));
  }

  @Test
  void rejectsTextWithoutClassLetters() {
    assertThrows(IllegalArgumentException.class, () -> new CallNumber("123"));
  }

}
