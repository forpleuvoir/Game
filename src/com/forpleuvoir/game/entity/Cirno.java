package com.forpleuvoir.game.entity;

import com.forpleuvoir.game.entity.danmaku.cirno.IcicleFall.Icebomb;
import com.forpleuvoir.game.entity.danmaku.cirno.IcicleFall.IcicleFall;
import com.forpleuvoir.game.gamenIterface.Fly;
import com.forpleuvoir.game.model.Direction;
import com.forpleuvoir.game.model.Node;
import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayList;

public class Cirno extends Node implements Fly {
    private double maxHp = 99999;
    private double hp = maxHp;
    private int size = 150;
    double a = hp;
    double b;
    int i = 0;
    IcicleFall icicleFall = new IcicleFall(this);

    public Cirno() {
    }

    public Cirno(int x, int y) {
        super(x, y);
    }

    public ArrayList<Icebomb> spellCard(int ice) {
        ArrayList<Icebomb> icebombs = new ArrayList<>();
        icebombs.addAll(icicleFall.create(ice));
        return icebombs;
    }

    public ArrayList<Icebomb> IcicleFall() {
        ArrayList<Icebomb> icebombs = new ArrayList<>();

        return icebombs;
    }

    public double beHit(double dmg) {
        hp = hp - dmg;
        return hp;
    }

    //分别返回 x,y,max血条长度,现在血条长度
    public int[] hpBar() {
        double Percentage = hp / maxHp;
        int hpP = (int) (Percentage * 120);
        return new int[]{getX(), getY() + 80, 120, hpP};
    }

    public double getDPS() {
        if (i / 60 == 1) {
            b = a - hp;
            a = hp;
            i = 0;
        }
        i++;
        return b;
    }

    @Override
    public void move(Direction direction, boolean isMove) {

    }

    @Override
    public int[] DecisionPoint() {
        return new int[]{getX(), getY(), size / 2 - 15};
    }

    public double getMaxHp() {
        return maxHp;
    }

    public double getHp() {
        return hp;
    }

    public int getSize() {
        return size;
    }

}
