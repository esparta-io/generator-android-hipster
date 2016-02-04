package <%= appPackage %>.domain.interactors.base;

import java.util.concurrent.Executor;

public abstract class UseCaseInteractor {

    protected Executor executor;

    public UseCaseInteractor(Executor executor) {
        this.executor = executor;
    }

}
