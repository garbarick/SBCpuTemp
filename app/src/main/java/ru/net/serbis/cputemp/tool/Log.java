package ru.net.serbis.cputemp.tool;

public class Log
{
    public static void info(Class o, String message)
    {
        android.util.Log.i(o.getName(), message);
    }
    
    public static void info(Object o, String message)
    {
        info(o.getClass(), message);
    }

    public static void info(Class o, String message, Throwable e)
    {
        android.util.Log.i(o.getName(), message, e);
    }
    
    public static void info(Object o, String message, Throwable e)
    {
        info(o.getClass(), message, e);
    }

    public static void info(Class o, Throwable e)
    {
        info(o, "Error", e);
    }
    
    public static void info(Object o, Throwable e)
    {
        info(o, "Error", e);
    }
}
