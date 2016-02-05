package <%= appPackage %>.domain.usecases.<%= useCasePackageName %>;

import <%= appPackage %>.domain.executors.ThreadExecutor;
import <%= appPackage %>.domain.interactors.base.BaseUseCase;

public class <%= useCaseName %>UseCaseImpl extends BaseUseCase implements <%= useCaseName %>UseCase {

    public <%= useCaseName %>UseCaseImpl(ThreadExecutor executor) {
        super(executor);
    }

    @Override
    public void invoke() {
        // TODO
    }

}
