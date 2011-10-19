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
   './template/threeColumnTpl'],
  (View,Model,threeColumnTpl) ->
    exports = {}
    
    exports.ThreeColumnView = class ThreeColumnView extends View
      
      className : 'page'
      
      render : () ->
        @el.innerHTML = threeColumnTpl()
        @leftSidebar  = $('.left-sidebar',@el)
        @content      = $('.content',@el)
        @rightSidebar = $('.right-sidebar',@el)
        this
    
    return exports

)
