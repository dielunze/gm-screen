package de.dielunze.gmscreen.engine.model;

/**
 * The unchanging profile of a combatant, one line of the compendium:
 * {@code Berserker | LP 38 | AP 6 | Regeneration 2 | ATK 6 | DEF 3}.
 * <p>
 * These are maximums and base values, never live state. A hero down to 4 hit
 * points still has {@code maxHp == 38}; only the current value moves, and that
 * lives on {@link Combatant}.
 *
 * @param maxHp   hit points at full health (LP)
 * @param maxAp   action point ceiling (AP)
 * @param apRegen action points regained per round from round 2 onwards
 * @param attack  flat bonus added to damage rolls (ATK)
 * @param defense flat amount subtracted from incoming damage (DEF)
 */
public record Stats(int maxHp, int maxAp, int apRegen, int attack, int defense) {

    public Stats {
        if (maxHp < 1) {
            throw new IllegalArgumentException("maxHp must be greater than 0 but was " + maxHp);
        }
        if (maxAp < 1) {
            throw new IllegalArgumentException("maxAp must be greater than 0 but was " + maxAp);
        }
        if (apRegen < 0) {
            throw new IllegalArgumentException("apRegen must be greater than -1 but was " + apRegen);
        }
        if (attack < 0) {
            throw new IllegalArgumentException("attack must be greater than -1 but was " + attack);
        }
        if (defense < 0) {
            throw new IllegalArgumentException("defense must be greater than -1 but was " + defense);
        }
    }
}
