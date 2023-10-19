package ch.szclsb.tjv.task;

import ch.szclsb.tjv.task.genrator.ClassWriter;
import ch.szclsb.tjv.task.genrator.pojo.PojoMatrixWriter;
import org.gradle.api.DefaultTask;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;

public abstract class GenerateMatrixTask extends DefaultTask {
    @OutputDirectory
    public abstract DirectoryProperty getOutputDir();

    @Input
    public abstract Property<String> getMatrixPackage();

    @Input
    public abstract NamedDomainObjectContainer<MatrixDefinition> getMatrices();

    @TaskAction
    public void generate() {
        var dir = getOutputDir().get().getAsFile().toPath();
        var packageName = getMatrixPackage().get();
        Collection<ClassWriter> classWriters = Set.of(
                new PojoMatrixWriter(dir, packageName)
        );

        System.out.println("Clearing output dir");
        try {
            prepareDir(dir);
            System.out.println("done");

            // generating classes
            getMatrices().forEach(matrixDef -> {
                System.out.println("Start generating classes for " + matrixDef.getName());
                classWriters.forEach(writer -> {
                    System.out.println("Using writer " + writer.getClass().getSimpleName());
                    try {
                        writer.write(matrixDef);
                    } catch (IOException ioe) {
                        System.err.println(ioe.getMessage());
                    }
                });
            });
            System.out.println("done");
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    private void prepareDir(Path dir) throws IOException {
        if (Files.exists(dir)) {
            try (var files = Files.walk(dir)) {
                files
                        .filter(path -> !dir.equals(path))
                        .sorted(Comparator.reverseOrder())
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                System.err.println("error: " + e.getMessage());
                            }
                        });
            }
        } else {
            Files.createDirectories(dir);
        }
    }
}
