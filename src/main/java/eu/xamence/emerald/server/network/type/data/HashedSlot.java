package eu.xamence.emerald.server.network.type.data;

import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.Optional;

public final class HashedSlot {
    private final boolean hasItem;
    private final Optional<Integer> itemId;
    private final Optional<Integer> itemCount;
    private final List<Component> componentsToAdd;
    private final List<Integer> componentDataHashes;
    private final List<Component> componentsToRemove;

    private HashedSlot(boolean hasItem, Optional<Integer> itemId, Optional<Integer> itemCount,
                       List<Component> componentsToAdd, List<Integer> componentDataHashes,
                       List<Component> componentsToRemove) {
        this.hasItem = hasItem;
        this.itemId = itemId;
        this.itemCount = itemCount;
        this.componentsToAdd = List.copyOf(componentsToAdd);
        this.componentDataHashes = List.copyOf(componentDataHashes);
        this.componentsToRemove = List.copyOf(componentsToRemove);
    }

    public boolean hasItem() {
        return hasItem;
    }

    public Optional<Integer> getItemId() {
        return itemId;
    }

    public Optional<Integer> getItemCount() {
        return itemCount;
    }

    public List<Component> getComponentsToAdd() {
        return componentsToAdd;
    }

    public List<Integer> getComponentDataHashes() {
        return componentDataHashes;
    }

    public List<Component> getComponentsToRemove() {
        return componentsToRemove;
    }

    public static class Builder {
        private boolean hasItem;
        private Optional<Integer> itemId = Optional.empty();
        private Optional<Integer> itemCount = Optional.empty();
        private List<Component> componentsToAdd = List.of();
        private List<Integer> componentDataHashes = List.of();
        private List<Component> componentsToRemove = List.of();

        public Builder withHasItem(boolean hasItem) {
            this.hasItem = hasItem;
            return this;
        }

        public Builder withItemId(int itemId) {
            this.itemId = Optional.of(itemId);
            return this;
        }

        public Builder withItemCount(int itemCount) {
            this.itemCount = Optional.of(itemCount);
            return this;
        }

        public Builder withComponentsToAdd(List<Component> componentsToAdd, List<Integer> componentDataHashes) {
            if (componentsToAdd.size() != componentDataHashes.size()) {
                throw new IllegalArgumentException("Components to add and hashes must have the same size");
            }
            this.componentsToAdd = List.copyOf(componentsToAdd);
            this.componentDataHashes = List.copyOf(componentDataHashes);
            return this;
        }

        public Builder withComponentsToRemove(List<Component> componentsToRemove) {
            this.componentsToRemove = List.copyOf(componentsToRemove);
            return this;
        }

        public HashedSlot build() {
            if (hasItem && itemId.isEmpty()) {
                throw new IllegalStateException("Item ID must be present if hasItem is true");
            }
            if (hasItem && itemCount.isEmpty()) {
                throw new IllegalStateException("Item count must be present if hasItem is true");
            }
            if (componentsToAdd.size() != componentDataHashes.size()) {
                throw new IllegalStateException("Components to add and hashes must have the same size");
            }
            return new HashedSlot(hasItem, itemId, itemCount, componentsToAdd, componentDataHashes, componentsToRemove);
        }
    }
}

