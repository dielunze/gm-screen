package de.dielunze.gmscreen.engine.model;

/**
 * Enemy strength tiers as the compendium names them.
 * <p>
 * Only classification for now. The tier starts to matter mechanically much later,
 * when bosses bring phase gates and party-size scaling.
 */
public enum EnemyTier {
    BASIC,
    RARE,
    EPIC,
    LEGENDARY,
    BOSS,
    FINAL_BOSS
}
