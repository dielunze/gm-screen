package de.dielunze.gmscreen.engine;

import de.dielunze.gmscreen.engine.model.Enemy;
import de.dielunze.gmscreen.engine.model.Hero;

import java.util.List;

/**
 * The complete state of one fight: who is taking part and which round it is.
 * <p>
 * Whose turn it is, and advancing through the hero and enemy phases, arrives in
 * phase 2. For now this is the container everything else will hang off.
 */
public final class CombatState {

    private final List<Hero> heroes;
    private final List<Enemy> enemies;

    private int round = 1;

    /**
     * TODO implement:
     * <ul>
     *   <li>reject {@code null} for either list, and reject empty ones - a fight
     *       without heroes or without enemies is not a fight</li>
     *   <li>store <em>defensive copies</em>. {@link List#copyOf(java.util.Collection)}
     *       is the one-liner for this. Without it the caller keeps a reference to
     *       the same list and can add or remove combatants behind our back, which
     *       would be a bug that only shows up much later and far away from here</li>
     *   <li>start the round counter at 1</li>
     * </ul>
     *
     * <p>Note that {@code List.copyOf} copies the <em>list</em>, not the heroes in
     * it. That is exactly what we want: the roster is fixed, the heroes themselves
     * keep changing.
     */
    public CombatState(List<Hero> heroes, List<Enemy> enemies) {
        // Copy first. List.copyOf raises NullPointerException on a null argument,
        // which is the convention for a missing value, so no separate null check
        // is needed. It also means the emptiness checks below can no longer see
        // a null, which lets them stand on their own.
        this.heroes = List.copyOf(heroes);
        this.enemies = List.copyOf(enemies);

        // Present but unusable is the other category, and it gets the other
        // exception. Separate checks so the message names the actual problem.
        if (this.heroes.isEmpty()) {
            throw new IllegalArgumentException("a fight needs at least one hero");
        }
        if (this.enemies.isEmpty()) {
            throw new IllegalArgumentException("a fight needs at least one enemy");
        }
    }

    /**
     * TODO implement: return the heroes so that callers cannot modify the roster.
     * If the constructor already used {@code List.copyOf}, the field is
     * unmodifiable and you can simply hand it out.
     */
    public List<Hero> heroes() {
        return heroes;
    }

    /**
     * TODO implement: same as {@link #heroes()}, for the other side.
     */
    public List<Enemy> enemies() {
        return enemies;
    }

    /**
     * The current round. A round is one hero phase plus one enemy phase.
     */
    public int round() {
        return round;
    }
}
