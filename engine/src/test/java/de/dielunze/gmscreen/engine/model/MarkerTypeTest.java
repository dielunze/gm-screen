package de.dielunze.gmscreen.engine.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MarkerTypeTest {

    @Test
    @DisplayName("holds name and cap")
    void holdsValues() {
        MarkerType blutrausch = new MarkerType("Blutrausch", 5);

        assertEquals("Blutrausch", blutrausch.name());
        assertEquals(5, blutrausch.maximum());
    }

    @Test
    @DisplayName("rejects a blank name")
    void rejectsBlankName() {
        assertThrows(IllegalArgumentException.class, () -> new MarkerType("", 5));
        assertThrows(IllegalArgumentException.class, () -> new MarkerType("   ", 5));
    }

    @Test
    @DisplayName("rejects a cap below 1")
    void rejectsUselessMaximum() {
        assertThrows(IllegalArgumentException.class, () -> new MarkerType("Blutrausch", 0));
        assertThrows(IllegalArgumentException.class, () -> new MarkerType("Blutrausch", -2));
    }
}
