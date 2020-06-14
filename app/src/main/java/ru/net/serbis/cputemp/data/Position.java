package ru.net.serbis.cputemp.data;

import android.content.*;
import android.graphics.*;
import android.view.*;
import android.view.WindowManager.*;
import ru.net.serbis.cputemp.*;
import ru.net.serbis.cputemp.tool.*;

public class Position
{
    private WindowManager.LayoutParams params;
    private int x;
    private int y;
    private float touchX;
    private float touchY;

    public Position(Context context)
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
    }

    private int getDefaultX(Context context)
    {
        WindowManager manager = UITools.getService(context, Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x / 2;
    }

    public WindowManager.LayoutParams getParams()
    {
        return params;
    }

    public void onDowun(MotionEvent event)
    {
        x = params.x;
        y = params.y;
        touchX = event.getRawX();
        touchY = event.getRawY();
    }
    
    public void onMove(Context context, MotionEvent event)
    {
        params.x = x + (int) (event.getRawX() - touchX);
        params.y = y + (int) (event.getRawY() - touchY);
        
        Params.writer(context)
            .putInt(Constants.FLOAT_X, params.x)
            .putInt(Constants.FLOAT_Y, params.y)
            .commit();
    }
}
