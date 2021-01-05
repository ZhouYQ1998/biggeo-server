package edu.zju.gis.dldsj.server.utils;

/**
 * @author Sun Katus
 * @version 1.0, 2021-01-04
 */
public final class TileUtil {
    public static final Double[] EXTENT_3857 = new Double[]{-20026376.39, 20026376.39, -20048966.10, 20048966.10};
    public static final Double[] EXTENT_4326 = new Double[]{-180.0, 180.0, -90.0, 90.0};

    public static double tile2Lat(int y, int z) {
        int n = 1 << z;
        return Math.atan(Math.sinh(Math.PI * (1 - 2.0 * y / n))) * 180.0 / Math.PI;
    }

    public static double tile2Lon(int x, int z) {
        int n = 1 << z;
        return 1.0 * x / n * 360 - 180;
    }

    public static int[] getTileId(double lon, double lat, int z) {
        int[] x_y = {-1, -1};
        x_y[0] = (int) ((1 << z) * ((lon + 180) / 360.0));
        double part = 1 - ((Math.log(Math.tan(lat * Math.PI / 180) + (1.0 / Math.cos(lat * Math.PI / 180)))) / Math.PI);
        x_y[1] = (int) ((1 << z) * part / 2);
        return x_y;
    }

    public static double tile2x(int x, int z) {
        int n = 1 << z;
        return 1.0 * x / n * (EXTENT_3857[1] - EXTENT_3857[0]) + EXTENT_3857[0];
    }

    public static double tile2y(int y, int z) {
        int n = 1 << z;
        return 1.0 * y / n * (EXTENT_3857[3] - EXTENT_3857[2]) + EXTENT_3857[2];
    }
}
