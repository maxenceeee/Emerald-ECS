package eu.xamence.emerald.server.network.type.display;

public abstract class SlotDisplay {

    private final int type;

    protected SlotDisplay(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public abstract static class Builder {
        public abstract SlotDisplay build();
    }
}
