module.exports = AndroidStyles;
var _ = require('lodash');
var fs = require('fs');
var pd = require('pretty-data2').pd;
var cheerio = require('cheerio');

function AndroidStyles() { this.data = {} }

AndroidStyles.prototype.toXML = function() {
  return pd.xml(this.$.xml());
}

AndroidStyles.prototype.writeFile = function(path) {
  var xmlString = this.toXML();
  fs.writeFileSync(path, xmlString);
  return this;
}

AndroidStyles.prototype.readFile = function(path) {
  var xml = fs.readFileSync(path).toString();
  this.$ = cheerio.load(xml, { xmlMode: true });
  return this;
}



AndroidStyles.prototype.findOrCreateByAndroidName = function(parent, tag, name) {
  var manifest = this;
  var $ = manifest.$;
  var nodes = $(parent).find(' > '+tag);
  var dupe = _.find(nodes, { attribs: { 'android:name': name } });
  if (dupe) return $(dupe);
  else {
    var elem = $('<'+tag+'>').attr('name', name);
    $(parent).append(elem);
    return $(elem);
  }
}


AndroidStyles.prototype.string = function(name) {
  return this.findOrCreateByAndroidName('resources', 'string', name);
}

AndroidStyles.prototype.style = function(name) {
  return this.findOrCreateByAndroidName('resources', 'style', name);
}
