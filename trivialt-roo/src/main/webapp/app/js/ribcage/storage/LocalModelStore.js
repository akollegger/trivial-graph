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
  */  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };
  define(['lib/backbone', 'lib/has'], function() {
    var InMemoryStoringStrategy, LocalModelStore, LocalStorageStoringStrategy;
    LocalStorageStoringStrategy = (function() {
      function LocalStorageStoringStrategy() {}
      LocalStorageStoringStrategy.prototype.store = function(key, obj) {
        return localStorage.setItem(key, JSON.stringify(obj));
      };
      LocalStorageStoringStrategy.prototype.fetch = function(key, defaults) {
        var stored;
        if (defaults == null) {
          defaults = {};
        }
        stored = localStorage.getItem(key);
        if (stored !== null) {
          return JSON.parse(stored);
        } else {
          return defaults;
        }
      };
      return LocalStorageStoringStrategy;
    })();
    InMemoryStoringStrategy = (function() {
      function InMemoryStoringStrategy() {
        this.storage = {};
      }
      InMemoryStoringStrategy.prototype.store = function(key, obj) {
        return this.storage[key] = obj;
      };
      InMemoryStoringStrategy.prototype.fetch = function(key, defaults) {
        if (defaults == null) {
          defaults = {};
        }
        if (this.storage[key] != null) {
          return this.storage[key];
        } else {
          return this.defaults;
        }
      };
      return InMemoryStoringStrategy;
    })();
    return LocalModelStore = (function() {
      LocalModelStore.prototype.storagePrefix = '';
      function LocalModelStore() {
        _(this).extend(Backbone.Events);
        if (has("native-localstorage")) {
          this.storingStrategy = new LocalStorageStoringStrategy();
        } else {
          this.storingStrategy = new InMemoryStoringStrategy();
        }
        this._cache = {};
      }
      /* Fetch and unserialize an object of the given type,
      stored at the given storage key.

      Once unserialized, resulting objects #setSaveMethod
      and #setFetchMethod are called. The provided save method
      expects the uneserialized object as an argument, and
      the provided fetch method returns the raw saved JSON.

      toJSON is used to serialize objects, and the objects
      constructor is passed the raw JSON upon instantiation.
      */
      LocalModelStore.prototype.get = function(key, type, defaults) {
        var fetch, item, save;
        if (type == null) {
          type = null;
        }
        if (defaults == null) {
          defaults = [];
        }
        if (!(this._cache[key] != null)) {
          fetch = __bind(function() {
            return this.storingStrategy.fetch(key, defaults);
          }, this);
          save = __bind(function(item) {
            return this.set(key, item);
          }, this);
          if (type != null) {
            item = new type(fetch());
            if (item.setSaveMethod != null) {
              item.setSaveMethod(save);
            }
            if (item.setFetchMethod != null) {
              item.setFetchMethod(fetch);
            }
          } else {
            item = fetch();
          }
          this._cache[key] = item;
        }
        return this._cache[key];
      };
      LocalModelStore.prototype.set = function(key, item) {
        this._cache[key] = item;
        if (item.toJSON()) {
          item = item.toJSON();
        }
        if (this.storagePrefix.length > 0) {
          key = "" + this.storagePrefix + "::" + key;
        }
        this.storingStrategy.store(key, item);
        this.trigger("change");
        return this.trigger("change:" + key);
      };
      return LocalModelStore;
    })();
  });
}).call(this);
