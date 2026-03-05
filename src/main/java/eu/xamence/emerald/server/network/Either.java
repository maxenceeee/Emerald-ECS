package eu.xamence.emerald.server.network;

public record Either<ID, X>(ID id, X value, boolean isId) {

    public static <ID, X> Either<ID, X> id(ID id) {
        return new Either<>(id, null, true);
    }

    public static <ID, X> Either<ID, X> value(X value) {
        return new Either<>(null, value, false);
    }

    public boolean isId() {
        return isId;
    }

    public ID id() {
        if (!isId) {
            throw new IllegalStateException("Not an ID");
        }
        return id;
    }

    public X value() {
        if (isId) {
            throw new IllegalStateException("Not a value");
        }
        return value;
    }
}
