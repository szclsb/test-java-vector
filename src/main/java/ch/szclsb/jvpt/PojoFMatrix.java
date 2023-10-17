package ch.szclsb.jvpt;

import java.util.Arrays;

public class PojoFMatrix implements FMatrix {
    private final float[] data;
    private final int rows, columns;

    public PojoFMatrix(int rows, int columns) {
        this.data = new float[rows * columns];
        this.rows = rows;
        this.columns = columns;
    }

    public PojoFMatrix(int rows, int columns, float ...data) {
        if (rows * columns != data.length) {
            throw new IllegalArgumentException("Input data does not match matrix dimension");
        }
        this.data = data;
        this.rows = rows;
        this.columns = columns;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getColumns() {
        return columns;
    }

    private void checkRange(int i, int max) {
        if (i < 0 || i >= max) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public float get(int row, int column) {
        checkRange(row, rows);
        checkRange(column, columns);
        return data[row * columns + column];
    }

    @Override
    public void set(int row, int column, float value) {
        checkRange(row, rows);
        checkRange(column, columns);
        data[row * columns + column] = value;
    }

    @Override
    public float[] toArray() {
        var tmp = new float[rows * columns];
        System.arraycopy(data, 0, tmp, 0, rows * columns);
        return tmp;
    }

    @Override
    public String toString() {
        return Arrays.toString(data);
    }
}
