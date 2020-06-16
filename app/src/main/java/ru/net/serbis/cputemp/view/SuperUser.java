package ru.net.serbis.cputemp.view;

import android.app.*;
import android.widget.*;
import ru.net.serbis.cputemp.*;
import ru.net.serbis.cputemp.service.*;
import ru.net.serbis.cputemp.tool.*;

public class SuperUser implements CompoundButton.OnCheckedChangeListener
{
    private Activity context;
    private CpuConnector conn;

    public SuperUser(Activity context, CpuConnector conn)
    {
        this.context = context;
        this.conn = conn;
        init();
    }

    private void init()
    {
        Switch view = UITools.findView(context, R.id.superUser);
        view.setChecked(Params.reader(context).getBoolean(Constants.SU, false));
        view.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton button, boolean value)
    {
        Params.writer(context)
            .putBoolean(Constants.SU, value)
            .commit();
        conn.unBind(context);
        conn.bind(context);
    }
}
