package com.sisindia.mysis.Camera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class DrawCircle extends View {

    private Paint mCircleYellow;
   // private Paint mCircleGray;

    private float mRadius;
    private RectF mArcBounds = new RectF();
    String circleColor;

    public DrawCircle(Context context, String circleColor) {
        super(context);
        initPaints(circleColor);
    }
    public DrawCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

     //   initPaints();
    }

    public DrawCircle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void initPaints(String circleColor) {
        mCircleYellow = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCircleYellow.setStyle(Paint.Style.FILL);
       // mCircleYellow.setColor(Color.GREEN);
        mCircleYellow.setStyle(Paint.Style.STROKE);
        mCircleYellow.setStrokeWidth(2 * getResources().getDisplayMetrics().density);
        mCircleYellow.setStrokeCap(Paint.Cap.ROUND);
        // mEyeAndMouthPaint.setColor(getResources().getColor(R.color.colorAccent));
        mCircleYellow.setColor(Color.parseColor(circleColor));

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mRadius = Math.min(w, h) / 2f;
        Log.e("mRadius>>>>>",""+mRadius);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);

        int size = Math.min(w, h);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Float drawUpto = 46f;


        float mouthInset = mRadius / 25f;
        mArcBounds.set(mouthInset, mouthInset*2, mRadius * 2-mouthInset, mRadius * 2 -mouthInset);
//        canvas.drawArc(mArcBounds, 0f, 360f, false, mCircleGray);
     //   canvas.drawCircle(75, 75, 100, mCircleYellow);
       canvas.drawArc(mArcBounds, 0f, 360f, true, mCircleYellow);


    }
}
