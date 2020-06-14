package ru.net.serbis.cputemp.tool;

import android.content.*;
import android.os.*;
import android.provider.*;
import ru.net.serbis.cputemp.service.*;

public class Floating
{
    private static Intent get(Context context)
    {
        return new Intent(context, FloatingService.class);
    }
    
    public static boolean enabled(Context context)
    {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
            Settings.canDrawOverlays(context);
    }

    public static void start(Context context)
    {
        if (enabled(context))
        {
            context.startService(get(context));
        }
    }

    public static void stop(Context context)
    {
        context.stopService(get(context));
    }
}
