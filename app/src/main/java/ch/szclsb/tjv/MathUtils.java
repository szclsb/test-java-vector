package ch.szclsb.tjv;

public class MathUtils {
    public static final float TOLERANCE = 0.000001f;

    public static boolean isFloatEquals(float a, float b) {
        return Math.abs(a - b) < TOLERANCE;
    }

    private static long cantorPairing(int x, int y) {
        return ((x + y) * (x + y + 1L) + y) / 2L;
    }

    private MathUtils() {}
}
