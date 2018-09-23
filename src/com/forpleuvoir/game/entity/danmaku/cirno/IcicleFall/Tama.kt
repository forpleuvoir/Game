package com.forpleuvoir.game.entity.danmaku.cirno.IcicleFall

import com.forpleuvoir.game.entity.Cirno
import com.forpleuvoir.game.entity.Player
import com.forpleuvoir.game.gameInterface.Fly
import com.forpleuvoir.game.model.Direction
import com.forpleuvoir.game.model.Node

class Tama constructor(cirno:Cirno,player:Player): Node(),Fly{
    var size:Int =5
    var player:Player=player
    var cx:Int =cirno.x
    var cy:Int=cirno.y
    var px:Int=player.x
    var py:Int=player.y


    override fun move(direction: Direction?, isMove: Boolean) {
         when(direction){
             Direction.DOWN->{

             }
             Direction.DOWN_LEFT->{

             }
             Direction.RIGHT->{

             }
         }
    }

    fun lef(){

    }
}
