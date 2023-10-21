package ch.szclsb.jvpt;

import ch.szclsb.tjv.PojoFMatrix2x2;
import ch.szclsb.tjv.PojoFMatrix2x2Api;
import org.junit.jupiter.api.Disabled;
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
        var b = -3f;
        var R2x2 = new PojoFMatrix2x2();

        api2x2.add(A2x2, b, R2x2);

        assertArrayEquals(new float[] {-2f, -1f, 0f, 1f}, R2x2.toArray());
    }

    @Test
    public void matrix_sub() {
        var A2x2 = new PojoFMatrix2x2(1f, 2f, 3f, 4f);
        var B2x2 = new PojoFMatrix2x2(2f, -4f, 6f, -8f);
        var R2x2 = new PojoFMatrix2x2();

        api2x2.sub(A2x2, B2x2, R2x2);

        assertArrayEquals(new float[] {-1f, 6f, -3f, 12f}, R2x2.toArray());
    }

    @Test
    public void scalar_sub() {
        var A2x2 = new PojoFMatrix2x2(1f, 2f, 3f, 4f);
        var b = -3f;
        var R2x2 = new PojoFMatrix2x2();

        api2x2.sub(A2x2, b, R2x2);

        assertArrayEquals(new float[] {4f, 5f, 6f, 7f}, R2x2.toArray());
    }

    @Test
    @Disabled
    public void matrix_multiply() {
        // todo
    }

    @Test
    public void matrix_elem_multiply() {
        var A2x2 = new PojoFMatrix2x2(1f, 2f, 3f, 4f);
        var B2x2 = new PojoFMatrix2x2(2f, -4f, 6f, -8f);
        var R2x2 = new PojoFMatrix2x2();

        api2x2.elemMul(A2x2, B2x2, R2x2);

        assertArrayEquals(new float[] {2f, -8f, 18f, -32f}, R2x2.toArray());
    }

    @Test
    public void scalar_scale() {
        var A2x2 = new PojoFMatrix2x2(1f, 2f, 3f, 4f);
        var s = -3f;
        var R2x2 = new PojoFMatrix2x2();

        api2x2.scale(A2x2, s, R2x2);

        assertArrayEquals(new float[] {-3f, -6f, -9f, -12f}, R2x2.toArray());
    }
}
