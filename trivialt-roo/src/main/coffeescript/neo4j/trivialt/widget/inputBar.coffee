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
   './template/inputBarTpl'],
  (View,Model,inputBarTpl) ->
    exports = {}
    
    exports.InputBarModel = class InputBarModel extends Model
      
      getValue : ( ) -> @get 'value',''
      setValue : (v,o) -> @set 'value',v,o
      
      setButtonLabel : (v,o) -> @set 'buttonLabel',v,o
      getButtonLabel : ( ) -> @get 'buttonLabel'
      
      getError : ( ) -> @get 'error',''
      setError : (v,o) -> @set 'error',v,o
    
    
    exports.InputBarView = class InputBarView extends View
      
      events : 
        'click .input-bar-execute' : 'onExecuteClicked'
      
      constructor : (opts) ->
        super(opts)
        @model = new InputBarModel
        @model.bind 'change:error',@onErrorChanged
        @model.bind 'change:value',@onValueChanged
        @model.bind 'change:buttonLabel',@onButtonLabelChanged
        
      
      render : ->
        @el.innerHTML = inputBarTpl()
        
        @onErrorChanged()
        @onValueChanged()
        @onButtonLabelChanged()
        
        this
        
      onButtonLabelChanged:=>
        $('.input-bar-execute',@el).html @model.getButtonLabel()
        
      onExecuteClicked : () =>
        @model.setValue $('.input-bar-input',@el).val()
    
      onErrorChanged : () =>
        error = @model.getError()
        
        errorEl = $('.error',@el)
        errorEl.html error
        
        if error.length > 0 then errorEl.show() else errorEl.hide()
    
      onValueChanged : () =>
        $('.input-bar-input',@el).val(@model.getValue())
    
    return exports

)
