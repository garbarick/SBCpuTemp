package ru.net.serbis.cputemp.receiver;

import android.content.*;
import ru.net.serbis.cputemp.*;
import ru.net.serbis.cputemp.tool.*;

public class Start extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (Params.reader(context).getBoolean(Constants.FLOAT, false))
        {
            Floating.start(context);
        }
    }
}
