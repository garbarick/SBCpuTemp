package ru.net.serbis.cputemp;

import android.app.*;
import android.os.*;
import android.widget.*;
import ru.net.serbis.cputemp.data.*;
import ru.net.serbis.cputemp.tool.*;

public class Main extends Activity implements CompoundButton.OnCheckedChangeListener
{
    private Connector connector;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initFloating();
    }

    private void initFloating()
    {
        Switch floating = UITools.findView(this, R.id.floating);
        floating.setOnCheckedChangeListener(this);
        if (Floating.enabled(this))
        {
            floating.setChecked(Params.reader(this).getBoolean(Constants.FLOAT, false));
        }
        else
        {
            floating.setEnabled(false);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton button, boolean value)
    {
        if (value)
        {
            Floating.start(this);
        }
        else
        {
            Floating.stop(this);
        }
        Params.writer(this)
            .putBoolean(Constants.FLOAT, value)
            .commit();
    }
    

    @Override
    protected void onStart()
    {
        super.onStart();
        connector = new Connector(this);
        connector.bind(this);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        connector.unBind(this);
    }
}
