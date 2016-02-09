package <%= appPackage %>.domain.usecases.<%= useCasePackageName %>;

import <%= appPackage %>.domain.executors.ThreadExecutor;
import <%= appPackage %>.domain.interactors.base.BaseUseCase;

public class <%= useCaseName %>UseCaseImpl(executor: ThreadExecutor) : BaseUseCase(executor), <%= useCaseName %>UseCase {

    override fun invoke() {
        // TODO
    }

}
