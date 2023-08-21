package com.whatsweb.whatswebscanner.gbwhats.gb_customview;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.whatsweb.whatswebscanner.gbwhats.R;

public class CustomRadioButtonView extends RadioButton {
    public CustomRadioButtonView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }

    public CustomRadioButtonView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    public CustomRadioButtonView(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.CustomTextView);
            String string = obtainStyledAttributes.getString(0);
            if (string != null) {
                AssetManager assets = getContext().getAssets();
                setTypeface(Typeface.createFromAsset(assets, "fonts/" + string));
            }
            obtainStyledAttributes.recycle();
        }
    }
}
