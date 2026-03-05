package eu.xamence.emerald.server.network;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public final class PartialProfile {

    private final Optional<String> username;
    private final Optional<UUID> uuid;
    private final List<GameProfileProperty> properties;

    public PartialProfile(Optional<String> username, Optional<UUID> uuid, List<GameProfileProperty> properties) {
        this.username = username;
        this.uuid = uuid;
        this.properties = List.copyOf(properties);
    }

    public Optional<String> getUsername() {
        return username;
    }

    public Optional<UUID> getUuid() {
        return uuid;
    }

    public List<GameProfileProperty> getProperties() {
        return properties;
    }
}
