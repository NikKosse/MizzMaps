package com.example.derek.teamb;

/**
 * Created by Derek on 11/28/2015.
 */
import android.content.Context;
import android.graphics.*;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.util.AttributeSet;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;


public class PinView extends SubsamplingScaleImageView {

    private PointF sPin;
    private Bitmap pin;
    private int strokeWidth;

    public PinView(Context context) {
        this(context, null);
    }

    public PinView(Context context, AttributeSet attr) {
        super(context, attr);
        initialise();
    }

    public void setPin(PointF sPin) {
        this.sPin = sPin;
        initialise();
        invalidate();
    }

    public PointF getPin() {
        return sPin;
    }

    private void initialise() {
        float density = getResources().getDisplayMetrics().densityDpi;
        pin = BitmapFactory.decodeResource(this.getResources(), R.drawable.pushpin_blue);
        float w = (density/420f) * pin.getWidth();
        float h = (density/420f) * pin.getHeight();
        strokeWidth = (int)(density/120f);
        pin = Bitmap.createScaledBitmap(pin, (int)w/3, (int)h/3, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Don't draw pin before image is ready so it doesn't move around during setup.
        if (!isReady()) {
            return;
        }

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        PointF sCenter = new PointF(getSWidth()/2, getSHeight()/2);
        PointF vCenter = sourceToViewCoord(sCenter);
        float radius = (getScale() * getSWidth()) * 0.25f;
        ;

        PointF[] vLine = new PointF[]{
                sourceToViewCoord(new PointF(1718f,581f)),sourceToViewCoord(new PointF(1290f,741f))
        };


        float lY = vLine[0].y;
        float lX = vLine[0].x;
        float lY2 = vLine[1].y;
        float lX2 = vLine[1].x;
        float x[] = {vCenter.x, vCenter.y, lX, lY, lX, lY, lX2, lY2};

        paint.setAntiAlias(true);
        paint.setStyle(Style.STROKE);
        paint.setStrokeCap(Cap.ROUND);
        paint.setStrokeWidth(strokeWidth * 2);
        paint.setColor(Color.BLACK);
        canvas.drawLines(x,paint);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(Color.argb(255, 51, 181, 229));


        if (sPin != null && pin != null) {
            PointF vPin = sourceToViewCoord(sPin);
            float vX = vPin.x - (pin.getWidth()/2);
            float vY = vPin.y - pin.getHeight();
            canvas.drawBitmap(pin, vX, vY, paint);
        }

    }

}