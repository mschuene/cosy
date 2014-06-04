
/**
 * Write a description of class TimeManager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TimeManager
{
    /** Attribute */
    private static final long maxTime = 2000;
    
    /**
     * Returns true if there is time left to think
     */
    public static boolean isTimeLeft(long started) {
        return (System.currentTimeMillis() - started) < maxTime;
    }
}
