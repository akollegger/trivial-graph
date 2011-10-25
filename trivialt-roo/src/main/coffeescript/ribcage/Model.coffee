###
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
###
define(
  ['order!lib/jquery', 'order!lib/backbone'], 
  () ->

    class Model extends Backbone.Model
      
      get: (key, defaultValue=null) ->
        val = super key
        if not val
          return defaultValue
        val
        
      set: (update, val, opts) ->
        if _(update).isString()
          updateMap = {}
          updateMap[update] = val
          update = updateMap
        else
          opts = val 
         
        super update, opts
        
      fetch : () =>
        super
        
      setFetchInterval : (interval = 5000)->
        if @_fetchInterval?
          clearInterval @_fechInterval
        @_fechInterval = setInterval @fetch, interval
        @fetch()
        
      clearFetchInterval : () ->
        if @_fetchInterval?
          clearInterval @_fechInterval
      
      fetchUntil: (key, targetValue)->
        if @get(key) isnt targetValue
          @_continuousFetchTargets      ?= {}
          @_continuousFetchTargets[key] ?= {}
          @_continuousFetchTargets[key][targetValue] = 1
          
          if not @_targetedFetchInterval?
            @_targetedFetchInterval = setInterval @_targetedContiniousFetch, 2000
            
      _continousFetchTargetsAchieved:->
        for key, targets of @_continuousFetchTargets
          delete targets[@get(key)]
          if _(targets).keys().length == 0
            delete @_continuousFetchTargets[key]
        return _(@_continuousFetchTargets).keys().length == 0
          
      _targetedContiniousFetch : (attrs,opts)=>      
        if @_continousFetchTargetsAchieved()
          clearInterval @_targetedFetchInterval
        else
          @fetch()
            
)
