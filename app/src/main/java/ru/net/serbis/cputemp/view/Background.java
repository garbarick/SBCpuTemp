package ru.net.serbis.cputemp.view;

import android.app.*;
import android.widget.*;
import ru.net.serbis.cputemp.*;
import ru.net.serbis.cputemp.service.*;
import ru.net.serbis.cputemp.tool.*;
import android.view.*;
import ru.net.serbis.cputemp.data.*;

public class Background implements AdapterView.OnItemSelectedListener
{
    private Activity context;
    private FloatingConnector conn;
    private Switch enable;

    public Background(Activity context, FloatingConnector conn)
    {
        this.context = context;
        this.conn = conn;
        enable = UITools.findView(context, R.id.floating);
        init();
    }

    private void init()
    {
        Spinner view = UITools.findView(context, R.id.background);
        view.setSelection(Params.reader(context).getInt(Constants.BACKGROUND, 0));
        view.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        Params.writer(context)
            .putInt(Constants.BACKGROUND, position)
            .commit();
        if (!enable.isChecked())
        {
            return;
        }
        Position floating = conn.getPosition();
        if (floating != null)
        {
            floating.initBackground();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
    }
}
