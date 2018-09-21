package com.forpleuvoir.game.gamenIterface;

import com.forpleuvoir.game.entity.Cirno;
import com.forpleuvoir.game.model.Direction;
import org.jetbrains.annotations.Nullable;

public interface Fly {
    void move(Direction direction, boolean isMove);
    //返回判定点数组 0:X 1:Y 2:R  判定点为圆形 X:x坐标,Y:y坐标,R:半径

}
