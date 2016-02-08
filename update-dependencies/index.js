'use strict';
var yeoman = require('yeoman-generator');
var chalk = require('chalk');
var yosay = require('yosay');
var mkdirp = require('mkdirp');

var generators = require('yeoman-generator');

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
            this.mvp = this.config.get('mvp');
            this.imageLib = this.config.get('imageLib');
            this.eventbus = this.config.get('eventbus');
            this.mixpanel = this.config.get('mixpanel');
            this.timber = this.config.get('timber');
            this.jodatime = this.config.get('jodatime');
            this.jodamoney = this.config.get('jodamoney');
            this.butterknife = this.config.get('butterknife');
            this.androidTargetSdkVersion = this.config.get('androidTargetSdkVersion');
            this.minSdk = this.config.get('minSdk');
            this.calligraphy = this.config.get('calligraphy');
            this.playServices = this.config.get('playServices');
            this.stetho = this.config.get('stetho');
            this.printview = this.config.get('printview');
            this.autoparcel = this.config.get('autoparcel')
        }
    },
    prompting: function () {
        var done = this.async();

        this.log(yosay(
            'Welcome to the ' + chalk.red('Android Hispter') + ' generator!'
        ));

        this.prompt([], function (props) {
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
            this.installGradleDependencies(this, true);

        },

        install: function () {
            // this.installDependencies()
        }
    }
});
