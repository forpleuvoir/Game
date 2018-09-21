package com.forpleuvoir.game.controller;

import com.forpleuvoir.game.model.Direction;
import com.forpleuvoir.game.GameApp;
import com.forpleuvoir.game.Settings;
import com.forpleuvoir.game.entity.Enemy;
import com.forpleuvoir.game.model.Map;
import com.forpleuvoir.game.view.GameView;

import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class GameController implements KeyListener, Runnable, MouseMotionListener {
    private Map map;
    private GameView gameView;
    private boolean running;
    private boolean[] isMove = new boolean[]{false, false, false, false};
    private boolean isShoot = false;
    private int shootSpeed = 5;
    private Shoot shootController;
    public int[] mouse = {0, 0};
    public boolean puase = false;
    private int ice = 0;
    private boolean isice = false;

    public GameController(Map map, GameView gameView) {
        this.map = map;
        this.gameView = gameView;
        shootController = new Shoot();
        running = true;
        shootSpeed = map.getPlayer().getPower();
    }

    public void gameOver() {
        running = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_PAGE_UP:
                map.getPlayer().powerUp(1);break;
            case KeyEvent.VK_PAGE_DOWN:
                map.getPlayer().powerDown(1);break;
            case KeyEvent.VK_P:
                System.out.println("暂停");
                puase = !puase;
                break;
            case KeyEvent.VK_R:
                GameApp.ReStart();
                break;
            case KeyEvent.VK_I:
                GameApp.control();
                break;
            case KeyEvent.VK_SHIFT:
                map.getPlayer().setMoveSpeed(2);
                map.getPlayer().getOnmyo().get(0).setMoveSpeed(1);
                map.getPlayer().getOnmyo().get(1).setMoveSpeed(1);
                map.getPlayer().getOnmyo().get(0).setX(map.getPlayer().getX() + 20);
                map.getPlayer().getOnmyo().get(1).setX(map.getPlayer().getX() - 20);
                map.getPlayer().getOnmyo().get(0).setY(map.getPlayer().getY() - 25);
                map.getPlayer().getOnmyo().get(1).setY(map.getPlayer().getY() - 25);
                break;
            case KeyEvent.VK_UP:
                isMove[0] = true;
                isMove[1] = false;
                if (isMove[2]) {
                    if (isMove[0]) {
                        map.changeDirection(Direction.UP_RIGHT);
                    }
                    break;
                } else if (isMove[3]) {
                    if (isMove[0]) {
                        map.changeDirection(Direction.UP_LEFT);
                    }
                    break;
                }
                map.changeDirection(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                isMove[1] = true;
                isMove[0] = false;
                if (isMove[2]) {
                    if (isMove[1])
                        map.changeDirection(Direction.DOWN_RIGHT);
                    break;
                } else if (isMove[3]) {
                    if (isMove[1])
                        map.changeDirection(Direction.DOWN_LEFT);
                    break;
                }

                map.changeDirection(Direction.DOWN);
                break;
            case KeyEvent.VK_RIGHT:
                isMove[2] = true;
                isMove[3] = false;
                if (isMove[1]) {
                    if (isMove[2])
                        map.changeDirection(Direction.DOWN_RIGHT);
                    break;
                } else if (isMove[0]) {
                    if (isMove[2])
                        map.changeDirection(Direction.UP_RIGHT);
                    break;
                }

                map.changeDirection(Direction.RIGHT);
                break;
            case KeyEvent.VK_LEFT:
                isMove[3] = true;
                isMove[2] = false;
                if (isMove[1]) {
                    if (isMove[3])
                        map.changeDirection(Direction.DOWN_LEFT);
                    break;
                } else if (isMove[0]) {
                    if (isMove[3])
                        map.changeDirection(Direction.UP_LEFT);
                    break;
                }

                map.changeDirection(Direction.LEFT);
                break;
        }
    }

    @Override
    public void run() {
        new Thread(shootController).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    if (!puase) {
                        try {
                            Thread.sleep(new Random().nextInt(1000) + 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        map.getEnemy().add(new Enemy(map.getPlayer(), map.getEnemyHp()));
                        try {
                            Thread.sleep(new Random().nextInt(500) + 500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        map.getEnemy().add(new Enemy(map.getPlayer(), map.getEnemyHp()));
                    }
                }

            }
        }).start();
        while (running) {
            try {
                Thread.sleep(Settings.GAME_SPEED);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
            if (isice) {
                if (ice / 20 > 11) {
                    ice = 0;
                } else if (ice % 20 == 0 || ice == 0) {
                    map.getNodes().addAll(map.getCirno().spellCard(ice / 20));
                }
                ice++;
            }
            if (!puase) {
                map.nextFrame();
                for (boolean b : isMove) {
                    map.nextFrame(b);
                    if (b) break;
                }
                gameView.draw();
            }

        }
    }

    public Shoot getShootController() {
        return shootController;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        mouse[0] = x;
        mouse[1] = y;
    }


    class Shoot implements Runnable, KeyListener {
        @Override
        public void run() {
            while (running) {
                try {
                    Thread.sleep(1000 / shootSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!puase) {
                    switch (map.getPlayer().getPower()) {
                        case 1:
                            shootSpeed = 8;
                            break;
                        case 2:
                            shootSpeed = 12;
                            break;
                        case 3:
                            shootSpeed = 8;
                            break;
                        case 4:
                            shootSpeed = 10;
                            break;
                        case 5:
                            shootSpeed = 16;
                            break;
                        case 6:
                            shootSpeed = 20;
                            break;
                        case 233:
                            shootSpeed = 1000;
                            break;
                    }
                    if (isShoot) {
                        map.getBullet().addAll(map.getPlayer().shoot());
                        if (map.getPlayer().getMoveSpeed() == 2) {
                            map.getBullet().addAll(map.getPlayer().getOnmyo().get(0).bShoot());
                            map.getBullet().addAll(map.getPlayer().getOnmyo().get(1).bShoot());
                        } else {
                            map.getOfudas().addAll(map.getPlayer().getOnmyo().get(0).oShoot());
                            map.getOfudas().addAll(map.getPlayer().getOnmyo().get(1).oShoot());
                        }
                    }

                }
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_Z:
                    isShoot = true;
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_Z:
                    isShoot = false;
                    break;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_S:
                isice = !isice;
//                map.getNodes().addAll(map.getCirno().spellCard());
                break;
            case KeyEvent.VK_X:
                try {
                    if (map.getMusouFuuin().getBody().getFirst() == null)
                        map.getMusouFuuin().create(map.getPlayer(), map);
                }catch (Exception e1){
                    map.getMusouFuuin().create(map.getPlayer(), map);
                }
                break;
            case KeyEvent.VK_SHIFT:
                map.getPlayer().setMoveSpeed(6);
                map.getPlayer().getOnmyo().get(0).setMoveSpeed(3);
                map.getPlayer().getOnmyo().get(1).setMoveSpeed(3);
                map.getPlayer().getOnmyo().get(0).setX(map.getPlayer().getX() + 40);
                map.getPlayer().getOnmyo().get(1).setX(map.getPlayer().getX() - 40);
                map.getPlayer().getOnmyo().get(0).setY(map.getPlayer().getY() - 10);
                map.getPlayer().getOnmyo().get(1).setY(map.getPlayer().getY() - 10);
                break;
            case KeyEvent.VK_UP:
                isMove[0] = false;
                if (isMove[2]) {
                    map.changeDirection(Direction.RIGHT);
                } else if (isMove[3]) {
                    map.changeDirection(Direction.LEFT);
                }
                break;
            case KeyEvent.VK_DOWN:
                isMove[1] = false;
                if (isMove[2]) {
                    map.changeDirection(Direction.RIGHT);
                } else if (isMove[3]) {
                    map.changeDirection(Direction.LEFT);
                }
                break;
            case KeyEvent.VK_RIGHT:
                isMove[2] = false;
                if (isMove[0]) {
                    map.changeDirection(Direction.UP);
                } else if (isMove[1]) {
                    map.changeDirection(Direction.DOWN);
                }
                break;
            case KeyEvent.VK_LEFT:
                isMove[3] = false;
                if (isMove[0]) {
                    map.changeDirection(Direction.UP);
                } else if (isMove[1]) {
                    map.changeDirection(Direction.DOWN);
                }
                break;
        }
    }

    public boolean isShoot() {
        return isShoot;
    }

    public boolean isRunning() {
        return running;
    }
}
