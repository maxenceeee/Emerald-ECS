package eu.xamence.emerald.server.geometry;


public record Position(double x, double y, double z, float yaw, double pitch) implements SpacePoint{

    public Position(double x, double y, double z) {
        this(x, y, z, 0, 0);
    }

    public Position(SpacePoint point, float yaw, float pitch) {
        this(point.x(), point.y(), point.z(), yaw, pitch);
    }

    public Position(SpacePoint point) {
        this(point, 0, 0);
    }

    public static Position decodePosition(long positionEncoded) {
        int x = (int) (positionEncoded >> 38);
        int y = (int) ((positionEncoded >> 26) & 0xFFF); // 0xFFF = 4095
        int z = (int) (positionEncoded << 38 >> 38);

        return new Position(x, y, z);
    }

    public static long encodePosition(Position position) {
        int x = (int) position.x();
        int y = (int) position.y();
        int z = (int) position.z();

        return ((long) x & 0x3FFFFFF) << 38 | ((long) y & 0xFFF) << 26 | ((long) z & 0x3FFFFFF);
    }
}
