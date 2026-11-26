package entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClassificationTest {

    @Test
    void testClassificationAttributes() {
        Classification classification = new Classification(
                "QA75.5-76.95",
                "QA",
                75.5,
                76.95,
                "Electronic computers. Computer science",
                "QA71-90",
                false,
                false
        );

        assertEquals("QA", classification.getPrefix());
        assertEquals(75.5, classification.getStart());
        assertEquals(76.95, classification.getEnd());
        assertEquals("Electronic computers. Computer science",
                classification.getSubject());
    }

    @Test
    void testNumberWithinRange() {
        Classification classification = new Classification(
                "QA75.5-76.95", "QA", 75.5, 76.95,
                "Electronic computers. Computer science",
                "QA71-90", false, false
        );

        assertTrue(classification.matches("QA", 76.73));
        assertTrue(classification.matches("QA", 75.5));
        assertTrue(classification.matches("QA", 76.95));
    }

    @Test
    void testNumberOutsideRange() {
        Classification classification = new Classification(
                "QA75.5-76.95", "QA", 75.5, 76.95,
                "Electronic computers. Computer science",
                "QA71-90", false, false
        );

        assertFalse(classification.matches("QA", 77));
        assertFalse(classification.matches("QB", 76.73));
        assertFalse(classification.matches("QA", 75));
    }
}
