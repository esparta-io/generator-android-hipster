package <%= appPackage %>.domain.interactors.<%= interactorPackageName %>;

import <%= appPackage %>.domain.executors.ThreadExecutor;
import <%= appPackage %>.domain.interactors.base.BaseInteractor;

import javax.inject.Inject;

public class <%= interactorName %>InteractorImpl extends BaseInteractor implements <%= interactorName %>Interactor {

    @Inject
    public <%= interactorName %>Interactor(ThreadExecutor executor) {
        super(executor);
    }

    @Override
    public void invoke() {
        // TODO
    }

}
