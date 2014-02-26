package eu.alebianco.intellij.tasks.bugzilla;

import com.intellij.openapi.util.IconLoader;
import com.intellij.tasks.Comment;
import com.intellij.tasks.Task;
import com.intellij.tasks.TaskType;
import com.j2bugzilla.base.Bug;
import eu.alebianco.intellij.tasks.bugzilla.model.Severity;
import eu.alebianco.intellij.tasks.bugzilla.model.Status;
import icons.TasksIcons;
import org.apache.xml.resolver.apps.resolver;
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
        return Integer.toString(bug.getID());
    }

    @NotNull
    @Override
    public String getSummary() {
        return bug.getSummary();
    }

    @Nullable
    @Override
    public String getDescription() {
        return ""; // TODO retrieve first comment?
    }

    @NotNull
    @Override
    public Comment[] getComments() {
        return Comment.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public Icon getIcon() {
        return isIssue() ? TasksIcons.Bug : TasksIcons.Feature;
    }

    @NotNull
    @Override
    public TaskType getType() {
        return isIssue() ? TaskType.BUG : TaskType.FEATURE;
    }

    @Nullable
    @Override
    public Date getUpdated() {
        return bug.getLastChanged();
    }

    @Nullable
    @Override
    public Date getCreated() {
        return null;
    }

    @Override
    public boolean isClosed() {
        final Status status = Status.valueOf(bug.getStatus());
        return status == Status.RESOLVED || status == Status.VERIFIED;
    }

    @Override
    public boolean isIssue() {
        final Severity severity = Severity.valueOf(bug.getSeverity());
        return severity != Severity.ENHANCEMENT;
    }

    @Nullable
    @Override
    public String getIssueUrl() {
        return String.format("%s/show_bug.cgi?id=%s", baseUrl, getId());
    }

}
