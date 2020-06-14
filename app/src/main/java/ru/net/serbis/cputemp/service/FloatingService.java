package ru.net.serbis.cputemp.service;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import ru.net.serbis.cputemp.*;
import ru.net.serbis.cputemp.data.*;
import ru.net.serbis.cputemp.tool.*;

public class FloatingService extends Service implements View.OnTouchListener
{
    private View view;
    private WindowManager manager;
    private Position position;
    private Connector connector;

    @Override
    public IBinder onBind(Intent p1)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        manager = UITools.getService(this, WINDOW_SERVICE);
        view = LayoutInflater.from(this).inflate(R.layout.floating, null);
        position = new Position(this);
        manager.addView(view, position.getParams());
        view.setOnTouchListener(this);
        
        connector = new Connector(view);
        connector.bind(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                position.onDowun(event);
                return true;

            case MotionEvent.ACTION_MOVE:
                position.onMove(this, event);
                manager.updateViewLayout(view, position.getParams());
                return true;
        }
        return false;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        connector.unBind(this);
        manager.removeView(view);
    }
}
