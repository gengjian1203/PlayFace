package com.mbpr.gengjian.playface;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.faceplusplus.api.FaceDetecter.Face;

/**
 * Created by gengjian on 16/1/23.
 */
public class FaceMask extends View {
    private Paint localPaint = null;
    private Face[] faceinfos = null;
    private RectF rect = null;
    private Bitmap m_bmpBackGroup = null;

    public FaceMask(Context context, AttributeSet atti) {
        super(context, atti);
        rect = new RectF();
        localPaint = new Paint();
        localPaint.setColor(0xff00b4ff);
        localPaint.setStrokeWidth(5);
        localPaint.setStyle(Paint.Style.STROKE);
    }

    public void setFaceInfo(Bitmap bmp, Face[] faceinfos)
    {
        m_bmpBackGroup = bmp;
        this.faceinfos = faceinfos;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (m_bmpBackGroup == null) {
            Log.e("eeee", "nulll null");
            return;
        }
        canvas.drawBitmap(m_bmpBackGroup, 0, 0, null);
        if (faceinfos == null) {
            return;
        }
        for (Face localFaceInfo : faceinfos) {
            rect.set(getWidth() * localFaceInfo.left, getHeight() * localFaceInfo.top, getWidth() * localFaceInfo.right, getHeight() * localFaceInfo.bottom);
            canvas.drawRect(rect, localPaint);
        }
    }
}
