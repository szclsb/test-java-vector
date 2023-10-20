package ch.szclsb.tjv;

public class MathUtils {
    public static final float TOLERANCE = 0.000001f;

    public static boolean isFloatEquals(float a, float b) {
        return Math.abs(a - b) < TOLERANCE;
    }

    private MathUtils() {}
}
