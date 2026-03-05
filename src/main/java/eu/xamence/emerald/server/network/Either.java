package eu.xamence.emerald.server.network;

import java.util.Objects;

public final class Either<X, Y> {
    private final X x;
    private final Y y;
    private final boolean isX;

    private Either(X x, Y y, boolean isX) {
        this.x = x;
        this.y = y;
        this.isX = isX;
    }

    public static <X, Y> Either<X, Y> ofX(X x) {
        return new Either<>(x, null, true);
    }

    public static <X, Y> Either<X, Y> ofY(Y y) {
        return new Either<>(null, y, false);
    }

    public boolean isX() {
        return isX;
    }

    public boolean isY() {
        return !isX;
    }

    public X getX() {
        if (!isX) {
            throw new IllegalStateException("Not X");
        }
        return x;
    }

    public Y getY() {
        if (isX) {
            throw new IllegalStateException("Not Y");
        }
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Either<?, ?> either = (Either<?, ?>) o;
        return isX == either.isX &&
                Objects.equals(x, either.x) &&
                Objects.equals(y, either.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, isX);
    }
}

