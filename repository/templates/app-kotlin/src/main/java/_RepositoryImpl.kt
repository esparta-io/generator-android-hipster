package <%= appPackage %>.domain.repository.<%= repositoryPackageName %>;

import javax.inject.Inject

class <%= repositoryName %>RepositoryImpl
@Inject
constructor(<% if (remoteLocal.indexOf('remote') >= 0) { %>val remoteRepository: <%= repositoryName %>RemoteRepository<% } %> <% if ((remoteLocal.indexOf('remote') >= 0) && (remoteLocal.indexOf('local') >= 0)) { %>, <% } %><% if (remoteLocal.indexOf('local') >= 0) { %>val localRepository: <%= repositoryName %>LocalRepository<% } %>) : <%= repositoryName %>Repository {
}
