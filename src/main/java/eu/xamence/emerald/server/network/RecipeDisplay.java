package eu.xamence.emerald.server.network;

public abstract class RecipeDisplay {

    private final int type;

    protected RecipeDisplay(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public abstract static class Builder {
        public abstract RecipeDisplay build();
    }
}
