package eu.xamence.emerald.test.server.network.types;

import eu.xamence.emerald.server.network.Types;
import eu.xamence.emerald.server.network.type.enumtype.TeleportFlags;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class TeleportFlagTypeTest {

    @Test
    void testSerializationRoundTrip() throws IOException {
        TeleportFlags original = TeleportFlags.empty()
                .with(TeleportFlags.Flag.RELATIVE_X)
                .with(TeleportFlags.Flag.RELATIVE_Z)
                .with(TeleportFlags.Flag.ROTATE_VELOCITY);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(outputStream);
        Types.TELEPORT_FLAGS.write(out, original);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        DataInputStream in = new DataInputStream(inputStream);
        TeleportFlags deserialized = Types.TELEPORT_FLAGS.read(in);

        assertEquals(original.toInt(), deserialized.toInt());
        for (TeleportFlags.Flag flag : TeleportFlags.Flag.values()) {
            assertEquals(original.isSet(flag), deserialized.isSet(flag));
        }
    }

    @Test
    void testEmptyFlagsSerialization() throws IOException {
        TeleportFlags original = TeleportFlags.empty();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(outputStream);
        Types.TELEPORT_FLAGS.write(out, original);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        DataInputStream in = new DataInputStream(inputStream);
        TeleportFlags deserialized = Types.TELEPORT_FLAGS.read(in);

        assertEquals(0, deserialized.toInt());
    }

    @Test
    void testAllFlagsSerialization() throws IOException {
        TeleportFlags original = TeleportFlags.of(0xFF); // Tous les flags activés

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(outputStream);
        Types.TELEPORT_FLAGS.write(out, original);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        DataInputStream in = new DataInputStream(inputStream);
        TeleportFlags deserialized = Types.TELEPORT_FLAGS.read(in);

        assertEquals(0xFF, deserialized.toInt());
        for (TeleportFlags.Flag flag : TeleportFlags.Flag.values()) {
            assertTrue(deserialized.isSet(flag));
        }
    }

    @Test
    void testNullInput() {
        assertThrows(IOException.class, () -> {
            Types.TELEPORT_FLAGS.write(new DataOutputStream(new ByteArrayOutputStream()), null);
        });
    }

    @Test
    void testOnlyLower8BitsUsed() throws IOException {
        // Create flag with 8 most significant bytes (normally ignored)
        TeleportFlags original = TeleportFlags.of(0xFF00); // Bits 8-15 true

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(outputStream);
        Types.TELEPORT_FLAGS.write(out, original);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        DataInputStream in = new DataInputStream(inputStream);
        TeleportFlags deserialized = Types.TELEPORT_FLAGS.read(in);

        // Only the 8 less significant bytes conserved
        assertEquals(0, deserialized.toInt());
    }
}
