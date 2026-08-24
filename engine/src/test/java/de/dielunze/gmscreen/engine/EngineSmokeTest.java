package de.dielunze.gmscreen.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Placeholder until the domain model arrives in phase 1.
 * <p>
 * The seeded Random is not decoration: architecture rule 3 says randomness is
 * injected, never created inside the logic. A fixed seed therefore has to
 * produce the same sequence on every machine and every run.
 */
class EngineSmokeTest {

    @Test
    void seededRandomIsDeterministic() {
        Random first = new Random(42L);
        Random second = new Random(42L);

        for (int i = 0; i < 10; i++) {
            assertEquals(first.nextInt(20) + 1, second.nextInt(20) + 1);
        }
    }
}
