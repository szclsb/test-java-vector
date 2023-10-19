package ch.szclsb.tjv.task.genrator;

import ch.szclsb.tjv.task.MatrixDefinition;

import java.io.BufferedWriter;
import java.io.IOException;

public interface ClassWriter {
    void write(MatrixDefinition definition) throws IOException;
}
