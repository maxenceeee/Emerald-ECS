package eu.xamence.emerald.test.server.network.types;

import eu.xamence.emerald.server.network.Types;
import eu.xamence.emerald.server.network.type.ChatType;
import eu.xamence.emerald.server.network.type.enumtype.ChatTypeParameter;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ChatTypeTest {

    @Test
    void testSerializationDeserialization() throws IOException {
        ChatType original = new ChatType(
                "chat.type.text",
                List.of(ChatTypeParameter.SENDER, ChatTypeParameter.CONTENT),
                CompoundBinaryTag.builder()
                        .putString("color", "white")
                        .putString("clickEvent", "suggest_command:/msg ")
                        .build()
        );

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(outputStream);
        Types.CHAT_TYPE.write(out, original);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        DataInputStream in = new DataInputStream(inputStream);
        ChatType deserialized = Types.CHAT_TYPE.read(in);

        assertEquals(original.translationKey(), deserialized.translationKey());
        assertEquals(original.parameters(), deserialized.parameters());
        assertEquals(original.style(), deserialized.style());
    }

    @Test
    void testEmptyParameters() throws IOException {
        ChatType original = new ChatType(
                "chat.type.text",
                List.of(),
                CompoundBinaryTag.empty()
        );

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(outputStream);
        Types.CHAT_TYPE.write(out, original);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        DataInputStream in = new DataInputStream(inputStream);
        ChatType deserialized = Types.CHAT_TYPE.read(in);

        assertEquals(original, deserialized);
    }

    @Test
    void testNullValidation() {
        assertThrows(NullPointerException.class, () ->
                new ChatType(null, List.of(), CompoundBinaryTag.empty())
        );

        assertThrows(NullPointerException.class, () ->
                new ChatType("key", null, CompoundBinaryTag.empty())
        );

        assertThrows(NullPointerException.class, () ->
                new ChatType("key", List.of(), null)
        );
    }

    @Test
    void testLongTranslationKey() {
        String longKey = "a".repeat(256);
        ChatType chatType = new ChatType(longKey, List.of(), CompoundBinaryTag.empty());

        assertThrows(IOException.class, () -> {
            Types.CHAT_TYPE.write(new DataOutputStream(new ByteArrayOutputStream()), chatType);
        });
    }

    @Test
    void testDuplicateParameters() {
        ChatType chatType = new ChatType(
                "key",
                List.of(ChatTypeParameter.SENDER, ChatTypeParameter.SENDER),
                CompoundBinaryTag.empty()
        );

        assertThrows(IOException.class, () -> {
            Types.CHAT_TYPE.write(new DataOutputStream(new ByteArrayOutputStream()), chatType);
        });
    }
}
