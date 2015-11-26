package com.innofarm.external;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * User: syc
 * Date: 2015/9/18
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc: 自定义EditText控件
 */
public class MyEditText extends EditText {
    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        Paint paint = new Paint();

        paint.setTextSize(40);
        paint.setColor(Color.GRAY);

        canvas.drawText("牛号:", 10, getHeight() / 2 + 5, paint);

        super.onDraw(canvas);




    }
}
