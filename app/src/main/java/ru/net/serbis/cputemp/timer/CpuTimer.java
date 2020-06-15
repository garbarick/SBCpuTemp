package ru.net.serbis.cputemp.timer;

import android.os.*;
import java.util.*;
import ru.net.serbis.cputemp.tool.*;

public class CpuTimer extends Handler
{
    private int interval;
    private int value;
    private List<Listener> listeners = new ArrayList<Listener>();
    
    private Runnable runnable = new Runnable()
    {
        @Override
        public void run()
        {
            try
            {
                postDelayed(this, interval);
                setTemp();
            }
            catch (Exception e)
            {
                Log.info(this, e);
            }
        }
        
        private void setTemp()
        {
            if (listeners.isEmpty())
            {
                return;
            }
            int cur = CpuTools.getTemp();
            if (cur == value)
            {
                return;
            }
            value = cur;
            onChange(value);
        }
    };   

    public CpuTimer(int interval)
    {
        this.interval = interval;
    }

    public synchronized void addListener(Listener listener)
    {
        excludeListener(listener);
        listener.onChange(value);
        listeners.add(listener);
    }

    public synchronized void excludeListener(Listener listener)
    {
        listeners.remove(listener);
    }

    private void onChange(int value)
    {
        for(Listener listener : listeners)
        {
            listener.onChange(value);
        }
    }

    public void start()
    {
        stop();
        postDelayed(runnable, 0);
    }
    
    public void stop()
    {
        removeCallbacks(runnable);
    }
}
