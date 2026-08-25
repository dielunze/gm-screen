package de.dielunze.gmscreen.engine;

import de.dielunze.gmscreen.engine.model.CharacterClass;
import de.dielunze.gmscreen.engine.model.Enemy;
import de.dielunze.gmscreen.engine.model.EnemyTier;
import de.dielunze.gmscreen.engine.model.Hero;
import de.dielunze.gmscreen.engine.model.MarkerType;
import de.dielunze.gmscreen.engine.model.Stats;

/**
 * The MVP roster, with the real numbers from compendium 1.6.2.
 * <p>
 * Hardcoded on purpose. Content moves to JSON in phase 10; until then tests need
 * something concrete to build from, and concrete beats invented.
 */
final class Roster {

    private Roster() {
    }

    // --- heroes ----------------------------------------------------------

    static CharacterClass mercenaryFighter() {
        return new CharacterClass("Mercenary Fighter", "Berserker",
                new Stats(38, 6, 2, 6, 3), new MarkerType("Blutrausch", 5));
    }

    static CharacterClass chaosPaladin() {
        return new CharacterClass("Chaos Paladin", "Tank / Support",
                new Stats(48, 6, 2, 4, 5), new MarkerType("Glaube", 5));
    }

    static CharacterClass highPriest() {
        return new CharacterClass("High Priest", "Pure Support / Buffer",
                new Stats(36, 8, 2, 4, 3), new MarkerType("Andacht", 5));
    }

    static CharacterClass demonicArcher() {
        return new CharacterClass("Demonic Archer", "Ranged DPS / Executioner",
                new Stats(34, 7, 2, 7, 3), new MarkerType("Daemonenmarken", 5));
    }

    // --- enemies ---------------------------------------------------------

    static Enemy goblin() {
        return new Enemy("Goblin", EnemyTier.BASIC, new Stats(16, 4, 2, 3, 1));
    }

    static Enemy knight() {
        return new Enemy("Knight", EnemyTier.BASIC, new Stats(26, 5, 2, 4, 4));
    }

    static Enemy wolf() {
        return new Enemy("Wolf", EnemyTier.BASIC, new Stats(14, 4, 2, 4, 1));
    }

    static Enemy archer() {
        return new Enemy("Archer", EnemyTier.BASIC, new Stats(18, 5, 2, 5, 1));
    }

    static Hero hero(CharacterClass characterClass) {
        return new Hero(characterClass);
    }
}
