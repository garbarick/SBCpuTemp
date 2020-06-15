package ru.net.serbis.cputemp.view;

import android.app.*;
import android.widget.*;
import ru.net.serbis.cputemp.*;
import ru.net.serbis.cputemp.service.*;
import ru.net.serbis.cputemp.tool.*;

public class Floating implements CompoundButton.OnCheckedChangeListener
{
    private Activity context;
    private FloatingConnector conn;

    public Floating(Activity context, FloatingConnector conn)
    {
        this.context = context;
        this.conn = conn;
        init();
    }
    
    private void init()
    {
        Switch view = UITools.findView(context, R.id.floating);
        view.setOnCheckedChangeListener(this);
        if (conn.enabled(context))
        {
            view.setChecked(Params.reader(context).getBoolean(Constants.FLOAT, false));
        }
        else
        {
            view.setEnabled(false);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton button, boolean value)
    {
        if (value)
        {
            conn.start(context);
            conn.bind(context);
        }
        else
        {
            conn.unBind(context);
            conn.stop(context);
        }
        Params.writer(context)
            .putBoolean(Constants.FLOAT, value)
            .commit();
    }
}
