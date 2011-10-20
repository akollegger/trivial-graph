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
  ['ribcage/Model',
   'ribcage/Collection'],
  (Model,Collection) ->
    
    exports = {}
    
    # Cheating a little bit..
    API_URL = '../api'
    
    exports.ValidationError = class ValidationError extends Error
      
      constructor : (@message) ->
        super(@message)
        
    exports.Repository = class Repository
      
      constructor : (@modelClass, @application) ->
        @models = {}
        
      addOrUpdate : (model) ->
        savedModel = @get model.id
        if savedModel?
          savedModel.set(if model.toJSON? then model.toJSON() else model)
        else
          model = if model.toJSON? then model else new @modelClass(model,application:@application)
          @add model
          return model
      
      get : (id)    -> @models[id]
      add : (model) -> @models[model.id] = model
    
    
    exports.TrivialtModel = class TrivialtModel extends Model
      
      constructor : (attrs,opts) ->
        @application = opts.application
        super(attrs,opts)
        
    
    exports.TrivialtCollection = class TrivialtCollection extends Collection
      
      constructor : (models,opts) ->
        @application = opts.application
        super(models,opts)
        
      ###
      Override backbones model creation to ensure globally consistent model
      references.
      ###
      _prepareModel : (model,opts) ->
        if model not instanceof Backbone.Model
          attrs = model
          if @repository?
            model = @repository.addOrUpdate attrs
          else
            model = new @model(attrs,opts)
          if model.validate and not model._performValidation(attrs, opts)
            model = false
        
        return model
    
    
    exports.Team = class Team extends TrivialtModel
    
      urlRoot : API_URL + "/teams"
    
      set : (update, opts) ->
        if update.currentDeck?
          @currentDeck = @application.decks.addOrUpdate update.currentDeck
          delete update.currentDeck
        super update, opts
    
      validate : (attrs) =>
        if attrs.name? and attrs.name.length == 0
          return new ValidationError("Team name is required.")
      
    
    exports.Match = class Match extends TrivialtModel
      
      url : -> API_URL + "/matches" + (if @id? then "/#{@id}" else "")
      
      constructor : (attrs,opts) ->
        super(attrs,opts)
        @rounds = new Rounds([],{url:@url() + "/rounds",application:@application})
        
    
    exports.Round = class Round extends TrivialtModel
    
      url : -> API_URL + "/rounds" + (if @id? then "/#{@id}" else "")
    
      constructor : (attrs, opts) ->
        super(attrs,opts)
        @framedQuestions = new FramedQuestions([],{url:@url() + "/frames",application:@application})
        
      isClosed : -> @get('closed') is true
    
      
    exports.Rounds = class Rounds extends TrivialtCollection
      
      model   : Round
      
      constructor : (models,opts)->
        @repository = opts.application.rounds
        super(models,opts)
            
      fetch : (opts) ->
        success = opts.success
        opts.success = (rounds) ->
          i = rounds.models.length
          for round in rounds.models
            round.framedQuestions.fetch success:()->
              if --i <= 0 then success rounds
        super opts
      
      ###
      Fetch the first non-closed round.
      ###
      getCurrent : ->
        for round in @models
          if not round.isClosed()
            return round
    
    
    exports.FramedQuestion = class FramedQuestion extends TrivialtModel
      
      constructor : (attrs,opts)->
        @possibleAnswers = new PossibleAnswers([],{url:@url() + "/possibleanswers",application:opts.application})
        super(attrs,opts)
      
      url : -> API_URL + "/framedquestions" + (if @id? then "/#{@id}" else "")
      
      set : (update, opts) ->
        
        if update.possibleAnswers?
          @possibleAnswers.reset update.possibleAnswers
          delete update.possibleAnswers
        super update, opts
      
      getPhrase : -> @get 'phrase'
    
    
    exports.FramedQuestions = class FramedQuestions extends TrivialtCollection
      
      model : FramedQuestion
      
      constructor : (models,opts) ->
        @repository = opts.application.framedQuestions
        super(models,opts)
        
    
    exports.PossibleAnswer = class PossibleAnswer extends TrivialtModel
      
      url : -> API_URL + "/possibleanswers" + (if @id? then "/#{@id}" else "")
      
      getText : -> @get 'text'
    
    
    exports.PossibleAnswers = class PossibleAnswers extends TrivialtCollection
      
      model : PossibleAnswer
      
      constructor : (models,opts) ->
        @repository = opts.application.possibleAnswers
        super(models,opts)
    
    
    exports.Proposal = class Proposal extends TrivialtModel
    
      url : -> API_URL + "/proposals" + (if @id? then "/#{@id}" else "")
      
      set : (update, opts) ->
        if update.posedQuestion?
          update.posedQuestion = @application.framedQuestions.addOrUpdate update.posedQuestion
          
        if update.card?
          update.card = @application.cards.addOrUpdate update.card
          
        super update, opts
      
      getCard: -> @get 'card'
      getFramedQuestion: -> @get 'posedQuestion'
      getAnswer: -> @get 'proposedAnswer',''
      
      setCard: (card) -> @set 'card' : card
      setAnswer: (answer) -> @set 'proposedAnswer' : answer
      
      toJSON:->
        attrs = @attributes
        if @getCard()? then attrs.card = id:@getCard().id
        if @getFramedQuestion()? then attrs.posedQuestion = id:@getFramedQuestion().id
        return attrs
    
    
    exports.Proposals = class Proposals extends TrivialtCollection
      
      model : Proposal
      
      constructor : (models,opts)->
        @repository = opts.application.proposals
        @card = opts.card
        super(models,opts)
      
      getOrCreateFor : (framedQuestion) ->
        for proposal in @models
          qid = if proposal.getFramedQuestion()? then proposal.getFramedQuestion().id else -1
          if  qid == framedQuestion.id
            return proposal
        prop = new Proposal {
          posedQuestion : framedQuestion
          card : @card
          },{ application:@application }
        @add prop
        return prop
    
    
    exports.Card = class Card extends TrivialtModel
    
      url : -> API_URL + "/cards" + (if @id? then "/#{@id}" else "")
      
      constructor : (attrs,opts) ->
        super(attrs,opts)
        
        @proposals = new Proposals [],
          url:@url() + "/proposals"
          card:this
          application:@application
    
      set: (update, opts) ->
        if update.round?
          @round = @application.rounds.addOrUpdate update.round
          delete update.round
        super update, opts
        
      confirmProposedAnswers: (opts)->
        i = @proposals.models.length
        for proposal in @proposals.models
          proposal.save null, success:->
            if --i <= 0 then opts.success()
    
    
    exports.Cards = class Cards extends TrivialtCollection
      
      model   : Card
      
      constructor : (models,opts) ->
        @repository = opts.application.cards
        super(models,opts)
            
      fetch : (opts) ->
        success = opts.success
        opts.success = (cards) ->
          i = cards.models.length * 2
          for card in cards.models
            
            card.proposals.fetch 
              add: true
              success:-> if --i <= 0 then success cards
            
            card.round.framedQuestions.fetch 
              add:true
              success:-> if --i <= 0 then success cards
            
        super opts
    
    
    exports.Deck = class Deck extends TrivialtModel
    
      urlRoot : API_URL + "/decks"
    
      constructor : (attrs, opts) ->
        super(attrs,opts)
        
        @cards = new Cards [],
          application:@application
          url:@url() + "/cards"
    
      set : (update, opts) ->
        if update.match?
          @match = @application.matches.addOrUpdate update.match
          delete update.match
        super update, opts
    
    return exports

)
