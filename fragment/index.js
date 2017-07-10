'use strict';
var yeoman = require('yeoman-generator');
var chalk = require('chalk');
var yosay = require('yosay');
var mkdirp = require('mkdirp');
var generators = require('yeoman-generator');
var _ = require('lodash');
var _s = require('underscore.string');
var shelljs = require('shelljs');
var scriptBase = require('../script-base');
var util = require('util');

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
        var defaultName = '';

        var prompts = [{
            name: 'name',
            message: 'What are the name of your Fragment? (Without FragmentSuffix. Ex: Login (for a LoginFragment)',
            store: true,
            validate: function (input) {
                if (/^([a-zA-Z0-9_]*)$/.test(input)) return true;
                return 'Your UseCase name cannot contain special characters or a blank space, using the default name instead : ' + defaultAppBaseName;
            },
            default: this.defaultAppBaseName
        },
            {
                when: function (response) {
                    defaultName = response.name;
                    return true;
                },
                name: 'package',
                message: 'What is the package of the Fragment? (it will be placed inside ui package)',
                validate: function (input) {
                    if (/^([a-z_]{1}[a-z0-9_]*(\.[a-z_]{1}[a-z0-9_]*)*)$/.test(input)) return true;
                    return 'The package name you have provided is not a valid Java package name.';
                },
                default: defaultName.toLowerCase(),
                store: true
            },
            {
                type: 'confirm',
                name: 'usePresenter',
                message: 'Create custom Presenter?',
                default: true
            },
            {
                type: 'confirm',
                name: 'activity',
                message: 'Apply fragment to some activity?',
                default: false
            },
            {
                when: function (response) {
                    return response.activity;
                },
                name: 'activityName',
                message: 'What is the package of the Activity?',
                store: true
            },
            {
                when: function (response) {
                    return response.activity;
                },
                type: 'list',
                name: 'componentType',
                message: 'For dependency injection, do you want to inject in Component... ',
                choices: [
                    {
                        value: 'createNew',
                        name: 'The SAME component of the activity, only when the activity owns your Component / Module'
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
                        name: 'Use the ANOTHER existing component to inject this activity'
                    },
                    {
                        value: 'createNewSub',
                        name: 'The SAME component of the activity based on @Subcomponent'
                    },
                ],
                default: 0
            },
            {
                when: function (response) {
                    return !response.activity;
                },
                type: 'list',
                name: 'componentType',
                message: 'For dependency injection, do you want to inject in Component... ',
                choices: [
                    {
                        value: 'useApplication',
                        name: 'Use the ApplicationComponent to inject this fragment'
                    },
                    {
                        value: 'useUserComponent',
                        name: 'Use the UserComponent to inject this fragment'
                    },
                    {
                        value: 'useExistingComponent',
                        name: 'Use the another existing component to inject this fragment'
                    }

                ],
                default: 0
            },
            {
                when: function (response) {
                    return response.componentType == 'useExistingComponent' || response.componentType == 'createNewSub';
                },
                name: 'useExistingComponentName',
                message: 'What is the full name of the existing Component?',
                store: true
            },

        ];

        this.prompt(prompts, function (props) {
            this.fragmentName = props.name;
            this.fragmentPackageName = props.package;
            this.activity = props.activity;
            this.activityName = props.activityName;
            this.usePresenter = props.usePresenter;
            this.componentType = props.componentType;
            this.useExistingComponentName = props.useExistingComponentName;
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

            this.fragmentName = _.capitalize(this.fragmentName).replace('Fragment', '');
            this.activityName = _.capitalize(this.activityName).replace('Activity', '');

            var packageFolder = this.fragmentPackageName.replace(/\./g, '/').replace(this.appPackage, '');
            var packageDir = this.appPackage.replace(/\./g, '/');

            var appFolder;
            if (this.language == 'java') {
                appFolder = 'app-java';
            } else {
                appFolder = 'app-kotlin';
            }

            this.underscoreFragmentName = _s.underscored(this.fragmentName);
            this.entityClass = _s.capitalize(this.name);

            var ext = this.language == 'java' ? ".java" : ".kt";

            if (this.componentType == 'createNew') {
                if (this.language == 'java') {
                    this.addComponentInjection(this.fragmentName + 'Fragment', packageDir, this.appPackage + '.ui.' + this.fragmentPackageName, this.activityName + "Component");
                } else {
                    this.addComponentInjectionKotlin(this.fragmentName + 'Fragment', packageDir, this.appPackage + '.ui.' + this.fragmentPackageName, this.activityName + "Component");
                }
            }
            if (this.componentType == 'createNewSub') {
                this.useExistingComponentName = this.useExistingComponentName.replace("Component", "");
                var name = this.useExistingComponentName + "Component";
                if (this.language == 'java') {
                    this.addComponentInjection(this.fragmentName + 'Fragment', packageDir, this.appPackage + '.ui.' + this.fragmentPackageName, name);
                } else {
                    this.addComponentInjectionKotlin(this.fragmentName + 'Fragment', packageDir, this.appPackage + '.ui.' + this.fragmentPackageName, name);
                }
            } else if (this.componentType == 'useApplication') {
                if (this.language == 'java') {
                    this.addComponentInjection(this.fragmentName + 'Fragment', packageDir, this.appPackage + '.ui.' + this.fragmentPackageName)
                } else {
                    this.addComponentInjectionKotlin(this.fragmentName + 'Fragment', packageDir, this.appPackage + '.ui.' + this.fragmentPackageName)
                }
            } else if (this.componentType == 'useUserComponent') {
              this.addComponentInjectionKotlin(this.fragmentName + 'Fragment', packageDir, this.appPackage + '.ui.' + this.fragmentPackageName, 'UserComponent')
            } else {
                this.activityName = this.activityName.replace("Component", "");
                var name = this.activityName + "Component";
                if (this.language == 'java') {
                    this.addComponentInjection(this.fragmentName + 'Fragment', packageDir, this.appPackage + '.ui.' + this.fragmentPackageName, name);
                } else {
                    this.addComponentInjectionKotlin(this.fragmentName + 'Fragment', packageDir, this.appPackage + '.ui.' + this.fragmentPackageName, name);
                }
            }

            this.template(appFolder + '/src/main/java/_Fragment' + ext,
                'app/src/main/java/' + packageDir + '/ui/' + packageFolder + '/' + this.fragmentName + 'Fragment' + ext, this, {});

            if (this.usePresenter) {
                this.template(appFolder + '/src/main/java/_Presenter' + ext,
                    'app/src/main/java/' + packageDir + '/ui/' + packageFolder + '/' + this.fragmentName + 'Presenter' + ext, this, {});
                this.template(appFolder + '/src/main/java/_View' + ext,
                    'app/src/main/java/' + packageDir + '/ui/' + packageFolder + '/' + this.fragmentName + 'View' + ext, this, {});
            }

            this.template('resources/res/layout/_fragment.xml',
                'app/src/main/res/layout/fragment_' + this.underscoreFragmentName + '.xml', this, {});

        },

        install: function () {
            //this.installDependencies();
        }
    }
});
