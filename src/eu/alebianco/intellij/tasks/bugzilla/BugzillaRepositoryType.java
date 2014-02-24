package eu.alebianco.intellij.tasks.bugzilla;

import com.intellij.openapi.util.IconLoader;
import com.intellij.tasks.TaskRepository;
import com.intellij.tasks.impl.BaseRepositoryType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Project: bugzilla-intellij-task-provider
 * <p/>
 * Author:  Alessandro Bianco
 * Website: http://alessandrobianco.eu
 * Twitter: @alebianco
 * Created: 17/02/2014 15:20
 * <p/>
 * Copyright © 2013 Alessandro Bianco
 */
public class BugzillaRepositoryType extends BaseRepositoryType<BugzillaRepository> {
    @NotNull
    @Override
    public String getName() {
        return "Bugzilla";
    }

    @NotNull
    @Override
    public Icon getIcon() {
        return IconLoader.getIcon("/bugzilla.png");
    }

    @NotNull
    @Override
    public TaskRepository createRepository() {
        return new BugzillaRepository(this);
    }

    @Override
    public Class<BugzillaRepository> getRepositoryClass() {
        return BugzillaRepository.class;
    }
}
