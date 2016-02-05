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


Generator.prototype.provideInComponent = function (name, basePath, packageName) {
    try {
        var fullPath = 'app/src/main/java/' +basePath+ '/di/components/ApplicationComponent.java';
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-component-injection-method',
            splicable: [
                    name + ' provide'+name+'();'
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

Generator.prototype.addChangelogToLiquibase = function (changelogName) {
    try {
        var fullPath = 'src/main/resources/config/liquibase/master.xml';
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'jhipster-needle-liquibase-add-changelog',
            splicable: [
                    '<include file="classpath:config/liquibase/changelog/' + changelogName + '.xml" relativeToChangelogFile="false"/>'
            ]
        });
    } catch (e) {
        this.log(chalk.yellow('\nUnable to find ') + fullPath + chalk.yellow(' or missing required jhipster-needle. Reference to ') + changelogName + '.xml ' + chalk.yellow('not added.\n'));
    }
};

/**
 * A a new column to a Liquibase changelog file for entity.
 *
 * @param {string} filePath - The full path of the changelog file.
 * @param {string} content - The content to be added as column, can have multiple columns as well
 */
Generator.prototype.addColumnToLiquibaseEntityChangeset = function (filePath, content) {
    try {
        jhipsterUtils.rewriteFile({
            file: filePath,
            needle: 'jhipster-needle-liquibase-add-column',
            splicable: [
                content
            ]
        });
    } catch (e) {
        this.log(chalk.yellow('\nUnable to find ') + filePath + chalk.yellow(' or missing required jhipster-needle. Column not added.\n') + e);
    }
};

/**
 * Add a new social connection factory in the SocialConfiguration.java file.
 *
 * @param {string} socialName - name of the social module. ex: 'facebook'
 * @param {string} socialParameter - parameter to send to social connection ex: 'public_profile,email'
 * @param {string} buttonColor - color of the social button. ex: '#3b5998'
 * @param {string} buttonHoverColor - color of the social button when is hover. ex: '#2d4373'
 */
Generator.prototype.addSocialButton = function (isUseSass, socialName, socialParameter, buttonColor, buttonHoverColor) {
    var socialServicefullPath = 'src/main/webapp/scripts/app/account/social/social.service.js';
    var loginfullPath = 'src/main/webapp/scripts/app/account/login/login.html';
    var registerfullPath = 'src/main/webapp/scripts/app/account/register/register.html';
    try {
        this.log(chalk.yellow('\nupdate ') + socialServicefullPath);
        var serviceCode =  "case '" + socialName + "': return '"+ socialParameter +"';";
        jhipsterUtils.rewriteFile({
            file: socialServicefullPath,
            needle: 'jhipster-needle-add-social-button',
            splicable: [
                serviceCode
            ]
        });

        var buttonCode = '<jh-social ng-provider="'+ socialName +'"></jh-social>';
        this.log(chalk.yellow('update ') + loginfullPath);
        jhipsterUtils.rewriteFile({
            file: loginfullPath,
            needle: 'jhipster-needle-add-social-button',
            splicable: [
                buttonCode
            ]
        });
        this.log(chalk.yellow('update ') + registerfullPath);
        jhipsterUtils.rewriteFile({
            file: registerfullPath,
            needle: 'jhipster-needle-add-social-button',
            splicable: [
                buttonCode
            ]
        });

        var buttonStyle = '.jh-btn-' + socialName + ' {\n' +
            '     background-color: ' + buttonColor + ';\n' +
            '     border-color: rgba(0, 0, 0, 0.2);\n' +
            '     color: #fff;\n' +
            '}\n\n' +
            '.jh-btn-' + socialName + ':hover, .jh-btn-' + socialName + ':focus, .jh-btn-' + socialName + ':active, .jh-btn-' + socialName + '.active, .open > .dropdown-toggle.jh-btn-' + socialName + ' {\n' +
            '    background-color: ' + buttonHoverColor + ';\n' +
            '    border-color: rgba(0, 0, 0, 0.2);\n' +
            '    color: #fff;\n' +
            '}';
        this.addMainCSSStyle(isUseSass, buttonStyle,'Add sign in style for ' +  socialName);

    } catch (e) {
        this.log(chalk.yellow('\nUnable to add social button modification.\n' + e));
    }
};

/**
 * Add a new social connection factory in the SocialConfiguration.java file.
 *
 * @param {string} javaDir - default java directory of the project (JHipster var)
 * @param {string} importPackagePath - package path of the ConnectionFactory class
 * @param {string} socialName - name of the social module
 * @param {string} connectionFactoryClassName - name of the ConnectionFactory class
 * @param {string} configurationName - name of the section in the config yaml file
 */
Generator.prototype.addSocialConnectionFactory = function (javaDir, importPackagePath, socialName, connectionFactoryClassName, configurationName) {
    var fullPath = javaDir + 'config/social/SocialConfiguration.java';
    try {
        this.log(chalk.yellow('\nupdate ') + fullPath);
        var javaImport = 'import ' + importPackagePath +';\n';
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'jhipster-needle-add-social-connection-factory-import-package',
            splicable: [
                javaImport
            ]
        });

        var clientId = socialName + 'ClientId';
        var clientSecret = socialName + 'ClientSecret';
        var javaCode = '// ' + socialName + ' configuration\n' +
            '        String ' + clientId + ' = environment.getProperty("spring.social.' + configurationName + '.clientId");\n' +
            '        String ' + clientSecret + ' = environment.getProperty("spring.social.' + configurationName + '.clientSecret");\n' +
            '        if (' + clientId + ' != null && ' + clientSecret + ' != null) {\n' +
            '            log.debug("Configuring ' + connectionFactoryClassName + '");\n' +
            '            connectionFactoryConfigurer.addConnectionFactory(\n' +
            '                new ' + connectionFactoryClassName + '(\n' +
            '                    ' + clientId + ',\n' +
            '                    ' + clientSecret + '\n' +
            '                )\n' +
            '            );\n' +
            '        } else {\n' +
            '            log.error("Cannot configure ' + connectionFactoryClassName + ' id or secret null");\n' +
            '        }\n';

        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'jhipster-needle-add-social-connection-factory',
            splicable: [
                javaCode
            ]
        });
    } catch (e) {
        this.log(chalk.yellow('\nUnable to find ') + fullPath + chalk.yellow(' or missing required jhipster-needle. Social connection ') + e + ' ' + chalk.yellow('not added.\n'));
    }
};

/**
 * Add a new Maven dependency.
 *
 * @param {groupId} dependency groupId
 * @param {artifactId} dependency artifactId
 * @param {version} explicit dependency version number
 * @param {other} explicit other thing: scope, exclusions...
 */
Generator.prototype.addMavenDependency = function (groupId, artifactId, version, other) {
    try {
        var fullPath = 'pom.xml';
        var dependency = '<dependency>\n' +
            '            <groupId>' + groupId + '</groupId>\n' +
            '            <artifactId>' + artifactId + '</artifactId>\n';
        if (version) {
            dependency += '            <version>' + version + '</version>\n';
        }
        if (other) {
            dependency += other + '\n';
        }
        dependency += '        </dependency>';
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'jhipster-needle-maven-add-dependency',
            splicable: [
                dependency
            ]
        });
    } catch (e) {
        this.log(chalk.yellow('\nUnable to find ') + fullPath + chalk.yellow(' or missing required jhipster-needle. Reference to ') + 'maven dependency (groupId: ' + groupId + ', artifactId:' + artifactId + ', version:' + version + ')' + chalk.yellow(' not added.\n'));
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
Generator.prototype.addGradleDependency = function (scope, group, name, version) {
    try {
        var fullPath = 'app/build.gradle';
        jhipsterUtils.rewriteFile({
            file: fullPath,
            needle: 'android-hipster-needle-gradle-dependency',
            splicable: [
                scope + ' "' + group + ':' + name + ':' + version + '"'
            ]
        });
    } catch (e) {
        this.log(e);
        this.log(chalk.yellow('\nUnable to find ') + fullPath + chalk.yellow(' or missing required jhipster-needle. Reference to ') + group + ':' + name + ':' + version + chalk.yellow(' not added.\n'));
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
 * Generate a date to be used by Liquibase changelogs.
 */
Generator.prototype.dateFormatForLiquibase = function () {
    var now = new Date();
    var now_utc = new Date(now.getUTCFullYear(), now.getUTCMonth(), now.getUTCDate(),  now.getUTCHours(), now.getUTCMinutes(), now.getUTCSeconds());
    var year = "" + now_utc.getFullYear();
    var month = "" + (now_utc.getMonth() + 1); if (month.length == 1) { month = "0" + month; }
    var day = "" + now_utc.getDate(); if (day.length == 1) { day = "0" + day; }
    var hour = "" + now_utc.getHours(); if (hour.length == 1) { hour = "0" + hour; }
    var minute = "" + now_utc.getMinutes(); if (minute.length == 1) { minute = "0" + minute; }
    var second = "" + now_utc.getSeconds(); if (second.length == 1) { second = "0" + second; }
    return year + "" + month + "" + day + "" + hour + "" + minute + "" + second;
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
