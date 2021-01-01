package dinosaur.math;

public class Vector2
{

    public final float x;
    public final float y;


    public Vector2(float x, float y)
    {
        this.x = x;
        this.y = y;
    }


    public Vector2 add(Vector2 vector)
    {
        return new Vector2(x + vector.x, y + vector.y);
    }

    public Vector2 sub(Vector2 vector)
    {
        return new Vector2(x - vector.x, y - vector.y);
    }

    public Vector2 mult(float value)
    {
        return new Vector2(x * value, y * value);
    }


    public float magSq()
    {
        return (x * x) + (y * y);
    }

    public float mag()
    {
        return (float) Math.sqrt(magSq());
    }

    public Vector2 normalize()
    {
        float magnitude = mag();
        return (magnitude > 0) ? mult(1 / magnitude) : this;
    }

}
