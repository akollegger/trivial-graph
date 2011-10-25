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

    class Collection extends Backbone.Collection
      
      constructor : (models, opts) ->
        super(models,opts)
        
        if opts.url?
          @url = opts.url
        @baseUrl = opts.baseUrl or ""
        
      url : () ->
        @baseUrl + @urlPath
        
      fetch : () =>
        super
        
      _add: (model,opts)->
        if model.id? and @get(model.id)?
          return model
        else
          return super(model,opts)
          
      setFetchInterval : (interval = 5000)->
        if @_fetchInterval?
          clearInterval @_fechInterval
        @_fechInterval = setInterval @fetch, interval
        @fetch()
        
      clearFetchInterval : () ->
        if @_fetchInterval?
          clearInterval @_fechInterval
        
      addOrUpdate : (model) ->
        savedModel = @get model.id
        if savedModel?
          savedModel.set(if model.toJSON? then model.toJSON() else model)
        else
          model = if model.toJSON? then model else new @model(model)
          @add model
          return model
          
)
