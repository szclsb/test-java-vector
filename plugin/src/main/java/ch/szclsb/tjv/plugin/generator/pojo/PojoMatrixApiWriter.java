package ch.szclsb.tjv.plugin.generator.pojo;

import ch.szclsb.tjv.plugin.MatrixDefinition;
import ch.szclsb.tjv.plugin.generator.ClassWriter;
import ch.szclsb.tjv.plugin.generator.FileWriter;
import org.apache.maven.plugin.logging.Log;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;

public class PojoMatrixApiWriter extends FileWriter implements ClassWriter {
    private final String generatedPackage;

    public PojoMatrixApiWriter(Log logger, Path dir, String generatedPackage) {
        super(logger, dir);
        this.generatedPackage = generatedPackage;
    }

    @Override
    public void write(MatrixDefinition matrixDef) throws IOException {
        var rows = matrixDef.getRows();
        var columns = matrixDef.getColumns();
        var size = rows * columns;
        var matName = "Pojo" + matrixDef.getName();
        var apiName = matName + "Api";
        // todo multiplication for all available matrices
        writeFile(apiName, writer -> {
            writer.write(String.format("""
                    // GENERATED CLASS, DO NOT MODIFY THIS CLASS: CHANGES WILL BE OVERWRITTEN
                    package %1$s;
                                        
                    public class %2$s implements ch.szclsb.tjv.FMatrixApi<%3$s> {
                        private static final %2$s INSTANCE = new %2$s();
                        
                        private %2$s() {}
                        
                        public static %2$s getInstance() {
                            return INSTANCE;
                        }
                        
                    """, generatedPackage, apiName, matName));
            writeMatrixEquality(writer, matName, size);
            writeMatrixMethod(writer, "add", "+", matName, size);
            writeScalarMethod(writer, "add", "+", matName, "b", size);
            writeMatrixMethod(writer, "sub", "-", matName, size);
            writeScalarMethod(writer, "sub", "-", matName, "b", size);
            writeMatrixMultiplication(writer, matName, size);
            writeMatrixMethod(writer, "elemMul", "*", matName, size);
            writeScalarMethod(writer, "scale", "*", matName, "s", size);
            writer.write("}");
        });
    }

    private void writeMatrixEquality(Writer writer, String typeName, int size) throws IOException {
        writer.write(String.format("""
                    public boolean equals(%1$s A, %1$s B, float tol) {
                        return ch.szclsb.tjv.MathUtils.isFloatEquals(A.data[0], B.data[0])
                """, typeName));
        for (var i = 1; i < size; i++) {
            writer.write(String.format("""
                                && ch.szclsb.tjv.MathUtils.isFloatEquals(A.data[%1$d], B.data[%1$d])%2$s
                    """, i, i + 1 == size ? ";" : ""));
        }
        writer.write("""
                    }
                """);
    }

    private void writeMatrixMethod(Writer writer, String name, String operator, String typeName, int size) throws IOException {
        writer.write(String.format("""
                    public void %1$s(%2$s A, %2$s B, %2$s R) {
                """, name, typeName));
        for (var i = 0; i < size; i++) {
            writer.write(String.format("""
                            R.data[%1$d] = A.data[%1$d] %2$s B.data[%1$d];
                    """, i, operator));
        }
        writer.write("""
                    }
                """);
    }

    private void writeScalarMethod(Writer writer, String name, String operator, String typeName, String scalarName, int size) throws IOException {
        writer.write(String.format("""
                    public void %1$s(%2$s A, float %3$s, %2$s R) {
                """, name, typeName, scalarName));
        for (var i = 0; i < size; i++) {
            writer.write(String.format("""
                            R.data[%1$d] = A.data[%1$d] %2$s %3$s;
                    """, i, operator, scalarName));
        }
        writer.write("""
                    }
                """);
    }

    private void writeMatrixMultiplication(Writer writer, String typeName, int size) throws IOException {
        writer.write(String.format("""
                    public void mul(%1$s A, %1$s B, %1$s R) {
                        throw new UnsupportedOperationException();
                    }
                """, typeName));
    }
}
