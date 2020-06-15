package ru.net.serbis.cputemp.service;

import android.content.*;
import android.os.*;
import android.provider.*;
import ru.net.serbis.cputemp.data.*;

public class FloatingConnector extends Connector
{
    private Position position;

    public FloatingConnector()
    {
        super(FloatingService.class);
    }

    public Position getPosition()
    {
        return position;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service)
    {
        super.onServiceConnected(name, service);

        FloatingService.ThisBinder binder = (FloatingService.ThisBinder) service;
        position = binder.getPosition();
    }

    public boolean enabled(Context context)
    {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
            Settings.canDrawOverlays(context);
    }

    @Override
    public void bind(Context context)
    {
        if (enabled(context))
        {
            super.bind(context);
        }
    }

    public void start(Context context)
    {
        if (enabled(context))
        {
            context.startService(getIntent(context));
        }
    }

    public void stop(Context context)
    {
        context.stopService(getIntent(context));
    }
}
