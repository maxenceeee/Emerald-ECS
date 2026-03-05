package eu.xamence.emerald.server.network.type.display;

import java.util.List;
import java.util.Objects;

public final class CompositeSlotDisplay extends SlotDisplay {
    private final List<SlotDisplay> options;

    private CompositeSlotDisplay(List<SlotDisplay> options) {
        super(7);
        this.options = List.copyOf(options);
    }

    public List<SlotDisplay> getOptions() {
        return options;
    }

    public static class Builder extends SlotDisplay.Builder {
        private List<SlotDisplay> options;

        public Builder withOptions(List<SlotDisplay> options) {
            this.options = List.copyOf(options);
            return this;
        }

        @Override
        public CompositeSlotDisplay build() {
            Objects.requireNonNull(options, "Options cannot be null");
            if (options.isEmpty()) {
                throw new IllegalStateException("Options cannot be empty");
            }
            return new CompositeSlotDisplay(options);
        }
    }
}

