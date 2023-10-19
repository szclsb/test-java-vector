package ch.szclsb.tjv.task.genrator.pojo;

import ch.szclsb.tjv.task.MatrixDefinition;
import ch.szclsb.tjv.task.genrator.FileWriter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class PojoMatrixWriter extends FileWriter {
    private final String generatedPackage;

    public PojoMatrixWriter(Path dir, String generatedPackage) {
        super(dir);
        this.generatedPackage = generatedPackage;
    }

    @Override
    public void write(MatrixDefinition matrixDef) throws IOException {
        var rows = matrixDef.getRows().get();
        var columns = matrixDef.getColumns().get();
        var size = rows * columns;
        writeFile(matrixDef.getName(), writer -> {
            writer.write(String.format("""
                            // GENERATED CLASS, DO NOT MODIFY THIS CLASS: CHANGES WILL BE OVERWRITTEN
                            package %1$s;
                            
                            import ch.szclsb.tjv.*;
                            import java.util.Arrays;
                                                
                            public class %2$s implements FMatrix {
                                private final float[] data;
                                
                                public %2$s() {
                                    this.data = new float[%5$d];
                                }
                                
                                public %2$s(%6$s) {
                                    this();
                                    %7$s
                                }
                                
                                @Override
                                public int getRows() {
                                    return %3$d;
                                }
                                
                                @Override
                                public int getColumns() {
                                    return %4$d;
                                }
                                
                                private void checkRange(int i, int max) {
                                    if (i < 0 || i >= max) {
                                        throw new IndexOutOfBoundsException();
                                    }
                                }
                                
                                @Override
                                public float get(int row, int column) {
                                    checkRange(row, %3$d);
                                    checkRange(column, %4$d);
                                    return data[row * %4$d + column];
                                }
                                
                                @Override
                                public void set(int row, int column, float value) {
                                    checkRange(row, %3$d);
                                    checkRange(column, %4$d);
                                    data[row * %4$d + column] = value;
                                }
                                
                                @Override
                                public float[] toArray() {
                                    var tmp = new float[%5$d];
                                    System.arraycopy(data, 0, tmp, 0, %5$d);
                                    return tmp;
                                }
                                
                                @Override
                                public String toString() {
                                    return Arrays.toString(data);
                                }
                            }
                            """, generatedPackage, matrixDef.getName(), rows, columns, size,
                    incStream(size)
                            .map(x -> "float a" + x)
                            .collect(Collectors.joining(", ")),
                    incStream(size)
                            .map(x -> "this.data[" + x + "] = a" + x + ";")
                            .collect(Collectors.joining("\n        "))));
        });
    }
}
