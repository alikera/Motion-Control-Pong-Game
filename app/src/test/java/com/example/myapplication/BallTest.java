package com.example.myapplication;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BallTest {

    private Ball ball;
    private Platform platform;
    private GameView gameView;

    @Before
    public void setup() {
        // Initialize the necessary objects for testing
        Vector2 position = new Vector2(0, 0);
        double radius = 10.0;
        gameView = null;
        platform = new Platform();
        ball = new Ball(position, radius, gameView, platform);
    }

    @Test
    public void testUpdate_noCollision() {
        // Set up the initial conditions for the test
        ball.setPosition(new Vector2(50, 50));
        ball.setVelocity(new Vector2(10, 10));
        ball.setCollision(false);

        // Perform the update
        ball.update();

        // Verify the expected changes
        assertEquals(60.0, ball.getPosition().x, 0.01);
        assertEquals(60.0, ball.getPosition().y, 0.01);
        assertFalse(ball.getCollision());
    }

    @Test
    public void testUpdate_collision() {
        // Set up the initial conditions for the test
        ball.setPosition(new Vector2(50, 50));
        ball.setVelocity(new Vector2(10, 10));
        ball.setCollision(false);
        platform.setDegree(45);

        // Perform the update
        ball.update();

        // Verify the expected changes
        assertEquals(40.0, ball.getPosition().x, 0.01);
        assertEquals(60.0, ball.getPosition().y, 0.01);
        assertTrue(ball.getCollision());
    }
}
