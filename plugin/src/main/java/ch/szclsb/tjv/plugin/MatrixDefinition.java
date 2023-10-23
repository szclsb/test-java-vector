package ch.szclsb.tjv.plugin;

import org.apache.maven.plugins.annotations.Parameter;

public class MatrixDefinition {
    @Parameter(property = "name")
    private String name;

    @Parameter(property = "rows")
    private int rows;

    @Parameter(property = "columns")
    private int columns;

//    @Parameter(property = "testData")
//    private MatrixTestData testData;

    public String getName() {
        return name;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

//    public MatrixTestData getTestData() {
//        return testData;
//    }
}
