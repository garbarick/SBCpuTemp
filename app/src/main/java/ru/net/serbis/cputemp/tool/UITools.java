package ru.net.serbis.cputemp.tool;

import android.app.*;
import android.content.*;
import android.view.*;

public class UITools
{
    public static <T> T findView(Object view, int id)
    {
        if (view instanceof Activity)
        {
            return (T) ((Activity)view).findViewById(id);
        }
        else if (view instanceof View)
        {
            return (T) ((View)view).findViewById(id);
        }
        return null;
    }

    public static <T> T getService(Context context, String name)
    {
        return (T) context.getSystemService(name);
    }    
}
