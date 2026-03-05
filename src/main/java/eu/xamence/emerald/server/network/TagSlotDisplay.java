package eu.xamence.emerald.server.network;

import java.util.Objects;

public final class TagSlotDisplay extends SlotDisplay {
    private final String tag;

    private TagSlotDisplay(String tag) {
        super(4);
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public static class Builder extends SlotDisplay.Builder {
        private String tag;

        public Builder withTag(String tag) {
            this.tag = tag;
            return this;
        }

        @Override
        public TagSlotDisplay build() {
            Objects.requireNonNull(tag, "Tag cannot be null");
            return new TagSlotDisplay(tag);
        }
    }
}

