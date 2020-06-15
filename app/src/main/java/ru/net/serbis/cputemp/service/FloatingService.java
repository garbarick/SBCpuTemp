package ru.net.serbis.cputemp.service;

import android.app.*;
import android.content.*;
import android.os.*;
import ru.net.serbis.cputemp.data.*;

public class FloatingService extends Service
{
    public class ThisBinder extends Binder
    {
        public Position getPosition()
        {
            return position;
        }
    }

    private Position position;
    private CpuConnector connector;
    private ThisBinder binder = new ThisBinder();;

    @Override
    public IBinder onBind(Intent intent)
    {
        return binder;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        position = new Position(this);
        connector = new CpuConnector(position.getView());
        connector.bind(this);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        connector.unBind(this);
        position.removeView();
    }
}
