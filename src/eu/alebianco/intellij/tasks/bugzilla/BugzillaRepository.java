package eu.alebianco.intellij.tasks.bugzilla;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.tasks.Task;
import com.intellij.tasks.impl.BaseRepository;
import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugzillaConnector;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;
import com.j2bugzilla.rpc.*;
import eu.alebianco.intellij.tasks.bugzilla.model.Status;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static java.util.Arrays.asList;

/**
 * Project: bugzilla-intellij-task-provider
 * <p/>
 * Author:  Alessandro Bianco
 * Website: http://alessandrobianco.eu
 * Twitter: @alebianco
 * Created: 17/02/2014 16:28
 * <p/>
 * Copyright © 2013 Alessandro Bianco
 */
public class BugzillaRepository extends BaseRepository {
    private static final Logger logger = Logger.getInstance(BugzillaRepository.class);

    private BugzillaConnector connector = new BugzillaConnector();

    public BugzillaRepository(BugzillaRepositoryType type) {
        super(type);
    }

    public BugzillaRepository(BugzillaRepository repository) {
        super(repository);
        connector = repository.connector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        BugzillaRepository that = (BugzillaRepository) o;

        return connector.equals(that.connector);
    }

    @Override
    public int hashCode() {
        return connector.hashCode();
    }

    @Override
    public Task[] getIssues(@Nullable String query, int max, long since) throws Exception {
        refreshConnection();

        final List<Task> tasks = new ArrayList<Task>();

        BugSearch search = new BugSearch(buildSearchQueries(query, max, since));
        connector.executeMethod(search);

        List<Bug> results = search.getSearchResults();
        for (Bug bug : results) {
            Callable<BugzillaComment[]> comments = buildLazyCommentsLoader(bug);
            BugzillaTask task = new BugzillaTask(bug, comments, getUrl());
            tasks.add(task);

        }

        return tasks.toArray(new Task[tasks.size()]);
    }

    @Nullable
    @Override
    public Task findTask(String id) throws Exception {
        refreshConnection();

        GetBug operation = new GetBug(id);
        connector.executeMethod(operation);
        Bug result = operation.getBug();
        Callable<BugzillaComment[]> comments = buildLazyCommentsLoader(result);

        return new BugzillaTask(result, comments, getUrl());
    }

    private Callable<BugzillaComment[]> buildLazyCommentsLoader(@NotNull final Bug bug) {
        return new Callable<BugzillaComment[]>() {
            private BugzillaComment[] comments;

            @Override
            public BugzillaComment[] call() throws Exception {
                if (comments == null) {
                    final BugComments service = new BugComments(bug);
                    connector.executeMethod(service);
                    List<com.j2bugzilla.base.Comment> list = service.getComments();

                    comments = new BugzillaComment[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        comments[i] = new BugzillaComment(list.get(i));
                    }
                }

                return comments;
            }
        };
    }

    private List<BugSearch.SearchQuery> buildSearchQueries(@Nullable String query, int max, long since) {
        List<BugSearch.SearchQuery> list = new ArrayList<BugSearch.SearchQuery>();

        if (StringUtils.isBlank(query)) {
            list.add(new BugSearch.SearchQuery(BugSearch.SearchLimiter.OWNER, asList(getUsername())));
        }

        if (max > 0) {
            list.add(new BugSearch.SearchQuery(BugSearch.SearchLimiter.LIMIT, String.valueOf(max)));
        }

        if (since > 0) {
            list.add(new BugSearch.SearchQuery(BugSearch.SearchLimiter.LAST_CHANGE_TIME, String.valueOf(since)));
        }

        return list;
    }

    @Nullable
    @Override
    public CancellableConnection createCancellableConnection() {
        return new CancellableConnection() {
            @Override
            protected void doTest() throws Exception {
                refreshConnection();
            }

            @Override
            public void cancel() {
                try {
                    connector.executeMethod(new LogOut());
                } catch (BugzillaException e) {
                    // fail silently ...
                }
            }
        };
    }

    private void refreshConnection() throws ConnectionException, BugzillaException {
        connector.connectTo(getUrl());
        LogIn login = new LogIn(getUsername(), getPassword());
        connector.executeMethod(login);
    }

    @Override
    public BaseRepository clone() {
        return new BugzillaRepository(this);
    }
}
