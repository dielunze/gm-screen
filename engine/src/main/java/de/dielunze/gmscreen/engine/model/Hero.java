package de.dielunze.gmscreen.engine.model;

/**
 * A player character. Beyond the shared state it carries its class and the
 * current value of that class's marker resource.
 * <p>
 * As with {@link Enemy}, the constructor is written out only because the call to
 * {@code super(...)} is forced by the language. Note where the name and the stats
 * come from: everything a hero needs is already inside its {@link CharacterClass}.
 */
public final class Hero extends Combatant {

    private final CharacterClass characterClass;

    private int markerValue;

    public Hero(CharacterClass characterClass) {
        super(characterClass.name(), Team.HEROES, characterClass.baseStats());
        this.characterClass = characterClass;
    }

    public CharacterClass characterClass() {
        return characterClass;
    }

    public int markerValue() {
        return markerValue;
    }

    /**
     * The cap for this hero's marker, e.g. 5 for the Mercenary Fighter's Blutrausch.
     *
     */
    public int markerMaximum() {
        return characterClass.marker().maximum();
    }

    /**
     * Sets the marker pool to {@code value}, clamped into {@code [0, markerMaximum()]}.
     *
     * <p>Clamping rather than throwing is what the compendium prescribes. It is
     * explicit that surplus is discarded: <em>"Bei 3/3 ist der Finisher voll
     * geladen; weitere Schattenenergie verfaellt."</em> A negative value must not
     * be storable either - a finisher that consumes every marker leaves 0, never -1.
     *
     */
    public void setMarker(int value) {
        markerValue = Math.clamp(value, 0, markerMaximum());
    }
}
