package ru.net.serbis.cputemp.tool;

import java.io.*;

public class IOTools
{
    public static void close(Closeable o)
    {
        if (o == null)
        {
            return;
        }
        try
        {
            o.close();
        }
        catch(Exception e)
        {
        }
    }
}
