package com.sisindia.mysis.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;



public class CSDrawable {

    /**
     * create Selector for any


    /**
     * @param imgName for imageName
     * @return drawable for image
     */
    public static Drawable getDrawable(Context context, String imgName) {
        return getDrawable(getDrawableId(context, imgName));
    }

    public static int getDrawableId(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "drawable", context.getPackageName());
    }


    public static GradientDrawable getRoundedCornerDrawable(int colorCode, int strokeColorCode, float[] radii) {
        return getRoundedCornerDrawable(colorCode, strokeColorCode, 2, radii);
    }

    public static GradientDrawable getRoundedCornerDrawable(int colorCode, int strokeColorCode, int strokeWeidth, float[] radii) {
        GradientDrawable gradientDrawable = new GradientDrawable(Orientation.BOTTOM_TOP, new int[]{});
        gradientDrawable.setStroke(strokeWeidth, strokeColorCode);
        gradientDrawable.setColor(colorCode);
        gradientDrawable.setCornerRadii(radii);
        return gradientDrawable;
    }


    public static Drawable getDrawable(int resourceId) {
        return CSUIUtil.getDrawable(resourceId);
    }

}
