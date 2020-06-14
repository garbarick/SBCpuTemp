package ru.net.serbis.cputemp.data;

import android.widget.*;
import ru.net.serbis.cputemp.timer.*;
import ru.net.serbis.cputemp.tool.*;

public class UIListener implements Listener
{
    private TextView text;

    public UIListener(Object view, int id)
    {
        text = UITools.findView(view, id);        
    }

    public void onChange(int value)
    {
        text.setText(value + "°C");                
    }
}
