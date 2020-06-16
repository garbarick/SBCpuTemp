package ru.net.serbis.cputemp.service;

import android.content.*;
import android.os.*;
import ru.net.serbis.cputemp.*;
import ru.net.serbis.cputemp.data.*;
import ru.net.serbis.cputemp.timer.*;

public class CpuConnector extends Connector
{
    private Context context;
    private Object view;
    private CpuTimer timer;
    private Listener listener;

    public CpuConnector(Context context, Object view)
    {
        super(CpuService.class);
        this.context = context;
        this.view = view;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service)
    {
        super.onServiceConnected(name, service);

        CpuService.ThisBinder binder = (CpuService.ThisBinder) service;
        timer = binder.getTimer();
        listener = new UIListener(view, R.id.value);
        timer.addListener(context, listener);
    }

    @Override
    public void unBind(Context context)
    {
        if (isBound())
        {
            timer.excludeListener(listener);
            super.unBind(context);
        }
    }
}
