package com.forpleuvoir.game.model;

public enum Direction {
    UP(0),
    RIGHT(1),
    DOWN(3),
    LEFT(4),
    UP_RIGHT(5),
    UP_LEFT(6),
    DOWN_RIGHT(7),
    DOWN_LEFT(8);

    private final int directionCode;

    public int directionCode() {
        return directionCode;
    }

    Direction(int directionCode) {
        this.directionCode = directionCode;
    }

}
