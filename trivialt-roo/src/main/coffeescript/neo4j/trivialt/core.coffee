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
   './domain'
   './page/signIn'
   './page/question'
   './page/notFound'
   './widget/header'],
  (Model,Router,View,domain,signIn,question,notFound,header) ->
    
    exports = {}
    
    exports.Application = class Application extends Model
      
      constructor : (@baseUrl) ->
        super()
        @notFoundView = new notFound.NotFoundView
        
        @teams   = new domain.Teams([],{baseUrl:@baseUrl})
        @matches = new domain.Matches([],{baseUrl:@baseUrl})
      
      setPage : (page) -> @set 'page', page
      getPage :        -> @get 'page', @notFoundView
      
      setTeam : (team) -> @set 'team', team
      getTeam : ()     -> @get 'team'
      
      
      navigate : (url, route) ->
        Backbone.history.navigate(url, route)
    
    
    exports.AppRouter = class AppRouter extends Router
      
      routes : 
        ''               : 'index'
        '/match/current' : 'currentMatch'
        '/match/current/q/:id' : 'questionForCurrentMatch'
        
      constructor : (@application) ->
        super()
      
      notFound : () =>
        @application.setPage @application.notFoundView
        
      index : () =>
        @application.setPage new signIn.SignInView(@application)
      
      currentMatch : () =>
        if @application.getTeam()?
          @application.getTeam().loadCurrentMatch 
            success : (match) =>
              console.log match
            error : () =>
              @notFound()
        else 
          @application.navigate("#",true)
        
        
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
