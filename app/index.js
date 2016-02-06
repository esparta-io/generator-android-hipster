'use strict'
var yeoman = require('yeoman-generator')
var chalk = require('chalk')
var yosay = require('yosay')
var mkdirp = require('mkdirp')
var generators = require('yeoman-generator')
var _ = require('lodash')
var fileExists = require('file-exists')

var scriptBase = require('../script-base')
var util = require('util')

var AndroidManifest = require('androidmanifest')
var AndroidResource = require('../androidresources')

var AppGenerator = generators.Base.extend({})

util.inherits(AppGenerator, scriptBase)

module.exports = AppGenerator.extend({
  prompting: function () {
    var done = this.async()

    this.log(yosay(
      'Welcome to the ' + chalk.red('Android Hispter') + ' generator!'
    ))

    var defaultAppBaseName = 'android.hipster'

    var prompts = [{
      name: 'name',
      message: 'What are the name of your app?',
      store: true,
      validate: function (input) {
        if (/^([a-zA-Z0-9_]*)$/.test(input)) return true
        return 'Your application name cannot contain special characters or a blank space, using the default name instead : ' + defaultAppBaseName
      },
      default: this.defaultAppBaseName
    },
      {
        name: 'package',
        message: 'What is the package name of the app?',
        validate: function (input) {
          if (/^([a-z_]{1}[a-z0-9_]*(\.[a-z_]{1}[a-z0-9_]*)*)$/.test(input)) return true
          return 'The package name you have provided is not a valid Java package name.'
        },
        default: 'io.android.hipster',
        store: true
      },
      {
        type: 'list',
        name: 'language',
        message: 'What language would you like to use? ',
        choices: [
          {
            value: 'java',
            name: 'Java (with Retrolambda)'
          // },
          // {
          // value: 'kotlin',
          // name: 'Kotlin'
          }

        ],
        default: 0
      },

      {
        type: 'confirm',
        name: 'butterknife',
        message: 'Use ButterKnife? ',
        default: true
      },

      {
        type: 'list',
        name: 'image',
        message: 'What Image Lib would you like to use? ',
        choices: [
          {
            value: 'glide',
            name: 'Glide'
          },
          {
            value: 'picasso',
            name: 'Picasso'
          }

        ],
        default: 0
      },
      {
        type: 'confirm',
        name: 'eventbus',
        message: 'Would you like to use EventBus?',
        default: true
      },
      {
        type: 'confirm',
        name: 'calligraphy',
        message: 'Would you like to use calligraphy for custom fonts?',
        default: true
      },
      {
        type: 'confirm',
        name: 'nucleus',
        message: 'Would you like to use Nucleus for MVP?',
        default: true
      },
      {
        type: 'confirm',
        name: 'jodatime',
        message: 'Would you like to use Joda Time?',
        default: true
      },
      {
        type: 'confirm',
        name: 'mixpanel',
        message: 'Would you like to use MixPanel?',
        default: true
      },
      {
        type: 'confirm',
        name: 'jodamoney',
        message: 'Would you like to use Joda Money?',
        default: true
      },
      {
        type: 'confirm',
        name: 'timber',
        message: 'Would you like to use Timber for logs?',
        default: true
      },
      {
        type: 'confirm',
        name: 'printview',
        message: 'Would you like to use PrintView for icon font?',
        default: true
      },
      {
        type: 'confirm',
        name: 'stetho',
        message: 'Would you like to use Stetho for Network Monitoring?',
        default: true
      },
      {
        type: 'confirm',
        name: 'autoparcel',
        message: 'Would you like to use AutoParcel?',
        default: true
      },
      {
        name: 'targetSdk',
        message: 'What Android SDK will you be targeting?',
        store: true,
        default: 23
      },
      {
        name: 'minSdk',
        message: 'What is the minimum Android SDK you wish to support?',
        store: true,
        default: 15
      },
      {
        type: 'checkbox',
        name: 'playServices',
        message: 'Enable Google Play Services Libraries?',
        choices: [
          {name: 'base', value: 'base'},
          {name: 'location', value: 'location'},
          {name: 'gcm', value: 'gcm'},
          {name: 'maps', value: 'maps'},
          {name: 'plus', value: 'plus'},
          {name: 'auth', value: 'auth'},
          {name: 'identity', value: 'identity'},
          {name: 'appindexing', value: 'appindexing'},
          {name: 'appinvite', value: 'appinvite'},
          {name: 'analytics', value: 'analytics'},
          {name: 'cast', value: 'cast'},
          {name: 'drive', value: 'drive'},
          {name: 'fitness', value: 'fitness'},
          {name: 'ads', value: 'ads'},
          {name: 'vision', value: 'vision'},
          {name: 'nearby', value: 'nearby'},
          {name: 'panorama', value: 'panorama'},
          {name: 'games', value: 'games'},
          {name: 'wearable', value: 'wearable'},
          {name: 'safetynet', value: 'safetynet'},
          {name: 'wallet', value: 'wallet'},
          {name: 'wearable', value: 'wearable'}

        ],
        default: ['no']
      }

    ]

    this.prompt(prompts, function (props) {
      this.appName = props.name
      this.imageLib = props.image
      this.eventbus = props.eventbus
      this.mixpanel = props.mixpanel
      this.timber = props.timber
      this.jodatime = props.jodatime
      this.jodamoney = props.jodamoney
      this.nucleus = props.nucleus
      this.butterknife = props.butterknife
      this.appPackage = props.package
      this.androidTargetSdkVersion = props.targetSdk
      this.androidMinSdkVersion = props.minSdk
      this.language = props.language
      this.calligraphy = props.calligraphy
      this.playServices = props.playServices
      this.stetho = props.stetho
      this.printview = props.printview
      this.autoparcel = true; // Yeap, need to be true at this time
      // this.autoparcel = props.autoparcel

      done()
    }.bind(this))
  },

  configuring: {
    saveSettings: function () {
      this.config.set('appPackage', this.appPackage)
      this.config.set('appName', this.appName)
      this.config.set('language', this.language)
      this.config.set('nucleus', this.nucleus)
    }
  },

  writing: {
    projectfiles: function () {
      this.copy('gitignore', '.gitignore')
      this.copy('gradle.properties', 'gradle.properties')
      this.copy('gradlew', 'gradlew')
      this.copy('gradlew.bat', 'gradlew.bat')
      this.template('settings.gradle', 'settings.gradle')
      this.directory('gradle', 'gradle')

      this.copy('common/gitignore', 'app/.gitignore')
      this.copy('common/proguard-rules.pro', 'app/proguard-rules.pro')

      this.template('_build.gradle', 'build.gradle', this, {})

      this.template('common/_app_build.gradle', 'app/build.gradle', this, {})
    },

    app: function () {
      var packageDir = this.appPackage.replace(/\./g, '/')

      mkdirp('app')
      mkdirp('app/libs')

      var i = 0
      var appFolder
      if (this.language == 'java') {
        appFolder = 'app-java'
      } else {
        appFolder = 'app-kotlin'
      }

      mkdirp('app/src/internal/java/' + packageDir)
      mkdirp('app/src/internalDebug/java/' + packageDir)
      mkdirp('app/src/internalRelease/java/' + packageDir)

      mkdirp('app/src/production/java/' + packageDir)
      mkdirp('app/src/productionDebug/java/' + packageDir)
      mkdirp('app/src/productionRelease/java/' + packageDir)

      mkdirp('app/src/main/java/' + packageDir)

      this.template(appFolder + '/src/main/java/environment', 'app/src/internalDebug/java/' + packageDir + '/environment', this, {})
      this.template(appFolder + '/src/main/java/environment', 'app/src/internalRelease/java/' + packageDir + '/environment', this, {})
      this.template(appFolder + '/src/main/java/environment', 'app/src/productionDebug/java/' + packageDir + '/environment', this, {})
      this.template(appFolder + '/src/main/java/environment', 'app/src/productionRelease/java/' + packageDir + '/environment', this, {})

      this.template(appFolder + '/src/main/java/application', 'app/src/main/java/' + packageDir + '/application', this, {})

      this.template(appFolder + '/src/main/java/di', 'app/src/main/java/' + packageDir + '/di', this, {})

      this.template(appFolder + '/src/main/java/domain', 'app/src/main/java/' + packageDir + '/domain', this, {})

      if (this.language == 'kotlin') {
        this.template(appFolder + '/src/main/java/extensions/ContextExtensions.kt', 'app/src/main/java/' + packageDir + '/extensions/ContextExtensions.kt', this, {})
        if (this.nucleus == true) {
          this.template(appFolder + '/src/main/java/extensions/PresenterExtensions.kt', 'app/src/main/java/' + packageDir + '/extensions/PresenterExtensions.kt', this, {})
        }
      }

      this.template(appFolder + '/src/main/java/model', 'app/src/main/java/' + packageDir + '/model', this, {})

      var ext = this.language == 'java' ? '.java' : '.kt'

      this.template(appFolder + '/src/main/java/ui/base/BaseActivity' + ext, 'app/src/main/java/' + packageDir + '/ui/base/BaseActivity' + ext, this, {})

      this.template(appFolder + '/src/main/java/ui/base/BaseFragment' + ext, 'app/src/main/java/' + packageDir + '/ui/base/BaseFragment' + ext, this, {})

      this.template(appFolder + '/src/main/java/ui/base/BasePresenter' + ext, 'app/src/main/java/' + packageDir + '/ui/base/BasePresenter' + ext, this, {})

      this.template(appFolder + '/src/main/java/ui/base/EmptyPresenter' + ext, 'app/src/main/java/' + packageDir + '/ui/base/EmptyPresenter' + ext, this, {})

      this.template(appFolder + '/src/main/java/ui/base/PresenterView' + ext, 'app/src/main/java/' + packageDir + '/ui/base/PresenterView' + ext, this, {})

      this.template(appFolder + '/src/main/java/storage', 'app/src/main/java/' + packageDir + '/storage', this, {})

      if (this.nucleus == false) {
        this.template(appFolder + '/src/main/java/ui/base/Presenter' + ext, 'app/src/main/java/' + packageDir + '/ui/base/Presenter' + ext, this, {})
      }

      if (this.timber) {
        this.template(appFolder + '/src/main/java/util/logging', 'app/src/main/java/' + packageDir + '/util/logging', this, {})
      }
      if (this.mixpanel) {
        this.template(appFolder + '/src/main/java/util/analytics', 'app/src/main/java/' + packageDir + '/util/analytics', this, {})
      }
      if (this.jodatime) {
        this.template(appFolder + '/src/main/java/util/gson/DateTimeTypeConverter' + ext, 'app/src/main/java/' + packageDir + '/util/gson/DateTimeTypeConverter' + ext, this, {})
        this.template(appFolder + '/src/main/java/util/gson/DateTimeZoneTypeConverter' + ext, 'app/src/main/java/' + packageDir + '/util/gson/DateTimeZoneTypeConverter' + ext, this, {})
      }
      if (this.jodamoney) {
        this.template(appFolder + '/src/main/java/util/gson/CurrencyUnitTypeConverter' + ext, 'app/src/main/java/' + packageDir + '/util/gson/CurrencyUnitTypeConverter' + ext, this, {})
        this.template(appFolder + '/src/main/java/util/gson/MoneyTypeConverter' + ext, 'app/src/main/java/' + packageDir + '/util/gson/MoneyTypeConverter' + ext, this, {})
      }

      if (this.autoparcel) {
        this.template(appFolder + '/src/main/java/util/gson/AutoGson' + ext, 'app/src/main/java/' + packageDir + '/util/gson/AutoGson' + ext, this, {})
        this.template(appFolder + '/src/main/java/util/gson/AutoValueTypeAdapterFactory' + ext, 'app/src/main/java/' + packageDir + '/util/gson/AutoValueTypeAdapterFactory' + ext, this, {})
      }

      this.template(appFolder + '/src/main/java/util/gson/GsonModule' + ext, 'app/src/main/java/' + packageDir + '/util/gson/GsonModule' + ext, this, {})
      this.template(appFolder + '/src/main/java/util/RxUtils' + ext, 'app/src/main/java/' + packageDir + '/util/RxUtils' + ext, this, {})
      this.template(appFolder + '/src/main/java/util/PermissionUtils' + ext, 'app/src/main/java/' + packageDir + '/util/PermissionUtils' + ext, this, {})
      this.template(appFolder + '/src/main/java/util/RepositoryUtils' + ext, 'app/src/main/java/' + packageDir + '/util/RepositoryUtils' + ext, this, {})

      this.template(appFolder + '/src/main/java/util/google', 'app/src/main/java/' + packageDir + '/util/google', this, {})

      this.template(appFolder + '/src/main/java/ui/main', 'app/src/main/java/' + packageDir + '/ui/main', this, {})

      mkdirp('app/src/main/assets')

      mkdirp('app/src/main/res')

      this.directory('resources/assets', 'app/src/main/assets')

      this.directory('resources/res', 'app/src/main/res')

      this.template('resources/_AndroidManifest.xml', 'app/src/main/AndroidManifest.xml', this, {})

      mkdirp('app/src/debug')

    },
  },
  install: function () {
    if (this.language == 'kotlin') {
      this.addGradleDependency('compile', 'io.reactivex', 'rxkotlin', '0.30.1')
      this.addGradleDependency('compile', 'org.jetbrains.kotlin', 'kotlin-stdlib', '1.0.0-beta-4584')
      this.addGradleDependency('compile', 'org.jetbrains.anko', 'anko-sdk15', '0.8.1')
      this.addGradleDependency('compile', 'org.jetbrains.anko', 'anko-support-v4', '0.8.1')
      this.addGradleDependency('kapt', 'com.google.dagger', 'dagger-compiler', '2.0.2')
      this.addGradleDependency('compile', 'io.reactivex', 'rxkotlin', '0.30.1')
      if (this.butterknife == true) {
        this.addGradleDependency('kapt', 'com.jakewharton', 'butterknife', '7.0.1')
      }
    } else {
      this.addGradleDependency('retrolambdaConfig', 'net.orfjackal.retrolambda', 'retrolambda', '2.1.0')
      this.addGradleDependency('apt', 'com.google.dagger', 'dagger-compiler', '2.0.2')
    }

    if (this.nucleus) {
      this.addGradleDependency('compile', 'info.android15.nucleus', 'nucleus', '2.0.5')
      this.addGradleDependency('compile', 'info.android15.nucleus', 'nucleus-support-v4', '2.0.5')
      this.addGradleDependency('compile', 'info.android15.nucleus', 'nucleus-support-v7', '2.0.5')
    }

    if (this.butterknife) {
      this.addGradleDependency('compile', 'com.jakewharton', 'butterknife', '7.0.1')
    }

    if (this.eventbus) {
      this.addGradleDependency('compile', 'org.greenrobot', 'eventbus', '3.0.0')
    }

    if (this.imageLib == 'glide') {
      this.addGradleDependency('compile', 'com.github.bumptech.glide', 'glide', '3.6.1')
    }

    if (this.imageLib == 'picasso') {
      this.addGradleDependency('compile', 'com.squareup.picasso', 'picasso', '2.5.2')
    }

    if (this.calligraphy == true) {
      this.addGradleDependency('compile', 'uk.co.chrisjenx', 'calligraphy', '2.1.0')
    }

    if (this.timber == true) {
      this.addGradleDependency('compile', 'com.jakewharton.timber', 'timber', '3.1.0')
    }
    if (this.jodatime == true) {
      this.addGradleDependency('compile', 'net.danlew', 'android.joda', '2.8.2')
    }

    if (this.jodamoney == true) {
      this.addGradleDependency('compile', 'org.joda', 'joda-money', '0.10.0')
    }

    if (this.printview == true) {
      this.addGradleDependency('compile', 'com.github.johnkil.print', 'print', '1.3.1')
    }

    if (this.mixpanel == true) {
      this.addGradleDependency('compile', 'com.mixpanel.android', 'mixpanel-android', '4.6.4');}

    if (this.stetho) {
      this.addGradleDependency('compile', 'com.facebook.stetho', 'stetho', '1.2.0')
      this.addGradleDependency('compile', 'com.facebook.stetho', 'stetho-okhttp', '1.2.0')
    }

    if (this.autoparcel) {
      this.addGradleDependency('compile', 'com.github.frankiesardo', 'auto-parcel', '0.3.1')
      if (this.language == 'java') {
        this.addGradleDependency('apt', 'com.github.frankiesardo', 'auto-parcel-processor', '0.3.1')
      } else {
        this.addGradleDependency('kapt', 'com.github.frankiesardo', 'auto-parcel-processor', '0.3.1')
      }
    }

    if (this.playServices.length > 0) {
      this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-base', '8.4.0')

      if (this.playServices.indexOf('plus') != -1)
        this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-plus', '8.4.0')
      if (this.playServices.indexOf('auth') != -1)
        this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-auth', '8.4.0')
      if (this.playServices.indexOf('identity') != -1)
        this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-identity', '8.4.0')
      if (this.playServices.indexOf('appindexing') != -1)
        this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-appindexing', '8.4.0')
      if (this.playServices.indexOf('appinvite') != -1)
        this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-appinvite', '8.4.0')
      if (this.playServices.indexOf('analytics') != -1)
        this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-analytics', '8.4.0')
      if (this.playServices.indexOf('cast') != -1)
        this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-cast', '8.4.0')
      if (this.playServices.indexOf('gcm') != -1)

        this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-gcm', '8.4.0')
      if (this.playServices.indexOf('drive') != -1)
        this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-drive', '8.4.0')
      if (this.playServices.indexOf('fitness') != -1)
        this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-fitness', '8.4.0')
      if (this.playServices.indexOf('location') != -1)
        this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-location', '8.4.0')
      if (this.playServices.indexOf('maps') != -1)
        this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-maps', '8.4.0')
      if (this.playServices.indexOf('ads') != -1)
        this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-ads', '8.4.0')
      if (this.playServices.indexOf('vision') != -1)
        this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-vision', '8.4.0')
      if (this.playServices.indexOf('nearby') != -1)
        this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-nearby', '8.4.0')
      if (this.playServices.indexOf('panorama') != -1)
        this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-panorama', '8.4.0')
      if (this.playServices.indexOf('games') != -1)
        this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-games', '8.4.0')
      if (this.playServices.indexOf('wearable') != -1)
        this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-wearable', '8.4.0')
      if (this.playServices.indexOf('safetynet') != -1)
        this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-safetynet', '8.4.0')
      if (this.playServices.indexOf('wallet') != -1)
        this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-wallet', '8.4.0')

    }
  }
})
