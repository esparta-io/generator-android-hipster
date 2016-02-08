'use strict';
var path = require('path'),
    util = require('util'),
    _ = require('lodash'),
    _s = require('underscore.string'),
    yeoman = require('yeoman-generator'),
    chalk = require('chalk'),
    jhipsterUtils = require('./util.js'),
    fs = require('fs'),
    shelljs = require('shelljs'),
    ejs = require('ejs');

var MODULES_HOOK_FILE = '.jhipster/modules/jhi-hooks.json';

module.exports = Generator;

function Generator() {
    yeoman.Base.apply(this, arguments);
}

util.inherits(Generator, yeoman.Base);

Generator.prototype.installGradleDependencies = function (config, update) {
    var appFolder;
    if (config.language == 'java') {
        appFolder = 'app-java';
    } else {
        appFolder = 'app-kotlin';
    }
    var ext = config.language == 'java' ? '.java' : '.kt';


    this.addGradleParentDependency('classpath', 'com.android.tools.build', 'gradle', '2.0.0-alpha9', update);
    this.addGradleParentDependency('classpath', 'com.neenbedankt.gradle.plugins', 'android-apt', '1.8', update);
    this.addGradleParentDependency('classpath', 'me.tatarka', 'gradle-retrolambda', '3.2.3', update);
    this.addGradleFieldDependency('buildToolsVersion', '"23.0.2"', update);


    if (config.language == 'kotlin') {
        this.addGradleDependency('compile', 'io.reactivex', 'rxkotlin', '0.30.1', update);
        this.addGradleDependency('compile', 'org.jetbrains.kotlin', 'kotlin-stdlib', '1.0.0-beta-4584', update);
        this.addGradleDependency('compile', 'org.jetbrains.anko', 'anko-sdk15', '0.8.1', update);
        this.addGradleDependency('compile', 'org.jetbrains.anko', 'anko-support-v4', '0.8.1', update);
        this.addGradleDependency('kapt', 'com.google.dagger', 'dagger-compiler', '2.0.2', update);
        this.addGradleDependency('compile', 'io.reactivex', 'rxkotlin', '0.30.1', update);
        if (config.butterknife == true) {
            this.addGradleDependency('kapt', 'com.jakewharton', 'butterknife', '7.0.1', update);
        }
    } else {
        this.addGradleDependency('retrolambdaConfig', 'net.orfjackal.retrolambda', 'retrolambda', '2.1.0', update);
        this.addGradleDependency('apt', 'com.google.dagger', 'dagger-compiler', '2.0.2', update);
    }

    if (config.nucleus) {
        this.addGradleDependency('compile', 'info.android15.nucleus', 'nucleus', '2.0.5', update);
        this.addGradleDependency('compile', 'info.android15.nucleus', 'nucleus-support-v4', '2.0.5', update);
        this.addGradleDependency('compile', 'info.android15.nucleus', 'nucleus-support-v7', '2.0.5', update);
    }

    if (config.butterknife) {
        this.addGradleDependency('compile', 'com.jakewharton', 'butterknife', '7.0.1', update);
    }

    if (config.eventbus) {
        this.addGradleDependency('compile', 'org.greenrobot', 'eventbus', '3.0.0', update);
    }

    if (config.imageLib == 'glide') {
        this.addGradleDependency('compile', 'com.github.bumptech.glide', 'glide', '3.6.1', update);
    }

    if (config.imageLib == 'picasso') {
        this.addGradleDependency('compile', 'com.squareup.picasso', 'picasso', '2.5.2', update);
    }

    if (config.calligraphy == true) {
        this.addGradleDependency('compile', 'uk.co.chrisjenx', 'calligraphy', '2.1.0', update);
    }

    if (config.timber == true) {
        this.addGradleDependency('compile', 'com.jakewharton.timber', 'timber', '3.1.0', update);
    }

    if (config.jodatime == true) {
        this.addGradleDependency('compile', 'net.danlew', 'android.joda', '2.8.2', update);
    }

    if (config.jodamoney == true) {
        this.addGradleDependency('compile', 'org.joda', 'joda-money', '0.10.0', update);
    }

    if (config.printview == true) {
        this.addGradleDependency('compile', 'com.github.johnkil.print', 'print', '1.3.1', update);
    }

    if (config.mixpanel == true) {
        this.addGradleDependency('compile', 'com.mixpanel.android', 'mixpanel-android', '4.6.4', update);
    }

    if (config.stetho) {
        this.addGradleDependency('compile', 'com.facebook.stetho', 'stetho', '1.2.0', update);
        this.addGradleDependency('compile', 'com.facebook.stetho', 'stetho-okhttp', '1.2.0', update);
    }

    if (config.autoparcel) {
        this.addGradleDependency('compile', 'com.github.frankiesardo', 'auto-parcel', '0.3.1', update);
        if (this.language == 'java') {
            this.addGradleDependency('apt', 'com.github.frankiesardo', 'auto-parcel-processor', '0.3.1', update);
        } else {
            this.addGradleDependency('kapt', 'com.github.frankiesardo', 'auto-parcel-processor', '0.3.1', update)
        }
    }

    var googlePlayVersion = '8.4.0';

    if (config.playServices && config.playServices.length > 0) {
        this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-base', googlePlayVersion, update);

        if (config.playServices.indexOf('plus') != -1)
            this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-plus', googlePlayVersion, update);
        if (config.playServices.indexOf('auth') != -1)
            this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-auth', googlePlayVersion, update);
        if (config.playServices.indexOf('identity') != -1)
            this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-identity', googlePlayVersion, update);
        if (config.playServices.indexOf('appindexing') != -1)
            this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-appindexing', googlePlayVersion, update);
        if (config.playServices.indexOf('appinvite') != -1)
            this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-appinvite', googlePlayVersion, update);
        if (config.playServices.indexOf('analytics') != -1)
            this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-analytics', googlePlayVersion, update);
        if (config.playServices.indexOf('cast') != -1)
            this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-cast', googlePlayVersion, update);
        if (config.playServices.indexOf('gcm') != -1)
            this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-gcm', googlePlayVersion, update);
        if (config.playServices.indexOf('drive') != -1)
            this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-drive', googlePlayVersion, update);
        if (config.playServices.indexOf('fitness') != -1)
            this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-fitness', googlePlayVersion, update);
        if (config.playServices.indexOf('location') != -1)
            this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-location', googlePlayVersion, update);
        if (config.playServices.indexOf('maps') != -1)
            this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-maps', googlePlayVersion, update);
        if (config.playServices.indexOf('ads') != -1)
            this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-ads', googlePlayVersion, update);
        if (config.playServices.indexOf('vision') != -1)
            this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-vision', googlePlayVersion, update);
        if (config.playServices.indexOf('nearby') != -1)
            this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-nearby', googlePlayVersion, update);
        if (config.playServices.indexOf('panorama') != -1)
            this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-panorama', googlePlayVersion, update);
        if (config.playServices.indexOf('games') != -1)
            this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-games', googlePlayVersion, update);
        if (config.playServices.indexOf('wearable') != -1)
            this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-wearable', googlePlayVersion, update);
        if (config.playServices.indexOf('safetynet') != -1)
            this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-safetynet', googlePlayVersion, update);
        if (config.playServices.indexOf('wallet') != -1)
            this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-wallet', googlePlayVersion, update);
    }
};

Generator.prototype.addComponentInjection = function (name, basePath, packageName) {
    try {
        var fullPath = 'app/src/main/java/' +basePath+ '/di/components/ApplicationComponent.java';
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-component-injection-method',
            splicable: [
                    'void inject('+name+' '+name.charAt(0).toLowerCase()+name.slice(1)+');'
            ]
        });
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-component-injection-import',
            splicable: [
                    'import ' + packageName + '.'+name+';'
            ]
        });
    } catch (e) {
        this.log(chalk.yellow('\nUnable to find ') + fullPath + chalk.yellow(' or missing required android-needle. Reference to ') + name + ' ' + chalk.yellow('not added.\n'));
    }
};

Generator.prototype.addCustomComponentInjection = function (component, name, basePath, packageName) {
    try {
        var fullPath = 'app/src/main/java/' +basePath+ '/di/components/'+component+'.java';
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-component-injection-method',
            splicable: [
                    'void inject('+name+' '+name.charAt(0).toLowerCase()+name.slice(1)+');'
            ]
        });
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-component-injection-import',
            splicable: [
                    'import ' + packageName + '.'+name+';'
            ]
        });
    } catch (e) {
        this.log(chalk.yellow('\nUnable to find ') + fullPath + chalk.yellow(' or missing required android-needle. Reference to ') + name + ' ' + chalk.yellow('not added.\n'));
    }
};

Generator.prototype.updateApplicationModuleToProvide = function (name, basePath, packageName, type) {
    try {
        var fullPath = 'app/src/main/java/' +basePath+ '/di/modules/ApplicationModule.java';
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-module-provides-method',
            splicable: [
                    '@Provides',
                    '@Singleton',
                    name + type + ' provide' + name + type + '(ThreadExecutor executor) {',
                    '   return new ' + name + type + 'Impl(executor);',
                    '}'
            ]
        });
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-module-provides-import',
            splicable: [
                    'import ' + packageName + '.'+name+type+';',
                    'import ' + packageName + '.'+name+type+'Impl;'
            ]
        });
    } catch (e) {
        this.log(chalk.yellow('\nUnable to find ') + fullPath + chalk.yellow(' or missing required android-needle. Reference to ') + name + ' ' + chalk.yellow('not added.\n'));
    }
};

Generator.prototype.updateApplicationModuleToRepository = function (name, basePath, packageName, remote, local) {
    try {
        var fullPath = 'app/src/main/java/' +basePath+ '/di/modules/ApplicationModule.java';
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-module-provides-method',
            splicable: [
                    '@Provides',
                    '@Singleton',
                    name + 'Repository' + ' provide' + name + 'Repository' + '(' + (remote? 'Retrofit retrofit' : '') + ') {',
                    '   return new ' + name + 'Repository' + 'Impl('+ (remote ? ('new ' + name+'RemoteRepository(retrofit)') : '') + ((remote && local) ?  ', ' : '') + (local ? ('new ' + name+'LocalRepository()') : '')+');',
                    '}'
            ]
        });
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-module-provides-import',
            splicable: [
                    'import ' + packageName + '.*;',
            ]
        });
    } catch (e) {
        this.log(chalk.yellow('\nUnable to find ') + fullPath + chalk.yellow(' or missing required android-needle. Reference to ') + name + ' ' + chalk.yellow('not added.\n'));
    }
};


Generator.prototype.provideInComponent = function (name, basePath, packageName, type) {
    try {
        var fullPath = 'app/src/main/java/' +basePath+ '/di/components/ApplicationComponent.java';
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-component-injection-method',
            splicable: [
                    name + type+ ' provide'+name+type+'();'
            ]
        });
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-component-injection-import',
            splicable: [
                    'import ' + packageName + '.'+name+type+';'
            ]
        });
    } catch (e) {
        this.log(chalk.yellow('\nUnable to find ') + fullPath + chalk.yellow(' or missing required jhipster-needle. Reference to ') + name + ' ' + chalk.yellow('not added.\n'));
    }
};

Generator.prototype.addComponentInjectionKotlin = function (name, basePath, packageName) {
    try {
        var fullPath = 'app/src/main/java/' +basePath+ '/di/components/ApplicationComponent.kt';
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-component-injection-method',
            splicable: [
                    'fun inject('+name.charAt(0).toLowerCase()+name.slice(1)+' : '+name+')'
            ]
        });
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-component-injection-import',
            splicable: [
                    'import ' + packageName + '.'+name+''
            ]
        });
    } catch (e) {
        this.log(chalk.yellow('\nUnable to find ') + fullPath + chalk.yellow(' or missing required jhipster-needle. Reference to ') + name + ' ' + chalk.yellow('not added.\n'));
    }
};


/**
 * A new Gradle plugin.
 *
 * @param {group} plugin GroupId
 * @param {name} plugin name
 * @param {version} explicit plugin version number
 */
Generator.prototype.addGradlePlugin = function (group, name, version) {
    try {
        var fullPath = 'build.gradle';
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-gradle-buildscript-dependency',
            splicable: [
                'classpath group: \'' + group + '\', name: \'' + name + '\', version: \'' + version + '\''
            ]
        });
    } catch (e) {
        this.log(chalk.yellow('\nUnable to find ') + fullPath + chalk.yellow(' or missing required jhipster-needle. Reference to ') + 'classpath: ' + group + ':' + name + ':' + version + chalk.yellow(' not added.\n'));
    }
};

/**
 * A new dependency to build.gradle file.
 *
 * @param {scope} scope of the new dependency, e.g. compile
 * @param {group} maven GroupId
 * @param {name} maven ArtifactId
 * @param {version} explicit version number
 */
Generator.prototype.addGradleDependency = function (scope, group, name, version, update) {
    try {
        var fullPath = 'app/build.gradle';
        if (update) {
          jhipsterUtils.rewriteReplace({
              file: fullPath,
              needle: scope + ' "' + group + ':' + name,
              splicable: [
                  scope + ' "' + group + ':' + name + ':' + version + '"'
              ]
          });
          //this.log(chalk.green('updated dependency: ' + scope + ' "' + group + ':' + name + ':' + version + '"'));
        } else {
          jhipsterUtils.rewriteFile({
              file: fullPath,
              needle: 'android-hipster-needle-gradle-dependency',
              splicable: [
                  scope + ' "' + group + ':' + name + ':' + version + '"'
              ]
          });
          //this.log(chalk.green('added dependency: ' + scope + ' "' + group + ':' + name + ':' + version + '"'));
        }
    } catch (e) {
        this.log(chalk.yellow('\nUnable to find ') + fullPath + chalk.yellow(' or missing required jhipster-needle. Reference to ') + group + ':' + name + ':' + version + chalk.yellow(' not added.\n'));
    }
};

Generator.prototype.addMultipleGradleDependency = function (dependencies, update) {
    try {
        var fullPath = 'app/build.gradle';
        if (update) {
            jhipsterUtils.rewriteReplaceMultiple({
                file: fullPath,
                dependencies : dependencies
            });
            //this.log(chalk.green('updated dependency: ' + scope + ' "' + group + ':' + name + ':' + version + '"'));
        } else {
            jhipsterUtils.rewriteFileMultiple({
                file: fullPath,
                dependencies : dependencies
            });
            //this.log(chalk.green('added dependency: ' + scope + ' "' + group + ':' + name + ':' + version + '"'));
        }
    } catch (e) {
        this.log(e);
    }
};

Generator.prototype.addMultipleParentGradleDependency = function (dependencies, update) {
    try {
        var fullPath = 'build.gradle';
        if (update) {
            jhipsterUtils.rewriteReplaceMultiple({
                file: fullPath,
                dependencies : dependencies
            });
            //this.log(chalk.green('updated dependency: ' + scope + ' "' + group + ':' + name + ':' + version + '"'));
        } else {

            jhipsterUtils.rewriteFileMultiple({
                file: fullPath,
                dependencies : dependencies
            });
            //this.log(chalk.green('added dependency: ' + scope + ' "' + group + ':' + name + ':' + version + '"'));
        }
    } catch (e) {
        this.log(e);
    }
};

Generator.prototype.addGradleParentDependency = function (scope, group, name, version, update) {
    try {
        var fullPath = 'build.gradle';
        if (update) {
          jhipsterUtils.rewriteReplace({
              file: fullPath,
              needle: scope + ' "' + group + ':' + name,
              splicable: [
                  scope + ' "' + group + ':' + name + ':' + version + '"'
              ]
          });
          //this.log(chalk.green('updated dependency: ' + scope + ' "' + group + ':' + name + ':' + version + '"'));
        } else {
          jhipsterUtils.rewriteFile({
              file: fullPath,
              needle: 'android-hipster-needle-gradle-dependency',
              splicable: [
                  scope + ' "' + group + ':' + name + ':' + version + '"'
              ]
          });
          //this.log(chalk.green('added dependency: ' + scope + ' "' + group + ':' + name + ':' + version + '"'));
        }

    } catch (e) {
        this.log(e);
        this.log(chalk.yellow('\nUnable to find ') + fullPath + chalk.yellow(' or missing required jhipster-needle. Reference to ') + group + ':' + name + ':' + version + chalk.yellow(' not added.\n'));
    }
};

Generator.prototype.addGradleFieldDependency = function (type, value, update) {
    try {
        var fullPath = 'app/build.gradle';
          jhipsterUtils.rewriteReplace({
              file: fullPath,
              needle: type,
              splicable: [
                  type + ' ' + value
              ]
          });
          this.log(chalk.green('updated field: ' + type + ' "' + value ));


    } catch (e) {
        this.log(e);
        this.log(chalk.yellow('\nUnable to find ') + fullPath + chalk.yellow(' or missing required jhipster-needle. Reference to ') + type + ':' + value  + chalk.yellow(' not added.\n'));
    }
};



/**
 * Apply from an external Gradle build script.
 *
 * @param {name} name of the file to apply from, must be 'fileName.gradle'
 */
Generator.prototype.applyFromGradleScript = function (name) {
    try {
        var fullPath = 'build.gradle';
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'jhipster-needle-gradle-apply-from',
            splicable: [
                    'apply from: \'' + name + '.gradle\''
            ]
        });
    } catch (e) {
        this.log(chalk.yellow('\nUnable to find ') + fullPath + chalk.yellow(' or missing required jhipster-needle. Reference to ') + name + chalk.yellow(' not added.\n'));
    }
};


/**
 * Copy templates with all the custom logic applied according to the type.
 *
 * @param {source} path of the source file to copy from
 * @param {dest} path of the destination file to copy to
 * @param {action} type of the action to be performed on the template file, i.e: stripHtml | stripJs | template | copy
 * @param {_this} context that can be used as the generator instance or data to process template
 * @param {_opt} options that can be passed to template method
 * @param {template} flag to use template method instead of copy method
 */
Generator.prototype.copyTemplate = function (source, dest, action, _this, _opt, template) {

    _this = _this !== undefined ? _this : this;
    _opt = _opt !== undefined ? _opt : {};
    switch(action) {
        case 'stripHtml' :
            var regex = '( translate\="([a-zA-Z0-9](\.)?)+")|( translate-values\="\{([a-zA-Z]|\d|\:|\{|\}|\[|\]|\-|\'|\s|\.)*?\}")';
            //looks for something like translate="foo.bar.message" and translate-values="{foo: '{{ foo.bar }}'}"
            jhipsterUtils.copyWebResource(source, dest, regex, 'html', _this, _opt, template);
            break;
        case 'stripJs' :
            var regex = '[a-zA-Z]+\:(\s)?\[[ \'a-zA-Z0-9\$\,\(\)\{\}\n\.\<\%\=\>\;\s]*\}\]';
            //looks for something like mainTranslatePartialLoader: [*]
            jhipsterUtils.copyWebResource(source, dest, regex, 'js', _this, _opt, template);
            break;
        case 'copy' :
            _this.copy(source, dest);
            break;
        default:
            _this.template(source, dest, _this, _opt);
    }
};

/**
 * Copy html templates after stripping translation keys when translation is disabled.
 *
 * @param {source} path of the source file to copy from
 * @param {dest} path of the destination file to copy to
 * @param {_this} context that can be used as the generator instance or data to process template
 * @param {_opt} options that can be passed to template method
 * @param {template} flag to use template method instead of copy
 */
Generator.prototype.copyHtml = function (source, dest, _this, _opt, template) {
    this.copyTemplate(source, dest, 'stripHtml', _this, _opt, template);
};

/**
 * Copy Js templates after stripping translation keys when translation is disabled.
 *
 * @param {source} path of the source file to copy from
 * @param {dest} path of the destination file to copy to
 * @param {_this} context that can be used as the generator instance or data to process template
 * @param {_opt} options that can be passed to template method
 * @param {template} flag to use template method instead of copy
 */
Generator.prototype.copyJs = function (source, dest, _this, _opt, template) {
    this.copyTemplate(source, dest, 'stripJs', _this, _opt, template);
};

/**
 * Rewrite the specified file with provided content at the needle location
 *
 * @param {fullPath} path of the source file to rewrite
 * @param {needle} needle to look for where content will be inserted
 * @param {content} content to be written
 */
Generator.prototype.rewriteFile = function(filePath, needle, content) {
    try {
        jhipsterUtils.rewriteFile({
            file: filePath,
            needle: needle,
            splicable: [
              content
            ]
        });
    } catch (e) {
        this.log(chalk.yellow('\nUnable to find ') + filePath + chalk.yellow(' or missing required needle. File rewrite failed.\n'));
    }
};

Generator.prototype.rewriteReplace = function(filePath, needle, content) {
    try {
        jhipsterUtils.rewriteReplace({
            file: filePath,
            needle: needle,
            splicable: [
              content
            ]
        });
    } catch (e) {
        this.log(chalk.yellow('\nUnable to find ') + filePath + chalk.yellow(' or missing required needle. File rewrite failed.\n'));
    }
};

/**
 * Replace the pattern/regex with provided content
 *
 * @param {fullPath} path of the source file to rewrite
 * @param {pattern} pattern to look for where content will be replaced
 * @param {content} content to be written
 * @param {regex} true if pattern is regex
 */
Generator.prototype.replaceContent = function(filePath, pattern, content, regex) {
    try {
        jhipsterUtils.replaceContent({
            file: filePath,
            pattern: pattern,
            content: content,
            regex: regex
        });
    } catch (e) {
        this.log(chalk.yellow('\nUnable to find ') + filePath + chalk.yellow(' or missing required pattern. File rewrite failed.\n') + e);
    }
};

/**
 * Register a module configuration to .jhipster/modules/jhi-hooks.json
 *
 * @param {npmPackageName} npm package name of the generator
 * @param {hookFor} from which Jhipster generator this should be hooked ( 'entity' or 'app')
 * @param {hookType} where to hook this at the generator stage ( 'pre' or 'post')
 * @param {callbackSubGenerator}[optional] sub generator to invoke, if this is not given the module's main generator will be called, i.e app
 * @param {description}[optional] description of the generator
 */
Generator.prototype.registerModule = function(npmPackageName, hookFor, hookType, callbackSubGenerator, description) {
    try {
        var modules;
        var error, duplicate;
        var moduleName = _s.humanize(npmPackageName.replace('generator-jhipster-',''));
        var generatorName = npmPackageName.replace('generator-','');
        var generatorCallback = generatorName + ':' + (callbackSubGenerator ? callbackSubGenerator : 'app') ;
        var moduleConfig = {
            name : moduleName + ' generator',
            npmPackageName : npmPackageName,
            description : description ? description : 'A JHipster module to generate ' + moduleName,
            hookFor : hookFor,
            hookType : hookType,
            generatorCallback : generatorCallback
        }
        if (shelljs.test('-f', MODULES_HOOK_FILE)) {
            // file is present append to it
            try {
                modules = this.fs.readJSON(MODULES_HOOK_FILE);
                duplicate = _.findIndex(modules, moduleConfig) !== -1;
            } catch (err) {
                error = true;
                this.log(chalk.red('The Jhipster module configuration file could not be read!'));
            }
        } else {
            // file not present create it and add config to it
            modules = [];
        }
        if(!error && !duplicate) {
            modules.push(moduleConfig);
            this.fs.writeJSON(MODULES_HOOK_FILE, modules, null, 4);
        }
    } catch (err) {
        this.log('\n' + chalk.bold.red('Could not add jhipster module configuration'));
    }
};

/**
 * Add configuration to Entity.json files
 *
 * @param {file} configuration file name for the entity
 * @param {key} key to be added or updated
 * @param {value} value to be added
 */
Generator.prototype.updateEntityConfig = function(file, key, value) {

    try {
        var entityJson = this.fs.readJSON(file);
        entityJson[key] = value;
        this.fs.writeJSON(file, entityJson, null, 4);
    } catch (err) {
        this.log(chalk.red('The Jhipster entity configuration file could not be read!') + err);
    }

}

/**
 * get the module hooks config json
 */
Generator.prototype.getModuleHooks = function() {
    var modulesConfig = [];
    try {
        if (shelljs.test('-f', MODULES_HOOK_FILE)) {
            modulesConfig = this.fs.readJSON(MODULES_HOOK_FILE);
        }
    } catch (err) {
        this.log(chalk.red('The module configuration file could not be read!'));
    }

    return modulesConfig;
}

Generator.prototype.removefile = function(file) {
    if (shelljs.test('-f', file)) {
        this.log('Removing the file - ' + file);
        shelljs.rm(file);
    }
}

Generator.prototype.removefolder = function(folder) {
    if (shelljs.test('-d', folder)) {
        this.log('Removing the folder - ' + folder)
        shelljs.rm("-rf", folder);
    }
}
