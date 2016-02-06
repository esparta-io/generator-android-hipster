'use strict';
var yeoman = require('yeoman-generator');
var chalk = require('chalk');
var yosay = require('yosay');
var mkdirp = require('mkdirp');
var generators = require('yeoman-generator');
var _ = require('lodash');
var _s = require('underscore.string');
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
      }

    ];

    this.prompt(prompts, function (props) {
      this.fragmentName = props.name;
      this.fragmentPackageName = props.package;
      this.activity = props.activity;
      this.activityName = props.activityName;
      this.usePresenter = props.usePresenter;
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

      this.fragmentName = _.capitalize(this.fragmentName)._replace('Fragment', '');
      this.activityName = _.capitalize(this.activityName)._replace('Activity', '');

      var packageFolder = this.appPackage.replace(/\./g, '/').replace(this.appPackage, '');
      var packageDir = this.appPackage.replace(/\./g, '/');

      var appFolder;
      if (this.language == 'java') {
        appFolder = 'app-java';
      } else {
        appFolder = 'app-kotlin';
      }

      this.underscoreFragmentName = _s.underscored(this.fragmentName);
      this.entityClass = _s.capitalize(this.name);
      console.log(this.entityClass);

      var ext = this.language == 'java' ? ".java" : ".kt";

      if (shelljs.test('-f', appFolder + '/src/main/java' + packageDir + '/di/components/' + this.activityName + 'Component')) {
          this.componentType = 'createNew';
          this.addCustomComponentInjection(activityName + 'Component', this.fragmentName+'Fragment', packageDir, this.appPackage+'.ui.'+this.fragmentPackageName);
      } else {
          this.componentType = 'useApplication';
          this.addComponentInjection(this.fragmentName+'Fragment', packageDir, this.appPackage+'.ui.'+this.fragmentPackageName);
      }

      this.template(appFolder + '/src/main/java/_Fragment' + ext,
        'app/src/main/java/' + packageDir + '/ui/' + packageFolder + '/' + this.fragmentName + 'Fragment' + ext, this, {});

      if (this.usePresenter) {
        this.template(appFolder + '/src/main/java/_Presenter' + ext,
          'app/src/main/java/' + packageDir + '/ui/' + packageFolder + '/' + this.fragmentName + 'Presenter' + ext, this, {});
      }

      this.template('resources/res/layout/_fragment.xml',
        'app/src/main/res/layout/fragment_' + this.underscoreFragmentName + '.xml', this, {});

    },

    install: function () {
      //this.installDependencies();
    }
  }
});
