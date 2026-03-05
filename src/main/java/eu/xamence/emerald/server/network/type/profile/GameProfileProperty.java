package eu.xamence.emerald.server.network.type.profile;

import java.util.Objects;
import java.util.Optional;

public class GameProfileProperty {

    private final String name;
    private final String value;
    private final Optional<String> signature;

    public GameProfileProperty(String name, String value, Optional<String> signature) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.value = Objects.requireNonNull(value, "Value cannot be null");
        this.signature = signature;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Optional<String> getSignature() {
        return signature;
    }
}

