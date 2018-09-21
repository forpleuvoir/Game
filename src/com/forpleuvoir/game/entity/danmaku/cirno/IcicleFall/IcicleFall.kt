package com.forpleuvoir.game.entity.danmaku.cirno.IcicleFall

import com.forpleuvoir.game.entity.Cirno
import com.forpleuvoir.game.model.Direction

class IcicleFall constructor(cirno: Cirno) {

    var cirno: Cirno =cirno
    fun create(position:Int):ArrayList<Icebomb>{
        var icebomb= arrayListOf<Icebomb>()
        when(position){
            1 -> shoot(0.995,icebomb)
            2 -> shoot(1.000,icebomb)
            3 -> shoot(1.005,icebomb)
            4 -> shoot(1.020,icebomb)
            5 -> shoot(1.045,icebomb)
            6 -> shoot(1.085,icebomb)
            7 -> shoot(1.135,icebomb)
            8 -> shoot(1.200,icebomb)
            9 -> shoot(1.290,icebomb)
            10 -> shoot(1.40,icebomb)
            11 -> shoot(1.55,icebomb)
        }
        return icebomb
    }

    fun shoot(sec:Double,icebomb:ArrayList<Icebomb>){
        icebomb.add(Icebomb(cirno, Direction.RIGHT, sec, 10))
        icebomb.add(Icebomb(cirno, Direction.RIGHT, sec, 19))
        icebomb.add(Icebomb(cirno, Direction.RIGHT, sec, 28))
        icebomb.add(Icebomb(cirno, Direction.LEFT, sec, 10))
        icebomb.add(Icebomb(cirno, Direction.LEFT, sec, 19))
        icebomb.add(Icebomb(cirno, Direction.LEFT, sec, 28))
    }
}