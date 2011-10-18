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
  */  define(['lib/backbone', 'lib/has'], function() {
    var InMemoryStoringStrategy, LocalStorageStoringStrategy, ModelStore;
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
    return ModelStore = (function() {
      function ModelStore() {}
      ModelStore.prototype.initialize = function() {
        if (has("native-localstorage")) {
          return this.storingStrategy = new LocalStorageStoringStrategy();
        } else {
          return this.storingStrategy = new InMemoryStoringStrategy();
        }
      };
      ModelStore.prototype.getCollection = function(type) {};
      ModelStore.prototype.fetch = function() {
        this.clear({
          silent: true
        });
        return this.set(this.storingStrategy.fetch(this.getStorageKey(), this.defaults));
      };
      ModelStore.prototype.save = function() {
        return this.storingStrategy.store(this.getStorageKey(), this.toJSON());
      };
      return ModelStore;
    })();
  });
}).call(this);
