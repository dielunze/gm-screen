package de.dielunze.gmscreen.engine.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Covers the marker pool. Fails until Hero.markerMaximum and Hero.setMarker exist. */
class HeroTest {

    private static Hero mercenary() {
        return new Hero(new CharacterClass("Mercenary Fighter", "Berserker",
                new Stats(38, 6, 2, 6, 3), new MarkerType("Blutrausch", 5)));
    }

    @Test
    @DisplayName("enters the fight at full health, zero AP and an empty marker pool")
    void startsEmpty() {
        Hero hero = mercenary();

        assertEquals(38, hero.currentHp());
        assertEquals(0, hero.currentAp());
        assertEquals(0, hero.markerValue());
    }

    @Test
    @DisplayName("reports the cap from its character class")
    void reportsMaximum() {
        assertEquals(5, mercenary().markerMaximum());
    }

    @Test
    @DisplayName("stores a value inside the allowed range unchanged")
    void storesInRange() {
        Hero hero = mercenary();

        hero.setMarker(3);

        assertEquals(3, hero.markerValue());
    }

    @Test
    @DisplayName("discards surplus beyond the cap instead of failing")
    void clampsAtMaximum() {
        Hero hero = mercenary();

        hero.setMarker(9);

        assertEquals(5, hero.markerValue());
    }

    @Test
    @DisplayName("never drops below zero")
    void clampsAtZero() {
        Hero hero = mercenary();

        hero.setMarker(-4);

        assertEquals(0, hero.markerValue());
    }

    @Test
    @DisplayName("the boundaries themselves are valid")
    void boundariesAreValid() {
        Hero hero = mercenary();

        hero.setMarker(5);
        assertEquals(5, hero.markerValue());

        hero.setMarker(0);
        assertEquals(0, hero.markerValue());
    }
}
