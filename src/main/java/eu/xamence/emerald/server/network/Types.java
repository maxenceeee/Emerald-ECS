package eu.xamence.emerald.server.network;

import java.util.UUID;

// https://minecraft.wiki/w/Java_Edition_protocol/Data_types
public final class Types {

    // Primitive
    public static final Type<Boolean> BOOLEAN = getBooleanType();
    public static final Type<Byte> BYTE = getByteType();
    public static final Type<Byte> UNSIGNED_BYTE = getUnsignedByteType();
    public static final Type<Short> SHORT = getShortType();
    public static final Type<Short> UNSIGNED_SHORT = getUnsignedShortType();
    public static final Type<Integer> INTEGER = getIntegerType();
    public static final Type<Long> LONG = getLongType();
    public static final Type<Float> FLOAT = getFloatType();
    public static final Type<Double> DOUBLE = getDoubleType();
    public static final Type<String> STRING = getStringType();
    // TODO: TextComponent, JSON TEXT Component
    public static final Type<String> IDENTIFIER = getIdentifierType();
    public static final Type<Integer> VAR_INT = getVarIntType();
    public static final Type<Long> VAR_LONG = getVarLong();
    // TODO: EntityMetadata, Slot, HashedSlot, NBT
    public static final Type<int[]> POSITION = getPositionType();
    public static final Type<Byte> ANGLE = getAngleType();

}