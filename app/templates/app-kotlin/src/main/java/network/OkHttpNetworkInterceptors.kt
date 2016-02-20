package <%= appPackage %>.network

import javax.inject.Qualifier;

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class OkHttpNetworkInterceptors
