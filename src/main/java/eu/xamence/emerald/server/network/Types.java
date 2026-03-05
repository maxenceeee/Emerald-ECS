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
import eu.xamence.emerald.server.network.type.profile.GameProfile;
import eu.xamence.emerald.server.network.type.profile.ResolvableProfile;
import eu.xamence.emerald.server.network.type.utils.Either;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.text.Component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
}