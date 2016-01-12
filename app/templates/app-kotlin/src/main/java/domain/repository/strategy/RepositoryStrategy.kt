package <%= appPackage %>.domain.repository.strategy

/**
 * Created by deividi on 03/01/16.
 */
enum class RepositoryStrategy {

  LOCAL_ONLY,
  LOCAL_AND_REMOTE,
  LOCAL_AND_REMOTE_IF_EMPTY_OR_EXPIRES,
  REMOTE_AND_LOCAL,
  REMOTE_ONLY

}
