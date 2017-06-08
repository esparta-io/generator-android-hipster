# generator-android-hipster
> Android generator based on MVP, Dagger2, RxJava, Java/Kotlin Stack and usefull libraries for Android

> Latest version: 1.0.1


> Update with `npm update -g generator-android-hipster`


> Info with `npm info generator-android-hipster`


---

# How it works?

It provides a generator to create and maintain a android application based on the latests frameworks and patterns used by the community.

Stack:
- [Kotlin](https://kotlinlang.org/)
- [RxJava](https://github.com/ReactiveX/RxJava)
- [RxAndroid](https://github.com/ReactiveX/RxAndroid)
- MVP
- Interactors / UseCases
- Repositories
- Flavors like used in [u2020](https://github.com/JakeWharton/u2020)
- [Dagger2](http://google.github.io/dagger/)
- Scoped dependencies
- Event bus systems ([EventBus](https://github.com/greenrobot/EventBus) / [Otto](http://square.github.io/otto/))
- [Stetho](http://facebook.github.io/stetho/)
- [Retrofit](http://square.github.io/retrofit/) / [OkHttp](http://square.github.io/okhttp/)
- [Timber](https://github.com/JakeWharton/timber)
- Image Loaders ([Glide](https://github.com/bumptech/glide) / [Picasso](http://square.github.io/picasso/))
- [Google Play Services](https://developers.google.com/android/guides/overview)
- Push notifications
- [ButterKnife](http://jakewharton.github.io/butterknife/)
- [Anko](https://github.com/Kotlin/anko) (kotlin)
- [JodaTimeAndroid](https://github.com/dlew/joda-time-android) / [Jodamoney](http://www.joda.org/joda-money/)
- [Gson](https://github.com/google/gson)
- [MixPanel](https://mixpanel.com/help/reference/android)
- [Storage](https://github.com/cavarzan/gson-preferences-storage) in SharedPreferences using Gson
- [PrintView](https://github.com/johnkil/Print)
- [Calligraphy](https://github.com/chrisjenx/Calligraphy)
- [AutoValue](https://github.com/google/auto/tree/master/value) / [AutoParcel](https://github.com/frankiesardo/auto-parcel)


Some of these technologies are optional, and you can choose what you want to use answering the questions when you create the project.
Some of them are mandatory, but can be optional soon. If you have any questions, please create a new issue and we will discuss.

---

## What you can do after create a project:

- Create a new Activity
  * Create new `Activity` (including resources and `AndroidManifest.xml` update)
  * Create a new Dagger2 Component / Module or use the `ApplicationComponent`
  * Create a Fragment (optional)
  * For all cases, create the presenter
  * Inject the presenter into Activity / Fragment
  * Create a View interface for communication between `Presenter` -> `View`

- Create a new Entity
  * It will create a model (`AutoParcel`), an Entity class for REST / DB communication, and a converter to parse from `Entity` <- -> `Model` class.

- Create a Interactor
  * Create a base `interactor`.
  * Use with or without Interface
  * Interface use @Provides in `ApplicationModule`

- Create a UseCase
  * Same as `Interactor`, but different name =)
  * It equal to `Interactor` generation, future changes proof.
  * Use with or without Interface
  * Interface use @Provides in `ApplicationModule`

- Create a repository
  * Create a repository layer (To manage calls to Remote / Local Repository without exposing them to Interactors / UseCases)
  * Can create a `RemoteRepository` class (for REST)
  * Can create a `LocalRepository` class (for local DB)
  * Use with or without Interface
  * Interface use @Provides in `ApplicationModule`

- Create a Push architecture:
  * Create the classes to handle push notifications like this [article](https://medium.com/@deividi/a-good-way-to-handle-incoming-notifications-in-android-dc64c29041a5)

- Update gradle dependencies
  * Update gradle dependencies to latest stable versions with just one command.

Check this [article](https://medium.com/@dmilicic/a-detailed-guide-on-developing-android-apps-using-the-clean-architecture-pattern-d38d71e94029#.ucymv1rr1)

Any suggestions? [Please let me know](https://github.com/cavarzan/generator-android-hipster/issues)!

### Sample:
> [Sample Project](https://github.com/cavarzan/android-hipster-sample)
---

## Installation

First, install [Yeoman](http://yeoman.io) and generator-android-hipster using [npm](https://www.npmjs.com/) (we assume you have pre-installed [node.js](https://nodejs.org/)).

```bash
npm install -g yo
npm install -g generator-android-hipster
```

# Available commands:

### Create new project:

```bash
yo android-hipster
```

### Update gradle dependencies:

```bash
yo android-hipster:update-dependencies
```

### Create a new activity:

```bash
yo android-hipster:activity
```

### Create a new fragment:

```bash
yo android-hipster:fragment
```

### Create a new interactor:

```bash
yo android-hipster:interactor
```

### Create a new use case:

```bash
yo android-hipster:usecase
```

### Create a new repository:

```bash
yo android-hipster:repository
```

### Create a new entity:

```bash
yo android-hipster:entity
```

### Create a new push support like this [article](https://medium.com/@deividi/a-good-way-to-handle-incoming-notifications-in-android-dc64c29041a5):

```bash
yo android-hipster:push
```


## License

 © [esparta-io](https://github.com/esparta-io/)


[npm-image]: https://badge.fury.io/js/generator-android-hipster.svg
[npm-url]: https://npmjs.org/package/generator-android-hipster
[travis-image]: https://travis-ci.org/cavarzan/generator-android-hipster.svg?branch=master
[travis-url]: https://travis-ci.org/cavarzan/generator-android-hipster
[daviddm-image]: https://david-dm.org/cavarzan/generator-android-hipster.svg?theme=shields.io
[daviddm-url]: https://david-dm.org/cavarzan/generator-android-hipster
[coveralls-image]: https://coveralls.io/repos/cavarzan/generator-android-hipster/badge.svg
[coveralls-url]: https://coveralls.io/r/cavarzan/generator-android-hipster
