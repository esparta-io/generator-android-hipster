package <%= appPackage %>.domain.usecases.<%= useCasePackageName %>;

import <%= appPackage %>.domain.executors.ThreadExecutor;
import <%= appPackage %>.domain.interactors.base.UseCaseInteractor;

import javax.inject.Inject;

public class <%= useCaseName %>UseCase extends UseCaseInteractor  {

    @Inject
    public <%= useCaseName %>UseCase(ThreadExecutor executor) {
        super(executor);
    }

    public void invoke() {
        // TODO
    }

}
