package ch.szclsb.tjv.plugin.genrator.pojo;

import ch.szclsb.tjv.plugin.MatrixDefinition;
import ch.szclsb.tjv.plugin.genrator.ClassWriter;
import ch.szclsb.tjv.plugin.genrator.FileWriter;
import org.apache.maven.plugin.logging.Log;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class PojoMatrixWriter extends FileWriter implements ClassWriter {
    private final String generatedPackage;

    public PojoMatrixWriter(Log logger, Path dir, String generatedPackage) {
        super(logger, dir);
        this.generatedPackage = generatedPackage;
    }

    @Override
    public void write(MatrixDefinition matrixDef) throws IOException {
        var rows = matrixDef.getRows();
        var columns = matrixDef.getColumns();
        var size = rows * columns;
        var className = "Pojo" + matrixDef.getName();
        writeFile(className, writer -> {
            writer.write(String.format("""
                            // GENERATED CLASS, DO NOT MODIFY THIS CLASS: CHANGES WILL BE OVERWRITTEN
                            package %1$s;
                            
                            import java.util.Arrays;
                                                
                            public class %2$s implements ch.szclsb.tjv.FMatrix {
                                float[] data;
                                
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
                            """, generatedPackage, className, rows, columns, size,
                    incStream(size)
                            .map(x -> "float a" + x)
                            .collect(Collectors.joining(", ")),
                    incStream(size)
                            .map(x -> "this.data[" + x + "] = a" + x + ";")
                            .collect(Collectors.joining("\n        "))));
        });
    }
}
