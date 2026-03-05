package eu.xamence.emerald.server.network;

import java.util.List;
import java.util.Objects;

public final class CraftingShapelessDisplay extends RecipeDisplay{

    private final List<SlotDisplay> ingredients;
    private final SlotDisplay result;
    private final SlotDisplay craftingStation;

    private CraftingShapelessDisplay(List<SlotDisplay> ingredients, SlotDisplay result, SlotDisplay craftingStation) {
        super(0);
        this.ingredients = List.copyOf(ingredients);
        this.result = result;
        this.craftingStation = craftingStation;
    }


    public List<SlotDisplay> getIngredients() {
        return ingredients;
    }

    public SlotDisplay getResult() {
        return result;
    }

    public SlotDisplay getCraftingStation() {
        return craftingStation;
    }

    public static class Builder extends RecipeDisplay.Builder {
        private List<SlotDisplay> ingredients;
        private SlotDisplay result;
        private SlotDisplay craftingStation;

        public Builder withIngredients(List<SlotDisplay> ingredients) {
            this.ingredients = List.copyOf(ingredients);
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
        public CraftingShapelessDisplay build() {
            Objects.requireNonNull(ingredients, "Ingredients cannot be null");
            Objects.requireNonNull(result, "Result cannot be null");
            Objects.requireNonNull(craftingStation, "Crafting station cannot be null");
            return new CraftingShapelessDisplay(ingredients, result, craftingStation);
        }
    }

}
