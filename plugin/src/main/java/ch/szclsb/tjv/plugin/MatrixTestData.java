package ch.szclsb.tjv.plugin;

import org.apache.maven.plugins.annotations.Parameter;

import java.util.List;

public class MatrixTestData {
    @Parameter(property = "matrixA")
    private List<Float> matrixA;
    @Parameter(property = "matrixB")
    private List<Float> matrixB;
    @Parameter(property = "scalarB")
    private Float scalarB;
    @Parameter(property = "matrixAddR")
    private List<Float> matrixAddR;
    @Parameter(property = "scalarAddR")
    private List<Float> scalarAddR;
    @Parameter(property = "matrixSubR")
    private List<Float> matrixSubR;
    @Parameter(property = "scalarSubR")
    private List<Float> scalarSubR;
    @Parameter(property = "matrixMulR")
    private List<Float> matrixMulR;
    @Parameter(property = "matrixElemMulR")
    private List<Float> matrixElemMulR;
    @Parameter(property = "scalarScaleR")
    private List<Float> scalarScaleR;

    public List<Float> getMatrixA() {
        return matrixA;
    }

    public List<Float> getMatrixB() {
        return matrixB;
    }

    public Float getScalarB() {
        return scalarB;
    }

    public List<Float> getMatrixAddR() {
        return matrixAddR;
    }

    public List<Float> getScalarAddR() {
        return scalarAddR;
    }

    public List<Float> getMatrixSubR() {
        return matrixSubR;
    }

    public List<Float> getScalarSubR() {
        return scalarSubR;
    }

    public List<Float> getMatrixMulR() {
        return matrixMulR;
    }

    public List<Float> getMatrixElemMulR() {
        return matrixElemMulR;
    }

    public List<Float> getScalarScaleR() {
        return scalarScaleR;
    }
}
