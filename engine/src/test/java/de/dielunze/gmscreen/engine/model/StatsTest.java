package de.dielunze.gmscreen.engine.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Guards the compact constructor. These pass already - they are a regression net. */
class StatsTest {

    @Test
    @DisplayName("holds the compendium line verbatim")
    void holdsValues() {
        Stats stats = new Stats(38, 6, 2, 6, 3);

        assertEquals(38, stats.maxHp());
        assertEquals(6, stats.maxAp());
        assertEquals(2, stats.apRegen());
        assertEquals(6, stats.attack());
        assertEquals(3, stats.defense());
    }

    @Test
    @DisplayName("two stat lines with the same numbers are equal")
    void valueSemantics() {
        assertEquals(new Stats(38, 6, 2, 6, 3), new Stats(38, 6, 2, 6, 3));
    }

    @Test
    @DisplayName("rejects impossible values")
    void rejectsNonsense() {
        assertThrows(IllegalArgumentException.class, () -> new Stats(0, 6, 2, 6, 3));
        assertThrows(IllegalArgumentException.class, () -> new Stats(38, 0, 2, 6, 3));
        assertThrows(IllegalArgumentException.class, () -> new Stats(38, 6, -1, 6, 3));
        assertThrows(IllegalArgumentException.class, () -> new Stats(38, 6, 2, -1, 3));
        assertThrows(IllegalArgumentException.class, () -> new Stats(38, 6, 2, 6, -1));
    }
}
