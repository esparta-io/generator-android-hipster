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

    var prompts = [
      {
        type: 'confirm',
        name: 'ok',
        message: 'This will create a Push structure in your app. Continue?',
        default: true
      }
    ];

    this.prompt(prompts, function (props) {
      this.ok = props.ok;

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

      if (!this.ok) {
        return;
      }

      var packageDir = this.appPackage.replace(/\./g, '/');

      var manifestFilePath = 'app/src/main/AndroidManifest.xml';

      var resourceFilePath = 'app/src/main/res/values/strings.xml';

      var manifest = new AndroidManifest().readFile(manifestFilePath);
      manifest.receiver('com.google.android.gms.gcm.GcmReceiver')
        .attr('android:exported', true);
        //android:permission="com.google.android.c2dm.permission.SEND">

      manifest.receiver(appPackage + '.service.push.DefaultPushReceiver')
        .attr('android:enabled', true)
        .attr('android:exported', true);
        // <intent-filter android:priority="0">
        //                 <action android:name="Events.PUSH" />
        //             </intent-filter>

      manifest.service(appPackage + '.service.push.PushIDListenerService')
          .attr('android:exported', false);
          // android:exported="false">
          //   <intent-filter>
          //       <action android:name="com.google.android.gms.iid.InstanceID" />
          //   </intent-filter>



      manifest.service(appPackage + '.service.push.PushServiceListener')
          .attr('android:exported', false);
            // <intent-filter>
            //                 <action android:name="com.google.android.c2dm.intent.RECEIVE" />
            //             </intent-filter>
      manifest.writeFile(manifestFilePath);

      var appFolder;
      if (this.language == 'java') {
        appFolder = 'app-java';
      } else {
        appFolder = 'app-kotlin';
      }

      var ext = this.language == 'java' ? ".java" : ".kt";

      this.template(appFolder + '/src/main/java/_DefaultPushReceiver' + ext,
        'app/src/main/java/' + packageDir + '/services/push/DefaultPushReceiver' + ext, this, {});

      this.template(appFolder + '/src/main/java/_PushIDListenerService' + ext,
        'app/src/main/java/' + packageDir + '/services/push/PushIDListenerService' + ext, this, {});

      this.template(appFolder + '/src/main/java/_PushIntentService' + ext,
        'app/src/main/java/' + packageDir + '/services/push/PushIntentService' + ext, this, {});

      this.template(appFolder + '/src/main/java/_PushServiceListener' + ext,
        'app/src/main/java/' + packageDir + '/services/push/PushServiceListener' + ext, this, {});

    },

    install: function () {
      //this.installDependencies();
    }
  }
});
