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
  ['ribcage/View'
   'ribcage/Model'
   '../domain'
   '../widget/inputBar'
   './threeColumn'
   './template/questionContentTpl'],
  (View,Model,domain,inputBar,threeColumn,questionContentTpl) ->
    exports = {}
    
    
    exports.GameState = class GameState
       
      @QUESTION = 1
      @WAITING_FOR_ROUND = 2
    
    
    exports.Game = class Game extends Model
    
      constructor : (@application) ->
        super()
        @set 'state' : GameState.WAITING_FOR_ROUND
            
      joinCurrentMatch : () ->
        @set 'state' : GameState.WAITING_FOR_ROUND
        @fetchCurrentDeck success:=>
          currentRound = @getDeck().match.rounds.getCurrent()
          roundIdx = @getDeck().match.rounds.indexOf currentRound
          @application.navigate("#/game/round/#{roundIdx}/question/0")
            
            
      showQuestion: (roundIdx,questionIdx) ->
        @fetchCurrentDeck success:=>
          deck = @getDeck()
          card  = deck.cards.at roundIdx
          question = card.round.framedQuestions.at questionIdx
          @set 
            "state"     : GameState.QUESTION
            "card"      : card
            "proposal" : card.proposals.getOrCreateFor question
      
      fetchCurrentDeck : (opts) ->
        @application.getTeam().fetch success : (team) =>
          deck = team.currentDeck
          deck.cards.fetch success : =>
            @setDeck deck
            deck.match.rounds.fetch success : =>
              if opts.success?
                opts.success deck
          
      getState    : -> @get 'state'
      getDeck     : -> @get 'deck'
      getProposal : -> @get 'proposal'
      
      setDeck : (deck) -> @set 'deck':deck
    
    
    exports.GameView = class GameView extends View
      
      constructor : (@application) ->
        super()
        @application.game.bind "change:state", @onGameStateChange
        
        @questionView = new QuestionView(@application)
        @waitingForRoundView = new WaitingForRoundView(@application)
      
      render : () ->
        super()
        @onGameStateChange()
        this
        
      onGameStateChange : () =>
        switch @application.game.getState()
          when GameState.QUESTION
            $(@el).html @questionView.render().el
          when GameState.WAITING_FOR_ROUND
            $(@el).html @waitingForRoundView.render().el
      
    exports.QuestionView = class QuestionView extends threeColumn.ThreeColumnView
      
      constructor : (@application)->
        super()
        @application.game.bind "change:proposal", @onQuestionChanged
      
      render : ->
        super()
        if @application.game.getProposal()?
          prop = @application.game.getProposal()
          @content.append prop.framedQuestion.getPhrase()
        this
        
      onQuestionChanged : =>
        @render()
      
    exports.WaitingForRoundView = class WaitingForRoundView extends threeColumn.ThreeColumnView
      
      constructor : (@application)->
        super()
      
      render : ->
        super()
        @content.append "Waiting for round to start.."
        this
      
    return exports

)
