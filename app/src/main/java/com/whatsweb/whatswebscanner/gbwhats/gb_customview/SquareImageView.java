package com.whatsweb.whatswebscanner.gbwhats.gb_customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquareImageView extends ImageView {
    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SquareImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void onMeasure(int i, int i2) {
        int mode = MeasureSpec.getMode(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        if (mode == 1073741824 && mode2 != 1073741824) {
            setMeasuredDimension(size, size);
        } else if (mode2 != 1073741824 || mode == 1073741824) {
            super.onMeasure(i, i2);
        } else {
            setMeasuredDimension(size2, size2);
        }
    }
}
