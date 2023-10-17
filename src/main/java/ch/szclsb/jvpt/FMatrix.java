package ch.szclsb.jvpt;

public interface FMatrix {
    int getRows();
    int getColumns();

    float get(int row, int column);

    void set(int row, int column, float value);

    float[] toArray();

    String toString();
}
