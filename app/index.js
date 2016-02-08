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
    prompting: function () {
        var done = this.async();

        this.log(yosay(
            'Welcome to the ' + chalk.red('Android Hipster') + ' generator!'
        ));

        var defaultAppBaseName = 'android.hipster';

        var prompts = [{
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
                        value: 'nucleus',
                        name: 'Nucleus MVP'
                    },
                    {
                        value: 'embeed',
                        name: 'Embeed MVP (No Lib)'
                    }
                ],
                default: 'nucleus'
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

        ];

        this.prompt(prompts, function (props) {
            this.appName = props.name;
            this.imageLib = props.image;
            this.glide = this.imageLib == 'glide';
            this.picasso = this.imageLib == 'picasso';
            this.eventbus = props.eventbus;
            this.mixpanel = props.mixpanel;
            this.timber = props.timber;
            this.jodatime = props.jodatime;
            this.jodamoney = props.jodamoney;
            this.butterknife = props.butterknife;
            this.appPackage = props.package;
            this.androidTargetSdkVersion = props.targetSdk;
            this.androidMinSdkVersion = props.minSdk;
            this.language = props.language;
            this.calligraphy = props.calligraphy;
            this.playServices = props.playServices;
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
            this.config.set('appPackage', this.appPackage);
            this.config.set('appName', this.appName);
            this.config.set('language', this.language);
            this.config.set('nucleus', this.nucleus);
            this.config.set('mvp', this.mvp);
            this.config.set('imageLib', this.imageLib);
            this.config.set('eventbus', this.eventbus);
            this.config.set('mixpanel', this.mixpanel);
            this.config.set('timber', this.timber);
            this.config.set('jodatime', this.jodatime);
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
            var packageDir = this.appPackage.replace(/\./g, '/');

            mkdirp('app');
            mkdirp('app/libs');

            var i = 0;
            var appFolder;
            if (this.language == 'java') {
                appFolder = 'app-java'
            } else {
                appFolder = 'app-kotlin'
            }

            mkdirp('app/src/internal/java/' + packageDir);
            mkdirp('app/src/internalDebug/java/' + packageDir);
            mkdirp('app/src/internalRelease/java/' + packageDir);

            mkdirp('app/src/production/java/' + packageDir);
            mkdirp('app/src/productionDebug/java/' + packageDir);
            mkdirp('app/src/productionRelease/java/' + packageDir);

            mkdirp('app/src/main/java/' + packageDir);

            this.template(appFolder + '/src/main/java/environment', 'app/src/internalDebug/java/' + packageDir + '/environment', this, {});
            this.template(appFolder + '/src/main/java/environment', 'app/src/internalRelease/java/' + packageDir + '/environment', this, {});
            this.template(appFolder + '/src/main/java/environment', 'app/src/productionDebug/java/' + packageDir + '/environment', this, {});
            this.template(appFolder + '/src/main/java/environment', 'app/src/productionRelease/java/' + packageDir + '/environment', this, {});

            this.template(appFolder + '/src/main/java/application', 'app/src/main/java/' + packageDir + '/application', this, {});

            this.template(appFolder + '/src/main/java/di', 'app/src/main/java/' + packageDir + '/di', this, {});

            this.template(appFolder + '/src/main/java/domain', 'app/src/main/java/' + packageDir + '/domain', this, {});

            if (this.language == 'kotlin') {
                this.template(appFolder + '/src/main/java/extensions/ContextExtensions.kt', 'app/src/main/java/' + packageDir + '/extensions/ContextExtensions.kt', this, {});
                if (this.nucleus == true) {
                    this.template(appFolder + '/src/main/java/extensions/PresenterExtensions.kt', 'app/src/main/java/' + packageDir + '/extensions/PresenterExtensions.kt', this, {})
                }
            }

            this.template(appFolder + '/src/main/java/model', 'app/src/main/java/' + packageDir + '/model', this, {});

            var ext = this.language == 'java' ? '.java' : '.kt';

            this.template(appFolder + '/src/main/java/ui/base/BaseActivity' + ext, 'app/src/main/java/' + packageDir + '/ui/base/BaseActivity' + ext, this, {});

            this.template(appFolder + '/src/main/java/ui/base/BaseFragment' + ext, 'app/src/main/java/' + packageDir + '/ui/base/BaseFragment' + ext, this, {});

            this.template(appFolder + '/src/main/java/ui/base/BasePresenter' + ext, 'app/src/main/java/' + packageDir + '/ui/base/BasePresenter' + ext, this, {});

            this.template(appFolder + '/src/main/java/ui/base/EmptyPresenter' + ext, 'app/src/main/java/' + packageDir + '/ui/base/EmptyPresenter' + ext, this, {});

            this.template(appFolder + '/src/main/java/ui/base/PresenterView' + ext, 'app/src/main/java/' + packageDir + '/ui/base/PresenterView' + ext, this, {});

            this.template(appFolder + '/src/main/java/storage', 'app/src/main/java/' + packageDir + '/storage', this, {});

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
                this.template(appFolder + '/src/main/java/util/gson/DateTimeTypeConverter' + ext, 'app/src/main/java/' + packageDir + '/util/gson/DateTimeTypeConverter' + ext, this, {});
                this.template(appFolder + '/src/main/java/util/gson/DateTimeZoneTypeConverter' + ext, 'app/src/main/java/' + packageDir + '/util/gson/DateTimeZoneTypeConverter' + ext, this, {})
            }
            if (this.jodamoney) {
                this.template(appFolder + '/src/main/java/util/gson/CurrencyUnitTypeConverter' + ext, 'app/src/main/java/' + packageDir + '/util/gson/CurrencyUnitTypeConverter' + ext, this, {});
                this.template(appFolder + '/src/main/java/util/gson/MoneyTypeConverter' + ext, 'app/src/main/java/' + packageDir + '/util/gson/MoneyTypeConverter' + ext, this, {})
            }

            if (this.autoparcel) {
                this.template(appFolder + '/src/main/java/util/gson/AutoGson' + ext, 'app/src/main/java/' + packageDir + '/util/gson/AutoGson' + ext, this, {});
                this.template(appFolder + '/src/main/java/util/gson/AutoValueTypeAdapterFactory' + ext, 'app/src/main/java/' + packageDir + '/util/gson/AutoValueTypeAdapterFactory' + ext, this, {})
            }

            this.template(appFolder + '/src/main/java/util/gson/GsonModule' + ext, 'app/src/main/java/' + packageDir + '/util/gson/GsonModule' + ext, this, {});
            this.template(appFolder + '/src/main/java/util/RxUtils' + ext, 'app/src/main/java/' + packageDir + '/util/RxUtils' + ext, this, {});
            this.template(appFolder + '/src/main/java/util/PermissionUtils' + ext, 'app/src/main/java/' + packageDir + '/util/PermissionUtils' + ext, this, {});
            this.template(appFolder + '/src/main/java/util/RepositoryUtils' + ext, 'app/src/main/java/' + packageDir + '/util/RepositoryUtils' + ext, this, {});

            this.template(appFolder + '/src/main/java/util/google', 'app/src/main/java/' + packageDir + '/util/google', this, {});

            this.template(appFolder + '/src/main/java/ui/main', 'app/src/main/java/' + packageDir + '/ui/main', this, {});

            mkdirp('app/src/main/assets');

            mkdirp('app/src/main/res');

            this.directory('resources/assets', 'app/src/main/assets');

            this.directory('resources/res', 'app/src/main/res');

            this.template('resources/_AndroidManifest.xml', 'app/src/main/AndroidManifest.xml', this, {});
            this.template('../../dependencies.json', 'dependencies.json', this, {}).on('end', function() {
                this.installGradleDependencies(this, false);
            });

            mkdirp('app/src/debug');

        }
    },
    install: function () {

    }
});
