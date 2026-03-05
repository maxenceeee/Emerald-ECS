package eu.xamence.emerald.server.network.type.display;


import java.util.Objects;

public final class FurnaceDisplay extends RecipeDisplay {
    private final SlotDisplay ingredient;
    private final SlotDisplay fuel;
    private final SlotDisplay result;
    private final SlotDisplay craftingStation;
    private final int cookingTime;
    private final float experience;

    private FurnaceDisplay(SlotDisplay ingredient, SlotDisplay fuel, SlotDisplay result, SlotDisplay craftingStation, int cookingTime, float experience) {
        super(2);
        this.ingredient = ingredient;
        this.fuel = fuel;
        this.result = result;
        this.craftingStation = craftingStation;
        this.cookingTime = cookingTime;
        this.experience = experience;
    }

    public SlotDisplay getIngredient() {
        return ingredient;
    }

    public SlotDisplay getFuel() {
        return fuel;
    }

    public SlotDisplay getResult() {
        return result;
    }

    public SlotDisplay getCraftingStation() {
        return craftingStation;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public float getExperience() {
        return experience;
    }

    public static class Builder extends RecipeDisplay.Builder {
        private SlotDisplay ingredient;
        private SlotDisplay fuel;
        private SlotDisplay result;
        private SlotDisplay craftingStation;
        private int cookingTime;
        private float experience;

        public Builder withIngredient(SlotDisplay ingredient) {
            this.ingredient = ingredient;
            return this;
        }

        public Builder withFuel(SlotDisplay fuel) {
            this.fuel = fuel;
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

        public Builder withCookingTime(int cookingTime) {
            this.cookingTime = cookingTime;
            return this;
        }

        public Builder withExperience(float experience) {
            this.experience = experience;
            return this;
        }

        @Override
        public FurnaceDisplay build() {
            Objects.requireNonNull(ingredient, "Ingredient cannot be null");
            Objects.requireNonNull(fuel, "Fuel cannot be null");
            Objects.requireNonNull(result, "Result cannot be null");
            Objects.requireNonNull(craftingStation, "Crafting station cannot be null");
            return new FurnaceDisplay(ingredient, fuel, result, craftingStation, cookingTime, experience);
        }
    }
}
