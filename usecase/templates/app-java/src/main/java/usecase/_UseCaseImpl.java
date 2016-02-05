package <%= appPackage %>.domain.interactors.<%= useCasePackageName %>;

import <%= appPackage %>.domain.executors.ThreadExecutor;
import <%= appPackage %>.domain.interactors.base.BaseUseCase;

import javax.inject.Inject;

public class <%= useCaseName %>UseCaseImpl extends BaseUseCase implements <%= useCaseName %>UseCase {

    public <%= useCaseName %>UseCaseImpl(ThreadExecutor executor) {
        super(executor);
    }

    @Override
    public void invoke() {
        // TODO
    }

}
