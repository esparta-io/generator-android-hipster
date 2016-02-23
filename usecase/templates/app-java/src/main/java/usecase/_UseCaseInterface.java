package <%= appPackage %>.domain.usecases.<%= useCasePackageName %>;

import rx.Observable;

public interface <%= useCaseName %>UseCase {

    Observable<Object> invoke();

}
