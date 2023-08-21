package com.whatsweb.whatswebscanner.gbwhats.gb_arclayout;

import android.graphics.Paint;
import android.graphics.Path;

import androidx.core.view.ViewCompat;

public class ClipPathManager implements ClipManager {
    private ClipPathCreator createClipPath;
    private final Paint paint;
    protected final Path path = new Path();

    public interface ClipPathCreator {
        Path createClipPath(int i, int i2);

        boolean requiresBitmap();
    }

    public ClipPathManager() {
        Paint paint2 = new Paint(1);
        this.paint = paint2;
        this.createClipPath = null;
        paint2.setColor(ViewCompat.MEASURED_STATE_MASK);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setAntiAlias(true);
        paint2.setStrokeWidth(1.0f);
    }

    @Override // com.whatsweb.whatswebscanner.gbwhats.arclayout.ClipManager
    public Paint getPaint() {
        return this.paint;
    }

    @Override // com.whatsweb.whatswebscanner.gbwhats.arclayout.ClipManager
    public boolean requiresBitmap() {
        ClipPathCreator clipPathCreator = this.createClipPath;
        return clipPathCreator != null && clipPathCreator.requiresBitmap();
    }

    
    public final Path createClipPath(int i, int i2) {
        ClipPathCreator clipPathCreator = this.createClipPath;
        if (clipPathCreator != null) {
            return clipPathCreator.createClipPath(i, i2);
        }
        return null;
    }

    public void setClipPathCreator(ClipPathCreator clipPathCreator) {
        this.createClipPath = clipPathCreator;
    }

    @Override // com.whatsweb.whatswebscanner.gbwhats.arclayout.ClipManager
    public Path createMask(int i, int i2) {
        return this.path;
    }

    @Override // com.whatsweb.whatswebscanner.gbwhats.arclayout.ClipManager
    public Path getShadowConvexPath() {
        return this.path;
    }

    @Override // com.whatsweb.whatswebscanner.gbwhats.arclayout.ClipManager
    public void setupClipLayout(int i, int i2) {
        this.path.reset();
        Path createClipPath2 = createClipPath(i, i2);
        if (createClipPath2 != null) {
            this.path.set(createClipPath2);
        }
    }
}
