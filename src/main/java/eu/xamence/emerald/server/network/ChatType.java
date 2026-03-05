package eu.xamence.emerald.server.network;

import net.kyori.adventure.nbt.BinaryTag;

import java.util.List;
import java.util.Objects;

public record ChatType(String translationKey, List<ChatTypeParameter> parameters, BinaryTag style) {

    public ChatType {
        Objects.requireNonNull(translationKey, "Translation key cannot be null");
        Objects.requireNonNull(parameters, "Parameters cannot be null");
        Objects.requireNonNull(style, "Style cannot be null");
    }
}
