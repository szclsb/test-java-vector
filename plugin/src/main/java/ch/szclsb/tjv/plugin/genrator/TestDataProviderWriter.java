package ch.szclsb.tjv.plugin.genrator;

import ch.szclsb.tjv.plugin.MatrixDefinition;
import org.apache.maven.plugin.logging.Log;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;


public class TestDataProviderWriter extends FileWriter implements MultiClassWriter {
    private final String generatedPackage;

    public TestDataProviderWriter(Log logger, Path dir, String generatePackage) {
        super(logger, dir);
        this.generatedPackage = generatePackage;
    }

    @Override
    public void write(Collection<MatrixDefinition> definition) throws IOException {

    }
}
