package com.whatsweb.whatswebscanner.gbwhats.gb_customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

public class ScalableImageView extends ImageView {
    boolean adjustViewBounds;

    public ScalableImageView(Context context) {
        super(context);
    }

    public ScalableImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ScalableImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setAdjustViewBounds(boolean z) {
        this.adjustViewBounds = z;
        super.setAdjustViewBounds(z);
    }

    public void onMeasure(int i, int i2) {
        try {
            Drawable drawable = getDrawable();
            if (drawable == null) {
                setMeasuredDimension(0, 0);
                return;
            }
            float intrinsicWidth = ((float) drawable.getIntrinsicWidth()) / ((float) drawable.getIntrinsicHeight());
            if (intrinsicWidth >= ((float) MeasureSpec.getSize(i)) / ((float) MeasureSpec.getSize(i2))) {
                int size = MeasureSpec.getSize(i);
                setMeasuredDimension(size, (int) (((float) size) / intrinsicWidth));
                return;
            }
            int size2 = MeasureSpec.getSize(i2);
            int i3 = (int) (((float) size2) * intrinsicWidth);
            Log.d("TAG", "onMeasure23:12 " + i3 + "---" + size2);
            setMeasuredDimension(i3, size2);
        } catch (Exception unused) {
            super.onMeasure(i, i2);
        }
    }

    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        Log.d("TAG", "onSizeChanged: " + i + "**" + i2 + "**" + i3 + "**" + i4);
    }

    private boolean isInScrollingContainer() {
        ViewParent parent = getParent();
        while (parent != null && (parent instanceof ViewGroup)) {
            if (((ViewGroup) parent).shouldDelayChildPressedState()) {
                return true;
            }
            parent = parent.getParent();
        }
        return false;
    }
}
