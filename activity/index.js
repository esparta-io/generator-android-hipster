'use strict';
var yeoman = require('yeoman-generator');
var chalk = require('chalk');
var yosay = require('yosay');
var mkdirp = require('mkdirp');
var generators = require('yeoman-generator');
var _s = require('underscore.string');

var scriptBase = require('../script-base');
var util = require('util');


var AndroidManifest = require('androidmanifest');
var AndroidResource = require('../androidresources');

var ActivityGenerator = generators.Base.extend({});

util.inherits(ActivityGenerator, scriptBase);

module.exports = ActivityGenerator.extend({

    initializing: {
        getConfig: function (args) {
            this.appName = this.config.get('appName');
            this.language = this.config.get('language');
            this.appPackage = this.config.get('appPackage');
            this.nucleus = this.config.get('nucleus');

        }
    },
    prompting: function () {
        var done = this.async();

        this.log(yosay(
            'Welcome to the ' + chalk.red('Android Hipster') + ' generator!'
        ));

        var defaultAppBaseName = 'Sample';
        var defaultName = 'sample';

        var prompts = [{
            name: 'name',
            message: 'What are the name of your activity (Without ActivitySuffix. Ex: Login (for a LoginActivity)?',
            store: true,
            validate: function (input) {
                if (/^([a-zA-Z0-9_]*)$/.test(input)) return true;
                return 'Your activity name cannot contain special characters or a blank space, using the default name instead : ' + defaultAppBaseName;
            },
            default: defaultAppBaseName
        },
            {
                when: function (response) {
                    defaultName = response.name;
                    return true;
                },
                name: 'activityPackageName',
                message: 'What is the package of the activity? (it will be placed inside ui package)',
                validate: function (input) {
                    if (/^([a-z_]{1}[a-z0-9_]*(\.[a-z_]{1}[a-z0-9_]*)*)$/.test(input)) return true;
                    return 'The package name you have provided is not a valid Java package name.';
                },
                default: defaultName.toLowerCase(),
                store: true
            },
            {
                type: 'list',
                name: 'componentType',
                message: 'For dependency injection, do you want a new Component? ',
                choices: [
                    {
                        value: 'createNew',
                        name: 'Create new Component / Module for this Activity'
                    },
                    {
                        value: 'useApplication',
                        name: 'Use the ApplicationComponent to inject this activity'
                    },
                    {
                        value: 'useUserComponent',
                        name: 'Use the UserComponent to inject this activity'
                    },
                    {
                        value: 'useExistingComponent',
                        name: 'Use the another existing component to inject this activity'
                    }

                ],
                default: 0
            },
            {
                when: function (response) {
                    return response.componentType == 'useExistingComponent';
                },
                name: 'useExistingComponentName',
                message: 'What is the name of the existing Component?',
                store: true
            },
            {
                when: function (response) {
                    return response.componentType == 'useExistingComponent';
                },
                type: 'confirm',
                name: 'useExistingComponentNameApplication',
                message: 'This component is available in App class? (If NO, will create a new instance to this component)',
                store: true,
                default: false
            }
        ];

        this.prompt(prompts, function (props) {
            this.activityName = props.name;
            this.activityPackageName = props.activityPackageName;
            this.componentType = props.componentType;
            this.useExistingComponentName = props.useExistingComponentName;
            this.useExistingComponentNameApplication = props.useExistingComponentNameApplication;
            this.usePresenter = true;
            done();
        }.bind(this));
    },

    configuring: {
        saveSettings: function () {

        }
    },

    writing: {
        projectfiles: function () {
        },

        app: function () {

            var packageFolder = this.activityPackageName.replace(/\./g, '/').replace(this.appPackage, '');
            var packageDir = this.appPackage.replace(/\./g, '/');
            this.underscoreActivityName = _s.underscored(this.activityName);
            if (this.useExistingComponentName != undefined) {
                this.underscoreUseExistingComponentName = this.useExistingComponentName.charAt(0).toLowerCase() + this.useExistingComponentName.slice(1);
            }

            var appFolder;
            if (this.language == 'java') {
                appFolder = 'app-java';
            } else {
                appFolder = 'app-kotlin';
            }

            var ext = this.language == 'java' ? ".java" : ".kt";

            if (this.componentType == 'createNew') {
                this.template(appFolder + '/src/main/java/di/components/_Component' + ext,
                    'app/src/main/java/' + packageDir + '/di/components/' + this.activityName + 'Component' + ext, this, {});
                this.template(appFolder + '/src/main/java/di/modules/_Module' + ext,
                    'app/src/main/java/' + packageDir + '/di/modules/' + this.activityName + 'Module' + ext, this, {});
            } else if (this.componentType == 'useApplication') {
                if (this.language == 'java') {
                    this.addComponentInjection(this.activityName + 'Activity', packageDir, this.appPackage + '.ui.' + this.activityPackageName);
                } else {
                    this.addComponentInjectionKotlin(this.activityName + 'Activity', packageDir, this.appPackage + '.ui.' + this.activityPackageName);
                }
            } else if (this.componentType == 'useUserComponent') {
                this.addComponentInjectionKotlin(this.activityName + 'Activity', packageDir, this.appPackage + '.ui.' + this.activityPackageName, "userComponent");
            } else {
                this.useExistingComponentName = this.useExistingComponentName.replace("Component", "");
                var name = this.useExistingComponentName + "Component";
                if (this.language == 'java') {
                    this.addComponentInjection(this.activityName + 'Activity', packageDir, this.appPackage + '.ui.' + this.activityPackageName, name);
                } else {
                    this.addComponentInjectionKotlin(this.activityName + 'Activity', packageDir, this.appPackage + '.ui.' + this.activityPackageName, name);
                }
            }

            this.template(appFolder + '/src/main/java/ui/_Activity' + ext,
                'app/src/main/java/' + packageDir + '/ui/' + packageFolder + '/' + this.activityName + 'Activity' + ext, this, {});

            this.template(appFolder + '/src/main/java/ui/_Presenter' + ext,
                'app/src/main/java/' + packageDir + '/ui/' + packageFolder + '/' + this.activityName + 'Presenter' + ext, this, {});
            this.template(appFolder + '/src/main/java/ui/_View' + ext,
                'app/src/main/java/' + packageDir + '/ui/' + packageFolder + '/' + this.activityName + 'View' + ext, this, {});


            this.template('resources/res/layout/_activity.xml',
                'app/src/main/res/layout/activity_' + this.underscoreActivityName + '.xml', this, {});

            var manifestFilePath = 'app/src/main/AndroidManifest.xml';

            var resourceFilePath = 'app/src/main/res/values/strings.xml';

            var manifest = new AndroidManifest().readFile(manifestFilePath);
            manifest.activity('.ui.' + this.activityPackageName + '.' + this.activityName + 'Activity')
                .attr('android:theme', '@style/' + this.activityName + 'Style')
                .attr('android:label', '@string/' + this.underscoreActivityName + '_name');

            manifest.writeFile(manifestFilePath);

            var resource = new AndroidResource().readFile(resourceFilePath);
            resource.string(this.underscoreActivityName + '_name').text(this.activityName + 'HipsterActivity');
            resource.writeFile(resourceFilePath);

            var stylesFilePath = 'app/src/main/res/values/styles.xml';
            var styles = new AndroidResource().readFile(stylesFilePath);
            styles.style(this.activityName + 'Style').attr('parent', 'AppTheme').text('');
            styles.writeFile(stylesFilePath);

            var stylesFilePath21 = 'app/src/main/res/values-v21/styles.xml';
            var styles21 = new AndroidResource().readFile(stylesFilePath21);
            styles21.style(this.activityName + 'Style').attr('parent', 'AppTheme').text('');
            styles21.writeFile(stylesFilePath21);

        },

        install: function () {
            //this.installDependencies();
        }
    }
});
