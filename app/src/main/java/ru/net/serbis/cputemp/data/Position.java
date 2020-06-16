package ru.net.serbis.cputemp.data;

import android.content.*;
import android.graphics.*;
import android.view.*;
import android.view.WindowManager.*;
import ru.net.serbis.cputemp.*;
import ru.net.serbis.cputemp.tool.*;

public class Position implements View.OnTouchListener
{
    private Context context;
    private View view;
    private WindowManager manager;
    private WindowManager.LayoutParams params;
    private Point size = new Point();

    public Position(Context context)
    {
        this.context = context;
        manager = UITools.getService(context, Context.WINDOW_SERVICE);
        view = LayoutInflater.from(context).inflate(R.layout.floating, null);
        initSize();
        initParams();
        view.setOnTouchListener(this);
    }

    public View getView()
    {
        return view;
    }

    private void initParams()
    {
        params = new WindowManager.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT,
            LayoutParams.TYPE_SYSTEM_ERROR,
            LayoutParams.FLAG_NOT_FOCUSABLE |
            LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        );
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = Params.reader(context).getInt(Constants.FLOAT_X, size.x/2);
        params.y = Params.reader(context).getInt(Constants.FLOAT_Y, 0);
        limit();
        manager.addView(view, params);
    }

    private void initSize()
    {
        WindowManager manager = UITools.getService(context, Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        display.getSize(size);
    }
    
    private void limit()
    {
        params.x = params.x < 0 ? 0 : params.x > size.x ? size.x : params.x;
        params.y = params.y < 0 ? 0 : params.y > size.y ? size.y : params.y;        
    }

    @Override
    public boolean onTouch(View view, MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                onDowun(event);
                return true;

            case MotionEvent.ACTION_MOVE:
                onMove(event);
                return true;
        }
        return false;
    }

    private Point start = new Point();
    private Point touch = new Point();
    private void onDowun(MotionEvent event)
    {
        start.x = params.x;
        start.y = params.y;
        touch.x = (int) event.getRawX();
        touch.y = (int) event.getRawY();
    }
    
    private void onMove(MotionEvent event)
    {
        params.x = start.x + (int) event.getRawX() - touch.x;
        params.y = start.y + (int) event.getRawY() - touch.y;
        update();
    }

    private void update()
    {
        limit();
        Params.writer(context)
            .putInt(Constants.FLOAT_X, params.x)
            .putInt(Constants.FLOAT_Y, params.y)
            .commit();
        manager.updateViewLayout(view, params);
    }

    public void move(int x, int y)
    {
        params.x += x;
        params.y += y;
        update();
    }

    public void removeView()
    {
        manager.removeView(view);
    }
}
