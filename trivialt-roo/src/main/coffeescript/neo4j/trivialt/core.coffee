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
   './widget/header'
   './widget/footer'],
  (Model,Router,View,domain,signIn,game,notFound,header,footer) ->
    
    exports = {}
    
    exports.Application = class Application extends Model
      
      constructor : (@baseUrl) ->
        super()
        @notFoundView = new notFound.NotFoundView
        
        savedTeam = @getLocallySavedTeam()
        if savedTeam?
          @setTeam savedTeam
        
        @game = new game.Game(this)
        
        opts = {application:this}
        
        # We use repositories to make sure
        # we don't have duplicate instances
        # of models.
        @matches         = new domain.Repository(domain.Match, this)
        @rounds          = new domain.Repository(domain.Round, this)
        @decks           = new domain.Repository(domain.Deck, this)
        @cards           = new domain.Repository(domain.Card, this)
        @proposals       = new domain.Repository(domain.Proposal, this)
        @framedQuestions = new domain.Repository(domain.FramedQuestion, this)
        @possibleAnswers = new domain.Repository(domain.PossibleAnswer, this)
        
      
      setPage : (page) -> @set 'page', page
      getPage :        -> @get 'page', @notFoundView
      
      setTeam : (team) -> 
        @set 'team', team
        @saveTeamLocally team
        
      getTeam : ()     -> @get 'team'
      
      
      navigate : (url, route=true) ->
        Backbone.history.navigate(url, route)
        
      quit:->
        if window.localStorage?
          delete window.localStorage['team']
        @navigate "#/signin"
        
      isSignedIn : () ->
        @getTeam()?
        
      saveTeamLocally : (team)->
        if window.localStorage?
          window.localStorage['team'] = JSON.stringify(team.toJSON())
          
      getLocallySavedTeam : ->
        try
          if window.localStorage? and window.localStorage['team']?
            return new domain.Team(JSON.parse(window.localStorage['team']),application:this)
        catch e
          return null
        return null
    
    
    exports.AppRouter = class AppRouter extends Router
      
      routes : 
        '' : 'index'
        '/signin' : 'signIn'
        '/game/round/:r/question/:q'  : 'question'
        '/game/round/:r/confirmation' : 'roundConfirmation'
        '/game/round/:r/summary'      : 'roundSummary'
        '/game/match/summary'         : 'matchSummary'
        
      @inGame = (f) ->
        ->
          @application.setPage @gameView
          if not @application.getTeam()?
            @application.navigate("#/signin")
          else
            f.apply(this,arguments)
        
      constructor: (@application) ->
        super()
        @gameView = new game.GameView(@application)
        @signInView = new signIn.SignInView(@application)
        
      index: @inGame ->
        @application.game.joinCurrentMatch()
        
      signIn : () ->
        @application.setPage @signInView
      
      question: @inGame (roundIdx,questionIdx) ->
        @application.game.showQuestion(roundIdx,questionIdx)
          
      roundConfirmation: @inGame (roundIdx) ->
        @application.game.showRoundConfirmation(roundIdx)
          
      roundSummary: @inGame (roundIdx) ->
        @application.game.showRoundSummary(roundIdx)
      
      matchSummary: @inGame (roundIdx) ->
        @application.game.showMatchSummary()
      
        
    exports.AppView = class AppView extends View
      
      constructor : (@application) ->
        super()
        @headerView = new header.HeaderView(@application)
        @footerView = new footer.FooterView(@application)
        @application.bind "change:page", @render
      
      render : () =>
        @el.innerHTML = ""
        $(@el).append(@headerView.render().el)
        $(@el).append(@footerView.render().el)
        $(@el).append(@application.getPage().render().el)
        this
    
    return exports

)
