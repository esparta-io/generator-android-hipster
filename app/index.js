'use strict';
var yeoman = require('yeoman-generator');
var chalk = require('chalk');
var yosay = require('yosay');
var mkdirp = require('mkdirp');
var generators = require('yeoman-generator');

var scriptBase = require('../script-base');
var util = require('util');

var AndroidManifest = require('androidmanifest');

var AppGenerator = generators.Base.extend({});

util.inherits(AppGenerator, scriptBase);

module.exports = AppGenerator.extend({
    initializing: {
        setupVars: function () {
            this.baseName = this.config.get('appName');
            console.log(this.baseName);
            this.jhipsterVersion = this.config.get('jhipsterVersion');
            this.testFrameworks = this.config.get('testFrameworks');

            var configFound = this.baseName != null;
            if (configFound) {
                this.existingProject = true;
            }

            this.appName = this.config.get('appName');
            this.language = this.config.get('language');
            this.appPackage = this.config.get('appPackage');

            this.nucleus = this.config.get('nucleus') || true;
            this.mvp = this.config.get('mvp') || 'nucleus';
            this.imageLib = this.config.get('imageLib') || 'glide';
            this.glide = this.config.get('glide');
            this.picasso = this.config.get('picasso');
            this.eventbus = this.config.get('eventbus') || true;
            this.mixpanel = this.config.get('mixpanel') || true;
            this.timber = this.config.get('timber') || true;
            this.jodatime = this.config.get('jodatime') || true;
            this.jodamoney = this.config.get('jodamoney') || true;
            this.threetenabp = this.config.get('threetenabp') || true;
            this.butterknife = this.config.get('butterknife') || true;
            this.androidTargetSdkVersion = this.config.get('androidTargetSdkVersion');
            this.androidMinSdkVersion = this.config.get('minSdk');
            this.calligraphy = this.config.get('calligraphy') || true;
            this.playServices = this.config.get('playServices') || [];
            this.stetho = this.config.get('stetho') || true;
            this.printview = this.config.get('printview') || true;
            this.autoparcel = this.config.get('autoparcel') || true;
        }
    },
    prompting: function () {
        var done = this.async();

        if (this.existingProject) {
            done();
            return;
        }
        this.log(yosay(
            'Welcome to the ' + chalk.red('Android Hipster') + ' generator!'
        ));

        var defaultAppBaseName = 'android.hipster';

        var prompts = [
            {
                name: 'name',
                message: 'What are the name of your app?',
                store: true,
                validate: function (input) {
                    if (/^([a-zA-Z0-9_]*)$/.test(input)) return true;
                    return 'Your application name cannot contain special characters or a blank space, using the default name instead : ' + defaultAppBaseName
                },
                default: this.defaultAppBaseName
            },
            {
                name: 'package',
                message: 'What is the package name of the app?',
                validate: function (input) {
                    if (/^([a-z_]{1}[a-z0-9_]*(\.[a-z_]{1}[a-z0-9_]*)*)$/.test(input)) return true;
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
                        value: 'kotlin',
                        name: 'Kotlin (Java not supported anymore)'
                    }
                ],
                default: 0
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
                    },
                    {
                        value: 'none',
                        name: 'None'
                    }
                ],
                default: 'glide'
            },
            {
                type: 'list',
                name: 'mvp',
                message: 'What MVP do you want to use? ',
                choices: [
                    {
                        value: 'embeed',
                        name: 'Embeed MVP (No Lib)'
                    }
                ],
                default: 'embeed'
            },
            {
                type: 'confirm',
                name: 'butterknife',
                message: 'Use ButterKnife? ',
                default: true
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
                type: 'list',
                name: 'dates',
                message: 'What date Library do you want to use? ',
                choices: [
                    {
                        value: 'jodatime',
                        name: 'Joda Time'
                    },
                    {
                        value: 'threetenabp',
                        name: 'ThreeTenAbp'
                    },
                    {
                        value: 'none',
                        name: 'None'
                    }
                ],
                default: 'jodatime'
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
                default: false
            },
            {
                when: function (data) {
                    return data.language == 'java';
                },
                type: 'confirm',
                name: 'autoparcel',
                message: 'Would you like to use AutoParcel?',
                default: false
            },
            {
                when: function (data) {
                    return data.language == 'kotlin';
                },
                type: 'confirm',
                name: 'paperparcel',
                message: 'Would you like to use PaperParcel?',
                default: true
            },
            {
                name: 'targetSdk',
                message: 'What Android SDK will you be targeting?',
                store: true,
                default: 26
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

        ];

        this.prompt(prompts, function (props) {
            this.appName = props.name;
            this.imageLib = props.image;
            this.glide = this.imageLib == 'glide';
            this.picasso = this.imageLib == 'picasso';
            this.eventbus = props.eventbus;
            this.mixpanel = props.mixpanel;
            this.timber = props.timber;
            this.jodatime = props.dates == 'jodatime';
            this.threetenabp = props.dates == 'threetenabp';
            this.jodamoney = props.jodamoney;
            this.butterknife = props.butterknife;
            this.appPackage = props.package;
            this.androidTargetSdkVersion = props.targetSdk;
            this.androidMinSdkVersion = props.minSdk;
            this.language = props.language;
            this.calligraphy = props.calligraphy;
            this.playServices = props.playServices;
            this.paperparcel = props.paperparcel;
            this.stetho = props.stetho;
            this.printview = props.printview;
            this.autoparcel = true; // Yeap, need to be true at this time
            // this.autoparcel = props.autoparcel
            this.mvp = props.mvp;
            this.mvpembeed = props.mvp == 'embeed';
            this.nucleus = props.mvp == 'nucleus';


            done()
        }.bind(this))
    },

    configuring: {
        saveSettings: function () {
            if (this.existingProject) {
                return;
            }
            this.config.set('appPackage', this.appPackage);
            this.config.set('appName', this.appName);
            this.config.set('language', this.language);
            this.config.set('nucleus', this.nucleus);
            this.config.set('mvp', this.mvp);
            this.config.set('imageLib', this.imageLib);
            this.config.set('picasso', this.picasso);
            this.config.set('glide', this.glide);
            this.config.set('eventbus', this.eventbus);
            this.config.set('mixpanel', this.mixpanel);
            this.config.set('timber', this.timber);
            this.config.set('jodatime', this.jodatime);
            this.config.set('threetenabp', this.threetenabp);
            this.config.set('paperparcel', this.paperparcel);
            this.config.set('jodamoney', this.jodamoney);
            this.config.set('butterknife', this.butterknife);
            this.config.set('androidTargetSdkVersion', this.androidTargetSdkVersion);
            this.config.set('minSdk', this.androidMinSdkVersion);
            this.config.set('calligraphy', this.calligraphy);
            this.config.set('playServices', this.playServices);
            this.config.set('stetho', this.stetho);
            this.config.set('printview', this.printview);
            this.config.set('autoparcel', this.autoparcel);
            this.config.set('mvp', this.mvp);
            this.config.set('nucleus', this.mvp == 'nucleus');
        }
    },


    writing: {
        projectfiles: function () {
            this.copy('gitignore', '.gitignore');
            this.copy('gradle.properties', 'gradle.properties');
            this.copy('gradlew', 'gradlew');
            this.copy('gradlew.bat', 'gradlew.bat');
            this.template('settings.gradle', 'settings.gradle');
            this.directory('gradle', 'gradle');

            this.copy('common/gitignore', 'app/.gitignore');
            this.copy('common/proguard-rules.pro', 'app/proguard-rules.pro');

            this.template('_build.gradle', 'build.gradle', this, {});

            this.template('common/_app_build.gradle', 'app/build.gradle', this, {})
        },

        app: function () {

            console.log(this.stetho);
            var packageDir = this.appPackage.replace(/\./g, '/');

            mkdirp('app');
            mkdirp('app/libs');

            var appFolder = 'app-kotlin';

            mkdirp('app/src/internal/java/' + packageDir);
            mkdirp('app/src/internalDebug/java/' + packageDir);
            mkdirp('app/src/internalRelease/java/' + packageDir);

            mkdirp('app/src/production/java/' + packageDir);
            mkdirp('app/src/productionDebug/java/' + packageDir);
            mkdirp('app/src/productionRelease/java/' + packageDir);

            mkdirp('app/src/main/java/' + packageDir);

            var ext = this.language == 'java' ? '.java' : '.kt';

            this.template(appFolder + '/src/main/java/network/OAuthInterceptor.kt', 'app/src/main/java/' + packageDir + '/network/OAuthInterceptor.kt', this, {});
            this.template(appFolder + '/src/main/java/network/OkHttpNetworkInterceptors.kt', 'app/src/main/java/' + packageDir + '/network/OkHttpNetworkInterceptors.kt', this, {});
            this.template(appFolder + '/src/main/java/network/OkHttpInterceptors.kt', 'app/src/main/java/' + packageDir + '/network/OkHttpInterceptors.kt', this, {});
            this.template(appFolder + '/src/main/java/network/OkHttpInterceptorsModuleInternal.kt', 'app/src/internal/java/' + packageDir + '/network/OkHttpInterceptorsModule.kt', this, {});
            this.template(appFolder + '/src/main/java/network/OkHttpInterceptorsModule.kt', 'app/src/production/java/' + packageDir + '/network/OkHttpInterceptorsModule.kt', this, {});

            this.template(appFolder + '/src/main/java/service/LogoutWorker.kt', 'app/src/main/java/' + packageDir + '/service/LogoutWorker.kt', this, {});
            this.template(appFolder + '/src/main/java/service/push/PushExtras.kt', 'app/src/main/java/' + packageDir + '/service/push/PushExtras.kt', this, {});

            this.template(appFolder + '/src/main/java/environment', 'app/src/internal/java/' + packageDir + '/environment', this, {});
            this.template(appFolder + '/src/main/java/environment', 'app/src/production/java/' + packageDir + '/environment', this, {});

            this.template(appFolder + '/src/main/java/application', 'app/src/main/java/' + packageDir + '/application', this, {});
            this.template(appFolder + '/src/main/java/di', 'app/src/main/java/' + packageDir + '/di', this, {});
            this.template(appFolder + '/src/main/java/domain', 'app/src/main/java/' + packageDir + '/domain', this, {});
            if (this.language == 'kotlin') {
                this.template(appFolder + '/src/main/java/extensions/ContextExtensions.kt', 'app/src/main/java/' + packageDir + '/extensions/ContextExtensions.kt', this, {});
                this.template(appFolder + '/src/main/java/extensions/Extensions.kt', 'app/src/main/java/' + packageDir + '/extensions/Extensions.kt', this, {});
                if (this.nucleus == true) {
                    this.template(appFolder + '/src/main/java/extensions/PresenterExtensions.kt', 'app/src/main/java/' + packageDir + '/extensions/PresenterExtensions.kt', this, {})
                }
            }
            this.template(appFolder + '/src/main/java/model/OAuth.kt', 'app/src/main/java/' + packageDir + '/model/OAuth.kt', this, {});

            this.template(appFolder + '/src/main/java/ui/base/BaseActivity.kt', 'app/src/main/java/' + packageDir + '/ui/base/BaseActivity.kt', this, {});
            this.template(appFolder + '/src/main/java/ui/base/BaseAdapter.kt', 'app/src/main/java/' + packageDir + '/ui/base/BaseAdapter.kt', this, {});
            this.template(appFolder + '/src/main/java/ui/base/BasePresenter.kt', 'app/src/main/java/' + packageDir + '/ui/base/BasePresenter.kt', this, {});
            this.template(appFolder + '/src/main/java/ui/base/BaseFragment.kt', 'app/src/main/java/' + packageDir + '/ui/base/BaseFragment.kt', this, {});
            this.template(appFolder + '/src/main/java/ui/base/EmptyPresenter.kt', 'app/src/main/java/' + packageDir + '/ui/base/EmptyPresenter.kt', this, {});
            this.template(appFolder + '/src/main/java/ui/base/PresenterView.kt', 'app/src/main/java/' + packageDir + '/ui/base/PresenterView.kt', this, {});
            if (this.language == 'kotlin') {
                if (this.eventbus) {
                    this.template(appFolder + '/src/main/java/ui/base/EventBusUser.kt', 'app/src/main/java/' + packageDir + '/ui/base/EventBusUser.kt', this, {})
                }

                this.template(appFolder + '/src/main/java/ui/base/IFailMessage.kt', 'app/src/main/java/' + packageDir + '/ui/base/IFailMessage.kt', this, {});
                this.template(appFolder + '/src/main/java/ui/base/IProgressActivity.kt', 'app/src/main/java/' + packageDir + '/ui/base/IProgressActivity.kt', this, {});
                this.template(appFolder + '/src/main/java/ui/base/IToolbarActivity.kt', 'app/src/main/java/' + packageDir + '/ui/base/IToolbarActivity.kt', this, {});
                this.template(appFolder + '/src/main/java/ui/base/ProgressPresenterView.kt', 'app/src/main/java/' + packageDir + '/ui/base/ProgressPresenterView.kt', this, {});
            }

            this.template(appFolder + '/src/main/java/storage', 'app/src/main/java/' + packageDir + '/storage', this, {});
            if (this.nucleus == false) {
                this.template(appFolder + '/src/main/java/ui/base/Presenter.kt', 'app/src/main/java/' + packageDir + '/ui/base/Presenter.kt', this, {})
            }
            if (this.timber) {
                this.template(appFolder + '/src/main/java/util/logging', 'app/src/main/java/' + packageDir + '/util/logging', this, {})
            }

            if (this.jodatime) {
                this.template(appFolder + '/src/main/java/util/gson/DateTimeTypeConverter.kt', 'app/src/main/java/' + packageDir + '/util/gson/DateTimeTypeConverter.kt', this, {});
                this.template(appFolder + '/src/main/java/util/gson/DateTimeZoneTypeConverter.kt', 'app/src/main/java/' + packageDir + '/util/gson/DateTimeZoneTypeConverter.kt', this, {})
            }
            if (this.jodamoney) {
                this.template(appFolder + '/src/main/java/util/gson/CurrencyUnitTypeConverter.kt', 'app/src/main/java/' + packageDir + '/util/gson/CurrencyUnitTypeConverter.kt', this, {});
                this.template(appFolder + '/src/main/java/util/gson/MoneyTypeConverter.kt', 'app/src/main/java/' + packageDir + '/util/gson/MoneyTypeConverter.kt', this, {})
            }
            if (this.autoparcel && this.language == 'java') {
                this.template(appFolder + '/src/main/java/util/gson/AutoGson.kt', 'app/src/main/java/' + packageDir + '/util/gson/AutoGson.kt', this, {});
                this.template(appFolder + '/src/main/java/util/gson/AutoValueTypeAdapterFactory.kt', 'app/src/main/java/' + packageDir + '/util/gson/AutoValueTypeAdapterFactory.kt', this, {})
            }
            this.template(appFolder + '/src/main/java/util/gson/GsonModule.kt', 'app/src/main/java/' + packageDir + '/util/gson/GsonModule.kt', this, {});
            this.template(appFolder + '/src/main/java/util/DensityUtil.kt', 'app/src/main/java/' + packageDir + '/util/DensityUtil.kt', this, {});
            this.template(appFolder + '/src/main/java/util/ExtractSingleResult.kt', 'app/src/main/java/' + packageDir + '/util/ExtractSingleResult.kt', this, {});
            this.template(appFolder + '/src/main/java/util/LinearMarginItemDecoration.kt', 'app/src/main/java/' + packageDir + '/util/LinearMarginItemDecoration.kt', this, {});
            this.template(appFolder + '/src/main/java/util/StringUtils.kt', 'app/src/main/java/' + packageDir + '/util/StringUtils.kt', this, {});
            this.template(appFolder + '/src/main/java/util/PermissionUtils.kt', 'app/src/main/java/' + packageDir + '/util/PermissionUtils.kt', this, {});

            if (this.language == 'kotlin') {
                this.template(appFolder + '/src/main/java/domain/repository/exception/ApiException.kt', 'app/src/main/java/' + packageDir + '/domain/repository/exception/ApiException.kt', this, {});
                this.template(appFolder + '/src/main/java/domain/repository/exception/ErrorMessage.kt', 'app/src/main/java/' + packageDir + '/domain/repository/exception/ErrorMessage.kt', this, {});
                this.template(appFolder + '/src/main/java/util/ExtractErrorUtil.kt', 'app/src/main/java/' + packageDir + '/util/ExtractErrorUtil.kt', this, {});
                this.template(appFolder + '/src/main/java/util/ExtractResult.kt', 'app/src/main/java/' + packageDir + '/util/ExtractResult.kt', this, {});
                this.template(appFolder + '/src/main/java/util/RetryWhen.kt', 'app/src/main/java/' + packageDir + '/util/RetryWhen.kt', this, {});
            }

            if (this.language == 'java') {
                this.template(appFolder + '/src/main/java/util/AsyncHandler.kt', 'app/src/main/java/' + packageDir + '/util/AsyncHandler.kt', this, {});
                this.template(appFolder + '/src/main/java/util/CallInExecutorThanMainThread.kt', 'app/src/main/java/' + packageDir + '/util/CallInExecutorThanMainThread.kt', this, {});
                this.template(appFolder + '/src/main/java/util/FilterOrThrow.kt', 'app/src/main/java/' + packageDir + '/util/FilterOrThrow.kt', this, {});
                this.template(appFolder + '/src/main/java/util/RetryWhen.kt', 'app/src/main/java/' + packageDir + '/util/RetryWhen.kt', this, {});
                this.template(appFolder + '/src/main/java/util/RxUtils.kt', 'app/src/main/java/' + packageDir + '/util/RxUtils.kt', this, {});
                this.template(appFolder + '/src/main/java/util/RepositoryUtils.kt', 'app/src/main/java/' + packageDir + '/util/RepositoryUtils.kt', this, {});
                this.template(appFolder + '/src/main/java/util/google', 'app/src/main/java/' + packageDir + '/util/google', this, {});
            }

            this.template(appFolder + '/src/main/java/ui/main', 'app/src/main/java/' + packageDir + '/ui/main', this, {});

            mkdirp('app/src/main/assets');
            mkdirp('app/src/main/res');

            this.directory('resources/assets', 'app/src/main/assets');

            this.directory('resources/res', 'app/src/main/res');

            if (this.language == 'java') {
                this.directory('config', 'config');
            }

            this.template('resources/_AndroidManifest.xml', 'app/src/main/AndroidManifest.xml', this, {});
            this.template('../../dependencies.json', 'dependencies.json', this, {}).on('end', function () {
                this.installGradleDependencies(this, false);
            });

            mkdirp('app/src/debug');

        }
    },
    install: function () {

    }
});
