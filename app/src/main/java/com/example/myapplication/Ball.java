package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball {

    private final double G = 9.8 * 500;

    private final Platform platform;

    private final double deltaTime;
    private final GameView gameView;
    private final Paint ballPaint;
    private final double radius;
    private Vector2 position;
    private Vector2 velocity;
    private boolean collision;


    public Ball(Vector2 vector2, double radius, GameView gameView, Platform platform) {
        this.position = vector2;
        this.radius = radius;
        this.gameView = gameView;
        this.platform = platform;
        deltaTime = 1 / (float) MainThread.FPS;
        ballPaint = new Paint();
        ballPaint.setColor(Color.rgb(255, 255, 255));
        ballPaint.setStyle(Paint.Style.FILL);
        velocity = new Vector2(0, 0);
    }

    public void draw(Canvas canvas) {
        canvas.getWidth();
        canvas.getHeight();
        canvas.drawCircle((float) position.x, (float) position.y, (float)radius, ballPaint);
    }

    public void update() {
        if (position.x + radius >= gameView.width && velocity.x > 0 || position.x - radius <= 0 && velocity.x < 0) {
            velocity = velocity.mul(new Vector2(-1, 1));
        }
        if (position.y + radius >= gameView.height && velocity.y > 0 || position.y - radius <= 0 && velocity.y < 0) {
            velocity = velocity.mul(new Vector2(1, -1));
        }
        if (platform.haveCollisionWithBall(position, radius) && velocity.y > 0 && !collision) {
            velocity = new Vector2(
                    velocity.x * Math.cos(Math.toRadians(2 * platform.getDegree()))
                            - velocity.y * Math.sin(Math.toRadians(2 * platform.getDegree())),
                    -velocity.x * Math.sin(Math.toRadians(2 * platform.getDegree()))
                            - velocity.y * Math.cos(Math.toRadians(2 * platform.getDegree())));
            collision = true;
        }
        if (!platform.haveCollisionWithBall(position, radius)) {
            collision = false;
            velocity.add(new Vector2(0, G * deltaTime));
        }
        position.add(velocity.mul(deltaTime));
    }

    public void reset(Vector2 vector) {
        position = vector;
    }
}
