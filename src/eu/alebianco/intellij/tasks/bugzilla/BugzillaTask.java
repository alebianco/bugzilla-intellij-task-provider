package eu.alebianco.intellij.tasks.bugzilla;

import com.intellij.tasks.Comment;
import com.intellij.tasks.Task;
import com.intellij.tasks.TaskType;
import com.j2bugzilla.base.Bug;
import com.j2bugzilla.rpc.BugComments;
import eu.alebianco.intellij.tasks.bugzilla.model.Severity;
import eu.alebianco.intellij.tasks.bugzilla.model.Status;
import icons.TasksIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Date;
import java.util.List;

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
    private String baseUrl;
    private BugzillaComment[] comments;

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
        Comment comment = getComments()[0];
        return (comment != null) ? comment.getText() : "";
    }

    @NotNull
    @Override
    public Comment[] getComments() {
        if (comments == null) {
            final BugComments service = new BugComments(bug);
            List<com.j2bugzilla.base.Comment> list = service.getComments();
            comments = new BugzillaComment[list.size()];
            for (int i = 0; i < list.size(); i++) {
                comments[i] = new BugzillaComment(list.get(i));
            }
        }

        return comments;
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
