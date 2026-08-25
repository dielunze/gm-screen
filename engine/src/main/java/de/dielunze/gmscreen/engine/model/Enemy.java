package de.dielunze.gmscreen.engine.model;

/**
 * A combatant on the enemy side. Beyond the shared state it carries only its
 * tier; attacks, target priorities and AI weights arrive in phase 6.
 * <p>
 * The constructor is written out because Java forces it: a subclass constructor
 * always calls one of the superclass constructors first, and Combatant has none
 * without arguments. There is no rule hiding in here - all of it lives one level
 * up, in {@link Combatant}.
 */
public final class Enemy extends Combatant {

    private final EnemyTier tier;

    public Enemy(String name, EnemyTier tier, Stats stats) {
        super(name, Team.ENEMIES, stats);
        this.tier = tier;
    }

    public EnemyTier tier() {
        return tier;
    }
}
