package ch.szclsb.jvpt;

import ch.szclsb.tjv.PojoFMatrix2x2;
import ch.szclsb.tjv.PojoFMatrix2x2Api;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FMatrixApiTest {
    private final PojoFMatrix2x2Api api2x2 = PojoFMatrix2x2Api.getInstance();
//    private final PojoFMatrix2x2Api api4x4 = PojoFMatrix2x2Api.getInstance();

    @Test
    public void matrix_equals() {
        var A2x2 = new PojoFMatrix2x2(1f, 2f, 3f, 4f);
        var B2x2 = new PojoFMatrix2x2(1f, 2f, 3f, 4f);
        var C2x2 = new PojoFMatrix2x2(2f, -4f, 6f, -8f);

        assertTrue(api2x2.equals(A2x2, B2x2));
        assertFalse(api2x2.equals(A2x2, C2x2));
    }

    @Test
    public void matrix_add() {
        var A2x2 = new PojoFMatrix2x2(1f, 2f, 3f, 4f);
        var B2x2 = new PojoFMatrix2x2(2f, -4f, 6f, -8f);
        var R2x2 = new PojoFMatrix2x2();

        api2x2.add(A2x2, B2x2, R2x2);

        assertArrayEquals(new float[] {3f, -2f, 9f, -4f}, R2x2.toArray());
    }

    @Test
    public void scalar_add() {
        var A2x2 = new PojoFMatrix2x2(1f, 2f, 3f, 4f);
        var B2x2 = -3f;
        var R2x2 = new PojoFMatrix2x2();

        api2x2.add(A2x2, B2x2, R2x2);

        assertArrayEquals(new float[] {-2f, -1f, 0f, 1f}, R2x2.toArray());
    }
}
