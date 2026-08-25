package de.dielunze.gmscreen.engine.model;

import java.util.Objects;

/**
 * A playable class: what the compendium prints at the top of a hero's page.
 * Abilities join in phase 5; until then a class is a name, a role and numbers.
 *
 * @param name      e.g. {@code "Mercenary Fighter"}
 * @param role      the compendium's role text, e.g. {@code "Berserker"}
 * @param baseStats the stat line before any buff or item
 * @param marker    the class's personal resource
 */
public record CharacterClass(String name, String role, Stats baseStats, MarkerType marker) {

    public CharacterClass {

        if (name.isBlank()) {
            throw new IllegalArgumentException("name can't be blank");
        }
        if (role.isBlank()) {
            throw new IllegalArgumentException("role can't be blank");
        }

        Objects.requireNonNull(marker, "marker");
        Objects.requireNonNull(baseStats, "baseStats");
    }

}
