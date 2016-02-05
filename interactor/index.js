'use strict';
var yeoman = require('yeoman-generator');
var chalk = require('chalk');
var yosay = require('yosay');
var mkdirp = require('mkdirp');
var generators = require('yeoman-generator');
var _ = require('lodash');
var fileExists = require('file-exists');

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
      'Welcome to the ' + chalk.red('Android Hispter') + ' generator!'
    ));

    var defaultAppBaseName = 'Sample';
    var defaultName = '';

    var prompts = [{
      name: 'name',
      message: 'What are the name of your Interactor (Without InteractorSuffix. Ex: Login (for a LoginInteractor)?',
      store: true,
      validate: function (input) {
        if (/^([a-zA-Z0-9_]*)$/.test(input)) return true;
        return 'Your Interactor name cannot contain special characters or a blank space, using the default name instead : ' + defaultAppBaseName;
      },
      default: this.defaultAppBaseName
    },
      {
        when: function (response) {
          defaultName = response.name;
          return true;
        },
        name: 'interactorPackageName',
        message: 'What is the package of the Interactor? (it will be placed inside interactors package)',
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
        message: 'Create interface for Interactor?',
        default: false
      }

    ];

    this.prompt(prompts, function (props) {
      this.interactorName = props.name;
      this.interface = props.interface;
      this.interactorPackageName = props.interactorPackageName;
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

      var dotPackageName = this.interactorPackageName.replace(/\./g, '/').replace(this.appPackage, '');
      var packageDir = this.appPackage.replace(/\./g, '/');

      var appFolder;
      if (this.language == 'java') {
        appFolder = 'app-java';
      } else {
        appFolder = 'app-kotlin';
      }

      var ext = this.language == 'java' ? ".java" : ".kt";

      if (this.interface == false) {
        this.template(appFolder + '/src/main/java/interactor/_Interactor' + ext,
          'app/src/main/java/' + packageDir + '/domain/interactors/' + dotPackageName + '/' + this.interactorName + 'Interactor' + ext, this, {});
      } else {
        this.template(appFolder + '/src/main/java/interactor/_InteractorInterface' + ext,
          'app/src/main/java/' + packageDir + '/domain/interactors/' + dotPackageName + '/' + this.interactorName + 'Interactor' + ext, this, {});
        this.template(appFolder + '/src/main/java/interactor/_InteractorImpl' + ext,
          'app/src/main/java/' + packageDir + '/domain/interactors/' + dotPackageName + '/' + this.interactorName + 'InteractorImpl' + ext, this, {});
        this.provideInComponent(this.interactorName, packageDir, this.appPackage+'.domain.interactors.'+this.interactorPackageName);
        this.updateApplicationModuleToProvide(this.interactorName, packageDir, this.appPackage+'.domain.interactors.'+this.interactorPackageName, 'Interactor');
      }


    },

    install: function () {
      //this.installDependencies();
    }
  }
});
