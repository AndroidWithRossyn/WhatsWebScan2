package com.whatsweb.whatswebscanner.gbwhats.gb_customview;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.whatsweb.whatswebscanner.gbwhats.R;

public class CustomEditTextView extends EditText {
    public CustomEditTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }

    public CustomEditTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    public CustomEditTextView(Context context) {
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
