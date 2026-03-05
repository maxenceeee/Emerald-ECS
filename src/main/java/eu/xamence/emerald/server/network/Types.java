package eu.xamence.emerald.server.network;

import eu.xamence.emerald.server.geometry.Position;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.text.Component;

import java.util.BitSet;
import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

// https://minecraft.wiki/w/Java_Edition_protocol/Data_types
public record Types() {

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
    public static final Type<Component> COMPONENT = getComponentType();
    public static final Type<Component> JSON_COMPONENT = getComponentType();
    public static final Type<String> IDENTIFIER = getIdentifierType();
    public static final Type<Integer> VAR_INT = getVarIntType();
    public static final Type<Long> VAR_LONG = getVarLongType();
    // TODO: EntityMetadata
    public static final Type<Slot> SLOT = getSlotType();
    public static final Type<HashedSlot> HASHED_SLOT = getHashedSlotType();
    public static final Type<BinaryTag> NBT = getNBTType();
    public static final Type<Position> POSITION = getPositionType();
    public static final Type<Byte> ANGLE = getAngleType();
    public static final Type<UUID> UUID = getUUIDType();
    public static final Type<BitSet> BIT_SET = getBitSetType();

    public static Type<BitSet> FIXED_BIT_SET(int length) {
        // TODO
    }

    public static <T> Type<Optional<T>> OPTIONAL(Type<T> type) {
        // TODO
    }

    public static <T> Type<Optional<T>> PREFIXED_OPTIONAL(Type<T> type) {
        // TODO
    }

    public static <T> Type<T[]> ARRAY(Type<T> type) {
        // TODO
    }

    public static <T> Type<T[]> PREFIXED_ARRAY(Type<T> type) {
        // TODO
    }

    public static <E extends Enum<E>> Type<E> ENUM(Class<E> enumClass) {
        //TODO
    }

    public static <E extends Enum<E>> Type<EnumSet<E>> ENUM_SET(Class<E> enumClass) {
        // TODO
    }

    public static final Type<Byte[]> BYTE_ARRAY = getByteArrayType();


    public static <T> Type<Either<Integer, T>> ID_OR_X(Type<T> type) {
        // TODO
    }

    public static final Type<IDSet> ID_SET = getIdSetType();

    public static final Type<SoundEvent> SOUND_EVENT = getSoundEventType();

    public static final Type<ChatType> CHAT_TYPE = getChatType();

    public static final Type<TeleportFlags> TELEPORT_FLAGS = getTeleportFlagsType();

    public static final Type<RecipeDisplay> RECIPE_DISPLAY = getRecipeDisplayType();

    public static final Type<SlotDisplay> SLOT_DISPLAY = getSlotDisplayType();

    public static final Type<ChunkData> CHUNK_DATA = getChunkDataType();

    public static final Type<LightData> LIGHT_DATA = getLightData();

    public static <X, Y> Type<Either<X, Y>> X_OR_Y(Type<X> xType, Type<Y> yType) {
        // TODO
    }

}