package com.example.derek.teamb;

/**
 * Created by Derek on 11/28/2015.
 */

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;


public class PinView extends SubsamplingScaleImageView {


    private PointF sPin;
    private PointF rPin;
    private Bitmap pinRed;
    private Bitmap pinBlue;
    private int strokeWidth;

    public PinView(Context context) {
        this(context, null);
    }

    public PinView(Context context, AttributeSet attr) {
        super(context, attr);
        initialise();
    }

    public void setRedPin(PointF rPin) {
        this.rPin = rPin;
        initialise();
        invalidate();
    }

    public void setPinBlue(PointF sPin) {
        this.sPin = sPin;
        initialise();
        invalidate();
    }

    public PointF getPinBlue() {
        return sPin;
    }

    public PointF getPinRed() {
        return rPin;
    }

    private void initialise() {
        float density = getResources().getDisplayMetrics().densityDpi;
        pinBlue = BitmapFactory.decodeResource(this.getResources(), R.drawable.pushpin_blue);
        float w = (density / 420f) * pinBlue.getWidth();
        float h = (density / 420f) * pinBlue.getHeight();
        strokeWidth = (int) (density / 120f);
        pinBlue = Bitmap.createScaledBitmap(pinBlue, (int) w / 3, (int) h / 3, true);
        pinRed = BitmapFactory.decodeResource(this.getResources(), R.drawable.pushpin_red);
        strokeWidth = (int) (density / 120f);
        pinRed = Bitmap.createScaledBitmap(pinRed, (int) w / 3, (int) h / 3, true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Don't draw pinBlue before image is ready so it doesn't move around during setup.
        if (!isReady()) {
            return;
        }

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        PointF sCenter = new PointF(getSWidth() / 2, getSHeight() / 2);
        PointF vCenter = sourceToViewCoord(sCenter);


        PointF[] vLine = new PointF[]{
                sourceToViewCoord(new PointF(1718f, 581f)), sourceToViewCoord(new PointF(1290f, 741f))
        };


        float lY = vLine[0].y;
        float lX = vLine[0].x;
        float lY2 = vLine[1].y;
        float lX2 = vLine[1].x;
        float x[] = {vCenter.x, vCenter.y, lX, lY, lX, lY, lX2, lY2};

        /*
        paint.setAntiAlias(true);
        paint.setStyle(Style.STROKE);
        paint.setStrokeCap(Cap.ROUND);
        paint.setStrokeWidth(strokeWidth * 2);
        paint.setColor(Color.BLACK);
        canvas.drawLines(x,paint);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(Color.argb(255, 51, 181, 229));

*/
        if (sPin != null && pinBlue != null) {
            PointF bPin = sourceToViewCoord(sPin);

            float bX = bPin.x - (pinBlue.getWidth() / 2);
            float bY = bPin.y - pinBlue.getHeight();

            canvas.drawBitmap(pinBlue, bX, bY, paint);

        }

        if (rPin != null && pinRed != null) {
            PointF redPin = sourceToViewCoord(rPin);
            float rX = redPin.x - (pinRed.getWidth() / 2);
            float rY = redPin.y - pinRed.getHeight();
            canvas.drawBitmap(pinRed, rX, rY, paint);
        }
    }

}