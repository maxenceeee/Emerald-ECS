package eu.xamence.emerald.test.server.network.type.enumtype;

import eu.xamence.emerald.server.network.type.enumtype.TeleportFlags;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TeleportFlagTest {

    @Test
    void testEmptyFlags() {
        TeleportFlags flags = TeleportFlags.empty();
        assertFalse(flags.isSet(TeleportFlags.Flag.RELATIVE_X));
        assertFalse(flags.isSet(TeleportFlags.Flag.RELATIVE_Y));
        assertFalse(flags.isSet(TeleportFlags.Flag.RELATIVE_Z));
        assertFalse(flags.isSet(TeleportFlags.Flag.RELATIVE_YAW));
        assertFalse(flags.isSet(TeleportFlags.Flag.RELATIVE_PITCH));
        assertFalse(flags.isSet(TeleportFlags.Flag.RELATIVE_VELOCITY_X));
        assertFalse(flags.isSet(TeleportFlags.Flag.RELATIVE_VELOCITY_Y));
        assertFalse(flags.isSet(TeleportFlags.Flag.RELATIVE_VELOCITY_Z));
        assertFalse(flags.isSet(TeleportFlags.Flag.ROTATE_VELOCITY));
        assertEquals(0, flags.toInt());
    }

    @Test
    void testSingleFlag() {
        TeleportFlags flags = TeleportFlags.empty().with(TeleportFlags.Flag.RELATIVE_X);
        assertTrue(flags.isSet(TeleportFlags.Flag.RELATIVE_X));
        assertFalse(flags.isSet(TeleportFlags.Flag.RELATIVE_Y));
        assertEquals(TeleportFlags.Flag.RELATIVE_X.getMask(), flags.toInt());
    }

    @Test
    void testMultipleFlags() {
        TeleportFlags flags = TeleportFlags.empty()
                .with(TeleportFlags.Flag.RELATIVE_X)
                .with(TeleportFlags.Flag.RELATIVE_Y)
                .with(TeleportFlags.Flag.ROTATE_VELOCITY);

        assertTrue(flags.isSet(TeleportFlags.Flag.RELATIVE_X));
        assertTrue(flags.isSet(TeleportFlags.Flag.RELATIVE_Y));
        assertFalse(flags.isSet(TeleportFlags.Flag.RELATIVE_Z));
        assertTrue(flags.isSet(TeleportFlags.Flag.ROTATE_VELOCITY));

        int expected = TeleportFlags.Flag.RELATIVE_X.getMask() |
                TeleportFlags.Flag.RELATIVE_Y.getMask() |
                TeleportFlags.Flag.ROTATE_VELOCITY.getMask();
        assertEquals(expected, flags.toInt());
    }

    @Test
    void testFromRawValue() {
        int rawValue = 0b00000011; // RELATIVE_X | RELATIVE_Y
        TeleportFlags flags = TeleportFlags.of(rawValue);

        assertTrue(flags.isSet(TeleportFlags.Flag.RELATIVE_X));
        assertTrue(flags.isSet(TeleportFlags.Flag.RELATIVE_Y));
        assertFalse(flags.isSet(TeleportFlags.Flag.RELATIVE_Z));
    }

    @Test
    void testAllFlags() {
        int allFlags = 0xFF;
        TeleportFlags flags = TeleportFlags.of(allFlags);

        for (TeleportFlags.Flag flag : TeleportFlags.Flag.values()) {
            assertTrue(flags.isSet(flag));
        }
    }
}
