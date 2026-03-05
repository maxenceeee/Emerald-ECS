package eu.xamence.emerald.server.network.type.display;

import net.kyori.adventure.nbt.BinaryTag;

import java.util.Objects;

public record BlockEntity(int packedXZ, short y, int type, BinaryTag data) {

    public BlockEntity(int packedXZ, short y, int type, BinaryTag data) {
        this.packedXZ = packedXZ;
        this.y = y;
        this.type = type;
        this.data = Objects.requireNonNull(data, "Data cannot be null");
    }

    public int getX() {
        return packedXZ >> 4;
    }

    public int getZ() {
        return packedXZ & 15;
    }

    public static int packXZ(int x, int z) {
        return ((x & 15) << 4) | (z & 15);
    }
}
