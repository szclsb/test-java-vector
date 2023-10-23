package ch.szclsb.tjv.plugin;

import ch.szclsb.tjv.plugin.genrator.ClassWriter;
import ch.szclsb.tjv.plugin.genrator.MultiClassWriter;
import ch.szclsb.tjv.plugin.genrator.TestDataProviderWriter;
import ch.szclsb.tjv.plugin.genrator.pojo.PojoMatrixApiWriter;
import ch.szclsb.tjv.plugin.genrator.pojo.PojoMatrixWriter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;

@Mojo(name = "generate-matrix")
public class GenerateMatrixMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Parameter(property = "outputDirectory", required = true, readonly = true)
    private File outputDirectory;

    @Parameter(property = "generatePackage")
    private String generatePackage;

    @Parameter(property = "matrixDefinitions")
    private Collection<MatrixDefinition> matrixDefinitions;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        var dir = outputDirectory.toPath().resolve(generatePackage.replace(".", "/"));
        Collection<MultiClassWriter> multiClassWriters = Set.of(
                new TestDataProviderWriter(getLog(), dir, generatePackage)
        );
        Collection<ClassWriter> classWriters = Set.of(
                new PojoMatrixWriter(getLog(), dir, generatePackage),
                new PojoMatrixApiWriter(getLog(), dir, generatePackage)
        );

        // add additional source root
        project.addCompileSourceRoot(outputDirectory.getAbsolutePath());
        // prepare directory
        getLog().info("Clearing build directory " + dir);
        try {
            prepareDir(dir);
            getLog().info("done");
        } catch (IOException ioe) {
            getLog().error(ioe.getMessage(), ioe);
        }
        // generating multi classes
        getLog().info("Start generating multi classes");
        multiClassWriters.forEach(writer -> {
            getLog().info("  Using writer " + writer.getClass().getSimpleName());
            try {
                writer.write(matrixDefinitions);
            } catch (IOException ioe) {
                getLog().error(ioe.getMessage(), ioe);
            }
        });
        // generating classes
        matrixDefinitions.forEach(matrixDef -> {
            getLog().info("Start generating classes for " + matrixDef.getName());
            classWriters.forEach(writer -> {
                getLog().info("  Using writer " + writer.getClass().getSimpleName());
                try {
                    writer.write(matrixDef);
                } catch (IOException ioe) {
                    getLog().error(ioe.getMessage(), ioe);
                }
            });
        });
        getLog().info("done");
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
                            } catch (IOException ioe) {
                                getLog().error(ioe.getMessage(), ioe);
                            }
                        });
            }
        } else {
            Files.createDirectories(dir);
        }
    }
}
