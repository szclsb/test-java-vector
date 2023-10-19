package ch.szclsb.tjv;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public abstract class GenerateMatrixTask extends DefaultTask {
    @TaskAction
    public void generate() {
        System.out.println("Start generating");
    }
}
