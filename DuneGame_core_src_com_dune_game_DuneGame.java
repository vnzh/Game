package com.dune.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class DuneGame extends ApplicationAdapter {

    private static class RedCircle {
        private Texture texture;
        private Vector2 position;

        public RedCircle(float x, float y) {
            this.texture = new Texture("RedCircle.png");
            this.position = new Vector2(x, y);
        }

        public void render(SpriteBatch batch) {
//            batch.draw(texture, position.x, position.y, 100f, 100f);
            batch.draw(texture, position.x, position.y,  100, 100);
        }

        public void dispose() {
            texture.dispose();
        }

        //        public void update(float x, float y) {
//
//            while (checkPosition(x, y)){
//                this.position.x = (float) Math.random() * 1100;
//                this.position.y = (float) Math.random() * 620;
//            }
//        }
//
//        private boolean checkPosition(float x, float y) {
//            if ((position.x <= x) && (position.x + 100 >= x)) {
//                if ((position.y <= y) && (position.y >= y)) return true;
//            }
//            return false;
//        }

    }//ResCircle

    private static class Tank {
        private Vector2 position;
        private Texture texture;
        private float angle;
        private float speed;

        public Tank(float x, float y) {
            this.position = new Vector2(x, y);
            this.texture = new Texture("tank.png");
            this.speed = 200.0f;
        }

        public void update(float dt) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                angle += 180.0f * dt;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                angle -= 180.0f * dt;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                position.x += speed * MathUtils.cosDeg(angle) * dt;
                position.y += speed * MathUtils.sinDeg(angle) * dt;

            }
            if (position.x > 1200 - 40) position.x = 1200 - 40;
            if (position.x < 0 + 40) position.x = 0 + 40;
            if (position.y > 720 - 40) position.y = 720 - 40;
            if (position.y < 0 - 40) position.y = 0 - 40;
        }

        //мы    рисунок  танка  белаем   квадратным с шириной  и высотой  по 80 пикселей  (7 и 7 аргумент метода)
        // рисуем    танк  с высотой
        public void render(SpriteBatch batch) {
            batch.draw(texture, position.x - 40, position.y - 40, 40, 40, 80, 80,
                    1, 1, angle, 0, 0, 80, 80, false, false);
        }

        public void dispose() {
            texture.dispose();
        }
    }//tank

    private SpriteBatch batch;
    private Tank tank;
    private RedCircle redCircle;

    @Override
    public void create() {
        batch = new SpriteBatch();
        tank = new Tank(200, 200);
        redCircle = new RedCircle(500, 320);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        Gdx.gl.glClearColor(0, 0.4f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        tank.render(batch);
        redCircle.render(batch);
        batch.end();
    }

    public void update(float dt) {
        tank.update(dt);

//        if ((redCircle.position.x <= tank.position.x + 80 ) && (redCircle.position.x + 100 >= tank.position.x -80)) {
//            if ((redCircle.position.y <= tank.position.y +80 ) && (redCircle.position.y >= tank.position.y -80)) {
////                while (redCircle.checkPosition(tank.position.x, tank.position.y)) {
////                    redCircle.position.x = (float) Math.random() * 1100;
////                    redCircle.position.y = (float) Math.random() * 620;
////                }
////                redCircle.position.x = redCircle.position.x + 100;
////                redCircle.position.y = redCircle.position.y + 100;
//                redCircle.position.x = (float) Math.random() * 1100;
//                redCircle.position.y = (float) Math.random() * 620;
//            }
//        }

        while ((redCircle.position.x <= tank.position.x + Math.abs(40 * MathUtils.cosDeg(tank.angle))) &&
                (redCircle.position.x + 100 >= tank.position.x - Math.abs(40 * MathUtils.cosDeg(tank.angle))) &&
                (redCircle.position.y <= tank.position.y + Math.abs(40 * MathUtils.sinDeg(tank.angle))) &&
                (redCircle.position.y >= tank.position.y - Math.abs(40 * MathUtils.sinDeg(tank.angle)))) {
            redCircle.position.x = (float) MathUtils.random(0, 1100);
            redCircle.position.y = (float) MathUtils.random(0, 620);
        }


    }


    @Override
    public void dispose() {
        batch.dispose();
        tank.dispose();
        redCircle.dispose();
    }

    // Домашнее задание:
    // - Задать координаты точки, и нарисовать в ней круг (любой круг, радиусом пикселей 50)
    // - Если "танк" подъедет вплотную к кругу, то круг должен переместиться в случайную точку
    // - * Нельзя давать танку заезжать за экран
}