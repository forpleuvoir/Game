package com.forpleuvoir.game.entity.danmaku.cirno.IcicleFall

import com.forpleuvoir.game.entity.Cirno
import com.forpleuvoir.game.gamenIterface.Fly
import com.forpleuvoir.game.model.Direction
import com.forpleuvoir.game.model.Node

class Icebomb constructor(cirno: Cirno, direction: Direction?, sec: Double, speed: Int) : Node(), Fly {
    var cx: Int = cirno.x
    var cy: Int = cirno.y
    var speed: Int = speed
    var sec: Double = sec
    val direction: Direction? = direction
    var size: Int = 12
    var r = 0.0
    var moveTime = 0
    var nx: Double = 0.0
    var ny: Double = 0.0
    var t: Boolean = false

    init {
        this.x = cx
        this.y = cy
    }

    override fun move(direction: Direction?, isMove: Boolean) {
        moveTime++
        when (this.direction) {
            Direction.RIGHT -> rightMove()
            Direction.LEFT -> leftMove()
        }
    }

    override fun DecisionPoint(): IntArray {
        var iar: IntArray = intArrayOf(x, y, size / 2)
        return iar
    }

    fun leftMove() {
        if (moveTime > 12) {
            if (moveTime > 35)
                leftDown()
        } else {
            r += speed
            if (sec >= 1) {
                nx = r / this.sec
                ny = Math.sqrt((r * r) - (nx * nx))
                x = (cx - nx).toInt()
                y = (cy + ny).toInt()
            } else if (sec < 1) {
                var nsec = sec - 2
                nx = r / Math.abs(nsec)
                ny = Math.sqrt((r * r) - (nx * nx))
                x = (cx - nx).toInt()
                y = (cy - ny).toInt()
            }
        }
    }

    fun leftDown() {
        if (!t) {
            r = 0.0
            cx = x
            cy = y
            t = !t
        }
        r += 4
        if (sec >= 1) {
            nx = r / this.sec
            ny = Math.sqrt((r * r) - (nx * nx))
            y = (cy + nx).toInt()
            x = (cx + ny).toInt()
        } else if (sec < 1) {
            var nsec = sec - 2
            nx = r / Math.abs(nsec)
            ny = Math.sqrt((r * r) - (nx * nx))
            y = (cy + nx).toInt()
            x = (cx - ny).toInt()
        }
    }

    fun rightMove() {
        if (moveTime > 12) {
            if (moveTime > 35)
                rightDown()
        } else {
            r += speed
            if (sec >= 1) {
                nx = r / this.sec
                ny = Math.sqrt((r * r) - (nx * nx))
                x = (cx + nx).toInt()
                y = (cy + ny).toInt()
            } else if (sec < 1) {
                var nsec = sec - 2
                nx = r / Math.abs(nsec)
                ny = Math.sqrt((r * r) - (nx * nx))
                x = (cx + nx).toInt()
                y = (cy - ny).toInt()
            }
        }
    }

    fun rightDown() {
        if (!t) {
            r = 0.0
            cx = x
            cy = y
            t = !t
        }
        r += 4
        if (sec >= 1) {
            nx = r / this.sec
            ny = Math.sqrt((r * r) - (nx * nx))
            y = (cy + nx).toInt()
            x = (cx - ny).toInt()
        } else if (sec < 1) {
            var nsec = sec - 2
            nx = r / Math.abs(nsec)
            ny = Math.sqrt((r * r) - (nx * nx))
            y = (cy + nx).toInt()
            x = (cx + ny).toInt()
        }
    }
}