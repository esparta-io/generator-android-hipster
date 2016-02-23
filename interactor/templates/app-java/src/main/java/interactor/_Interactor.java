package <%= appPackage %>.domain.interactors.<%= interactorPackageName %>;

import <%= appPackage %>.domain.executors.ThreadExecutor;
import <%= appPackage %>.domain.interactors.base.BaseInteractor;

import javax.inject.Inject;
import rx.Observable;

public class <%= interactorName %>Interactor extends BaseInteractor {

    @Inject
    public <%= interactorName %>Interactor(ThreadExecutor executor) {
        super(executor);
    }

    public Observable<Object> invoke() {
        return Observable.just(null);
    }

}
