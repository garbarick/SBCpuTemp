package ru.net.serbis.cputemp.data;

import android.content.*;
import android.os.*;
import ru.net.serbis.cputemp.*;
import ru.net.serbis.cputemp.service.*;
import ru.net.serbis.cputemp.timer.*;

public class Connector implements ServiceConnection
{
    private Object view;
    private CpuTimer timer;
    private boolean bound;
    private Listener listener;

    public Connector(Object view)
    {
        this.view = view;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service)
    {
        CpuService.ThisBinder binder = (CpuService.ThisBinder) service;
        timer = binder.getTimer();
        listener = new UIListener(view, R.id.value);
        timer.addListener(listener);
        bound = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName name)
    {
        bound = false;
    }

    public void bind(Context context)
    {
        Intent intent = new Intent(context, CpuService.class);
        context.bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    public void unBind(Context context)
    {
        if (!bound)
        {
            return;
        }
        timer.excludeListener(listener);
        context.unbindService(this);
        bound = false;
    }
}
