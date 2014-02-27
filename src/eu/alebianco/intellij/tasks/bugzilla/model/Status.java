package eu.alebianco.intellij.tasks.bugzilla.model;

import java.io.Serializable;

/**
 * Project: bugzilla-intellij-task-provider
 * <p/>
 * Author:  Alessandro Bianco
 * Website: http://alessandrobianco.eu
 * Twitter: @alebianco
 * Created: 26/02/2014 09:40
 * <p/>
 * Copyright © 2013 Alessandro Bianco
 */
public enum Status implements Serializable {
    UNCONFIRMED,
    NEW,
    ASSIGNED,
    REOPENED,
    READY,
    RESOLVED,
    VERIFIED,
    CLOSED,
    DEFERRED;
}
