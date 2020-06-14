package ru.net.serbis.cputemp.tool;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import ru.net.serbis.cputemp.*;
import ru.net.serbis.cputemp.service.*;

public class Floating
{
    private static Intent get(Activity context)
    {
        return new Intent(context, FloatingService.class);
    }

    public static void start(Activity context)
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
            Settings.canDrawOverlays(context))
        {
            context.startService(get(context));
            return;
        }
        Intent intent= new Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:" + context.getPackageName()));
        context.startActivityForResult(intent, Constants.SYSTEM_ALERT_WINDOW_PERMISSION);
    }

    public static void stop(Activity context)
    {
        context.stopService(get(context));
    }
}
