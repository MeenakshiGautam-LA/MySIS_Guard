//package com.sisindia.guardattendance.activity;
//
//import android.content.res.Resources;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.PointF;
//import android.graphics.Rect;
//import android.graphics.RectF;
//
//import androidx.annotation.ColorInt;
//import androidx.annotation.NonNull;
//
//import uk.co.samuelwall.materialtaptargetprompt.extras.PromptBackground;
//import uk.co.samuelwall.materialtaptargetprompt.extras.PromptOptions;
//import uk.co.samuelwall.materialtaptargetprompt.extras.PromptUtils;
//
//public class CirclePrompt extends PromptBackground
//{
//    RectF mBounds, mBaseBounds;
//    Paint mPaint;
//    int mBaseColourAlpha;
//    float mRx, mRy;
//    PointF mFocalCentre;
//
//    /**
//     * Constructor.
//     */
//    public RectanglePromptBackground()
//    {
//        mPaint = new Paint();
//        mPaint.setAntiAlias(true);
//        mBounds = new RectF();
//        mBaseBounds = new RectF();
//        mFocalCentre = new PointF();
//        final float density = Resources.getSystem().getDisplayMetrics().density;
//        mRx = mRy = 2 * density;
//    }
//
//    /**
//     * Set the radius for the rectangle corners.
//     *
//     * @param rx The x-radius of the oval used to round the corners
//     * @param ry The y-radius of the oval used to round the corners
//     * @return This prompt background
//     */
//    @NonNull
//    public RectanglePromptBackground setCornerRadius(final float rx, final float ry)
//    {
//        mRx = rx;
//        mRy = ry;
//        return this;
//    }
//
//    @Override
//    public void setColour(@ColorInt int colour)
//    {
//        mPaint.setColor(colour);
//        mBaseColourAlpha = Color.alpha(colour);
////        mPaint.setAlpha(mBaseColourAlpha);
//    }
//
//    @Override
//    public void prepare(@NonNull final PromptOptions options, final boolean clipToBounds, @NonNull Rect clipBounds)
//    {
//        final RectF focalBounds = options.getPromptFocal().getBounds();
//        final RectF textBounds = options.getPromptText().getBounds();
//        final float textPadding = options.getTextPadding();
//        float x1, x2, y1, y2;
//        if (textBounds.top < focalBounds.top)
//        {
//            y1 = textBounds.top - textPadding;
//            y2 = focalBounds.bottom + textPadding;
//        }
//        else
//        {
//            y1 = focalBounds.top - textPadding;
//            y2 = textBounds.bottom + textPadding;
//        }
//        x1 = Math.min(textBounds.left - textPadding,
//                focalBounds.left - textPadding);
//        x2 = Math.max(textBounds.right + textPadding,
//                focalBounds.right + textPadding);
//        mBaseBounds.set(x1, y1, x2, y2);
//        mFocalCentre.x = focalBounds.centerX();
//        mFocalCentre.y = focalBounds.centerY();
//    }
//
//    @Override
//    public void update(@NonNull final PromptOptions prompt, float revealModifier,
//                       float alphaModifier)
//    {
//        mPaint.setAlpha((int) (mBaseColourAlpha * alphaModifier));
//        PromptUtils.scale(mFocalCentre, mBaseBounds, mBounds, revealModifier, false);
//    }
//
//    @Override
//    public void draw(@NonNull Canvas canvas)
//    {
//        canvas.drawCircle( mRx, mRy,4.f, mPaint);
//    }
//
//    @Override
//    public boolean contains(float x, float y)
//    {
//        return mBounds.contains(x, y);
//    }
//}
