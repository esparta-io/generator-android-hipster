'use strict';
var yeoman = require('yeoman-generator');
var chalk = require('chalk');
var yosay = require('yosay');
var mkdirp = require('mkdirp');
var generators = require('yeoman-generator');
var _ = require('lodash');
var fileExists = require('file-exists');
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
            this.nucleus = this.config.get('nucleus')
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
            message: 'What are the name of your Repository (Without ActivitySuffix. Ex: User (for a UserRepository)?',
            store: true,
            validate: function (input) {
                if (/^([a-zA-Z0-9_]*)$/.test(input)) return true;
                return 'Your Repository name cannot contain special characters or a blank space, using the default name instead : ' + defaultAppBaseName
            },
            default: this.defaultAppBaseName
        },
            {
                when: function (response) {
                    defaultName = response.name;
                    return true
                },
                name: 'packageName',
                message: 'What is the package of the repository? (it will be placed inside domain/repository package)',
                validate: function (input) {
                    if (/^([a-z_]{1}[a-z0-9_]*(\.[a-z_]{1}[a-z0-9_]*)*)$/.test(input)) return true;
                    return 'The package name you have provided is not a valid Java package name.'
                },
                default: defaultName.toLowerCase(),
                store: true
            },
            {
                type: 'checkbox',
                name: 'remoteLocal',
                message: 'Enable Local / Remote Repository?',
                choices: [
                    {name: 'Remote', value: 'remote'},
                    {name: 'Local', value: 'local'}
                ],
                default: ['no']
            },
            {
                when: function (response) {
                    return response.remoteLocal.indexOf('remote') >= 0
                },
                type: 'confirm',
                name: 'service',
                message: 'Create a simple Retorfit Service inside RemoteRepository?',
                default: true
            },
            {
                type: 'confirm',
                name: 'interface',
                message: 'Create interface for Repository?',
                default: false
            }

        ];

        this.prompt(prompts, function (props) {
            this.repositoryName = props.name;
            this.repositoryPackageName = props.packageName;
            this.remoteLocal = props.remoteLocal;
            this.interface = props.interface;
            this.service = props.service;
            done()
        }.bind(this))
    },

    configuring: {
        saveSettings: function () {
        }
    },

    writing: {
        projectfiles: function () {
        },

        app: function () {

            this.repositoryName = _.capitalize(this.repositoryName).replace('Repository', '');

            var packageFolder = this.repositoryPackageName.replace(/\./g, '/').replace(this.appPackage, '');
            var packageDir = this.appPackage.replace(/\./g, '/');

            var appFolder;
            if (this.language == 'java') {
                appFolder = 'app-java'
            } else {
                appFolder = 'app-kotlin'
            }

            var ext = this.language == 'java' ? '.java' : '.kt';

            if (this.interface == false) {
                this.template(appFolder + '/src/main/java/_Repository' + ext,
                    'app/src/main/java/' + packageDir + '/domain/repository/' + packageFolder + '/' + this.repositoryName + 'Repository' + ext, this, {})
            } else {
                this.template(appFolder + '/src/main/java/_RepositoryInterface' + ext,
                    'app/src/main/java/' + packageDir + '/domain/repository/' + packageFolder + '/' + this.repositoryName + 'Repository' + ext, this, {});
                this.template(appFolder + '/src/main/java/_RepositoryImpl' + ext,
                    'app/src/main/java/' + packageDir + '/domain/repository/' + packageFolder + '/' + this.repositoryName + 'RepositoryImpl' + ext, this, {});
                this.provideInComponent(this.repositoryName, packageDir, this.appPackage + '.domain.repository.' + this.repositoryPackageName, 'Repository');
                this.updateApplicationModuleToRepository(this.repositoryName, packageDir, this.appPackage + '.domain.repository.' + this.repositoryPackageName, this.remoteLocal.indexOf('remote') >= 0, this.remoteLocal.indexOf('local') >= 0)
            }

            if (this.remoteLocal.indexOf('local') >= 0) {
                this.template(appFolder + '/src/main/java/_LocalRepository' + ext,
                    'app/src/main/java/' + packageDir + '/domain/repository/' + packageFolder + '/' + this.repositoryName + 'LocalRepository' + ext, this, {})
            }

            if (this.remoteLocal.indexOf('remote') >= 0) {
                this.template(appFolder + '/src/main/java/_RemoteRepository' + ext,
                    'app/src/main/java/' + packageDir + '/domain/repository/' + packageFolder + '/' + this.repositoryName + 'RemoteRepository' + ext, this, {})
            }

        },

        install: function () {
        }
    }
});
