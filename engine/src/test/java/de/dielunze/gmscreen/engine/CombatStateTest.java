package de.dielunze.gmscreen.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.dielunze.gmscreen.engine.model.Enemy;
import de.dielunze.gmscreen.engine.model.Hero;
import de.dielunze.gmscreen.engine.model.Team;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The phase 1 checkpoint: a party and an enemy group, built by hand.
 * Fails until the CombatState constructor and its accessors exist.
 */
class CombatStateTest {

    private static List<Hero> party() {
        return List.of(
                Roster.hero(Roster.mercenaryFighter()),
                Roster.hero(Roster.chaosPaladin()),
                Roster.hero(Roster.highPriest()),
                Roster.hero(Roster.demonicArcher()));
    }

    private static List<Enemy> enemyGroup() {
        return List.of(Roster.goblin(), Roster.goblin(), Roster.wolf(), Roster.archer());
    }

    @Test
    @DisplayName("a fight starts in round 1 with both sides present")
    void buildsAFight() {
        CombatState state = new CombatState(party(), enemyGroup());

        assertEquals(1, state.round());
        assertEquals(4, state.heroes().size());
        assertEquals(4, state.enemies().size());
    }

    @Test
    @DisplayName("everyone starts at full health with zero AP")
    void everyoneStartsFresh() {
        CombatState state = new CombatState(party(), enemyGroup());

        for (Hero hero : state.heroes()) {
            assertEquals(hero.stats().maxHp(), hero.currentHp(), hero.name());
            assertEquals(0, hero.currentAp(), hero.name());
            assertEquals(Team.HEROES, hero.team());
        }
        for (Enemy enemy : state.enemies()) {
            assertEquals(enemy.stats().maxHp(), enemy.currentHp(), enemy.name());
            assertEquals(0, enemy.currentAp(), enemy.name());
            assertEquals(Team.ENEMIES, enemy.team());
        }
    }

    @Test
    @DisplayName("the roster carries the compendium values")
    void carriesCompendiumValues() {
        CombatState state = new CombatState(party(), enemyGroup());

        Hero paladin = state.heroes().get(1);
        assertEquals("Chaos Paladin", paladin.name());
        assertEquals(48, paladin.stats().maxHp());
        assertEquals(5, paladin.stats().defense());
        assertEquals("Glaube", paladin.characterClass().marker().name());

        Enemy goblin = state.enemies().get(0);
        assertEquals("Goblin", goblin.name());
        assertEquals(16, goblin.stats().maxHp());
        assertEquals(1, goblin.stats().defense());
    }

    @Test
    @DisplayName("two goblins are separate combatants, not the same object twice")
    void duplicatesAreIndependent() {
        CombatState state = new CombatState(party(), enemyGroup());

        Enemy first = state.enemies().get(0);
        Enemy second = state.enemies().get(1);

        assertEquals(first.name(), second.name());
        org.junit.jupiter.api.Assertions.assertNotSame(first, second);
    }

    @Test
    @DisplayName("changing the caller's list afterwards does not change the fight")
    void copiesDefensively() {
        List<Hero> callersParty = new ArrayList<>(party());
        CombatState state = new CombatState(callersParty, enemyGroup());

        callersParty.clear();

        assertEquals(4, state.heroes().size());
    }

    @Test
    @DisplayName("a fight needs both sides")
    void rejectsIncompleteFights() {
        // Convention in Java: null arguments raise NullPointerException
        // (Objects.requireNonNull does this for you), while a non-null but
        // invalid argument raises IllegalArgumentException. Asserting the
        // precise type matters here - a vague RuntimeException would also
        // match the UnsupportedOperationException of the unimplemented stub,
        // so the test would pass without testing anything.
        assertThrows(IllegalArgumentException.class, () -> new CombatState(List.of(), enemyGroup()));
        assertThrows(IllegalArgumentException.class, () -> new CombatState(party(), List.of()));
        assertThrows(NullPointerException.class, () -> new CombatState(null, enemyGroup()));
        assertThrows(NullPointerException.class, () -> new CombatState(party(), null));
    }
}
