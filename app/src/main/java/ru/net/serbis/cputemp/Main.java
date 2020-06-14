package ru.net.serbis.cputemp;

import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;
import ru.net.serbis.cputemp.*;
import ru.net.serbis.cputemp.service.*;
import ru.net.serbis.cputemp.timer.*;
import ru.net.serbis.cputemp.tool.*;

public class Main extends Activity implements CompoundButton.OnCheckedChangeListener
{
    private CpuTimer timer;
    private boolean bound;

    private Listener listener = new Listener()
    {
        private TextView text;
        
        public void onChange(int value)
        {
            if (text == null)
            {
                text = UITools.findView(Main.this, R.id.value);   
            }
            text.setText(value + "°C");                
        }
    };

    private ServiceConnection connection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            CpuService.ThisBinder binder = (CpuService.ThisBinder) service;
            timer = binder.getTimer();
            timer.addListener(listener);
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            bound = false;
        }
    };

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
        SharedPreferences params = getSharedPreferences(Constants.APP, MODE_PRIVATE);
        if (params.getBoolean(Constants.FLOAT, false))
        {
            floating.setChecked(true);
        }
        floating.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton button, boolean value)
    {
        SharedPreferences params = getSharedPreferences(Constants.APP, MODE_PRIVATE);
        SharedPreferences.Editor editor = params.edit();
        editor.putBoolean(Constants.FLOAT, value);
        editor.commit();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Intent intent = new Intent(this, CpuService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if (bound)
        {
            timer.excludeListener(listener);
            unbindService(connection);
            bound = false;            
        }
    }
}
