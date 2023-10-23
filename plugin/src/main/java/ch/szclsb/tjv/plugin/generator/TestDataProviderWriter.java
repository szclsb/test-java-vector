package ch.szclsb.tjv.plugin.generator;

import ch.szclsb.tjv.plugin.MatrixDefinition;
import org.apache.maven.plugin.logging.Log;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.StringTemplate.STR;


public class TestDataProviderWriter extends FileWriter implements MultiClassWriter {
    private final String generatedPackage;

    public TestDataProviderWriter(Log logger, Path dir, String generatePackage) {
        super(logger, dir);
        this.generatedPackage = generatePackage;
    }

    @Override
    public void write(Collection<MatrixDefinition> definitions) throws IOException {
        var className = "TestDataProvider";
        var interfaceName = "FMatrixTestDataProvider";
        writeFile(className, writer -> {
            writer.write(STR. """
                    package \{ generatedPackage };

                    import java.util.Map;
                    import java.util.TreeMap;

                    import static ch.szclsb.tjv.MathUtils.cantorPairing;

                    public class \{ className } implements \{ interfaceName } {
                        private static final \{ className } INSTANCE = new \{ className }();

                        public static \{ className } getInstance() {
                            return INSTANCE;
                        }

                        static record TestData(
                            float[] matrixA,
                            float[] matrixB,
                            float scalarB,
                            float[] matrixAddR,
                            float[] scalarAddR,
                            float[] matrixSubR,
                            float[] scalarSubR,
                            float[] matrixMulR,
                            float[] matrixElemMulR,
                            float[] scalarScaleR
                        ) {}

                        private final Map<Long, TestData> testData;

                        private \{ className }() {
                            this.testData = new TreeMap<>();
                            \{ definitions.stream()
                    .filter(def -> def.getTestData() != null)
                    .map(def -> {
                        var p = ((def.getRows() + def.getColumns()) * (def.getRows() + def.getColumns() + 1L) + def.getColumns()) / 2L;
                        var t = def.getTestData();
                        return STR. """
                        this.testData.put(\{ p }L, new TestData(
                                    \{ printFloatArrayInit(t.getMatrixA()) },
                                    \{ printFloatArrayInit(t.getMatrixB()) },
                                    \{ t.getScalarB() }f,
                                    \{ printFloatArrayInit(t.getMatrixAddR()) },
                                    \{ printFloatArrayInit(t.getScalarAddR()) },
                                    \{ printFloatArrayInit(t.getMatrixSubR()) },
                                    \{ printFloatArrayInit(t.getScalarSubR()) },
                                    \{ printFloatArrayInit(t.getMatrixMulR()) },
                                    \{ printFloatArrayInit(t.getMatrixElemMulR()) },
                                    \{ printFloatArrayInit(t.getScalarScaleR()) }
                            ));""" ;
                    })
                    .collect(Collectors.joining("\n")) }
                        }

                    \{ printPutTestData(false, "getMatrixA", "matrixA") }

                    \{ printPutTestData(false, "getMatrixB", "matrixB") }

                    \{ printPutTestData(true, "getScalarB", "scalarB") }

                    \{ printPutTestData(false, "getMatrixAddR", "matrixAddR") }

                    \{ printPutTestData(false, "getScalarAddR", "scalarAddR") }

                    \{ printPutTestData(false, "getMatrixSubR", "matrixSubR") }

                    \{ printPutTestData(false, "getScalarSubR", "scalarSubR") }

                    \{ printPutTestData(false, "getMatrixMulR", "matrixMulR") }

                    \{ printPutTestData(false, "getMatrixElemMulR", "matrixElemMulR") }

                    \{ printPutTestData(false, "getScalarScaleR", "scalarScaleR") }
                    }
                    """ );
        });
    }

    private String printFloatArrayInit(List<Float> floats) {
        return STR. "new float[] {\{ floats.stream()
                .map(f -> f + "f")
                .collect(Collectors.joining(", ")) }}" ;
    }

    private String printPutTestData(boolean isScalar, String methodName, String field) {
        return STR. """
                    @Override
                    public <M extends FMatrix> \{ isScalar ? "float" : "float[]" } \{ methodName }(M matrix) {
                        var m = testData.get(cantorPairing(matrix.getRows(), matrix.getColumns()));
                        if (m == null) {
                            throw new IllegalArgumentException("Missing test data for matrix " + matrix.toString());
                        }
                        return m.\{ field }();
                    }
                """ ;
    }
}
