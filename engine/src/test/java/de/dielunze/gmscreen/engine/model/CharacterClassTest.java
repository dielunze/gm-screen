package de.dielunze.gmscreen.engine.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CharacterClassTest {

    private static Stats stats() {
        return new Stats(38, 6, 2, 6, 3);
    }

    private static MarkerType marker() {
        return new MarkerType("Blutrausch", 5);
    }

    @Test
    @DisplayName("holds the compendium header of a hero page")
    void holdsValues() {
        CharacterClass merc = new CharacterClass("Mercenary Fighter", "Berserker", stats(), marker());

        assertEquals("Mercenary Fighter", merc.name());
        assertEquals("Berserker", merc.role());
        assertEquals(38, merc.baseStats().maxHp());
        assertEquals(5, merc.marker().maximum());
    }

    @Test
    @DisplayName("rejects blank text")
    void rejectsBlankText() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterClass(" ", "Berserker", stats(), marker()));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterClass("Mercenary Fighter", "", stats(), marker()));
    }

    @Test
    @DisplayName("rejects missing objects")
    void rejectsNulls() {
        assertThrows(NullPointerException.class,
                () -> new CharacterClass("Mercenary Fighter", "Berserker", null, marker()));
        assertThrows(NullPointerException.class,
                () -> new CharacterClass("Mercenary Fighter", "Berserker", stats(), null));
    }
}
