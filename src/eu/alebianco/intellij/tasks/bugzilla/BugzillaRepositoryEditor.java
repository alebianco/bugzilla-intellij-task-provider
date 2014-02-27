package eu.alebianco.intellij.tasks.bugzilla;

import com.intellij.openapi.project.Project;
import com.intellij.tasks.config.BaseRepositoryEditor;
import com.intellij.util.Consumer;

/**
 * Project: bugzilla-intellij-task-provider
 * <p/>
 * Author:  Alessandro Bianco
 * Website: http://alessandrobianco.eu
 * Twitter: @alebianco
 * Created: 17/02/2014 16:33
 * <p/>
 * Copyright © 2013 Alessandro Bianco
 */
public class BugzillaRepositoryEditor extends BaseRepositoryEditor<BugzillaRepository> {

    public BugzillaRepositoryEditor(Project project, BugzillaRepository repository, Consumer<BugzillaRepository> consumer) {
        super(project, repository, consumer);
    }

    @Override
    public void apply() {
        super.apply();
    }

    @Override
    protected void afterTestConnection(boolean connectionSuccessful) {
        super.afterTestConnection(connectionSuccessful);
    }
}
