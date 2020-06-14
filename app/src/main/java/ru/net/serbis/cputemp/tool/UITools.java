package ru.net.serbis.cputemp.tool;

import android.app.*;

public class UITools
{
    public static <T> T findView(Activity view, int id)
    {
        return (T) view.findViewById(id);
    }
}
