package eu.xamence.emerald.server.network;

public final class StonecutterDisplay extends RecipeDisplay {
    private final SlotDisplay ingredient;
    private final SlotDisplay result;
    private final SlotDisplay craftingStation;

    private StonecutterDisplay(SlotDisplay ingredient, SlotDisplay result, SlotDisplay craftingStation) {
        super(3);
        this.ingredient = ingredient;
        this.result = result;
        this.craftingStation = craftingStation;
    }

    public SlotDisplay getIngredient() {
        return ingredient;
    }

    public SlotDisplay getResult() {
        return result;
    }

    public SlotDisplay getCraftingStation() {
        return craftingStation;
    }

    public static class Builder extends RecipeDisplay.Builder {
        private SlotDisplay ingredient;
        private SlotDisplay result;
        private SlotDisplay craftingStation;

        public Builder withIngredient(SlotDisplay ingredient) {
            this.ingredient = ingredient;
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
        public StonecutterDisplay build() {
            Objects.requireNonNull(ingredient, "Ingredient cannot be null");
            Objects.requireNonNull(result, "Result cannot be null");
            Objects.requireNonNull(craftingStation, "Crafting station cannot be null");
            return new StonecutterDisplay(ingredient, result, craftingStation);
        }
    }
}
