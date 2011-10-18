(function() {
  /*
  Copyright (c) 2002-2011 "Neo Technology,"
  Network Engine for Objects in Lund AB [http://neotechnology.com]

  This file is part of Neo4j.

  Neo4j is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
  */  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; }, __hasProp = Object.prototype.hasOwnProperty, __extends = function(child, parent) {
    for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; }
    function ctor() { this.constructor = child; }
    ctor.prototype = parent.prototype;
    child.prototype = new ctor;
    child.__super__ = parent.prototype;
    return child;
  };
  define(['lib/backbone'], function() {
    /*
    A local collection keeps track of ids locally, and adds the
    methods #setFetchMethod and #setSaveMethod to the Collection
    API. These allow persistence strategies that do not depend
    on global variables.
    */    var LocalCollection;
    return LocalCollection = (function() {
      __extends(LocalCollection, Backbone.Collection);
      function LocalCollection(items) {
        this.save = __bind(this.save, this);;
        this.fetch = __bind(this.fetch, this);;        var p, _i, _len;
        this._idCounter = 0;
        for (_i = 0, _len = items.length; _i < _len; _i++) {
          p = items[_i];
          if (p.id && p.id > this._idCounter) {
            this._idCounter = p.id;
          }
        }
        LocalCollection.__super__.constructor.call(this, items);
      }
      LocalCollection.prototype.add = function(items, opts) {
        var item, processed, _i, _len;
        if (!_(items).isArray()) {
          items = [items];
        }
        processed = [];
        for (_i = 0, _len = items.length; _i < _len; _i++) {
          item = items[_i];
          if (!(item.id != null)) {
            item.id = ++this._idCounter;
          }
          if ((this.model != null) && !(item instanceof this.model)) {
            item = new this.model(item);
          }
          if (item.setSaveMethod != null) {
            item.setSaveMethod(this.save);
          }
          if (item.setFetchMethod != null) {
            item.setFetchMethod(this.fetch);
          }
          processed.push(item);
        }
        return LocalCollection.__super__.add.call(this, processed, opts);
      };
      LocalCollection.prototype.fetch = function() {
        var item, itemJSON, _i, _len, _ref, _results;
        _ref = this._fetch();
        _results = [];
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          itemJSON = _ref[_i];
          _results.push((item = this.get(itemJSON.id)) !== null ? item.set(itemJSON) : this.add(itemJSON));
        }
        return _results;
      };
      LocalCollection.prototype.save = function() {
        return this._save(this);
      };
      LocalCollection.prototype.setFetchMethod = function(_fetch) {
        this._fetch = _fetch;
      };
      LocalCollection.prototype.setSaveMethod = function(_save) {
        this._save = _save;
      };
      return LocalCollection;
    })();
  });
}).call(this);
