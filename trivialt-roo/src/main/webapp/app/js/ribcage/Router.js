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
  */  var __hasProp = Object.prototype.hasOwnProperty, __extends = function(child, parent) {
    for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; }
    function ctor() { this.constructor = child; }
    ctor.prototype = parent.prototype;
    child.prototype = new ctor;
    child.__super__ = parent.prototype;
    return child;
  };
  define(['order!lib/jquery', 'order!lib/jquery.hotkeys', 'order!lib/backbone'], function() {
    var Router;
    return Router = (function() {
      __extends(Router, Backbone.Controller);
      Router.prototype.routes = {};
      Router.prototype.shortcuts = {};
      function Router() {
        var definition, method, _ref;
        Router.__super__.constructor.call(this);
        _ref = this.shortcuts;
        for (definition in _ref) {
          method = _ref[definition];
          $(document).bind("keyup", definition, this[method]);
        }
      }
      return Router;
    })();
  });
}).call(this);
