package eu.xamence.emerald.server.network.type.display;

import java.util.Objects;

public final class SmithingDisplay extends RecipeDisplay {
    private final SlotDisplay template;
    private final SlotDisplay base;
    private final SlotDisplay addition;
    private final SlotDisplay result;
    private final SlotDisplay craftingStation;

    private SmithingDisplay(SlotDisplay template, SlotDisplay base, SlotDisplay addition, SlotDisplay result, SlotDisplay craftingStation) {
        super(4);
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.result = result;
        this.craftingStation = craftingStation;
    }

    public SlotDisplay getTemplate() {
        return template;
    }

    public SlotDisplay getBase() {
        return base;
    }

    public SlotDisplay getAddition() {
        return addition;
    }

    public SlotDisplay getResult() {
        return result;
    }

    public SlotDisplay getCraftingStation() {
        return craftingStation;
    }

    public static class Builder extends RecipeDisplay.Builder {
        private SlotDisplay template;
        private SlotDisplay base;
        private SlotDisplay addition;
        private SlotDisplay result;
        private SlotDisplay craftingStation;

        public Builder withTemplate(SlotDisplay template) {
            this.template = template;
            return this;
        }

        public Builder withBase(SlotDisplay base) {
            this.base = base;
            return this;
        }

        public Builder withAddition(SlotDisplay addition) {
            this.addition = addition;
            return this;
        }

        public Builder withResult(SlotDisplay result) {
            this.result = result;
            return this;
        }

        public Builder withCraftingStation(SlotDisplay craftingStation) {
            this.craftingStation = craftingStation;
            return this;
        }

        @Override
        public SmithingDisplay build() {
            Objects.requireNonNull(template, "Template cannot be null");
            Objects.requireNonNull(base, "Base cannot be null");
            Objects.requireNonNull(addition, "Addition cannot be null");
            Objects.requireNonNull(result, "Result cannot be null");
            Objects.requireNonNull(craftingStation, "Crafting station cannot be null");
            return new SmithingDisplay(template, base, addition, result, craftingStation);
        }
    }
}

