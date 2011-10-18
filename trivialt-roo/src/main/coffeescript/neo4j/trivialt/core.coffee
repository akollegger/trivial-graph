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
  ['ribcage/Model'
   'ribcage/Router'
   'ribcage/View'
   './page/signIn'
   './page/notFound'
   './widget/header'],
  (Model,Router,View,signIn,notFound,header) ->
    
    exports = {}
    
    exports.Application = class Application extends Model
      
      constructor : () ->
        super()
        @notFoundView = new notFound.NotFoundView
      
      setPage : (page) -> @set 'page', page
      getPage :        -> @get 'page', @notFoundView
    
    
    exports.AppRouter = class AppRouter extends Router
      
      routes : 
        '' : 'index'
        
      constructor : (@application) ->
        super()
        
      index : () =>
        @application.setPage new signIn.SignInView(@application)
        
        
    exports.AppView = class AppView extends View
      
      constructor : (@application) ->
        super()
        @headerView = new header.HeaderView(@application)
        @application.bind "change:page", @render
      
      render : () =>
        @el.innerHTML = ""
        $(@el).append(@headerView.render().el)
        $(@el).append(@application.getPage().render().el)
        this
    
    return exports

)
