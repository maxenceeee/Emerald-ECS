package eu.xamence.emerald.server.network.type.display;

import java.util.Objects;

public final class SmithingTrimSlotDisplay extends SlotDisplay {
    private final SlotDisplay base;
    private final SlotDisplay material;
    private final int pattern;

    private SmithingTrimSlotDisplay(SlotDisplay base, SlotDisplay material, int pattern) {
        super(5);
        this.base = base;
        this.material = material;
        this.pattern = pattern;
    }

    public SlotDisplay getBase() {
        return base;
    }

    public SlotDisplay getMaterial() {
        return material;
    }

    public int getPattern() {
        return pattern;
    }

    public static class Builder extends SlotDisplay.Builder {
        private SlotDisplay base;
        private SlotDisplay material;
        private int pattern;

        public Builder withBase(SlotDisplay base) {
            this.base = base;
            return this;
        }

        public Builder withMaterial(SlotDisplay material) {
            this.material = material;
            return this;
        }

        public Builder withPattern(int pattern) {
            this.pattern = pattern;
            return this;
        }

        @Override
        public SmithingTrimSlotDisplay build() {
            Objects.requireNonNull(base, "Base cannot be null");
            Objects.requireNonNull(material, "Material cannot be null");
            return new SmithingTrimSlotDisplay(base, material, pattern);
        }
    }
}

