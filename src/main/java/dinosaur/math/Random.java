package dinosaur.math;

public class Random
{

    private static final java.util.Random random = new java.util.Random();


    public static float next()
    {
        return random.nextFloat();
    }

    public static void setSeed(long seed)
    {
        random.setSeed(seed);
    }

}
