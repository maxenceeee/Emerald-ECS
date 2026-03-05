package eu.xamence.emerald.server.network;

public final class AnyFuelSlotDisplay extends SlotDisplay {
    public AnyFuelSlotDisplay() {
        super(1);
    }

    public static class Builder extends SlotDisplay.Builder {
        @Override
        public AnyFuelSlotDisplay build() {
            return new AnyFuelSlotDisplay();
        }
    }
}

