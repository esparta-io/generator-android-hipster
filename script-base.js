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

    var dependencies = this.fs.readJSON('dependencies.json');

    this.addGradleFieldDependency('buildToolsVersion', '"25.0.3"', update);

    var parent = [];
    var parentKotlin = [];
    for (var i = 0; i < dependencies[0].dependencies.length; i++) {

        if (dependencies[0].dependencies[i].lang == 'all') {
            parent.push(dependencies[0].dependencies[i]);
        }
        if (dependencies[0].dependencies[i].lang == 'java' && config.language == 'java') {
            parent.push(dependencies[0].dependencies[i]);
        }
        if (dependencies[0].dependencies[i].lang == 'kotlin' && config.language == 'kotlin') {
            parentKotlin.push(dependencies[0].dependencies[i]);
        }

    }

    this.addMultipleParentGradleDependency(parent, update, 'build.gradle');
    this.addMultipleParentGradleDependency(parentKotlin, update, 'app/build.gradle');

    var gradle = dependencies[1].dependencies;

    var gradleDependencies = [];

    for (var i = 0; i < gradle.length; i++) {

        if (gradle[i].lang == 'all') {
            gradleDependencies.push(gradle[i]);
        }
        if (gradle[i].lang == 'java' && config.language == 'java') {
            gradleDependencies.push(gradle[i]);
        }
        if (gradle[i].lang == 'kotlin' && config.language == 'kotlin') {
            gradleDependencies.push(gradle[i]);
        }

    }

    if (!update) {
        var toRemove = [];
        for (var i = 0; i < gradleDependencies.length; i++) {
            if (gradleDependencies[i].selection != undefined) {
                if (gradleDependencies[i].selection == 'playServices') {
                    if (config.playServices == undefined || config.playServices.length == 0) {
                        toRemove.push(gradleDependencies[i]);
                    } else if (config.playServices != undefined && config.playServices.length > 0) {
                        if (config.playServices.indexOf(gradleDependencies[i].name.replace('play-services-', '')) == -1) {
                            toRemove.push(gradleDependencies[i]);
                        }
                    }
                } else if (config[gradleDependencies[i].selection] == undefined || config[gradleDependencies[i].selection] == false) {
                    toRemove.push(gradleDependencies[i]);
                }
            }
        }
        for (var i = 0; i < toRemove.length; i++) {
            gradleDependencies.splice(gradleDependencies.indexOf(toRemove[i]), 1);
        }
    } else {
        var toRemove = [];
        for (var i = 0; i < gradleDependencies.length; i++) {
            if (gradleDependencies[i].selection != undefined) {
                if (gradleDependencies[i].selection == 'playServices') {
                    if (config.playServices == undefined || config.playServices.length == 0) {
                        toRemove.push(gradleDependencies[i]);
                    } else if (config.playServices != undefined && config.playServices.length > 0) {
                        if (config.playServices.indexOf(gradleDependencies[i].name.replace('play-services-', '')) == -1 && gradleDependencies[i].name != 'play-services-base') {
                            toRemove.push(gradleDependencies[i]);
                        }
                    }
                } else if (config[gradleDependencies[i].selection] == undefined || config[gradleDependencies[i].selection] == false) {
                    toRemove.push(gradleDependencies[i]);
                }
            } else {
                if (gradleDependencies[i].selection == undefined) {

                } else if (config[gradleDependencies[i].selection] == undefined || config[gradleDependencies[i].selection] == false) {
                    toRemove.push(gradleDependencies[i]);
                }
            }
        }
        for (var i = 0; i < toRemove.length; i++) {
            gradleDependencies.splice(gradleDependencies.indexOf(toRemove[i]), 1);
        }
    }

    this.addMultipleGradleDependency(gradleDependencies, update);
};



Generator.prototype.addComponentInjection = function (name, basePath, packageName, filename) {
    try {
        var fullPath = 'app/src/main/java/' + basePath + '/di/components/' + (filename != undefined ? (filename + '.java') : 'ApplicationComponent.java');
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-component-injection-method',
            splicable: [
                'void inject(' + name + ' ' + name.charAt(0).toLowerCase() + name.slice(1) + ');'
            ]
        });
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-component-injection-import',
            splicable: [
                'import ' + packageName + '.' + name + ';'
            ]
        });
    } catch (e) {
        this.log(chalk.yellow('\nUnable to find ') + fullPath + chalk.yellow(' or missing required android-needle. Reference to ') + name + ' ' + chalk.yellow('not added.\n'));
    }
};

Generator.prototype.addCustomComponentInjection = function (component, name, basePath, packageName) {
    try {
        var fullPath = 'app/src/main/java/' + basePath + '/di/components/' + component + '.java';
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-component-injection-method',
            splicable: [
                'void inject(' + name + ' ' + name.charAt(0).toLowerCase() + name.slice(1) + ');'
            ]
        });
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-component-injection-import',
            splicable: [
                'import ' + packageName + '.' + name + ';'
            ]
        });
    } catch (e) {
        this.log(chalk.yellow('\nUnable to find ') + fullPath + chalk.yellow(' or missing required android-needle. Reference to ') + name + ' ' + chalk.yellow('not added.\n'));
    }
};

Generator.prototype.addCustomComponentInjectionKotlin = function (component, name, basePath, packageName) {
    try {
        var fullPath = 'app/src/main/java/' + basePath + '/di/components/' + component + '.kt';
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-component-injection-method',
            splicable: [
                'fun inject(' + name.charAt(0).toLowerCase() + name.slice(1) + ': '+name+');'
            ]
        });
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-component-injection-import',
            splicable: [
                'import ' + packageName + '.' + name + ''
            ]
        });
    } catch (e) {
        this.log(chalk.yellow('\nUnable to find ') + fullPath + chalk.yellow(' or missing required android-needle. Reference to ') + name + ' ' + chalk.yellow('not added.\n'));
    }
};

Generator.prototype.updateApplicationModuleToProvide = function (name, basePath, packageName, type, ext) {
    try {
        var fullPath = 'app/src/main/java/' + basePath + '/di/modules/ApplicationModule.java';
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-module-provides-method',
            splicable: [
                '@Provides',
                '@Singleton',
                name + type + ' provide' + name + type + '(Scheduler executor) {',
                '   return new ' + name + type + 'Impl(executor);',
                '}'
            ]
        });
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-module-provides-import',
            splicable: [
                'import ' + packageName + '.' + name + type + ';',
                'import ' + packageName + '.' + name + type + 'Impl;'
            ]
        });
    } catch (e) {
        this.log(chalk.yellow('\nUnable to find ') + fullPath + chalk.yellow(' or missing required android-needle. Reference to ') + name + ' ' + chalk.yellow('not added.\n'));
    }
};

Generator.prototype.updateApplicationModuleToProvideKotlin = function (name, basePath, packageName, type) {
    try {
        var fullPath = 'app/src/main/java/' + basePath + '/di/modules/ApplicationModule.kt';
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-module-provides-method',
            splicable: [
                '@Provides',
                '@Singleton',
                'fun provide' + name + type + '(executor: Scheduler): '+name + type + ' {',
                '   return ' + name + type + 'Impl(executor);',
                '}'
            ]
        });
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-module-provides-import',
            splicable: [
                'import ' + packageName + '.' + name + type + '',
                'import ' + packageName + '.' + name + type + 'Impl'
            ]
        });
    } catch (e) {
        this.log(chalk.yellow('\nUnable to find ') + fullPath + chalk.yellow(' or missing required android-needle. Reference to ') + name + ' ' + chalk.yellow('not added.\n'));
    }
};

Generator.prototype.updateApplicationModuleToRepository = function (name, basePath, packageName, remote, local) {
    try {
        var fullPath = 'app/src/main/java/' + basePath + '/di/modules/ApplicationModule.java';
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-module-provides-method',
            splicable: [
                '@Provides',
                '@Singleton',
                name + 'Repository' + ' provide' + name + 'Repository' + '(' + (remote ? 'Retrofit retrofit' : '') + ') {',
                '   return new ' + name + 'Repository' + 'Impl(' + (remote ? ('new ' + name + 'RemoteRepository(retrofit)') : '') + ((remote && local) ? ', ' : '') + (local ? ('new ' + name + 'LocalRepository()') : '') + ');',
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
        var fullPath = 'app/src/main/java/' + basePath + '/di/components/ApplicationComponent.java';
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-component-injection-method',
            splicable: [
                name + type + ' provide' + name + type + '();'
            ]
        });
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-component-injection-import',
            splicable: [
                'import ' + packageName + '.' + name + type + ';'
            ]
        });
    } catch (e) {
        this.log(chalk.yellow('\nUnable to find ') + fullPath + chalk.yellow(' or missing required jhipster-needle. Reference to ') + name + ' ' + chalk.yellow('not added.\n'));
    }
};
Generator.prototype.provideInComponentKotlin = function (name, basePath, packageName, type) {
    try {
        var fullPath = 'app/src/main/java/' + basePath + '/di/components/ApplicationComponent.kt';
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-component-injection-method',
            splicable: [
                'fun provide' + name + type + '(): '+name + type
            ]
        });
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-component-injection-import',
            splicable: [
                'import ' + packageName + '.' + name + type + ''
            ]
        });
    } catch (e) {
        this.log(chalk.yellow('\nUnable to find ') + fullPath + chalk.yellow(' or missing required jhipster-needle. Reference to ') + name + ' ' + chalk.yellow('not added.\n'));
    }
};

Generator.prototype.addComponentInjectionKotlin = function (name, basePath, packageName, filename) {
    try {
        var fullPath = 'app/src/main/java/' + basePath + '/di/components/' + (filename != undefined ? (filename + '.kt') : 'ApplicationComponent.kt');
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-component-injection-method',
            splicable: [
                'fun inject(' + name.charAt(0).toLowerCase() + name.slice(1) + ' : ' + name + ')'
            ]
        });
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-component-injection-import',
            splicable: [
                'import ' + packageName + '.' + name + ''
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
                dependencies: dependencies
            });
            // this.log(chalk.green('updated dependency: ' + scope + ' "' + group + ':' + name + ':' + version + '"'));
        } else {
            jhipsterUtils.rewriteFileMultiple({
                file: fullPath,
                dependencies: dependencies
            });
            // this.log(chalk.green('added dependency: ' + scope + ' "' + group + ':' + name + ':' + version + '"'));
        }
    } catch (e) {
        this.log(e);
    }
};

Generator.prototype.addMultipleParentGradleDependency = function (dependencies, update, fullPath) {
    try {
        if (update) {
            jhipsterUtils.rewriteReplaceMultiple({
                file: fullPath,
                dependencies: dependencies,
                needle : 'android-hipster-needle-gradle-parent-dependency'
            });
            //this.log(chalk.green('updated dependency: ' + scope + ' "' + group + ':' + name + ':' + version + '"'));
        } else {

            jhipsterUtils.rewriteFileMultiple({
                file: fullPath,
                dependencies: dependencies,
                needle : 'android-hipster-needle-gradle-parent-dependency'

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
        this.log(chalk.green('updated field: ' + type + ' "' + value));


    } catch (e) {
        this.log(e);
        this.log(chalk.yellow('\nUnable to find ') + fullPath + chalk.yellow(' or missing required jhipster-needle. Reference to ') + type + ':' + value + chalk.yellow(' not added.\n'));
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
    switch (action) {
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
Generator.prototype.rewriteFile = function (filePath, needle, content) {
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

Generator.prototype.rewriteReplace = function (filePath, needle, content) {
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
Generator.prototype.replaceContent = function (filePath, pattern, content, regex) {
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
Generator.prototype.registerModule = function (npmPackageName, hookFor, hookType, callbackSubGenerator, description) {
    try {
        var modules;
        var error, duplicate;
        var moduleName = _s.humanize(npmPackageName.replace('generator-jhipster-', ''));
        var generatorName = npmPackageName.replace('generator-', '');
        var generatorCallback = generatorName + ':' + (callbackSubGenerator ? callbackSubGenerator : 'app');
        var moduleConfig = {
            name: moduleName + ' generator',
            npmPackageName: npmPackageName,
            description: description ? description : 'A JHipster module to generate ' + moduleName,
            hookFor: hookFor,
            hookType: hookType,
            generatorCallback: generatorCallback
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
        if (!error && !duplicate) {
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
Generator.prototype.updateEntityConfig = function (file, key, value) {

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
Generator.prototype.getModuleHooks = function () {
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

Generator.prototype.removefile = function (file) {
    if (shelljs.test('-f', file)) {
        this.log('Removing the file - ' + file);
        shelljs.rm(file);
    }
}

Generator.prototype.removefolder = function (folder) {
    if (shelljs.test('-d', folder)) {
        this.log('Removing the folder - ' + folder)
        shelljs.rm("-rf", folder);
    }
}
