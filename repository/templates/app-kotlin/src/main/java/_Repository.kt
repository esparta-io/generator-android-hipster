package <%= appPackage %>.domain.repository.<%= repositoryPackageName %>;

import javax.inject.Inject;

class <%= repositoryName %>Repository @Inject constructor() {

    <% if (remoteLocal.indexOf('local') >= 0) { %>@Inject
    lateinit var localRepository: <%= repositoryName %>LocalRepository <% } %>

    <% if (remoteLocal.indexOf('remote') >= 0) { %>@Inject
    lateinit var remoteRepository: <%= repositoryName %>RemoteRepository<% } %>

  }
