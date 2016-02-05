package <%= appPackage %>.domain.interactors.base;

import java.util.concurrent.Executor;

public abstract class BaseUseCase {

    protected Executor executor;

    public BaseUseCase(Executor executor) {
        this.executor = executor;
    }

}
