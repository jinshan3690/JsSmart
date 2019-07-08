package com.js.smart.common.util;

import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;

public class GlideUtil {
    
    public static void autoWidth(ImageView imageView, Drawable resource){
        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
        float scale = (float) vw / (float) resource.getIntrinsicWidth();
        int vh = Math.round(resource.getIntrinsicHeight() * scale);
        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
        imageView.setLayoutParams(params);
        imageView.setImageDrawable(resource);
    }
    
}
