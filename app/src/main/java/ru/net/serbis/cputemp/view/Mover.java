package ru.net.serbis.cputemp.view;

import android.app.*;
import android.view.*;
import android.widget.*;
import ru.net.serbis.cputemp.*;
import ru.net.serbis.cputemp.service.*;
import ru.net.serbis.cputemp.tool.*;

public class Mover implements View.OnClickListener
{
    private Activity context;
    private FloatingConnector conn;
    private Switch enable;

    public Mover(Activity context, FloatingConnector conn)
    {
        this.context = context;
        this.conn = conn;
        enable = UITools.findView(context, R.id.floating);
        init(R.id.up);
        init(R.id.down);
        init(R.id.left);
        init(R.id.right);
    }
    
    private void init(int id)
    {
        ImageView view = UITools.findView(context, id);
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if (!enable.isChecked())
        {
            return;
        }
        switch(view.getId())
        {
            case R.id.up:
                conn.getPosition().moveUp(10);
                break;
            case R.id.down:
                conn.getPosition().moveDown(10);
                break;
            case R.id.left:
                conn.getPosition().moveLeft(10);
                break;
            case R.id.right:
                conn.getPosition().moveRight(10);
                break;
        }
    }
}
