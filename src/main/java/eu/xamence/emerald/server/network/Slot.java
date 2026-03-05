package eu.xamence.emerald.server.network;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

// https://minecraft.wiki/w/Java_Edition_protocol/Slot_data
public record Slot(int itemCount, @Nullable Integer itemId, List<Component> componentsToAdd, List<Component> componentsToRemove) {

    public int getItemCount() {
        return itemCount;
    }

    public Optional<Integer> getItemId() {
        return Optional.ofNullable(itemId);
    }

    public List<Component> getComponentsToAdd() {
        return componentsToAdd;
    }

    public List<Component> getComponentsToRemove() {
        return componentsToRemove;
    }

    public static class Builder {
        private int itemCount;
        private Optional<Integer> itemId = Optional.empty();
        private List<Component> componentsToAdd = List.of();
        private List<Component> componentsToRemove = List.of();

        public Builder withItemCount(int itemCount) {
            this.itemCount = itemCount;
            return this;
        }

        public Builder withItemId(int itemId) {
            this.itemId = Optional.of(itemId);
            return this;
        }

        public Builder withComponentsToAdd(List<Component> componentsToAdd) {
            this.componentsToAdd = List.copyOf(componentsToAdd);
            return this;
        }

        public Builder withComponentsToRemove(List<Component> componentsToRemove) {
            this.componentsToRemove = List.copyOf(componentsToRemove);
            return this;
        }

        public Slot build() {
            if (itemCount < 0) {
                throw new IllegalStateException("Item count cannot be negative");
            }
            if (itemCount > 0 && itemId.isEmpty()) {
                throw new IllegalStateException("Item ID must be present if item count is greater than zero");
            }
            return new Slot(itemCount, itemId.orElse(null), componentsToAdd, componentsToRemove);
        }
    }
}
