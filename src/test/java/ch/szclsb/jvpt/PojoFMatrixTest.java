package ch.szclsb.jvpt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PojoFMatrixTest {
    @Test
    public void test2x2FMatrix() {
        var A = new PojoFMatrix(2, 2,
                1f, 2f,
                3f, 4f
        );
        assertEquals("[1.0, 2.0, 3.0, 4.0]", A.toString());
    }
}
