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
   './template/signInContentTpl'],
  (View,Model,domain,inputBar,threeColumn,signInContentTpl) ->
    exports = {}
    
    exports.SignInView = class SignInView extends threeColumn.ThreeColumnView
      
      constructor : (@application) ->
        super()
        @inputBar = new inputBar.InputBarView()
        @inputBar.model.bind 'change:value',@onTeamNameSet
      
      render : () ->
        super()
        @content.append signInContentTpl()
        @content.append @inputBar.render().el
        this
        
      onTeamNameSet : () =>
        name = @inputBar.model.getValue()
        team = new domain.Team({name:name},{application:@application})
        team.save null,
          success : @onTeamCreated
          error   : @onTeamCreationFailed
      
      onTeamCreated : (team) =>
        @inputBar.model.setError ""
        @application.setTeam team
        
        @application.game.joinCurrentMatch()
        
      onTeamCreationFailed : (team, failure) =>
        if failure instanceof domain.ValidationError
          @inputBar.model.setError failure.message
        else
          @inputBar.model.setError "Unable to create team, the name might already be taken. Try a different one!"
    
    return exports

)
