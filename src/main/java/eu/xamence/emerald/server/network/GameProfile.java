package eu.xamence.emerald.server.network;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class GameProfile {
    private final UUID uuid;
    private final String username;
    private final List<GameProfileProperty> properties;

    public GameProfile(UUID uuid, String username, List<GameProfileProperty> properties) {
        this.uuid = Objects.requireNonNull(uuid, "UUID cannot be null");
        this.username = Objects.requireNonNull(username, "Username cannot be null");
        this.properties = List.copyOf(properties);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    public List<GameProfileProperty> getProperties() {
        return properties;
    }
}
