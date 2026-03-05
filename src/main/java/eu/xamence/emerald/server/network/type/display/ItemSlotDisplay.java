package eu.xamence.emerald.server.network.type.display;

public final class ItemSlotDisplay extends SlotDisplay {
    private final int itemType;

    private ItemSlotDisplay(int itemType) {
        super(2);
        this.itemType = itemType;
    }

    public int getItemType() {
        return itemType;
    }

    public static class Builder extends SlotDisplay.Builder {
        private int itemType;

        public Builder withItemType(int itemType) {
            this.itemType = itemType;
            return this;
        }

        @Override
        public ItemSlotDisplay build() {
            return new ItemSlotDisplay(itemType);
        }
    }
}

