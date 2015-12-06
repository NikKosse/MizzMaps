package com.example.derek.teamb;

/**
 * Created by Derek on 11/28/2015.
 */

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

import com.Models.Node;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.List;


public class PinView extends SubsamplingScaleImageView {


    private PointF sPin;
    private PointF rPin;
    private Bitmap pinRed;
    private Bitmap pinBlue;
    private int strokeWidth;
    private List<Node> listPath;

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

    public List<Node> getNodes(){
        return listPath;
    }

    public void setNodes(List<Node> listPath){
        this.listPath = listPath;
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

    private void drawGround(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(Color.RED);

        int i = 0;
        int k = 0;
        int it = 0;

        if (listPath != null) {
            for (Node element : listPath) {

                i++;
            }
        }

        if (listPath != null) {
            PointF[] vLine = new PointF[100];


            for (int j = 0; j < i; j++) {

                if (listPath.get(j).getFloor() == 0) {
                    vLine[j] = sourceToViewCoord(new PointF(listPath.get(j).getxNodeCoord(), listPath.get(j).getyNodeCoord()));
                    it++;
                }

            }

            //float x[] = {vLine[0].x, vLine[0].y, vLine[1].x, vLine[1].y};

            float[] y = new float[100];

            double factoral;
            if (i < 4) {
                factoral = 2.5;
            } else if (i < 6) {
                factoral = 3;
            } else {
                factoral = 3.5;
            }

            for (int j = 0; j < (it - 1) * factoral; j = j + 4) {
                y[j] = vLine[k].x;
                y[j + 1] = vLine[k].y;
                y[j + 2] = vLine[k + 1].x;
                y[j + 3] = vLine[k + 1].y;
                k++;
            }


            //for(int j = 0; j < i; j++){

            canvas.drawLines(y, paint);
        }
    }
    private void drawFirst(Canvas canvas){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(strokeWidth );
        paint.setColor(Color.RED);

        int i=0;
        int k=0;
        int it=0;

        if(listPath != null) {
            for (Node element : listPath) {

                i++;
            }
        }

        if(listPath != null) {
            PointF[] vLine = new PointF[100];


            for(int j=0; j < i; j++) {

                if (listPath.get(j).getFloor() == 0){
                    vLine[j] = sourceToViewCoord(new PointF(listPath.get(j).getxNodeCoord(), listPath.get(j).getyNodeCoord()));
                    it++;
                }

            }

            //float x[] = {vLine[0].x, vLine[0].y, vLine[1].x, vLine[1].y};

            float[] y = new float[100];

            double factoral;
            if(i<4){
                factoral = 2.5;
            }else if (i<6){
                factoral = 3;
            }else{
                factoral = 3.5;
            }

            for (int j=0; j <(it-1)* factoral; j=j+4) {
                y[j]=vLine[k].x;
                y[j+1]=vLine[k].y;
                y[j+2]=vLine[k+1].x;
                y[j+3]=vLine[k+1].y;
                k++;
            }



            //for(int j = 0; j < i; j++){

            canvas.drawLines(y, paint );
        }


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

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(strokeWidth );
        paint.setColor(Color.RED);

        drawGround(canvas);

        paint.setStrokeWidth(strokeWidth);
        paint.setColor(Color.argb(255, 51, 181, 229));


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