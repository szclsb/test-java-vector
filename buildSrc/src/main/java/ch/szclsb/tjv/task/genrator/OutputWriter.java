package ch.szclsb.tjv.task.genrator;

import java.io.BufferedWriter;
import java.io.IOException;

@FunctionalInterface
public interface OutputWriter {
    void write(BufferedWriter writer) throws IOException;
}