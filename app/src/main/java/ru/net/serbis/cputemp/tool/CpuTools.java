package ru.net.serbis.cputemp.tool;

import java.io.*;

public class CpuTools
{
    public static int getTemp()
    {
        BufferedReader reader = null;
        try
        {
            Process process = Runtime.getRuntime().exec("cat sys/class/thermal/thermal_zone0/temp");
            process.waitFor();
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            if (line != null)
            {
                float temp = Float.parseFloat(line);
                if (temp > 1000)
                {
                    return (int) temp / 1000;
                }
                return (int) temp;
            }
        }
        catch (Exception e)
        {
            Log.info(CpuTools.class, e);
        }
        finally
        {
            IOTools.close(reader);
        }
        return 0;
    }
}
