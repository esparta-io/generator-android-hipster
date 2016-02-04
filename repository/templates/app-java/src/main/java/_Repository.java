package c<%= appPackage %>.domain.repository.installation;

import javax.inject.Inject;

public class <%= repositoryName %>Repository {

    <% if (remoteLocal.indexOf('local') >= 0) { %>@Inject
    <%= repositoryName %>LocalRepository localRepository;<% } %>

    <% if (remoteLocal.indexOf('local') >= 0) { %>@Inject
    <%= repositoryName %>RemoteRepository remoteRepository;<% } %>

    @Inject
    <%= repositoryName %>Repository() { }

  }
