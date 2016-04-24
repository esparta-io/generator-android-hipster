package <%= appPackage %>.domain.interactors.<%= interactorPackageName %>;

import rx.Scheduler;
import <%= appPackage %>.domain.interactors.base.BaseInteractor;

import javax.inject.Inject;
import rx.Observable;

public class <%= interactorName %>Interactor extends BaseInteractor {

    @Inject
    public <%= interactorName %>Interactor(Scheduler executor) {
        super(executor);
    }

    public Observable<Object> invoke() {
        return Observable.just(null);
    }

}
