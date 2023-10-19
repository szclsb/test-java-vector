package ch.szclsb.tjv.task.genrator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public abstract class FileWriter implements ClassWriter {
    private final Path dir;

    public FileWriter(Path dir) {
        this.dir = dir;
    }

    protected void writeFile(String name, OutputWriter outputWriter) throws IOException {
        var file = dir.resolve(name + ".java");
        var tmpFile = dir.resolve(name + ".tmp");
        try (var writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(tmpFile,
                StandardOpenOption.WRITE, StandardOpenOption.CREATE)))) {
            outputWriter.write(writer);
            Files.move(tmpFile, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            System.out.println("warning: " + e.getMessage());
        } finally {
            Files.deleteIfExists(tmpFile);
        }
    }

    protected Stream<Integer> incStream(int count) {
        var counter = new AtomicInteger();
        return Stream.generate(counter::getAndIncrement).takeWhile(c -> c < count);
    }
}
