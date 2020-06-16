package ru.net.serbis.cputemp.tool;

import java.io.*;

public class CpuTools
{
    private static final String SUC = "su -c ";
    private static final String CPU_TEMP = "cat sys/class/thermal/thermal_zone0/temp";
    
    public static int getTemp(boolean superUser)
    {
        BufferedReader reader = null;
        try
        {
            String command = CPU_TEMP;
            if (superUser)
            {
                command = SUC + command;
            }
            Process process = Runtime.getRuntime().exec(command);
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
