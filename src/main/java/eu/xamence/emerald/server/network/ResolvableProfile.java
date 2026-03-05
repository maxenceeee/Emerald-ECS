package eu.xamence.emerald.server.network;

import java.util.Objects;
import java.util.Optional;

public final class ResolvableProfile {
    private final ProfileKind kind;
    private final Object unpack;
    private final Optional<String> body;
    private final Optional<String> cape;
    private final Optional<String> elytra;
    private final Optional<Integer> model;

    private ResolvableProfile(ProfileKind kind, Object unpack, Optional<String> body,
                              Optional<String> cape, Optional<String> elytra, Optional<Integer> model) {
        this.kind = Objects.requireNonNull(kind, "Kind cannot be null");
        this.unpack = unpack;
        this.body = body;
        this.cape = cape;
        this.elytra = elytra;
        this.model = model;
    }

    public ProfileKind getKind() {
        return kind;
    }

    public Object getUnpack() {
        return unpack;
    }

    public Optional<String> getBody() {
        return body;
    }

    public Optional<String> getCape() {
        return cape;
    }

    public Optional<String> getElytra() {
        return elytra;
    }

    public Optional<Integer> getModel() {
        return model;
    }

    public static class Builder {
        private ProfileKind kind;
        private Object unpack;
        private Optional<String> body = Optional.empty();
        private Optional<String> cape = Optional.empty();
        private Optional<String> elytra = Optional.empty();
        private Optional<Integer> model = Optional.empty();

        public Builder withKind(ProfileKind kind) {
            this.kind = kind;
            return this;
        }

        public Builder withUnpack(Object unpack) {
            this.unpack = unpack;
            return this;
        }

        public Builder withBody(String body) {
            this.body = Optional.of(body);
            return this;
        }

        public Builder withCape(String cape) {
            this.cape = Optional.of(cape);
            return this;
        }

        public Builder withElytra(String elytra) {
            this.elytra = Optional.of(elytra);
            return this;
        }

        public Builder withModel(int model) {
            this.model = Optional.of(model);
            return this;
        }

        public ResolvableProfile build() {
            Objects.requireNonNull(kind, "Kind cannot be null");
            Objects.requireNonNull(unpack, "Unpack cannot be null");
            return new ResolvableProfile(kind, unpack, body, cape, elytra, model);
        }
    }
}

