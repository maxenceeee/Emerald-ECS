package eu.xamence.emerald.server.geometry;

public class GeoUtils {

    private GeoUtils() {
        throw new IllegalStateException("Utility class.");
    }

    public static int toBlockCoordinate(double value) {
        return (int) Math.floor(value);
    }
}
