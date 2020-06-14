package ru.net.serbis.cputemp.tool;

import android.content.*;
import ru.net.serbis.cputemp.*;

public class Params
{
    public static SharedPreferences reader(Context context)
    {
        return context.getSharedPreferences(Constants.APP, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor writer(Context context)
    {
        return reader(context).edit();
    }
}
