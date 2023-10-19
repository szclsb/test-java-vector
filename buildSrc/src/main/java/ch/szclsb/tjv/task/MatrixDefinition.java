package ch.szclsb.tjv.task;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

public interface MatrixDefinition {
    String getName();

//    Property<String> getType();

    Property<Integer> getRows();

    Property<Integer> getColumns();
}
