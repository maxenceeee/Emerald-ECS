package eu.xamence.emerald.server.network.type.display;

import java.util.Objects;

public final class WithRemainderSlotDisplay extends SlotDisplay {
    private final SlotDisplay ingredient;
    private final SlotDisplay remainder;

    private WithRemainderSlotDisplay(SlotDisplay ingredient, SlotDisplay remainder) {
        super(6);
        this.ingredient = ingredient;
        this.remainder = remainder;
    }

    public SlotDisplay getIngredient() {
        return ingredient;
    }

    public SlotDisplay getRemainder() {
        return remainder;
    }

    public static class Builder extends SlotDisplay.Builder {
        private SlotDisplay ingredient;
        private SlotDisplay remainder;

        public Builder withIngredient(SlotDisplay ingredient) {
            this.ingredient = ingredient;
            return this;
        }

        public Builder withRemainder(SlotDisplay remainder) {
            this.remainder = remainder;
            return this;
        }

        @Override
        public WithRemainderSlotDisplay build() {
            Objects.requireNonNull(ingredient, "Ingredient cannot be null");
            Objects.requireNonNull(remainder, "Remainder cannot be null");
            return new WithRemainderSlotDisplay(ingredient, remainder);
        }
    }
}

