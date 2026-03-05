package eu.xamence.emerald.server.geometry;

public interface SpacePoint {

    double x();
    double y();
    double z();

    default int blockX() {
        return GeoUtils.toBlockCoordinate(x());
    }

    default int blockY() {
        return GeoUtils.toBlockCoordinate(y());
    }

    default int blockZ() {
        return GeoUtils.toBlockCoordinate(z());
    }
}
