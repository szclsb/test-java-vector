package ch.szclsb.tjv;

public interface FMatrixApi<M extends FMatrix> {
    /**
     * Checks if difference between A and B are less than tolerance.
     *
     * @param A matrix
     * @param B matrix
     * @param tol tolerance
     */
    boolean equals(M A, M B, float tol);

    default boolean equals(M A, M B) {
        return equals(A, B, MathUtils.TOLERANCE);
    }

    /**
     * Matrix addition: A + B = R
     *
     * @param A matrix
     * @param B matrix
     * @param R result matrix
     */
    void add(M A, M B, M R);

    /**
     * Scalar addition: A + b = R
     *
     * @param A matrix
     * @param b scalar
     * @param R result matrix
     */
    void add(M A, float b, M R);

    /**
     * Matrix subtraction: A - B = R
     *
     * @param A matrix
     * @param B matrix
     * @param R result matrix
     */
    void sub(M A, M B, M R);

    /**
     * Scalar subtraction: A - b = R
     *
     * @param A matrix
     * @param b matrix
     * @param R result matrix
     */
    void sub(M A, float b, M R);

    /**
     * Matrix multiplication: A * B = R
     *
     * @param A matrix
     * @param B matrix
     * @param R result matrix
     */
    void mul(M A, M B, M R);

    /**
     * Matrix element-wise multiplication: A .* B = R
     *
     * @param A matrix
     * @param B matrix
     * @param R result matrix
     */
    void elemMul(M A, M B, M R);

    /**
     * Scalar multiplication: A * s = R
     *
     * @param A matrix
     * @param s matrix
     * @param R result matrix
     */
    void scale(M A, float s, M R);
}
