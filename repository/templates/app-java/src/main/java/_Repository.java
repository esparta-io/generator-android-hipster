package <%= appPackage %>.domain.repository.<%= repositoryPackageName %>;

import javax.inject.Inject;

public class <%= repositoryName %>Repository {

    <% if (remoteLocal.indexOf('local') >= 0) { %>@Inject
    <%= repositoryName %>LocalRepository localRepository;<% } %>

    <% if (remoteLocal.indexOf('remote') >= 0) { %>@Inject
    <%= repositoryName %>RemoteRepository remoteRepository;<% } %>

    @Inject
    public <%= repositoryName %>Repository() { }

  }
