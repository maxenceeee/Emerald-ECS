package eu.xamence.emerald.server.network.type;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record IDSet(int type, Optional<String> tagName, Optional<List<Integer>> ids) {

    public static IDSet byTag(String tagName) {
        Objects.requireNonNull(tagName, "Tag name cannot be null");
        return new IDSet(0, Optional.of(tagName), Optional.empty());
    }

    public static IDSet byIds(List<Integer> ids) {
        Objects.requireNonNull(ids, "IDs list cannot be null");
        if (ids.isEmpty()) {
            throw new IllegalArgumentException("IDs list cannot be empty");
        }
        return new IDSet(ids.size() + 1, Optional.empty(), Optional.of(ids));
    }

    public boolean isTag() {
        return this.type == 0;
    }
}
