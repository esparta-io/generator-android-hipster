'use strict';
var yeoman = require('yeoman-generator');
var chalk = require('chalk');
var yosay = require('yosay');
var mkdirp = require('mkdirp');
var generators = require('yeoman-generator');

var scriptBase = require('../script-base');
var util = require('util');

var AndroidManifest = require('androidmanifest');

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
                .attr('android:exported', true)
                .attr('android:permission', 'com.google.android.c2dm.permission.SEND')
                .append('<intent-filter>')
                .find('intent-filter')
                .append('<action>')
                .find('action')
                .attr('android:name', 'com.google.android.c2dm.intent.RECEIVE');

            manifest.receiver(this.appPackage + '.services.push.DefaultPushReceiver')
                .attr('android:enabled', true)
                .attr('android:exported', true)
                .append('<intent-filter>')
                .find('intent-filter')
                .attr('android:priority', 0)
                .append('<action>')
                .find('action')
                .attr('android:name', 'Events.PUSH');

            manifest.service(this.appPackage + '.services.push.PushIDListenerService')
                .attr('android:exported', false)
                .append('<intent-filter>')
                .find('intent-filter')
                .append('<action>')
                .find('action')
                .attr('android:name', 'com.google.android.gms.iid.InstanceID');

            manifest.service(this.appPackage + '.services.push.PushServiceListener')
                .attr('android:exported', false)
                .append('<intent-filter>')
                .find('intent-filter')
                .append('<action>')
                .find('action')
                .attr('android:name', 'com.google.android.c2dm.intent.RECEIVE');

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

            if (this.language == 'java') {
                this.addComponentInjection('PushServiceListener', packageDir, this.appPackage + '.services.push');
                this.addComponentInjection('PushIntentService', packageDir, this.appPackage + '.services.push')
            } else {
                this.addComponentInjectionKotlin('PushServiceListener', packageDir, this.appPackage + '.services.push');
                this.addComponentInjectionKotlin('PushIntentService', packageDir, this.appPackage + '.services.push')
            }


            this.template('../../dependencies.json', 'dependencies.json', this, {}).on('end', function () {
                this.playServices = this.config.get('playServices');
                if (this.playServices == undefined) {
                    this.playServices = ['base'];
                }
                if (this.playServices.indexOf('gcm') == -1) {
                    this.playServices.push('gcm');
                }
                this.config.set('playServices', this.playServices);
                this.installGradleDependencies(this, true);
            });
        },

        install: function () {
        }
    }
});
