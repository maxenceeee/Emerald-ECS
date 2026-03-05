package eu.xamence.emerald.server.network.type.data;

import eu.xamence.emerald.server.network.type.display.BlockEntity;

import java.util.List;
import java.util.Objects;

public record ChunkData(List<Heightmap> heightmaps, byte[] data,List<BlockEntity> blockEntities) {

    public static class Builder {
        private List<Heightmap> heightmaps = List.of();
        private byte[] data = new byte[0];
        private List<BlockEntity> blockEntities = List.of();

        public Builder withHeightmaps(List<Heightmap> heightmaps) {
            this.heightmaps = List.copyOf(heightmaps);
            return this;
        }

        public Builder withData(byte[] data) {
            this.data = data.clone();
            return this;
        }

        public Builder withBlockEntities(List<BlockEntity> blockEntities) {
            this.blockEntities = List.copyOf(blockEntities);
            return this;
        }

        public ChunkData build() {
            Objects.requireNonNull(heightmaps, "Heightmaps cannot be null");
            Objects.requireNonNull(data, "Data cannot be null");
            Objects.requireNonNull(blockEntities, "Block entities cannot be null");
            return new ChunkData(heightmaps, data, blockEntities);
        }
    }
}
