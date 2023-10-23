package ch.szclsb.tjv.plugin.genrator;

import ch.szclsb.tjv.plugin.MatrixDefinition;

import java.io.IOException;

public interface ClassWriter {
    void write(MatrixDefinition definition) throws IOException;
}
