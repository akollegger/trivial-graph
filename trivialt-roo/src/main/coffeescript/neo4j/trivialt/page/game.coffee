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
   './template/questionSummaryTpl'
   './template/matchSummaryTpl'
   './template/scoresTpl'],
  (View,Model,domain,inputBar,threeColumn,questionContentTpl,possibleAnswerTpl,roundConfirmationTpl,roundSummaryTpl,questionSummaryTpl,matchSummaryTpl,scoresTpl)->
    exports = {}
    
    
    exports.GameState = class GameState
       
      @WAITING_FOR_ROUND_START = 0
      @QUESTION                = 1
      @ROUND_CONFIRMATION      = 2
      @WAITING_FOR_ROUND_END   = 3
      @ROUND_SUMMARY           = 4
      @MATCH_SUMMARY           = 5
      
    exports.GameUrlFactory = class GameUrlFactory
      
      forQuestion:(roundIdx,questionIdx)->
        "#/game/round/#{roundIdx}/question/#{questionIdx}"
      
      forRoundConfirmation:(roundIdx)->
        "#/game/round/#{roundIdx}/confirmation"
      
      forRoundSummary:(roundIdx)->
        "#/game/round/#{roundIdx}/summary"
      
      forMatchSummary:()->
        "#/game/match/summary"
    
    
    exports.Game = class Game extends Model
    
      constructor : (@application) ->
        super()
        @set 'state' : GameState.WAITING_FOR_ROUND_START
        @url = new GameUrlFactory()
            
      joinCurrentMatch : () ->
        @set 'state' : GameState.WAITING_FOR_ROUND_START
        @fetchCurrentDeck success:=>
          currentRound = @getDeck().match.rounds.getCurrent()
          roundIdx = @getDeck().match.rounds.indexOf currentRound
          
          @application.navigate @url.forQuestion(roundIdx,0)
          
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
            "state"    : GameState.WAITING_FOR_ROUND_END
            "card"     : card 
          
          if card.round.isAvailable()
            # Round is not over yet
            card.round.fetchUntil('available',false)
          else 
            #@set 
            #  "state"    : GameState.ROUND_SUMMARY
            #  "card"     : card
            @showNextRound()
      
      showMatchSummary: ->
        @fetchCurrentDeck success:=>
          @set 
            "state"    : GameState.MATCH_SUMMARY
            
      showNextQuestion: ->
        next = @getNextQuestionIndex()
        if @next isnt null
          @application.navigate @url.forQuestion(@getCurrentRoundIndex(),next)
        else
          @application.navigate @url.forRoundConfirmation(@getCurrentRoundIndex())
            
      showNextRound: ->
        next = @getNextRoundIndex()
        if next isnt null
          @application.navigate @url.forQuestion(next,0)
        else
          @application.navigate @url.forMatchSummary()
            
      confirmAnswersForCurrentRound: ->
        card = @getCard()
        if card?
          card.confirmProposedAnswers success:=>
            @application.navigate @url.forRoundSummary(@getCurrentRoundIndex())
              
      fetchCurrentDeck : (opts) ->
        @application.getTeam().fetch success : (team) =>
          deck = team.currentDeck
          
          deck.match.getScores().fetch()
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
      
      getNextQuestionIndex:->
        questionIdx = @getCurrentQuestionIndex()
        if questionIdx > -1
          nextIdx = questionIdx + 1
          if @getRound().framedQuestions.length > nextIdx
            return nextIdx
        return null
            
      getNextRoundIndex:->
        roundIdx = @getCurrentRoundIndex()
        if roundIdx > -1
          nextIdx = roundIdx + 1
          if @getMatch().rounds.length > nextIdx
            return nextIdx
        return null
      
      
      ## ATTRIBUTES
          
      getState    : -> @get 'state'
      getDeck     : -> @get 'deck'
      getCard     : -> @get 'card'
      getProposal : -> @get 'proposal'
      
      getRound    : -> if @getCard()?  then @getCard().round else null
      getMatch    : -> if @getDeck()?  then @getDeck().match else null
      getScores   : -> if @getMatch()? then @getMatch().getScores() else null
      
      setDeck : (deck) -> @set 'deck':deck
    
    
      
    ## GAME VIEWS
    
    exports.GameView = class GameView extends View
      
      constructor : (@application) ->
        super()
        @application.game.bind "change:state", @onGameStateChange
        
        @waitingForRoundStartView = new WaitingForRoundStartView(@application)
        @questionView             = new QuestionView(@application)
        @roundConfirmationView    = new RoundConfirmationView(@application)
        @waitingForRoundEndView   = new WaitingForRoundEndView(@application)
        @roundSummaryView         = new RoundSummaryView(@application)
        @matchSummaryView         = new MatchSummaryView(@application)
      
      render : () ->
        super()
        @onGameStateChange()
        this
        
      onGameStateChange : =>
        $(@el).contents().detach()
        switch @application.game.getState()
        
          when GameState.WAITING_FOR_ROUND_START
            view = @waitingForRoundStartView
            
          when GameState.QUESTION
            view = @questionView
            
          when GameState.ROUND_CONFIRMATION
            view = @roundConfirmationView
           
          when GameState.WAITING_FOR_ROUND_END
            view = @waitingForRoundEndView
          
          when GameState.ROUND_SUMMARY
            view = @roundSummaryView
            
          when GameState.MATCH_SUMMARY
            view = @matchSummaryView
            
        if view?
          $(@el).append view.render().el
        
    
    exports.AbstractMatchView = class AbstractMatchView extends threeColumn.ThreeColumnView
    
      constructor : (@application)->
        super()
        @scoresView = new ScoresView()
        @application.game.bind "change:deck",@onDeckChanged
      
      render : ->
        $(@scoresView.el).detach()
        super()
        @leftSidebar.append @scoresView.render().el
        this
        
      onDeckChanged:=>
        @scoresView.setScores @application.game.getScores()
        
      
    exports.WaitingForRoundStartView = class WaitingForRoundStartView extends AbstractMatchView
      
      render : ->
        super()
        @content.append "Waiting for round to start.."
        this
    
    
    exports.QuestionView = class QuestionView extends AbstractMatchView
      
      events : 
        'click .input-bar-execute' : 'onExecuteClicked'
      
      constructor : (@application)->
        super(@application)
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
        @application.game.showNextQuestion()
       
    
    exports.RoundConfirmationView = class RoundConfirmationView extends AbstractMatchView
      
      events : 
        'click .confirm-answers'   : 'onConfirmAnswersClicked'
        'click .input-bar-execute' : 'onExecuteClicked'
        
      constructor : (@application)->
        super(@application)
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
        
        
    
    exports.WaitingForRoundEndView = class WaitingForRoundEndView extends AbstractMatchView
      
      constructor : (@application)->
        super(@application)
      
      render : ->
        super()
        @content.append "Waiting for round to finish.."
        this
        
    
    exports.RoundSummaryView = class RoundSummaryView extends AbstractMatchView
      
      constructor : (@application)->
        super(@application)
        @inputBar = new inputBar.InputBarView()
        @application.game.bind "change:card",@onCardChanged
      
      render : ->
        super()
        if @round?
          @content.append roundSummaryTpl()
          @content.append @inputBar.render().el
        this 
        
      onCardChanged : =>
        @round = @application.game.getRound()
        @render()
        
    
    exports.MatchSummaryView = class MatchSummaryView extends AbstractMatchView
      
      constructor : (@application)->
        super(@application)
        #@application.game.bind "change:deck",@onCardChanged
      
      render : ->
        super()
        @content.append matchSummaryTpl()
        this 
        
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
    
    
    exports.ScoresView = class ScoresView extends View
      
      render:=>
        if @scores?
          $(@el).html scoresTpl()
          scoreList = $(".score-list",@el)
          for score in @scores.models
            scoreList.append "<li>#{score.getName()}: #{score.getScore()}</li>"
        this
      
      setScores:(scores)->
        if @scores?
          @scores.unbind "change", @onScoresChanged
        @scores = scores
        @scores.bind "change", @onScoresChanged
        
      onScoresChanged:=>
        @render()
      
    return exports

)
