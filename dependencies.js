'use strict';
var yeoman = require('yeoman-generator')
var chalk = require('chalk')
var yosay = require('yosay')
var mkdirp = require('mkdirp')
var generators = require('yeoman-generator')
var _ = require('lodash')
var fileExists = require('file-exists')

var scriptBase = require('./script-base')
var util = require('util')

module.exports = Generator;

function Generator() {
    yeoman.Base.apply(this, arguments);
}

util.inherits(Generator, scriptBase)

Generator.prototype.installGradleDependencies = function (config, update) {
  var appFolder;
  if (config.language == 'java') {
    appFolder = 'app-java';
  } else {
    appFolder = 'app-kotlin';
  }
  var ext = config.language == 'java' ? '.java' : '.kt';

  // this.updateGradleClasspathDependency('2.0.0-alpha9');
  // this.updateGradleBuildToolsVersion('23.0.2');

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

if (config.playServices && config.playServices.length > 0) {
  this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-base', '8.4.0', update);

  if (config.playServices.indexOf('plus') != -1)
    this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-plus', '8.4.0', update);
  if (config.playServices.indexOf('auth') != -1)
    this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-auth', '8.4.0', update);
  if (config.playServices.indexOf('identity') != -1)
    this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-identity', '8.4.0', update);
  if (config.playServices.indexOf('appindexing') != -1)
    this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-appindexing', '8.4.0', update);
  if (config.playServices.indexOf('appinvite') != -1)
    this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-appinvite', '8.4.0', update);
  if (config.playServices.indexOf('analytics') != -1)
    this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-analytics', '8.4.0', update);
  if (config.playServices.indexOf('cast') != -1)
    this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-cast', '8.4.0', update);
  if (config.playServices.indexOf('gcm') != -1)
    this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-gcm', '8.4.0', update);
  if (config.playServices.indexOf('drive') != -1)
    this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-drive', '8.4.0', update);
  if (config.playServices.indexOf('fitness') != -1)
    this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-fitness', '8.4.0', update);
  if (config.playServices.indexOf('location') != -1)
    this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-location', '8.4.0', update);
  if (config.playServices.indexOf('maps') != -1)
    this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-maps', '8.4.0', update);
  if (config.playServices.indexOf('ads') != -1)
    this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-ads', '8.4.0', update);
  if (config.playServices.indexOf('vision') != -1)
    this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-vision', '8.4.0', update);
  if (config.playServices.indexOf('nearby') != -1)
    this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-nearby', '8.4.0', update);
  if (config.playServices.indexOf('panorama') != -1)
    this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-panorama', '8.4.0', update);
  if (config.playServices.indexOf('games') != -1)
    this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-games', '8.4.0', update);
  if (config.playServices.indexOf('wearable') != -1)
    this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-wearable', '8.4.0', update);
  if (config.playServices.indexOf('safetynet') != -1)
    this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-safetynet', '8.4.0', update);
  if (config.playServices.indexOf('wallet') != -1)
    this.addGradleDependency('compile', 'com.google.android.gms', 'play-services-wallet', '8.4.0', update);
}
};
