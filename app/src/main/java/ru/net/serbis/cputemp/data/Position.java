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
    
    private int x;
    private int y;
    private float touchX;
    private float touchY;

    public Position(Context context)
    {
        this.context = context;
        manager = UITools.getService(context, Context.WINDOW_SERVICE);
        view = LayoutInflater.from(context).inflate(R.layout.floating, null);
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
        params.x = Params.reader(context).getInt(Constants.FLOAT_X, getDefaultX(context));
        params.y = Params.reader(context).getInt(Constants.FLOAT_Y, 0);
        manager.addView(view, params);
    }

    private int getDefaultX(Context context)
    {
        WindowManager manager = UITools.getService(context, Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x / 2;
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

    private void onDowun(MotionEvent event)
    {
        x = params.x;
        y = params.y;
        touchX = event.getRawX();
        touchY = event.getRawY();
    }
    
    private void onMove(MotionEvent event)
    {
        params.x = x + (int) (event.getRawX() - touchX);
        params.y = y + (int) (event.getRawY() - touchY);
        update();
    }

    private void update()
    {
        Params.writer(context)
            .putInt(Constants.FLOAT_X, params.x)
            .putInt(Constants.FLOAT_Y, params.y)
            .commit();
        manager.updateViewLayout(view, params);
    }

    public void moveUp(int p)
    {
        params.y -= p;
        update();
    }

    public void moveDown(int p)
    {
        params.y += p;
        update();
    }

    public void moveLeft(int p)
    {
        params.x -= p;
        update();
    }

    public void moveRight(int p)
    {
        params.x += p;
        update();
    }

    public void removeView()
    {
        manager.removeView(view);
    }
}
