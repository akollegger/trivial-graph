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
    
    exports.ValidationError = class ValidationError extends Error
      
      constructor : (@message) ->
        super(@message)
    
    
    exports.Team = class Team extends Model
    
      validate : (attrs) =>
        if not attrs.name? or attrs.name.length == 0
          return new ValidationError("Team name is required.")
          
      loadCurrentMatch : (opts) ->
        new Match().fetch _.extend(opts,{
          url : @url() + "/matches/current"
        })
    
    exports.Teams = class Teams extends Collection
      
      urlPath : "/teams"
      model   : Team
      
    
    exports.Match = class Match extends Model
      
      constructor : (attrs,opts) ->
        super(attrs,opts)
        @questions = new FramedQuestions([],{baseUrl:@url})
        @teams = new Teams([],{baseUrl:@url})
        
    
    exports.Matches = class Matches extends Collection
      
      urlPath : "/matches"
      model   : Match
    
    exports.FramedQuestion = class FramedQuestion extends Model
    
    exports.FramedQuestions = class FramedQuestions extends Collection
      
      urlPath : "/framedquestions"
      model   : FramedQuestion
    
    return exports

)
