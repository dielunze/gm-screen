package de.dielunze.gmscreen.engine.model;

/**
 * A hero's personal combat resource: Blutrausch, Andacht, Fokus, Seelen and so on.
 * Every one of the nineteen heroes has exactly one, so this is core structure
 * rather than a special case.
 * <p>
 * Describes the resource itself. How much of it a hero currently holds lives on
 * {@link Hero}.
 *
 * @param name    the compendium's own name for the resource
 * @param maximum the cap; surplus beyond it is discarded, not stored
 */
public record MarkerType(String name, int maximum) {

    public MarkerType {
        if (name.isBlank()) {
            throw new IllegalArgumentException("name can't be blank");
        }
        if (maximum < 1) {
            throw new IllegalArgumentException("maximum can't be 0 or negative. was " + maximum);
        }
    }
}
