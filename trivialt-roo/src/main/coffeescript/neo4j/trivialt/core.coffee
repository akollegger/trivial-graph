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
   './page/game'
   './page/notFound'
   './widget/header'],
  (Model,Router,View,domain,signIn,game,notFound,header) ->
    
    exports = {}
    
    exports.Application = class Application extends Model
      
      constructor : (@baseUrl) ->
        super()
        @notFoundView = new notFound.NotFoundView
        
        @game = new game.Game(this)
        
        opts = {application:this}
        
        # We use repositories to make sure
        # we don't have duplicate instances
        # of models.
        @matches         = new domain.Repository(domain.Match, this)
        @rounds          = new domain.Repository(domain.Round, this)
        @decks           = new domain.Repository(domain.Deck, this)
        @cards           = new domain.Repository(domain.Card, this)
        @framedQuestions = new domain.Repository(domain.FramedQuestion, this)
        
      
      setPage : (page) -> @set 'page', page
      getPage :        -> @get 'page', @notFoundView
      
      setTeam : (team) -> @set 'team', team
      getTeam : ()     -> @get 'team'
      
      
      navigate : (url, route=true) ->
        Backbone.history.navigate(url, route)
    
    
    exports.AppRouter = class AppRouter extends Router
      
      routes : 
        ''      : 'index'
        '/game/round/:r/question/:q' : 'question'
        
      constructor : (@application) ->
        super()
        @gameView = new game.GameView(@application)
        @signInView = new signIn.SignInView(@application)
        
      notFound : () =>
        @application.setPage @application.notFoundView
        
      index : () =>
        @application.setPage @signInView
      
      question : (roundIdx,questionIdx) =>
        
        @application.setPage @gameView
        
        if not @application.getTeam()?
          # Temporary
          new domain.Team({id:294},{application:@application}).fetch success:(team)=>
            @application.setTeam team
            @application.game.showQuestion(roundIdx,questionIdx)
        else
          @application.game.showQuestion(roundIdx,questionIdx)
        
        
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
