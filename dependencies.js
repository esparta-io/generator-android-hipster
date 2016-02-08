'use strict';


var util = require('util'),
    fs = require('fs'),
    jsons = require('read-json'),
    path = require('path'),
    generators = require('yeoman-generator'),
    chalk = require('chalk'),
    _ = require('lodash'),
    _s = require('underscore.string'),
    shelljs = require('shelljs');

var scriptBase = require('./script-base');

module.exports = Generator;

function Generator() {
    yeoman.Base.apply(this, arguments);
}

util.inherits(Generator, scriptBase);

Generator.prototype.installGradleDependencies = function (config, update) {

    var dependencies = this.fs.readJSON('dependencies.json');

    this.addMultipleParentGradleDependency(dependencies[0].dependencies, update);

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
                        if (config.playServices.indexOf(gradleDependencies[i].name.replace('play-services-', '')) == -1) {
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

