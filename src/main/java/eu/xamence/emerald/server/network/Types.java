package eu.xamence.emerald.server.network;

import eu.xamence.emerald.server.geometry.Position;
import eu.xamence.emerald.server.geometry.Vector3D;
import eu.xamence.emerald.server.network.type.ChatType;
import eu.xamence.emerald.server.network.type.IDSet;
import eu.xamence.emerald.server.network.type.SoundEvent;
import eu.xamence.emerald.server.network.type.Type;
import eu.xamence.emerald.server.network.type.data.ChunkData;
import eu.xamence.emerald.server.network.type.data.HashedSlot;
import eu.xamence.emerald.server.network.type.data.LightData;
import eu.xamence.emerald.server.network.type.data.Slot;
import eu.xamence.emerald.server.network.type.display.RecipeDisplay;
import eu.xamence.emerald.server.network.type.display.SlotDisplay;
import eu.xamence.emerald.server.network.type.enumtype.TeleportFlags;
import eu.xamence.emerald.server.network.type.nbt.NBTUtils;
import eu.xamence.emerald.server.network.type.profile.GameProfile;
import eu.xamence.emerald.server.network.type.profile.ResolvableProfile;
import eu.xamence.emerald.server.network.type.utils.Either;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.BinaryTagType;
import net.kyori.adventure.text.Component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.*;

// https://minecraft.wiki/w/Java_Edition_protocol/Data_types
public record Types() {
    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;


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

    public static Type<String> STRING (int maxLength) {
        return new Type<String>() {
            @Override
            public String read(DataInputStream stream) throws IOException {
                int length = VAR_INT.read(stream);

                if (length > maxLength * 3)
                    throw new IOException("String too long");

                byte[] bytes = new byte[length];
                stream.readFully(bytes);

                return new String(bytes, StandardCharsets.UTF_8);
            }

            @Override
            public void write(DataOutputStream stream, String value) throws IOException {
                byte[] bytes =  value.getBytes(StandardCharsets.UTF_8);

                if (bytes.length > maxLength * 3)
                    throw new IOException("String too long");

                VAR_INT.write(stream, bytes.length);
                stream.write(bytes);
            }
        };
    }

    public static final Type<Component> COMPONENT = getComponentType();
    public static final Type<Component> JSON_COMPONENT = COMPONENT;
    public static final Type<String> IDENTIFIER = STRING(32767);
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
        return new Type<BitSet>() {

            final int byteLength = (int) Math.ceil(length / 8);
            @Override
            public BitSet read(DataInputStream stream) throws IOException {
                byte[] bytes = new byte[byteLength];

                stream.readFully(bytes);
                BitSet bitSet =  BitSet.valueOf(bytes);

                return bitSet.get(0, byteLength);
            }

            @Override
            public void write(DataOutputStream stream, BitSet value) throws IOException {
                byte[] bytes = value.toByteArray();

                // To be sure the bitSet contains the right length
                byte[] paddedBytes =  new byte[byteLength];
                System.arraycopy(bytes, 0, paddedBytes, 0, Math.min(bytes.length, byteLength));

                stream.write(paddedBytes);
            }
        }
    }


    /// If an exception like `EOFException` is trigger, it's cuz the optional is empty.
    public static <T> Type<Optional<T>> OPTIONAL(Type<T> type) {
        return new Type<Optional<T>>() {
            @Override
            public Optional<T> read(DataInputStream stream) throws IOException {
                try {
                    T value = type.read(stream);
                    return Optional.of(value);
                } catch (IOException _) {
                    return Optional.empty();
                }
            }

            @Override
            public void write(DataOutputStream stream, Optional<T> value) throws IOException {
                value.ifPresent(v -> {
                    try {
                        type.write(stream, v);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to write an Optional ", e);
                    }
                });
            }
        };

    }

    public static <T> Type<Optional<T>> PREFIXED_OPTIONAL(Type<T> type) {
        return new Type<Optional<T>>() {
            @Override
            public Optional<T> read(DataInputStream stream) throws IOException {
                boolean containsValue = stream.readBoolean();

                if (containsValue)
                    return Optional.of(type.read(stream));
                else
                    return Optional.empty();
            }

            @Override
            public void write(DataOutputStream stream, Optional<T> value) throws IOException {
                boolean containsValue = value.isPresent();

                stream.writeBoolean(containsValue);

                if (containsValue)
                    type.write(stream, value.get());
            }
        };
    }

    public static <T> Type<T[]> ARRAY(Type<T> type, int length) {
        return new Type<T[]>() {
            @Override
            public T[] read(DataInputStream stream) throws IOException {
                T[] array = (T[]) Array.newInstance(type.getClass().getComponentType(), length);

                for (int i = 0; i < length; i++)
                    array[i] = type.read(stream);

                return array;
            }

            @Override
            public void write(DataOutputStream stream, T[] value) throws IOException {
                if (value.length != length)
                    throw new IOException("Array length mismatch: expected " + length + ", got " + value.length);

                for (T element : value)
                    type.write(stream, element);
            }
        };
    }

    public static <T> Type<T[]> PREFIXED_ARRAY(Type<T> type) {
        return new Type<T[]>() {
            @Override
            public T[] read(DataInputStream stream) throws IOException {
                int length = VAR_INT.read(stream);

                T[] array = (T[]) Array.newInstance(type.getClass().getComponentType(), length);

                for (int i = 0; i< length; i++)
                    array[i] = type.read(stream);

                return array;
            }

            @Override
            public void write(DataOutputStream stream, T[] value) throws IOException {
                int length = value.length;
                VAR_INT.write(stream, length);

                for (T element : value) type.write(stream, element);
            }
        };
    }

    public static <E extends Enum<E>> Type<E> ENUM(Class<E> enumClass) {
        final E[] constants = enumClass.getEnumConstants();

        return new Type<E>() {
            @Override
            public E read(DataInputStream stream) throws IOException {
                int ordinal = Types.VAR_INT.read(stream);

                if (ordinal < 0 || ordinal >= constants.length)
                    throw new IOException("Invalid enum ordinal: " + ordinal);

                return constants[ordinal];
            }

            @Override
            public void write(DataOutputStream stream, E value) throws IOException {
                if (value == null)
                    throw new IOException("Enum value cannot be null");

                Types.VAR_INT.write(stream, value.ordinal());
            }
        };
    }

    public static <E extends Enum<E>> Type<EnumSet<E>> ENUM_SET(Class<E> enumClass) {
        final E[] values = enumClass.getEnumConstants();
        final int length = values.length;
        final Type<BitSet> bitSetType = Types.FIXED_BIT_SET(length);


        return new Type<EnumSet<E>>() {
            @Override
            public EnumSet<E> read(DataInputStream stream) throws IOException {
                BitSet bitSet = bitSetType.read(stream);
                EnumSet<E> set = EnumSet.noneOf(enumClass);

                for (int i = 0; i < length; i++) {
                    if (bitSet.get(i)) {
                        set.add(values[i]);
                    }
                }

                return set;
            }

            @Override
            public void write(DataOutputStream stream, EnumSet<E> value) throws IOException {
                BitSet bitSet = new BitSet(length);

                for (int i = 0; i < length; i++) {
                    bitSet.set(i, value.contains(values[i]));
                }

                bitSetType.write(stream, bitSet);
            }
        };
    }

    public static Type<byte[]> BYTE_ARRAY(int length) {
        return new Type<byte[]>() {
            @Override
            public byte[] read(DataInputStream stream) throws IOException {
                byte[] data = new byte[length];
                stream.readFully(data);

                return data;
            }

            @Override
            public void write(DataOutputStream stream, byte[] value) throws IOException {
                if (value.length != length)
                    throw new IOException("Byte array lentgh mismatch. Need " + length + ", but got " + value.length);

                stream.write(value);
            }
        };
    }

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

    public static final Type<GameProfile> GAME_PROFILE = getGameProfileType();

    public static final Type<ResolvableProfile> RESOLVABLE_PROFILE = getResolvableProfile();

    public static final Type<Vector3D> LP_VECTOR3 = getLpVector3Type();

    private static Type<Boolean> getBooleanType() {
        return new Type<Boolean>() {
            @Override
            public Boolean read(DataInputStream stream) throws IOException {
                return stream.readBoolean();
            }

            @Override
            public void write(DataOutputStream stream, Boolean value) throws IOException {
                stream.writeBoolean(value);
            }
        };
    }

    private static Type<Byte> getByteType() {
        return new Type<Byte>() {
            @Override
            public Byte read(DataInputStream stream) throws IOException {
                return stream.readByte();
            }

            @Override
            public void write(DataOutputStream stream, Byte value) throws IOException {
                stream.writeByte(value);
            }
        };
    }

    private static Type<Byte> getUnsignedByteType() {
        return new Type<Byte>() {
            @Override
            public Byte read(DataInputStream stream) throws IOException {
                return (byte) (stream.readByte() & 0xFF);
            }

            @Override
            public void write(DataOutputStream stream, Byte value) throws IOException {
                stream.writeByte(value);
            }
        };
    }

    private static Type<Short> getShortType() {
        return new Type<Short>() {
            @Override
            public Short read(DataInputStream stream) throws IOException {
                return stream.readShort();
            }

            @Override
            public void write(DataOutputStream stream, Short value) throws IOException {
                stream.writeShort(value);
            }
        };
    }

    private static Type<Short> getUnsignedShortType() {
        return new Type<Short>() {
            @Override
            public Short read(DataInputStream stream) throws IOException {
                return (short) stream.readUnsignedShort();
            }

            @Override
            public void write(DataOutputStream stream, Short value) throws IOException {
                stream.writeShort(value);
            }
        };
    }

    private static Type<Integer> getIntegerType() {
        return new Type<Integer>() {
            @Override
            public Integer read(DataInputStream stream) throws IOException {
                return stream.readInt();
            }

            @Override
            public void write(DataOutputStream stream, Integer value) throws IOException {
                stream.writeInt(value);
            }
        };
    }

    private static Type<Long> getLongType() {
        return new Type<Long>() {
            @Override
            public Long read(DataInputStream stream) throws IOException {
                return stream.readLong();
            }

            @Override
            public void write(DataOutputStream stream, Long value) throws IOException {
                stream.writeLong(value);
            }
        };
    }

    private static Type<Float> getFloatType() {
        return new Type<Float>() {
            @Override
            public Float read(DataInputStream stream) throws IOException {
                return stream.readFloat();
            }

            @Override
            public void write(DataOutputStream stream, Float value) throws IOException {
                stream.writeFloat(value);
            }
        };
    }

    private static Type<Double> getDoubleType() {
        return new Type<Double>() {
            @Override
            public Double read(DataInputStream stream) throws IOException {
                return stream.readDouble();
            }

            @Override
            public void write(DataOutputStream stream, Double value) throws IOException {
                stream.writeDouble(value);
            }
        };
    }

    private static Type<Component> getComponentType() {
        // TODO
    }

    private static Type<Integer> getVarIntType() {
        return new Type<>() {
            @Override
            public Integer read(DataInputStream in) throws IOException {
                int value = 0;
                int length = 0;
                byte currentByte;

                do {
                    currentByte = in.readByte();
                    value |= (currentByte & SEGMENT_BITS) << (length * 7);
                    length++;
                    if (length > 5) {
                        throw new IOException("VarInt too long");
                    }
                } while ((currentByte & CONTINUE_BIT) == CONTINUE_BIT);

                return value;
            }

            @Override
            public void write(DataOutputStream out, Integer value) throws IOException {
                while (true) {

                    if ((value & ~SEGMENT_BITS) == 0) {
                        out.writeByte(value);
                        return;
                    } else {
                        out.writeByte((value & SEGMENT_BITS) | CONTINUE_BIT);
                        value >>>= 7;
                    }
                }
            }
        };
    }

    private static Type<Long> getVarLongType() {
        return new Type<>() {
            @Override
            public Long read(DataInputStream in) throws IOException {
                long value = 0;
                int position = 0;
                byte currentByte;

                while (true) {
                    currentByte = in.readByte();
                    value |= (long) (currentByte & SEGMENT_BITS) << position;

                    if ((currentByte & CONTINUE_BIT) == 0) break;

                    position += 7;

                    if (position >= 64) throw new RuntimeException("VarLong is too big");
                }

                return value;
            }

            @Override
            public void write(DataOutputStream out, Long value) throws IOException {
                while (true) {
                    if ((value & ~ SEGMENT_BITS) == 0) {
                        out.writeByte(Math.toIntExact(value));
                        return;
                    }

                    out.write((int) ((value & SEGMENT_BITS) | CONTINUE_BIT));

                    // Note: >>> means that the leftmost bits are filled with zeroes regardless of the sign,
                    // rather than being filled with copies of the sign bit to preserve the sign.
                    // In languages that don't have a ">>>" operator, This behavior can often be selected by
                    // performing the shift on an unsigned type.
                    value >>>= 7;
                }
            }
        };
    }

    private static Type<Slot> getSlotType() {
        return new Type<Slot>() {
            @Override
            public Slot read(DataInputStream stream) throws IOException {
                int itemCount = VAR_INT.read(stream);
                Optional<Integer> itemID = Optional.empty();
                List<Component> componentToAdd = List.of();
                List<Component> componentToRemove = List.of();

                if (itemCount > 0) {
                    itemID = OPTIONAL(VAR_INT).read(stream);

                    int componentToAddCount = VAR_INT.read(stream);
                    componentToAdd = List.of(PREFIXED_ARRAY(COMPONENT).read(stream));

                    int componentToRemoveCount = VAR_INT.read(stream);
                    componentToRemove = List.of(PREFIXED_ARRAY(COMPONENT).read(stream));
                }

                return new Slot(itemCount, itemID.orElse(null), componentToAdd, componentToRemove);
            }

            @Override
            public void write(DataOutputStream stream, Slot value) throws IOException {
                VAR_INT.write(stream, value.getItemCount());

                if (value.getItemCount() > 0) {
                    VAR_INT.write(stream, value.getItemId().orElseThrow(() -> new IOException("Item ID is missing")));

                    PREFIXED_ARRAY(COMPONENT).write(stream, value.getComponentsToAdd().toArray(new Component[0]));
                    PREFIXED_ARRAY(COMPONENT).write(stream, value.getComponentsToRemove().toArray(new Component[0]));

                }
            }
        };
    }

    private static Type<HashedSlot> getHashedSlotType() {
        return new Type<HashedSlot>() {
            @Override
            public HashedSlot read(DataInputStream stream) throws IOException {
                boolean hasItem = BOOLEAN.read(stream);

                Optional<Integer> itemId = Optional.empty();
                Optional<Integer> itemCount = Optional.empty();
                List<Component> componentsToAdd = new ArrayList<>();
                List<Integer> componentDataHashes = new ArrayList<>();
                List<Component> componentsToRemove = new ArrayList<>();

                if (hasItem) {
                    itemId = Optional.of(VAR_INT.read(stream));

                    itemCount = Optional.of(VAR_INT.read(stream));

                    int componentsToAddCount = VAR_INT.read(stream);
                    for (int i = 0; i < componentsToAddCount; i++) {
                        Component component = COMPONENT.read(stream);
                        int dataHash = INTEGER.read(stream);
                        componentsToAdd.add(component);
                        componentDataHashes.add(dataHash);
                    }

                    int componentsToRemoveCount = VAR_INT.read(stream);
                    for (int i = 0; i < componentsToRemoveCount; i++) {
                        componentsToRemove.add(COMPONENT.read(stream));
                    }
                }

                return new HashedSlot(hasItem, itemId, itemCount, componentsToAdd, componentDataHashes, componentsToRemove);
            }



            @Override
            public void write(DataOutputStream stream, HashedSlot value) throws IOException {
                Types.BOOLEAN.write(stream, value.hasItem());

                if (value.hasItem()) {
                    Types.VAR_INT.write(stream, value.getItemId().orElseThrow(() -> new IOException("Item ID is missing")));

                    Types.VAR_INT.write(stream, value.getItemCount().orElseThrow(() -> new IOException("Item count is missing")));

                    Types.VAR_INT.write(stream, value.getComponentsToAdd().size());
                    for (int i = 0; i < value.getComponentsToAdd().size(); i++) {
                        Types.COMPONENT.write(stream, value.getComponentsToAdd().get(i));
                        Types.INTEGER.write(stream, value.getComponentDataHashes().get(i));
                    }

                    Types.VAR_INT.write(stream, value.getComponentsToRemove().size());
                    for (Component component : value.getComponentsToRemove()) {
                        Types.COMPONENT.write(stream, component);
                    }
                }

            }
        };
    }


    private static Type<BinaryTag> getNBTType() {
        return new Type<BinaryTag>() {
            @Override
            public BinaryTag read(DataInputStream stream) throws IOException {
                BinaryTagType<? extends BinaryTag> type = NBTUtils.nbtTypeFromId(stream.readByte());
                return type.read(stream);
            }

            @Override
            public void write(DataOutputStream stream, BinaryTag value) throws IOException {
                BinaryTagType<BinaryTag> type = (BinaryTagType<BinaryTag>) value.type();
                stream.writeByte(type.id());
                type.write(value, stream);
            }
        };
    }

    private static Type<Position> getPositionType() {
        return new Type<Position>() {
            @Override
            public Position read(DataInputStream stream) throws IOException {
                long encodedPosition = stream.readLong();
                return Position.decodePosition(encodedPosition);
            }

            @Override
            public void write(DataOutputStream stream, Position value) throws IOException {
                long encodedPosition = Position.encodePosition(value);
                stream.writeLong(encodedPosition);
            }
        };
    }

    private static Type<Byte> getAngleType() {
        return new Type<Byte>() {
            @Override
            public Byte read(DataInputStream stream) throws IOException {
                return stream.readByte();
            }

            @Override
            public void write(DataOutputStream stream, Byte value) throws IOException {
                stream.writeByte(value);
            }
        };
    }

    private static Type<UUID> getUUIDType() {
        return new Type<UUID>() {
            @Override
            public UUID read(DataInputStream stream) throws IOException {
                long mostSigBits = stream.readLong();
                long leastSigBits = stream.readLong();
                return new UUID(mostSigBits, leastSigBits);
            }

            @Override
            public void write(DataOutputStream stream, UUID value) throws IOException {
                stream.writeLong(value.getMostSignificantBits());
                stream.writeLong(value.getLeastSignificantBits());
            }
        };
    }

    private static Type<BitSet> getBitSetType() {
        return new Type<BitSet>() {
            @Override
            public BitSet read(DataInputStream stream) throws IOException {
                int length = VAR_INT.read(stream);

                long[] longs = new long[length];

                for (int i = 0; i < length; i++)
                    longs[i] = stream.readLong();

                return BitSet.valueOf(longs);
            }

            @Override
            public void write(DataOutputStream stream, BitSet value) throws IOException {
                long[] longs = value.toLongArray();

                VAR_INT.write(stream, longs.length);

                for (long l : longs)
                    stream.writeLong(l);
            }
        };
    }

    public static byte toAngleByte(float degrees) {
        return (byte) (degrees * 256f / 360f);
    }

    public static float toDegrees(float angleBytes) {
        return angleBytes * 360f / 256f;
    }


}