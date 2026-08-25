package de.dielunze.gmscreen.engine.model;

import java.util.Objects;

/**
 * Anything that takes a turn in a fight.
 * <p>
 * Sealed on purpose: there are exactly two kinds and there will not be a third.
 * That lets later phases switch over {@link Hero} and {@link Enemy} without a
 * {@code default} branch, and the compiler points at every such switch if the
 * set ever changes.
 * <p>
 * Deliberately <em>mutable</em>, unlike {@link Stats}. Current hit points and
 * action points change many times per round; rebuilding the object graph for
 * every single point of damage would buy nothing here.
 * <p>
 * No mutators yet - phase 1 is structure only. Spending and regaining action
 * points arrives in phase 2, taking damage in phase 3.
 */
public abstract sealed class Combatant permits Hero, Enemy {

    private final String name;
    private final Team team;
    private final Stats stats;

    private int currentHp;
    private int currentAp;

    /**
     * TODO implement.
     *
     * <p>Beyond storing the three arguments and rejecting nonsense, this
     * constructor decides the state a combatant enters a fight in. Two rules from
     * the compendium apply, and both belong here rather than anywhere else:
     *
     * <ul>
     *   <li>everyone starts at full health</li>
     *   <li><em>"Helden und Gegner starten Runde 1 mit 0 AP. Die normale
     *       AP-Regeneration beginnt ab Runde 2."</em> - so the starting action
     *       points are not {@code maxAp}</li>
     * </ul>
     */
    protected Combatant(String name, Team team, Stats stats) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("name can't be blank");
        }
        this.name = name;
        this.team = Objects.requireNonNull(team, "team can't be null");
        this.stats = Objects.requireNonNull(stats, "stats can't be null");

        this.currentHp = stats.maxHp();
        this.currentAp = 0;

    }

    public String name() {
        return name;
    }

    public Team team() {
        return team;
    }

    public Stats stats() {
        return stats;
    }

    public int currentHp() {
        return currentHp;
    }

    public int currentAp() {
        return currentAp;
    }
}
