package <%= appPackage %>.domain.repository.<%= repositoryPackageName %>;

public class <%= repositoryName %>RepositoryImpl implements <%= repositoryName %>Repository {

    <% if (remoteLocal.indexOf('local') >= 0) { %><%= repositoryName %>LocalRepository localRepository;<% } %>

    <% if (remoteLocal.indexOf('remote') >= 0) { %><%= repositoryName %>RemoteRepository remoteRepository;<% } %>

    public <%= repositoryName %>RepositoryImpl(<% if (remoteLocal.indexOf('remote') >= 0) { %><%= repositoryName %>RemoteRepository remoteRepository<% } %> <% if ((remoteLocal.indexOf('remote') >= 0) && (remoteLocal.indexOf('local') >= 0)) { %>, <% } %><% if (remoteLocal.indexOf('local') >= 0) { %><%= repositoryName %>LocalRepository localRepository<% } %>) {
        <% if (remoteLocal.indexOf('remote') >= 0) { %>this.remoteRepository = remoteRepository; <% } %>
        <% if (remoteLocal.indexOf('local') >= 0) { %>this.localRepository = localRepository; <% } %>
    }


}
