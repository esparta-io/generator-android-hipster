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
            this.nucleus = this.config.get('nucleus') || true;
            this.mvp = this.config.get('mvp') || 'nucleus';
            this.imageLib = this.config.get('imageLib') || 'glide';
            this.eventbus = this.config.get('eventbus') || true;
            this.mixpanel = this.config.get('mixpanel') || true;
            this.timber = this.config.get('timber') || true;
            this.jodatime = this.config.get('jodatime') || true;
            this.jodamoney = this.config.get('jodamoney') || true;
            this.butterknife = this.config.get('butterknife') || true;
            this.androidTargetSdkVersion = this.config.get('androidTargetSdkVersion');
            this.minSdk = this.config.get('minSdk');
            this.calligraphy = this.config.get('calligraphy') || true;
            this.playServices = this.config.get('playServices') || [];
            this.stetho = this.config.get('stetho') || true;
            this.printview = this.config.get('printview') || true;
            this.autoparcel = this.config.get('autoparcel') || true;
        }
    },
    prompting: function () {
        var done = this.async();

        this.log(yosay(
            'Welcome to the ' + chalk.red('Android Hipster') + ' generator!'
        ));

        this.prompt([], function (props) {
            done();
        }.bind(this))
    },

    configuring: {
        saveSettings: function () {
        }
    },

    writing: {
        projectfiles: function () {
            this.template('../../dependencies.json', 'dependencies.json', this, {}).on('end', function() {
                this.installGradleDependencies(this, true);
            });
        },

        app: function () {
        }
    },

    install: function () {

    }
})
;
