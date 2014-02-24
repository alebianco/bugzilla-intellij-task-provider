package eu.alebianco.intellij.tasks.bugzilla;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.tasks.Task;
import com.intellij.tasks.impl.BaseRepository;
import de.lightningbug.api.BugzillaClient;
import de.lightningbug.api.domain.Bug;
import de.lightningbug.api.service.BugService;
import de.lightningbug.api.service.ProductService;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.generate.tostring.util.StringUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private BugzillaClient client;
    private BugService bugs;
    private ProductService products;

    public BugzillaRepository(BugzillaRepositoryType type) {
        super(type);
    }

    public BugzillaRepository(BugzillaRepository repository) {
        super(repository);
        client = repository.client;
        bugs = repository.bugs;
        products = repository.products;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof BugzillaRepository)) return false;
        if (!super.equals(object)) return false;

        BugzillaRepository that = (BugzillaRepository) object;

        return  client == that.client &&
                bugs == that.bugs &&
                products == that.products;

    }

    @Override
    public int hashCode() {
        int result = client != null ? 1 : 0;
        result = 31 * result + (bugs != null ? bugs.hashCode() : 0);
        result = 31 * result + (products != null ? products.hashCode() : 0);
        return result;
    }

    @Override
    public Task[] getIssues(@Nullable String query, int max, long since) throws Exception {
        refreshProviders();

        final List<Task> tasks = new ArrayList<Task>();

        final Map<String, Object[]> searchParams = buildSearchParams(query);
        searchParams.put("status", new Object[]{ "UNCONFIRMED", "NEW", "ASSIGNED", "REOPENED", "READY" });
        if (since > 0) {
            searchParams.put("creation_time", new Object[]{ since });
        }
        if (max > 0) {
            searchParams.put("limit", new Object[]{ max });
        }

        for (Bug bug : bugs.search(searchParams)) {
            tasks.add(new BugzillaTask(bug, getUrl()));
        }

        return tasks.toArray(new Task[tasks.size()]);
    }

    protected Map<String, Object[]> buildSearchParams(String query) {
        Map<String, Object[]> result = null;

        query = StringUtil.isEmpty(query) ? getDefaultQuery() : query;

        try {
            result = new ObjectMapper().readValue(query, new TypeReference<HashMap<String, Object[]>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private String getDefaultQuery() {
        return String.format("{\"assigned_to\":[\"%s\"]}", getUsername());
    }

    @Nullable
    @Override
    public Task findTask(String id) throws Exception {
        final String query = String.format("{\"id\":[\"%s\"]}", id);
        return getIssues(query, 1, 0)[0];
    }

    @Nullable
    @Override
    public CancellableConnection createCancellableConnection() {
        return new CancellableConnection() {
            @Override
            protected void doTest() throws Exception {
                refreshProviders();
                if (client == null || !client.isLoggedIn()) {
                    throw new IOException("Couldn't establish connection, sure this is the correct endpoint and login information?");
                }
            }

            @Override
            public void cancel() {
                if (client != null) {
                    client.logout();
                }
            }
        };
    }

    @Override
    public void setUrl(String url) {
        super.setUrl(url);
        if (client != null) {
            client.logout();
            client = null;
        }
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    private void refreshProviders() throws Exception {
        if (client == null) {
            client = new BugzillaClient(new URL(getUrl() + "/"), getUsername(), getPassword());
        }

        if (!client.isLoggedIn()) {
            client.setUserName(getUsername());
            client.setPassword(getPassword());
            client.login();
        }

        if (client.isLoggedIn()) {
            if (bugs == null) {
                bugs = new BugService(client);
            }
            if (products == null) {
                products = new ProductService(client);
            }
        }
    }

    @Override
    public BaseRepository clone() {
        return new BugzillaRepository(this);
    }
}
