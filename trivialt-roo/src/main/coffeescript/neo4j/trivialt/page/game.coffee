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
   './template/questionContentTpl'
   './template/possibleAnswerTpl'
   './template/roundConfirmationTpl'
   './template/roundSummaryTpl'
   './template/questionSummaryTpl'],
  (View,Model,domain,inputBar,threeColumn,questionContentTpl,possibleAnswerTpl,roundConfirmationTpl,roundSummaryTpl,questionSummaryTpl)->
    exports = {}
    
    
    exports.GameState = class GameState
       
      @WAITING_FOR_ROUND  = 0
      
      @QUESTION           = 1
      @ROUND_CONFIRMATION = 3
      @ROUND_SUMMARY      = 4
    
    
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
            "state"    : GameState.QUESTION
            "card"     : card
            "proposal" : card.proposals.getOrCreateFor question
            
      showRoundConfirmation: (roundIdx) ->
        @fetchCurrentDeck success:=>
          deck = @getDeck()
          card = deck.cards.at roundIdx
          @set 
            "state"    : GameState.ROUND_CONFIRMATION
            "card"     : card
            
      showRoundSummary: (roundIdx) ->
        @fetchCurrentDeck success:=>
          deck = @getDeck()
          card = deck.cards.at roundIdx
          @set 
            "state"    : GameState.ROUND_SUMMARY
            "card"     : card
            
      nextQuestion: ->
        questionIdx = @getCurrentQuestionIndex()
        if questionIdx > -1
          nextIdx = questionIdx + 1
          if @getRound().framedQuestions.length > nextIdx
            @application.navigate("#/game/round/#{@getCurrentRoundIndex()}/question/#{nextIdx}")
          else
            @application.navigate("#/game/round/#{@getCurrentRoundIndex()}/confirmation")
            
      confirmAnswersForCurrentRound: ->
        card = @getCard()
        if card?
          card.confirmProposedAnswers success:=>
            @application.navigate("#/game/round/#{@getCurrentRoundIndex()}/summary")
              
      fetchCurrentDeck : (opts) ->
        @application.getTeam().fetch success : (team) =>
          deck = team.currentDeck
          deck.cards.fetch success : =>
            @setDeck deck
            deck.match.rounds.fetch success : =>
              if opts.success?
                opts.success deck
          
      
      ## UTILS
      
      getCurrentQuestionIndex : -> 
        if @getRound()? then @getRound().framedQuestions.indexOf @getProposal().getFramedQuestion()
        else -1
        
      getCurrentRoundIndex : -> 
        if @getMatch()? then @getMatch().rounds.indexOf @getRound()
        else -1
      
      
      ## ATTRIBUTES
          
      getState    : -> @get 'state'
      getDeck     : -> @get 'deck'
      getCard     : -> @get 'card'
      getProposal : -> @get 'proposal'
      
      getRound    : -> if @getCard()? then @getCard().round else null
      getMatch    : -> if @getDeck()? then @getDeck().match else null
      
      setDeck : (deck) -> @set 'deck':deck
    
    
      
    ## GAME VIEWS
    
    exports.GameView = class GameView extends View
      
      constructor : (@application) ->
        super()
        @application.game.bind "change:state", @onGameStateChange
        
        @questionView = new QuestionView(@application)
        @waitingForRoundView = new WaitingForRoundView(@application)
        @roundConfirmationView = new RoundConfirmationView(@application)
        @roundSummaryView = new RoundSummaryView(@application)
      
      render : () ->
        super()
        @onGameStateChange()
        this
        
      onGameStateChange : =>
        $(@el).contents().detach()
        switch @application.game.getState()
          when GameState.QUESTION
            $(@el).append @questionView.render().el
          when GameState.WAITING_FOR_ROUND
            $(@el).append @waitingForRoundView.render().el
          when GameState.ROUND_CONFIRMATION
            $(@el).append @roundConfirmationView.render().el
          when GameState.ROUND_SUMMARY
            $(@el).append @roundSummaryView.render().el
        
    
    exports.QuestionView = class QuestionView extends threeColumn.ThreeColumnView
      
      events : 
        'click .input-bar-execute' : 'onExecuteClicked'
      
      constructor : (@application)->
        super()
        @application.game.bind "change:proposal", @onQuestionChanged
        
        @inputBar = new inputBar.InputBarView()
      
      render : ->
        super()
        if @proposal?
        
          question = @proposal.getFramedQuestion()
          @content.append questionContentTpl question : question.getPhrase()
          
          ul = $('.answer-alternatives',@el)
          for alternative in question.possibleAnswers.models
            ul.append (new PossibleAnswerView(@proposal, alternative).render().el)
            
          @content.append @inputBar.render().el
          @onProposalAnswerChanged()
          
        this
        
      onQuestionChanged : =>
        if @proposal?
          @proposal.unbind "change:proposedAnswer", @onProposalAnswerChanged
        
        @proposal = @application.game.getProposal()
        
        if @proposal?
          @proposal.bind "change:proposedAnswer", @onProposalAnswerChanged
        
        @render()
        
      onProposalAnswerChanged : =>
        @inputBar.model.setValue @proposal.getAnswer()
        
      onExecuteClicked : =>
        @proposal.setAnswer @inputBar.model.getValue()
        @application.game.nextQuestion()
      
    
    exports.WaitingForRoundView = class WaitingForRoundView extends threeColumn.ThreeColumnView
      
      constructor : (@application)->
        super()
      
      render : ->
        super()
        @content.append "Waiting for round to start.."
        this
       
    
    exports.RoundConfirmationView = class RoundConfirmationView extends threeColumn.ThreeColumnView
      
      events : 
        'click .confirm-answers'   : 'onConfirmAnswersClicked'
        'click .input-bar-execute' : 'onExecuteClicked'
        
      constructor : (@application)->
        super()
        @application.game.bind "change:card", @onCardChanged
        @inputBar = new inputBar.InputBarView()
      
      render : ->
        super()
        if @round?
        
          @content.append roundConfirmationTpl()
          
          summaryList = $(".question-summary-list",@el)
          for q in @round.framedQuestions.models
            proposal = @card.proposals.getOrCreateFor q
            summaryList.append (new QuestionSummaryView(proposal).render().el)
          
          @content.append @inputBar.render().el
          
        this 
        
      onCardChanged : =>
        @round = @application.game.getRound()
        @card = @application.game.getCard()
        @render()
        
      onConfirmAnswersClicked: =>
        @inputBar.model.setValue("Yes")
        
      onExecuteClicked: =>
        if @inputBar.model.getValue().toLowerCase() is "yes"
          @application.game.confirmAnswersForCurrentRound()
        
    
    exports.RoundSummaryView = class RoundSummaryView extends threeColumn.ThreeColumnView
      
      constructor : (@application)->
        super()
        @inputBar = new inputBar.InputBarView()
        @application.game.bind "change:card",@onCardChanged
      
      render : ->
        super()
        if @round?
        
          if @round.isClosed()
            @content.append roundSummaryTpl()
          else
            @content.append "Waiting for other players to finish.."
        this 
        
      onCardChanged : =>
        if @round?
          clearInterval @roundUpdateInterval
          @round.unbind "change:closed", @onRoundCloseStatusChanged
        
        @round = @application.game.getRound()
        @round.bind "change:closed", @onRoundCloseStatusChanged
        
        @roundUpdateInterval = setInterval (=>@round.fetch()), 1000
        
        @render()
        
      onRoundCloseStatusChanged: =>
        clearInterval @roundUpdateInterval
        @render()
        
    ## WIDGETS
    
    exports.PossibleAnswerView = class PossibleAnswerView extends View
      
      tagName : 'li'
      
      events : 
        'click' : "onClick"
      
      constructor: (@proposal, @alternative) -> 
        super()
      
      render : ->
        $(@el).html possibleAnswerTpl alternative : @alternative
        this
        
      onClick : =>
        @proposal.setAnswer(@alternative.getText())
        
    
    
    exports.QuestionSummaryView = class QuestionSummaryView extends View
      
      tagName : 'li'
      
      constructor: (@proposal) -> 
        super()
      
      render: ->
        $(@el).html questionSummaryTpl proposal : @proposal
        this
      
    return exports

)
