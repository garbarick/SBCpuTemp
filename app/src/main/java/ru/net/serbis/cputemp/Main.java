package ru.net.serbis.cputemp;

import android.app.*;
import android.os.*;
import ru.net.serbis.cputemp.service.*;
import ru.net.serbis.cputemp.view.*;

public class Main extends Activity
{
    private CpuConnector cpuConn;
    private FloatingConnector floatingConn = new FloatingConnector();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        cpuConn = new CpuConnector(this, this);
        new SuperUser(this, cpuConn);
        new Floating(this, floatingConn);
        new Mover(this, floatingConn);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        cpuConn.bind(this);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        cpuConn.unBind(this);
        floatingConn.unBind(this);
    }
}
