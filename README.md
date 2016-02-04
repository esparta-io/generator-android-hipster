# generator-android-hipster
> Android generator based on MVP, Dagger2, RxJava, Java/Kotlin Stack and usefull libraries for Android

## Advice: Beta stage, currently kotlin generation is broken... I'm working on full Java code generation and after that I will migrate code to kotlin.

# How it works?

It provides a generator to create and maintain a android application based on the latests frameworks and patterns used by the community.

Stack:
- Java / Kotlin
- RxJava
- RxAndroid
- MVP
- Interactors / UseCases
- Repositories
- Flavors like used in u2020
- Dagger2
- Event bus systems (EventBus / Otto)
- Stetho
- Retrofit / OkHttp
- Timber
- Image Loaders (Glide / Picasso)
- Google Play Services
- Push notifications
- ButterKnife
- Anko (kotlin)
- Retrolambda (Java)
- JodaTime / jodamoney
- Gson
- MixPanel
- Storage in SharedPreferences using Gson
- PrintView
- Calligraphy
- AutoValue / AutoParcel
- Guava Optionals (source included)

Yet to come:
- RxLifeCycle
- Hank / RxPreferences
- Dexter / RxPermissions
- DB implementations (I usually work with ORMLite, but it will be awesome to create a multi db options to use ORMLite, DBFlow, Storio, Realm and etc..)
- ???

Some of these technologies are optional, and you can choose what you want to use answering the questions when you create the project.
Some of them are mandatory, but can be optional soon. If you have any questions, please create a new issue and we will discuss.

---

## What you can do after create a project:

- Create a new Activity
  * Create new Activity (including resources and `AndroidManifest.xml` update)
  * Create a new Dagger2 Component / Module or use the ApplicationComponent
  * Create a Fragment (optional)
  * For all cases, create the presenter
  * Inject the presenter into Activity / Fragment
  * Create a View interface for communication between Presenter > View

- Create a new Entity
  * It will create a model (AutoParcel), an Entity class for REST / DB communication, and a converter to change from Entity <> Model class.

- Create a Interactor
  * Create a base interactor.

- Create a UseCase
  * Same as Interactor, but different name =)
  * It equal to Interactor generation, future changes proof.

- Create a repository
  * Create a repository layer (To manage calls to Remote / Local without exposing them to Interactors / UseCases)
  * Can create a RemoteRepository class (for REST)
  * Can create a LocalRepository class (for local DB)

- Create a Push architecture:
  * Create the classes to handle push notifications like this [article](https://medium.com/@deividi/a-good-way-to-handle-incoming-notifications-in-android-dc64c29041a5)

Check this [article](https://medium.com/@dmilicic/a-detailed-guide-on-developing-android-apps-using-the-clean-architecture-pattern-d38d71e94029#.ucymv1rr1)

Any suggestions? [Please let me know](https://github.com/cavarzan/generator-android-hipster/issues)!

### Sample:
>yet to come
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

### Create a new activity:

```bash
yo android-hipster:activity
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

# Special THANKS to:

[JHipster](https://github.com/jhipster/generator-jhipster) that inspired me and helped in development!!!
[Android Kickstarter](https://github.com/cristhianescobar/generator-android-starter/) another inspiration for Environment classes and Mixpanel.


## License

 © [cavarzan](https://github.com/cavarzan/)


[npm-image]: https://badge.fury.io/js/generator-android-hipster.svg
[npm-url]: https://npmjs.org/package/generator-android-hipster
[travis-image]: https://travis-ci.org/cavarzan/generator-android-hipster.svg?branch=master
[travis-url]: https://travis-ci.org/cavarzan/generator-android-hipster
[daviddm-image]: https://david-dm.org/cavarzan/generator-android-hipster.svg?theme=shields.io
[daviddm-url]: https://david-dm.org/cavarzan/generator-android-hipster
[coveralls-image]: https://coveralls.io/repos/cavarzan/generator-android-hipster/badge.svg
[coveralls-url]: https://coveralls.io/r/cavarzan/generator-android-hipster
