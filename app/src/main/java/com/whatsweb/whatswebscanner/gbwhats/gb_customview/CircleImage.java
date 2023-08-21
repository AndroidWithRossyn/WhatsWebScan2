package com.whatsweb.whatswebscanner.gbwhats.gb_customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.core.view.ViewCompat;

import com.whatsweb.whatswebscanner.gbwhats.R;

public class CircleImage extends ImageView {
    public static final int[] f16254a = {R.attr.civ_border_color, R.attr.civ_border_overlay, R.attr.civ_border_width, R.attr.civ_fill_color};
    private static final Bitmap.Config f16255b = Bitmap.Config.ARGB_8888;
    private static final ScaleType f16256c = ScaleType.CENTER_CROP;
    private Bitmap f16257d;
    private int f16258e;
    private final Paint f16259f;
    private BitmapShader f16260g;
    private int f16261h;
    private int f16262i;
    private boolean f16263j;
    private final Paint f16264k;
    private float f16265l;
    private final RectF f16266m;
    private int f16267n;
    private ColorFilter f16268o;
    private boolean f16269p;
    private float f16270q;
    private final RectF f16271r;
    private int f16272s;
    private final Paint f16273t;
    private boolean f16274u;
    private boolean f16275v;
    private final Matrix f16276w;

    public CircleImage(Context context) {
        super(context);
        this.f16271r = new RectF();
        this.f16266m = new RectF();
        this.f16276w = new Matrix();
        this.f16259f = new Paint();
        this.f16264k = new Paint();
        this.f16273t = new Paint();
        this.f16262i = ViewCompat.MEASURED_STATE_MASK;
        this.f16267n = 0;
        this.f16272s = 0;
        m19781a();
    }

    public CircleImage(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CircleImage(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f16271r = new RectF();
        this.f16266m = new RectF();
        this.f16276w = new Matrix();
        this.f16259f = new Paint();
        this.f16264k = new Paint();
        this.f16273t = new Paint();
        this.f16262i = ViewCompat.MEASURED_STATE_MASK;
        this.f16267n = 0;
        this.f16272s = 0;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, f16254a, i, 0);
        this.f16267n = obtainStyledAttributes.getDimensionPixelSize(2, 0);
        this.f16262i = obtainStyledAttributes.getColor(0, ViewCompat.MEASURED_STATE_MASK);
        this.f16263j = obtainStyledAttributes.getBoolean(1, false);
        this.f16272s = obtainStyledAttributes.getColor(3, 0);
        obtainStyledAttributes.recycle();
        m19781a();
    }

    private Bitmap m19780a(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            Bitmap createBitmap = drawable instanceof ColorDrawable ? Bitmap.createBitmap(2, 2, f16255b) : Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), f16255b);
            Canvas canvas = new Canvas(createBitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return createBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void m19781a() {
        super.setScaleType(f16256c);
        this.f16274u = true;
        if (this.f16275v) {
            m19784d();
            this.f16275v = false;
        }
    }

    private void m19782b() {
        Paint paint = this.f16259f;
        if (paint != null) {
            paint.setColorFilter(this.f16268o);
        }
    }

    private void m19783c() {
        if (this.f16269p) {
            this.f16257d = null;
        } else {
            this.f16257d = m19780a(getDrawable());
        }
        m19784d();
    }

    private void m19784d() {
        int i;
        if (!this.f16274u) {
            this.f16275v = true;
        } else if (getWidth() != 0 || getHeight() != 0) {
            if (this.f16257d == null) {
                invalidate();
                return;
            }
            this.f16260g = new BitmapShader(this.f16257d, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            this.f16259f.setAntiAlias(true);
            this.f16259f.setShader(this.f16260g);
            this.f16264k.setStyle(Paint.Style.STROKE);
            this.f16264k.setAntiAlias(true);
            this.f16264k.setColor(this.f16262i);
            this.f16264k.setStrokeWidth((float) this.f16267n);
            this.f16273t.setStyle(Paint.Style.FILL);
            this.f16273t.setAntiAlias(true);
            this.f16273t.setColor(this.f16272s);
            this.f16258e = this.f16257d.getHeight();
            this.f16261h = this.f16257d.getWidth();
            this.f16266m.set(m19785e());
            this.f16265l = Math.min((this.f16266m.height() - ((float) this.f16267n)) / 2.0f, (this.f16266m.width() - ((float) this.f16267n)) / 2.0f);
            this.f16271r.set(this.f16266m);
            if (!this.f16263j && (i = this.f16267n) > 0) {
                this.f16271r.inset(((float) i) - 1.0f, ((float) i) - 1.0f);
            }
            this.f16270q = Math.min(this.f16271r.height() / 2.0f, this.f16271r.width() / 2.0f);
            m19782b();
            m19786f();
            invalidate();
        }
    }

    private RectF m19785e() {
        int width = (getWidth() - getPaddingLeft()) - getPaddingRight();
        int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
        int min = Math.min(width, height);
        float paddingLeft = (((float) (width - min)) / 2.0f) + ((float) getPaddingLeft());
        float paddingTop = (((float) (height - min)) / 2.0f) + ((float) getPaddingTop());
        float f = (float) min;
        return new RectF(paddingLeft, paddingTop, f + paddingLeft, f + paddingTop);
    }

    private void m19786f() {
        float f;
        float f2;
        this.f16276w.set(null);
        float f3 = 0.0f;
        if (((float) this.f16261h) * this.f16271r.height() > this.f16271r.width() * ((float) this.f16258e)) {
            f2 = this.f16271r.height() / ((float) this.f16258e);
            f3 = (this.f16271r.width() - (((float) this.f16261h) * f2)) * 0.5f;
            f = 0.0f;
        } else {
            f2 = this.f16271r.width() / ((float) this.f16261h);
            f = (this.f16271r.height() - (((float) this.f16258e) * f2)) * 0.5f;
        }
        this.f16276w.setScale(f2, f2);
        this.f16276w.postTranslate(((float) ((int) (f3 + 0.5f))) + this.f16271r.left, ((float) ((int) (f + 0.5f))) + this.f16271r.top);
        this.f16260g.setLocalMatrix(this.f16276w);
    }

    public int getBorderColor() {
        return this.f16262i;
    }

    public int getBorderWidth() {
        return this.f16267n;
    }

    public ColorFilter getColorFilter() {
        return this.f16268o;
    }

    @Deprecated
    public int getFillColor() {
        return this.f16272s;
    }

    public ScaleType getScaleType() {
        return f16256c;
    }

    public void onDraw(Canvas canvas) {
        if (this.f16269p) {
            super.onDraw(canvas);
        } else if (this.f16257d != null) {
            if (this.f16272s != 0) {
                canvas.drawCircle(this.f16271r.centerX(), this.f16271r.centerY(), this.f16270q, this.f16273t);
            }
            canvas.drawCircle(this.f16271r.centerX(), this.f16271r.centerY(), this.f16270q, this.f16259f);
            if (this.f16267n > 0) {
                canvas.drawCircle(this.f16266m.centerX(), this.f16266m.centerY(), this.f16265l, this.f16264k);
            }
        }
    }

    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        m19784d();
    }

    public void setAdjustViewBounds(boolean z) {
        if (z) {
            throw new IllegalArgumentException("adjustViewBounds not supported.");
        }
    }

    public void setBorderColor(int i) {
        if (i != this.f16262i) {
            this.f16262i = i;
            this.f16264k.setColor(i);
            invalidate();
        }
    }

    @Deprecated
    public void setBorderColorResource(int i) {
        setBorderColor(getContext().getResources().getColor(i));
    }

    public void setBorderOverlay(boolean z) {
        if (z != this.f16263j) {
            this.f16263j = z;
            m19784d();
        }
    }

    public void setBorderWidth(int i) {
        if (i != this.f16267n) {
            this.f16267n = i;
            m19784d();
        }
    }

    @Override // android.widget.ImageView
    public void setColorFilter(ColorFilter colorFilter) {
        if (colorFilter != this.f16268o) {
            this.f16268o = colorFilter;
            m19782b();
            invalidate();
        }
    }

    public void setDisableCircularTransformation(boolean z) {
        if (this.f16269p != z) {
            this.f16269p = z;
            m19783c();
        }
    }

    @Deprecated
    public void setFillColor(int i) {
        if (i != this.f16272s) {
            this.f16272s = i;
            this.f16273t.setColor(i);
            invalidate();
        }
    }

    @Deprecated
    public void setFillColorResource(int i) {
        setFillColor(getContext().getResources().getColor(i));
    }

    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        m19783c();
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        m19783c();
    }

    public void setImageResource(int i) {
        super.setImageResource(i);
        m19783c();
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        m19783c();
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        super.setPadding(i, i2, i3, i4);
        m19784d();
    }

    public void setPaddingRelative(int i, int i2, int i3, int i4) {
        super.setPaddingRelative(i, i2, i3, i4);
        m19784d();
    }

    public void setScaleType(ScaleType scaleType) {
        if (scaleType != f16256c) {
            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType));
        }
    }
}
