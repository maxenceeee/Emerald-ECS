package eu.xamence.emerald.server.network;

import java.util.Objects;
import java.util.Optional;

public record SoundEvent(String soundName, boolean hasFixedRange, Optional<Float> fixedRange) {

    public SoundEvent(String soundName, boolean hasFixedRange, float fixedRange) {
        this(Objects.requireNonNull(soundName, "Sound name cannot be null"), hasFixedRange, hasFixedRange ?
                Optional.of(fixedRange) :
                Optional.empty());

    }

    public static SoundEvent withVariableRange(String soundName) {
        return new SoundEvent(soundName, false, Optional.empty());
    }

    public static SoundEvent withFixedRange(String soundName, float fixedRange) {
        return new SoundEvent(soundName, true, Optional.of(fixedRange));
    }


}
