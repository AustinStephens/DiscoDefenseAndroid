package isys221.discodefense;

import android.graphics.PointF;

public class Vector2f extends PointF {


    public Vector2f(float x, float y) {
        super(x, y);
    }

    public Vector2f(Vector2f v) {
        this(v.x, v.y);
    }

    public static Vector2f add(Vector2f v1, Vector2f v2) {
        return new Vector2f(v1.x + v2.x, v1.y + v2.y);
    }

    public static Vector2f sub(Vector2f v1, Vector2f v2) {
        return new Vector2f(v1.x + v2.x, v1.y + v2.y);
    }

    public static Vector2f div(Vector2f v, float m) {
        return new Vector2f(v.x / m, v.y / m);
    }

    public static Vector2f mult(Vector2f v, float m) {
        return new Vector2f(v.x * m, v.y * m);
    }

    public float mag() {
        return (float)Math.sqrt(x * x + y * y);
    }

    public Vector2f copy() {
        return new Vector2f(x, y);
    }

    public Vector2f normalize() {
        float mag = mag();
        return new Vector2f(this.x / mag, this.y / mag);
    }

    public void add(Vector2f v){
        this.x += v.x;
        this.y += v.y;
    }

    public void sub(Vector2f v){
        this.x -= v.x;
        this.y -= v.y;
    }

    public void mult(float m){
        this.x += m;
        this.y += m;
    }

    public void div(float m){
        this.x /= m;
        this.y /= m;
    }
}
