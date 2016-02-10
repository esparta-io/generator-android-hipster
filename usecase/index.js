'use strict';
var yeoman = require('yeoman-generator');
var chalk = require('chalk');
var yosay = require('yosay');
var mkdirp = require('mkdirp');
var generators = require('yeoman-generator');
var _ = require('lodash');

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
            message: 'What are the name of your UseCase (Without UseCaseSuffix. Ex: Login (for a LoginUseCase)?',
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
                name: 'useCasePackageName',
                message: 'What is the package of the UseCase? (it will be placed inside usecase package)',
                validate: function (input) {
                    if (/^([a-z_]{1}[a-z0-9_]*(\.[a-z_]{1}[a-z0-9_]*)*)$/.test(input)) return true;
                    return 'The package name you have provided is not a valid Java package name.';
                },
                default: defaultName.toLowerCase(),
                store: true
            },
            {
                type: 'confirm',
                name: 'interface',
                message: 'Create interface for UseCase?',
                default: false
            }

        ];

        this.prompt(prompts, function (props) {
            this.useCaseName = props.name;
            this.useCasePackageName = props.useCasePackageName;
            this.interface = props.interface;
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


            this.useCaseName = _.capitalize(this.useCaseName).replace('UseCase', '');

            var packageFolder = this.useCasePackageName.replace(/\./g, '/').replace(this.appPackage, '');
            var packageDir = this.appPackage.replace(/\./g, '/');

            var appFolder;
            if (this.language == 'java') {
                appFolder = 'app-java';
            } else {
                appFolder = 'app-kotlin';
            }

            var ext = this.language == 'java' ? ".java" : ".kt";

            if (this.interface == false) {
                this.template(appFolder + '/src/main/java/usecase/_UseCase' + ext,
                    'app/src/main/java/' + packageDir + '/domain/usecases/' + packageFolder + '/' + this.useCaseName + 'UseCase' + ext, this, {});
            } else {
                this.template(appFolder + '/src/main/java/usecase/_UseCaseInterface' + ext,
                    'app/src/main/java/' + packageDir + '/domain/usecases/' + packageFolder + '/' + this.useCaseName + 'UseCase' + ext, this, {});
                this.template(appFolder + '/src/main/java/usecase/_UseCaseImpl' + ext,
                    'app/src/main/java/' + packageDir + '/domain/usecases/' + packageFolder + '/' + this.useCaseName + 'UseCaseImpl' + ext, this, {});
                if (this.language == 'java') {
                    this.provideInComponent(this.useCaseName, packageDir, this.appPackage + '.domain.usecases.' + this.useCasePackageName, "UseCase");
                    this.updateApplicationModuleToProvide(this.useCaseName, packageDir, this.appPackage + '.domain.usecases.' + this.useCasePackageName, 'UseCase');
                } else {
                    this.provideInComponentKotlin(this.useCaseName, packageDir, this.appPackage + '.domain.usecases.' + this.useCasePackageName, "UseCase");
                    this.updateApplicationModuleToProvideKotlin(this.useCaseName, packageDir, this.appPackage + '.domain.usecases.' + this.useCasePackageName, 'UseCase');
                }
            }

        },

        install: function () {
            //this.installDependencies();
        }
    }
});
