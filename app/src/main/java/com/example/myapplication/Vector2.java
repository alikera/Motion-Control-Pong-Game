package com.example.myapplication;


public class Vector2 {

    public static Vector2 Up = new Vector2(0, 1);
    public static Vector2 Right = new Vector2(1, 0);
    public double x;
    public double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector2 vector2){
        this.x += vector2.x;
        this.y += vector2.y;
    }

    public Vector2 mul(float num){
        return new Vector2(x * num, y * num);
    }

    public Vector2 mul(Vector2 vector2){
        return new Vector2(x * vector2.x, y * vector2.y);
    }

    public void minus(Vector2 vector2) {
        this.x -= vector2.x;
        this.y -= vector2.y;
    }
}
