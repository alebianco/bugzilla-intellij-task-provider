package eu.alebianco.intellij.tasks.bugzilla;

import com.intellij.openapi.util.IconLoader;
import com.intellij.tasks.Comment;
import com.intellij.tasks.Task;
import com.intellij.tasks.TaskType;
import de.lightningbug.api.domain.Bug;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Date;

/**
 * Project: bugzilla-intellij-task-provider
 * <p/>
 * Author:  Alessandro Bianco
 * Website: http://alessandrobianco.eu
 * Twitter: @alebianco
 * Created: 17/02/2014 16:44
 * <p/>
 * Copyright © 2013 Alessandro Bianco
 */
public class BugzillaTask extends Task {
    private final Bug bug;
    public String baseUrl;

    public BugzillaTask(Bug bug, String serverUrl) {
        this.bug = bug;
        this.baseUrl = serverUrl;
    }

    @NotNull
    @Override
    public String getId() {
        return String.format("#%s", bug.getId().toString());
    }

    @NotNull
    @Override
    public String getSummary() {
        return bug.getSummary();
    }

    @Nullable
    @Override
    public String getDescription() {
        return bug.getDescription();
    }

    @NotNull
    @Override
    public Comment[] getComments() {
        return Comment.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public Icon getIcon() {
        return IconLoader.getIcon("/bugzilla.png");
    }

    @NotNull
    @Override
    public TaskType getType() {
        return TaskType.BUG;
    }

    @Nullable
    @Override
    public Date getUpdated() {
        return null;
    }

    @Nullable
    @Override
    public Date getCreated() {
        return null;
    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public boolean isIssue() {
        return true;
    }

    @Nullable
    @Override
    public String getIssueUrl() {
        return String.format("%s/show_bug.cgi?id=%s", baseUrl, getId());
    }

}
