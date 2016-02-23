package <%= appPackage %>.domain.interactors.<%= interactorPackageName %>;

import <%= appPackage %>.domain.executors.ThreadExecutor;
import <%= appPackage %>.domain.interactors.base.BaseInteractor;

import rx.Observable;

public class <%= interactorName %>InteractorImpl extends BaseInteractor implements <%= interactorName %>Interactor {

    public <%= interactorName %>InteractorImpl(ThreadExecutor executor) {
        super(executor);
    }

    public Observable<Object> invoke() {
        return Observable.just(null);
    }

}
