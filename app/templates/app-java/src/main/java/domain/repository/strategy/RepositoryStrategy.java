package <%= appPackage %>.domain.repository.strategy;

public enum RepositoryStrategy {

    LOCAL_ONLY,
    LOCAL_AND_REMOTE,
    LOCAL_AND_REMOTE_IF_EMPTY_OR_EXPIRES,
    REMOTE_AND_LOCAL,
    REMOTE_ONLY

}
