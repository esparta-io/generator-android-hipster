package <%= appPackage %>.util;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.Result;
import rx.Observable;
import rx.Single;
import rx.functions.Func1;

public class RepositoryUtils {

    @NonNull
    public static <T> Observable.Transformer<Result<T>, T> transformResult() {

        return responseObservable -> responseObservable.flatMap((Func1<Result<T>, Observable<T>>) result -> {
            if (!result.isError() && result.response().isSuccess()) {
                return Observable.just(result.response().body());
            }

            if (result.isError()) {

                if (result.error() instanceof SocketTimeoutException) {
                    // TODO handle exception
                } else if (result.error() instanceof IOException) {
                    if (result.error() instanceof java.net.ConnectException) {
                        // TODO handle exception
                    } else if (result.error() instanceof SocketTimeoutException) {
                        // TODO handle exception
                    } else {
                        // TODO handle exception
                    }
                } else {
                    // TODO handle exception
                }

                // TODO change to handled exception
                return Observable.error(result.error());
            }
            return Observable.error(result.error());
        });
    }

    public static <T> Single.Transformer<Result<T>, T> transformSingleResult() {

        return responseObservable -> responseObservable.flatMap((Func1<Result<T>, Single<T>>) result -> {

          if (!result.isError() && result.response().isSuccess()) {
              return Single.just(result.response().body());
          }

          if (result.isError()) {

              if (result.error() instanceof SocketTimeoutException) {
                  // TODO handle exception
              } else if (result.error() instanceof IOException) {
                  if (result.error() instanceof java.net.ConnectException) {
                      // TODO handle exception
                  } else if (result.error() instanceof SocketTimeoutException) {
                      // TODO handle exception
                  } else {
                      // TODO handle exception
                  }
              } else {
                  // TODO handle exception
              }

              // TODO change to handled exception
              return Single.error(result.error());
          }
          return Single.error(result.error());
        });
    }
}
