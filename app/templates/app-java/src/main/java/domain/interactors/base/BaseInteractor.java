package <%= appPackage %>.domain.interactors.base;

import rx.Observable;
import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import java.util.concurrent.Executor;

public abstract class BaseInteractor {

  Executor executor;
    public BaseInteractor(Executor executor) {
      this.executor = executor;
    }

}
