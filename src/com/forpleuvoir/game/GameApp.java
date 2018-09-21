package com.forpleuvoir.game;

import com.forpleuvoir.game.controller.GameController;
import com.forpleuvoir.game.controller.GameObserver;
import com.forpleuvoir.game.entity.Enemy;
import com.forpleuvoir.game.model.Map;
import com.forpleuvoir.game.view.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameApp {
    private static JFrame window;
    private static Map map;
    private static GameView gameView;
    private static GameController gameController;
    private static JFrame control;
    static JLabel coordinate, bullets;
    static JButton powerup, powerdown;
    static int width = Settings.WIDTH;
    static int height = Settings.HEIGHT;
    static GameObserver gameObserver;
    public static void init() {
        window = new JFrame("game");
        map = new Map(width, height);
        map.init();
        gameView = new GameView(map);
        gameView.init();
        gameObserver=new GameObserver(map);
        gameView.getCanvas().setPreferredSize(new Dimension(width, height));
        gameController = new GameController(map, gameView);
        map.addController(gameController);
        map.addObserver(gameObserver);
        window.addKeyListener(gameController);
        window.addMouseMotionListener(gameController);
        window.addKeyListener(gameController.getShootController());
        window.getContentPane().add(gameView.getCanvas());
        window.setPreferredSize(new Dimension(width + 8, height + 30));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static void control() {
        control = new JFrame("控制面板");
        control.setPreferredSize(new Dimension(200, 500));
        control.setResizable(false);
        JPanel panel = new JPanel(new GridLayout(5, 1));
        panel.setPreferredSize(new Dimension(200, 500));
        coordinate = new JLabel("x,y");
        panel.add(coordinate);
        bullets = new JLabel("子弹:");
        bullets.setPreferredSize(new Dimension(200, 100));
        panel.add(bullets);
        JButton createEnemy = new JButton("创建敌人");
        createEnemy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map.getEnemy().add(new Enemy(map.getPlayer(), map.getEnemyHp()));
                System.out.println("创建敌人");
            }
        });
        powerup = new JButton("powerUP");
        powerup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (map.getPlayer().getPower() < 6 && map.getPlayer().getPower() > 0) {
                    map.getPlayer().setPower(map.getPlayer().powerUp(1));
                    System.out.println("当前火力值：" + map.getPlayer().getPower());
                }
            }
        });
        powerdown = new JButton("powerDOWN");
        powerdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (map.getPlayer().getPower() <= 6 && map.getPlayer().getPower() > 0) {
                    map.getPlayer().setPower(map.getPlayer().powerDown(1));
                    System.out.println("当前火力值：" + map.getPlayer().getPower());
                }
            }
        });
        JPanel panel1 = new JPanel();
        panel1.add(powerup);
        panel1.add(powerdown);
        panel1.setPreferredSize(new Dimension(200, 400));
        panel.add(panel1);
        panel.add(createEnemy);
        control.getContentPane().add(panel);
        control.setLocation(window.getX() + window.getWidth() + 15, window.getY());
        control.pack();
        control.setVisible(true);
    }

    public static void refresh() {
        if (coordinate != null || bullets != null) {
            coordinate.setText("<html>自机:(" + map.getPlayer().getX() + "," + map.getPlayer().getY() + ")" +
                    "<br>鼠标位置(x:" + gameController.mouse[0] + ",y:" + gameController.mouse[1] + ")</html>");
            bullets.setText("<html>子弹数量:" + (map.getBullet().size() + map.getOfudas().size()) +
                    "<br>敌人数量:" + map.getEnemy().size() +
                    "<br>当前火力" + map.getPlayer().getPower() +
                    "<br>敌人当前血量" + map.getEnemyHp() +
                    "</html>");
        }
    }

    public static GameController getGameController() {
        return gameController;
    }

    public static void StartGame() {
        init();
        new Thread(gameController).start();
    }

    public static void ReStart() {
        window.dispose();
        StartGame();
    }

    public static void main(String[] args) {
        StartGame();
    }
}
