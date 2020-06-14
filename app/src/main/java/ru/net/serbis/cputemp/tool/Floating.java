package ru.net.serbis.cputemp.tool;

import android.content.*;
import android.provider.*;
import ru.net.serbis.cputemp.service.*;

public class Floating
{
    private static Intent get(Context context)
    {
        return new Intent(context, FloatingService.class);
    }

    public static void start(Context context)
    {
        if (Settings.canDrawOverlays(context))
        {
            context.startService(get(context));
        }
    }

    public static void stop(Context context)
    {
        context.stopService(get(context));
    }
}
