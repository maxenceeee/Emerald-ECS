package eu.xamence.emerald.server.network;

import java.util.BitSet;
import java.util.List;
import java.util.Objects;

public final class LightData {
    private final BitSet skyLightMask;
    private final BitSet blockLightMask;
    private final BitSet emptySkyLightMask;
    private final BitSet emptyBlockLightMask;
    private final List<byte[]> skyLightArrays;
    private final List<byte[]> blockLightArrays;

    private LightData(BitSet skyLightMask, BitSet blockLightMask, BitSet emptySkyLightMask,
                      BitSet emptyBlockLightMask, List<byte[]> skyLightArrays, List<byte[]> blockLightArrays) {
        this.skyLightMask = (BitSet) skyLightMask.clone();
        this.blockLightMask = (BitSet) blockLightMask.clone();
        this.emptySkyLightMask = (BitSet) emptySkyLightMask.clone();
        this.emptyBlockLightMask = (BitSet) emptyBlockLightMask.clone();
        this.skyLightArrays = List.copyOf(skyLightArrays);
        this.blockLightArrays = List.copyOf(blockLightArrays);
    }

    public BitSet getSkyLightMask() {
        return (BitSet) skyLightMask.clone();
    }

    public BitSet getBlockLightMask() {
        return (BitSet) blockLightMask.clone();
    }

    public BitSet getEmptySkyLightMask() {
        return (BitSet) emptySkyLightMask.clone();
    }

    public BitSet getEmptyBlockLightMask() {
        return (BitSet) emptyBlockLightMask.clone();
    }

    public List<byte[]> getSkyLightArrays() {
        return skyLightArrays;
    }

    public List<byte[]> getBlockLightArrays() {
        return blockLightArrays;
    }

    public static class Builder {
        private BitSet skyLightMask = new BitSet();
        private BitSet blockLightMask = new BitSet();
        private BitSet emptySkyLightMask = new BitSet();
        private BitSet emptyBlockLightMask = new BitSet();
        private List<byte[]> skyLightArrays = List.of();
        private List<byte[]> blockLightArrays = List.of();

        public Builder withSkyLightMask(BitSet skyLightMask) {
            this.skyLightMask = (BitSet) skyLightMask.clone();
            return this;
        }

        public Builder withBlockLightMask(BitSet blockLightMask) {
            this.blockLightMask = (BitSet) blockLightMask.clone();
            return this;
        }

        public Builder withEmptySkyLightMask(BitSet emptySkyLightMask) {
            this.emptySkyLightMask = (BitSet) emptySkyLightMask.clone();
            return this;
        }

        public Builder withEmptyBlockLightMask(BitSet emptyBlockLightMask) {
            this.emptyBlockLightMask = (BitSet) emptyBlockLightMask.clone();
            return this;
        }

        public Builder withSkyLightArrays(List<byte[]> skyLightArrays) {
            this.skyLightArrays = List.copyOf(skyLightArrays);
            return this;
        }

        public Builder withBlockLightArrays(List<byte[]> blockLightArrays) {
            this.blockLightArrays = List.copyOf(blockLightArrays);
            return this;
        }

        public LightData build() {
            Objects.requireNonNull(skyLightMask, "Sky light mask cannot be null");
            Objects.requireNonNull(blockLightMask, "Block light mask cannot be null");
            Objects.requireNonNull(emptySkyLightMask, "Empty sky light mask cannot be null");
            Objects.requireNonNull(emptyBlockLightMask, "Empty block light mask cannot be null");
            Objects.requireNonNull(skyLightArrays, "Sky light arrays cannot be null");
            Objects.requireNonNull(blockLightArrays, "Block light arrays cannot be null");

            if (skyLightArrays.size() != skyLightMask.cardinality()) {
                throw new IllegalStateException("Number of sky light arrays must match the number of set bits in sky light mask");
            }
            if (blockLightArrays.size() != blockLightMask.cardinality()) {
                throw new IllegalStateException("Number of block light arrays must match the number of set bits in block light mask");
            }

            for (byte[] array : skyLightArrays) {
                if (array.length != 2048) {
                    throw new IllegalStateException("Each sky light array must have a length of 2048 bytes");
                }
            }
            for (byte[] array : blockLightArrays) {
                if (array.length != 2048) {
                    throw new IllegalStateException("Each block light array must have a length of 2048 bytes");
                }
            }

            return new LightData(skyLightMask, blockLightMask, emptySkyLightMask, emptyBlockLightMask, skyLightArrays, blockLightArrays);
        }
    }
}

