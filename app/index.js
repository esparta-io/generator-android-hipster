'use strict';
var yeoman = require('yeoman-generator');
var chalk = require('chalk');
var yosay = require('yosay');
var mkdirp = require('mkdirp');


module.exports = yeoman.generators.Base.extend({
    prompting: function () {
        var done = this.async();

        this.log(yosay(
            'Welcome to the ' + chalk.red('Android Hispter') + ' generator!'
        ));

        var defaultAppBaseName = 'android.hipster';

        var prompts = [{
            name: 'name',
            message: 'What are the name of your app?',
            store: true,
            validate: function (input) {
                if (/^([a-zA-Z0-9_]*)$/.test(input)) return true;
                return 'Your application name cannot contain special characters or a blank space, using the default name instead : ' + defaultAppBaseName;
            },
            default: this.defaultAppBaseName
        },
            {
                name: 'package',
                message: 'What is the package name of the app?',
                validate: function (input) {
                    if (/^([a-z_]{1}[a-z0-9_]*(\.[a-z_]{1}[a-z0-9_]*)*)$/.test(input)) return true;
                    return 'The package name you have provided is not a valid Java package name.';
                },
                default: 'io.android.hipster',
                store: true
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
                type: 'list',
                name: 'language',
                message: 'What language would you like to use? ',
                choices: [
                    {
                        value: 'java',
                        name: 'Java (with Retrolambda)'
                    },
                    {
                        value: 'kotlin',
                        name: 'Kotlin'
                    }

                ],
                default: 0
            },

            {
                type: 'list',
                name: 'butterknife',
                message: 'Use ButterKnife? ',
                choices: [
                    {
                        value: 'yes',
                        name: 'Yes'
                    },
                    {
                        value: 'no',
                        name: 'No, I like the old way / I will use Anko (kotlin)'
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
                    }

                ],
                default: 0
            },

            {
                type: 'list',
                name: 'events',
                message: 'Do you want to use Event bus libs? ',
                choices: [
                    {
                        value: 'no',
                        name: 'No'
                    },
                    {
                        value: 'eventbus',
                        name: 'Event Bus'
                    },
                    {
                        value: 'otto',
                        name: 'Otto'
                    }

                ],
                default: 0
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
            this.events = props.events;
            this.mixpanel = props.mixpanel;
            this.timber = props.timber;
            this.jodatime = props.jodatime;
            this.jodamoney = props.jodamoney;
            this.nucleus = props.nucleus;
            this.butterknife = props.butterknife;
            this.appPackage = props.package;
            this.androidTargetSdkVersion = props.targetSdk;
            this.androidMinSdkVersion = props.minSdk;
            this.language = props.language;
            this.calligraphy = props.calligraphy;
            this.playServices = props.playServices;

            done();
        }.bind(this));
    },

    configuring: {
        saveSettings: function () {
            this.config.set('appPackage', this.appPackage);
            this.config.set('appName', this.appName);
            this.config.set('language', this.language);
            this.config.set('nucleus', this.nucleus);
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

        },

        app: function () {
            var packageDir = this.appPackage.replace(/\./g, '/');

            mkdirp('app');
            mkdirp('app/libs');

            var i = 0;
            var appFolder;
            if (this.language == 'java') {
                appFolder = 'app-java';
            } else {
                appFolder = 'app-kotlin';
            }

            this.copy('common/gitignore', 'app/.gitignore');

            this.copy('common/proguard-rules.pro', 'app/proguard-rules.pro');

            this.template('_build.gradle', 'build.gradle', this, {});

            this.template('common/_app_build.gradle', 'app/build.gradle', this, {});

            mkdirp('app/src/internal/java/' + packageDir);

            this.template(appFolder + '/src/internal/java', 'app/src/internal/java/' + packageDir, this, {});

            mkdirp('app/src/production/java/' + packageDir);

            this.template(appFolder + '/src/production/java', 'app/src/production/java/' + packageDir, this, {});

            mkdirp('app/src/main/java/' + packageDir);

            this.template(appFolder + '/src/main/java/application', 'app/src/main/java/' + packageDir + '/application', this, {});

            this.template(appFolder + '/src/main/java/di', 'app/src/main/java/' + packageDir + '/di', this, {});

            this.template(appFolder + '/src/main/java/domain', 'app/src/main/java/' + packageDir + '/domain', this, {});

            if (this.language == 'kotlin') {
                this.template(appFolder + '/src/main/java/extensions/ContextExtensions.kt', 'app/src/main/java/' + packageDir + '/extensions/ContextExtensions.kt', this, {});
                if (this.nucleus == true) {
                    this.template(appFolder + '/src/main/java/extensions/PresenterExtensions.kt', 'app/src/main/java/' + packageDir + '/extensions/PresenterExtensions.kt', this, {});
                }
            }

            this.template(appFolder + '/src/main/java/model', 'app/src/main/java/' + packageDir + '/model', this, {});

            var ext = this.language == 'java' ? ".java" : ".kt";


            this.template(appFolder + '/src/main/java/ui/base/BaseActivity' + ext, 'app/src/main/java/' + packageDir + '/ui/base/BaseActivity' + ext, this, {});

            this.template(appFolder + '/src/main/java/ui/base/BaseFragment' + ext, 'app/src/main/java/' + packageDir + '/ui/base/BaseFragment' + ext, this, {});

            this.template(appFolder + '/src/main/java/ui/base/BasePresenter' + ext, 'app/src/main/java/' + packageDir + '/ui/base/BasePresenter' + ext, this, {});

            this.template(appFolder + '/src/main/java/ui/base/EmptyPresenter' + ext, 'app/src/main/java/' + packageDir + '/ui/base/EmptyPresenter' + ext, this, {});

            this.template(appFolder + '/src/main/java/ui/base/PresenterView' + ext, 'app/src/main/java/' + packageDir + '/ui/base/PresenterView' + ext, this, {});

            if (this.nucleus == false) {
                this.template(appFolder + '/src/main/java/ui/base/Presenter' + ext, 'app/src/main/java/' + packageDir + '/ui/base/Presenter' + ext, this, {});
            }

            this.template(appFolder + '/src/main/java/util', 'app/src/main/java/' + packageDir + '/util', this, {});

            this.template(appFolder + '/src/main/java/ui/main', 'app/src/main/java/' + packageDir + '/ui/main', this, {});

            mkdirp('app/src/main/assets');

            mkdirp('app/src/main/res');

            this.directory('resources/assets', 'app/src/main/assets');

            this.directory('resources/res', 'app/src/main/res');

            this.template('resources/_AndroidManifest.xml', 'app/src/main/AndroidManifest.xml', this, {});

            mkdirp('app/src/debug');

        },

        install: function () {
            //this.installDependencies();
        }
    }
});
