package eu.xamence.emerald.server.network.type.data;

import java.util.Arrays;
import java.util.Objects;

public record Heightmap(String type, long[] data) {

    public Heightmap(String type, long[] data) {
        this.type = Objects.requireNonNull(type, "Type cannot be null");
        this.data = Arrays.copyOf(data, data.length);
    }
}
