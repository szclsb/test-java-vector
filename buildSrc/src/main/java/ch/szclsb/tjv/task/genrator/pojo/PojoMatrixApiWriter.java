package ch.szclsb.tjv.task.genrator.pojo;

import ch.szclsb.tjv.task.MatrixDefinition;
import ch.szclsb.tjv.task.genrator.FileWriter;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static org.gradle.internal.impldep.org.yaml.snakeyaml.nodes.Tag.STR;

public class PojoMatrixApiWriter extends FileWriter {
    private final String generatedPackage;

    public PojoMatrixApiWriter(Path dir, String generatedPackage) {
        super(dir);
        this.generatedPackage = generatedPackage;
    }

    @Override
    public void write(MatrixDefinition matrixDef) throws IOException {
        var rows = matrixDef.getRows().get();
        var columns = matrixDef.getColumns().get();
        var size = rows * columns;
        var apiName = matrixDef.getName() + "Api";
        // todo multiplication for all available matrices
        writeFile(apiName, writer -> {
            writer.write(String.format("""
                    // GENERATED CLASS, DO NOT MODIFY THIS CLASS: CHANGES WILL BE OVERWRITTEN
                    package %1$s;
                                        
                    public class %2$s implements FMatrixApi<%3$s> {
                        private static final %2$s INSTANCE = new %2$s();
                        
                        private %2$s() {}
                        
                        public static %2$s getInstance() {
                            return INSTANCE;
                        }
                        
                    """, generatedPackage, apiName, matrixDef.getName()));
            writeMatrixEquality(writer, matrixDef.getName(), size);
            writeMatrixMethod(writer, "add", "+", matrixDef.getName(), size);
            writeScalarMethod(writer, "add", "+", matrixDef.getName(), "b", size);
            writeMatrixMethod(writer, "sub", "-", matrixDef.getName(), size);
            writeScalarMethod(writer, "sub", "-", matrixDef.getName(), "b", size);
            writeMatrixMultiplication(writer, matrixDef.getName(), size);
            writeMatrixMethod(writer, "elemMul", "*", matrixDef.getName(), size);
            writeScalarMethod(writer, "scale", "*", matrixDef.getName(), "s", size);
            writer.write("}");
        });
    }

    private void writeMatrixEquality(Writer writer, String typeName, int size) throws IOException {
        writer.write(String.format("""
                    public boolean equals(%1$s A, %1$s B, float tol) {
                        return MathUtils.isFloatEquals(A.data[0], B.data[0])
                """, typeName));
        for (var i = 1; i < size; i++) {
            writer.write(String.format("""
                                && MathUtils.isFloatEquals(A.data[%1$d], B.data[%1$d])%2$s
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
