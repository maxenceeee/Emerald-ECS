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
}
