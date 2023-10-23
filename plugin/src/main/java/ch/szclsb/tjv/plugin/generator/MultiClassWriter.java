package ch.szclsb.tjv.plugin.generator;

import ch.szclsb.tjv.plugin.MatrixDefinition;

import java.io.IOException;
import java.util.Collection;

public interface MultiClassWriter {
    void write(Collection<MatrixDefinition> definition) throws IOException;
}
