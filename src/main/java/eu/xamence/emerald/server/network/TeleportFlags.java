package eu.xamence.emerald.server.network;

import java.util.BitSet;

public record TeleportFlags(BitSet flags) {

    public enum Flag {
        RELATIVE_X(0x0001),
        RELATIVE_Y(0x0002),
        RELATIVE_Z(0x0004),
        RELATIVE_YAW(0x0008),
        RELATIVE_PITCH(0x0010),
        RELATIVE_VELOCITY_X(0x0020),
        RELATIVE_VELOCITY_Y(0x0040),
        RELATIVE_VELOCITY_Z(0x0080),
        ROTATE_VELOCITY(0x0100);

        private final int mask;

        Flag(int mask) {
            this.mask = mask;
        }

        public int getMask() {
            return mask;
        }
    }


    public static TeleportFlags of(int rawFlags) {
        BitSet flags = BitSet.valueOf(new long[]{rawFlags & 0xFFFFFFFFL});
        return new TeleportFlags(flags);
    }

    public static TeleportFlags empty() {
        return new TeleportFlags(new BitSet(Flag.values().length));
    }

    public boolean isSet(Flag flag) {
        return flags.get(flag.ordinal());
    }

    public TeleportFlags with(Flag flag) {
        BitSet newFlags = (BitSet) flags.clone();
        newFlags.set(flag.ordinal());
        return new TeleportFlags(newFlags);
    }

    public int toInt() {
        long[] bits = flags.toLongArray();
        return bits.length > 0 ? (int) bits[0] : 0;
    }
}
