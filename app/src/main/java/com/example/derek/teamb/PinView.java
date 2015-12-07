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
    private int floorInt;
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

    public void getFloor(int floor){
        this.floorInt= floor;

    }

    public List<Node> getNodes(){
        return listPath;
    }

    public void setNodes(List<Node> listPath){
        this.listPath = listPath;
    }

    public void drawGround(Canvas canvas){

        Paint paint = new Paint();
        paint.setAntiAlias(true);

      /*  if(listPath != null) {
            for (Node element : listPath) {

            }
        }*/

        int iterate=0;
        try{
        if(listPath != null) {
            PointF[] vLine = new PointF[100];

            for(int i=0; i < listPath.size(); i++){
                if (listPath.get(i).getFloor() == 0) {
                    vLine[iterate] = sourceToViewCoord(new PointF(listPath.get(i).getxNodeCoord(), listPath.get(i).getyNodeCoord()));
                    iterate++;
                }
            }

            int count=0;

            Path path = new Path();
            path.moveTo(vLine[0].x, vLine[0].y);

            for (int i=1; i < iterate; i++) {
                path.lineTo(vLine[i].x, vLine[i].y);
                count++;
            }



            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(strokeWidth);
            paint.setColor(Color.RED);

            canvas.drawPath(path, paint);
            canvas.drawCircle(vLine[count].x, vLine[count].y, 10, paint);
        }
        }catch (NullPointerException e){

        }
    }

    public void drawFirst(Canvas canvas){

        Paint paint = new Paint();
        paint.setAntiAlias(true);

      /*  if(listPath != null) {
            for (Node element : listPath) {

            }
        }*/
        try {

            int iterate = 0;

            if (listPath != null) {
                PointF[] vLine = new PointF[100];

                for (int i = 0; i < listPath.size(); i++) {
                    if (listPath.get(i).getFloor() == 1) {
                        vLine[iterate] = sourceToViewCoord(new PointF(listPath.get(i).getxNodeCoord(), listPath.get(i).getyNodeCoord()));
                        iterate++;
                    }
                }

                int count=0;


                Path path = new Path();
                path.moveTo(vLine[0].x, vLine[0].y);

                for (int i = 1; i < iterate; i++) {
                    path.lineTo(vLine[i].x, vLine[i].y);
                    count++;
                }


                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeCap(Paint.Cap.ROUND);
                paint.setStrokeWidth(strokeWidth);
                paint.setColor(Color.RED);

                canvas.drawPath(path, paint);
                canvas.drawCircle(vLine[count].x, vLine[count].y, 10, paint);
            }
        }catch (NullPointerException e){

    }
    }

    public void drawSecond(Canvas canvas){

        Paint paint = new Paint();
        paint.setAntiAlias(true);

      /*  if(listPath != null) {
            for (Node element : listPath) {

            }
        }*/
        int iterate=0;

        if(listPath != null) {
            PointF[] vLine = new PointF[100];
            try {

                for (int i = 0; i < listPath.size(); i++) {
                    if (listPath.get(i).getFloor() == 2) {
                        vLine[iterate] = sourceToViewCoord(new PointF(listPath.get(i).getxNodeCoord(), listPath.get(i).getyNodeCoord()));
                        iterate++;
                    }
                }
                int count=0;

                Path path = new Path();
                path.moveTo(vLine[0].x, vLine[0].y);

                for (int i = 1; i < iterate; i++) {
                    path.lineTo(vLine[i].x, vLine[i].y);
                    count++;
                }


                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeCap(Paint.Cap.ROUND);
                paint.setStrokeWidth(strokeWidth);
                paint.setColor(Color.RED);

                canvas.drawPath(path, paint);
                canvas.drawCircle(vLine[count].x, vLine[count].y, 10, paint);
            }catch (NullPointerException e){

            }
        }
    }

    public void drawThird(Canvas canvas){

        Paint paint = new Paint();
        paint.setAntiAlias(true);

      /*  if(listPath != null) {
            for (Node element : listPath) {

            }
        }*/
        int iterate=0;

        try {
            if (listPath != null) {
                PointF[] vLine = new PointF[100];

                for (int i = 0; i < listPath.size(); i++) {
                    if (listPath.get(i).getFloor() == 3) {
                        vLine[iterate] = sourceToViewCoord(new PointF(listPath.get(i).getxNodeCoord(), listPath.get(i).getyNodeCoord()));
                        iterate++;
                    }
                }

                int count=0;

                Path path = new Path();
                path.moveTo(vLine[0].x, vLine[0].y);

                for (int i = 1; i < iterate; i++) {
                    path.lineTo(vLine[i].x, vLine[i].y);
                    count++;
                }


                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeCap(Paint.Cap.ROUND);
                paint.setStrokeWidth(strokeWidth);
                paint.setColor(Color.RED);

                canvas.drawPath(path, paint);
                canvas.drawCircle(vLine[count].x, vLine[count].y, 10, paint);
            }
        }catch (NullPointerException e){

    }
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

      /*  if(listPath != null) {
            for (Node element : listPath) {

            }
        }*/

        if(floorInt ==0) {
            drawGround(canvas);
        }else if(floorInt ==1) {
            drawFirst(canvas);
        }else if(floorInt ==2) {
            drawSecond(canvas);
        }else if(floorInt ==3) {
            drawThird(canvas);
        }

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