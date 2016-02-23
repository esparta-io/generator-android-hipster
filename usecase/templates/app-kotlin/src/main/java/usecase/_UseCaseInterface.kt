package <%= appPackage %>.domain.usecases.<%= useCasePackageName %>;

import rx.Observable

interface <%= useCaseName %>UseCase {

    operator fun invoke() : Observable<Any>

}
