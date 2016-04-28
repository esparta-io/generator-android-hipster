'use strict';
var yeoman = require('yeoman-generator');
var chalk = require('chalk');
var yosay = require('yosay');
var mkdirp = require('mkdirp');
var generators = require('yeoman-generator');
var _s = require('underscore.string');

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
            message: 'What are the name of your Entity?',
            store: true,
            validate: function (input) {
                if (/^([a-zA-Z0-9_]*)$/.test(input)) return true;
                return 'Your UseCase name cannot contain special characters or a blank space, using the default name instead : ' + defaultAppBaseName;
            },
            default: this.defaultAppBaseName
        }
        ];

        this.prompt(prompts, function (props) {
            this.entityName = props.name;
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

            var packageFolder = this.appPackage.replace(/\./g, '/').replace(this.appPackage, '');
            var packageDir = this.appPackage.replace(/\./g, '/');

            var appFolder;
            if (this.language == 'java') {
                appFolder = 'app-java';
            } else {
                appFolder = 'app-kotlin';
            }

            this.entityClass = _s.capitalize(this.name);
            console.log(this.entityClass);

            var ext = this.language == 'java' ? ".java" : ".kt";

            this.template(appFolder + '/src/main/java/_DomainEntity' + ext,
                'app/src/main/java/' + packageDir + '/model/' + this.entityName + ext, this, {});
            this.template(appFolder + '/src/main/java/_Entity' + ext,
                'app/src/main/java/' + packageDir + '/domain/entity/' + this.entityName + 'Entity' + ext, this, {});
            this.template(appFolder + '/src/main/java/_Converter' + ext,
                'app/src/main/java/' + packageDir + '/domain/entity/converter/' + this.entityName + 'Converter' + ext, this, {});

        },

        install: function () {

        }
    }
});
