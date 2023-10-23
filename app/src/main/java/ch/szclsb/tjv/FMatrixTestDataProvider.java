package ch.szclsb.tjv;

public interface FMatrixTestDataProvider {
    <M extends FMatrix> float[] getMatrixA(M matrix);
    <M extends FMatrix> float[] getMatrixB(M matrix);
    <M extends FMatrix> float getScalarB(M matrix);
    <M extends FMatrix> float[] getMatrixAddR(M matrix);
    <M extends FMatrix> float[] getScalarAddR(M matrix);
    <M extends FMatrix> float[] getMatrixSubR(M matrix);
    <M extends FMatrix> float[] getScalarSubR(M matrix);
    <M extends FMatrix> float[] getMatrixMulR(M matrix);
    <M extends FMatrix> float[] getMatrixElemMulR(M matrix);
    <M extends FMatrix> float[] getScalarScaleR(M matrix);
}
