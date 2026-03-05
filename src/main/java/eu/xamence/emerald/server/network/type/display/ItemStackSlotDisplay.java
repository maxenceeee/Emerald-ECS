package eu.xamence.emerald.server.network.type.display;

import eu.xamence.emerald.server.network.type.data.Slot;

public final class ItemStackSlotDisplay extends SlotDisplay {
    private final Slot itemStack;

    private ItemStackSlotDisplay(Slot itemStack) {
        super(3);
        this.itemStack = itemStack;
    }

    public Slot getItemStack() {
        return itemStack;
    }

    public static class Builder extends SlotDisplay.Builder {
        private Slot itemStack;

        public Builder withItemStack(Slot itemStack) {
            this.itemStack = itemStack;
            return this;
        }

        @Override
        public ItemStackSlotDisplay build() {
            Objects.requireNonNull(itemStack, "Item stack cannot be null");
            return new ItemStackSlotDisplay(itemStack);
        }
    }
}
