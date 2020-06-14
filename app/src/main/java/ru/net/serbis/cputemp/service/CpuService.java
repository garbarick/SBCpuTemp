package ru.net.serbis.cputemp.service;

import android.app.*;
import android.content.*;
import android.os.*;
import ru.net.serbis.cputemp.timer.*;

public class CpuService extends Service
{
    public class ThisBinder extends Binder
    {
        public CpuTimer getTimer()
        {
            return timer;
        }
    }

    private final IBinder binder = new ThisBinder();
    private final CpuTimer timer = new CpuTimer(1000);;

    @Override
    public IBinder onBind(Intent itent)
    {
        return binder;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        timer.start();
    }

    @Override
    public void onDestroy()
    {
        timer.stop();
        super.onDestroy();
    }
}
