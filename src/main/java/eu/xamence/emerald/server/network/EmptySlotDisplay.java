package eu.xamence.emerald.server.network;

public final class EmptySlotDisplay extends SlotDisplay {
    public EmptySlotDisplay() {
        super(0);
    }

    public static class Builder extends SlotDisplay.Builder {
        @Override
        public EmptySlotDisplay build() {
            return new EmptySlotDisplay();
        }
    }
}

