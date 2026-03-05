package eu.xamence.emerald.server.network.type.display;


import java.util.List;
import java.util.Objects;

public final class CraftingShapedDisplay extends RecipeDisplay {
    private final int width;
    private final int height;
    private final List<SlotDisplay> ingredients;
    private final SlotDisplay result;
    private final SlotDisplay craftingStation;

    private CraftingShapedDisplay(int width, int height, List<SlotDisplay> ingredients, SlotDisplay result, SlotDisplay craftingStation) {
        super(1);
        this.width = width;
        this.height = height;
        this.ingredients = List.copyOf(ingredients);
        this.result = result;
        this.craftingStation = craftingStation;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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
        private int width;
        private int height;
        private List<SlotDisplay> ingredients;
        private SlotDisplay result;
        private SlotDisplay craftingStation;

        public Builder withWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder withHeight(int height) {
            this.height = height;
            return this;
        }

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
        public CraftingShapedDisplay build() {
            Objects.requireNonNull(ingredients, "Ingredients cannot be null");
            Objects.requireNonNull(result, "Result cannot be null");
            Objects.requireNonNull(craftingStation, "Crafting station cannot be null");
            if (ingredients.size() != width * height) {
                throw new IllegalStateException("Ingredients size must equal width * height");
            }
            return new CraftingShapedDisplay(width, height, ingredients, result, craftingStation);
        }
    }

}
