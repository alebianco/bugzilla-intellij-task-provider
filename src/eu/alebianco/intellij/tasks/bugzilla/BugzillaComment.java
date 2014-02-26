package eu.alebianco.intellij.tasks.bugzilla;

import com.intellij.tasks.Comment;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

/**
 * Project: bugzilla-intellij-task-provider
 * <p/>
 * Author:  Alessandro Bianco
 * Website: http://alessandrobianco.eu
 * Twitter: @alebianco
 * Created: 26/02/2014 10:06
 * <p/>
 * Copyright © 2013 Alessandro Bianco
 */
public class BugzillaComment extends Comment {

    private final com.j2bugzilla.base.Comment comment;

    public BugzillaComment(com.j2bugzilla.base.Comment comment) {
        this.comment = comment;
    }

    @Override
    public String getText() {
        return comment.getText();
    }

    @Nullable
    @Override
    public String getAuthor() {
        return comment.getCreator();
    }

    @Nullable
    @Override
    public Date getDate() {
        return comment.getTime();
    }
}
