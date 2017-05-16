package com.gufran.wizardcheckpointapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Gufran Khurshid
 * @version 1.0
 * @since 5/16/17
 */

public class WizardLineView extends View {
    private int progressColor, trackColor;
    private int progress = 0;
    private int height, width;
    private Paint progressPaint;
    private Paint trackPaint;
    private RectF progressLineRect = new RectF();
    private RectF trackLineRect = new RectF();
    private final int MAX = 100;
    private Handler progressHandler;
    private Runnable progressRunnable;
    private final int PROGRESS_STEP = 2;
    private final int PROGRESS_DELAY = 15;

    public WizardLineView(Context context) {
        this(context, null);
    }

    public WizardLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WizardLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WizardLineView, 0, defStyleAttr);
        progressColor = typedArray
                .getColor(R.styleable.WizardLineView_progressColor, 0);
        trackColor = typedArray
                .getColor(R.styleable.WizardLineView_trackColor, 0);
        setProgress(typedArray.getInt(R.styleable.WizardLineView_lineProgress, 0));
        typedArray.recycle();
        initPainters();
    }

    protected void initPainters() {
        progressPaint = new Paint();
        progressPaint.setColor(progressColor);
        progressPaint.setStyle(Paint.Style.FILL);
        progressPaint.setAntiAlias(true);

        trackPaint = new Paint();
        trackPaint.setColor(trackColor);
        trackPaint.setStyle(Paint.Style.FILL);
        trackPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTrack(canvas);
        drawProgress(canvas);
    }

    private void drawTrack(Canvas canvas) {
        trackLineRect.set(0,
                0,
                width,
                height);
        canvas.drawRect(trackLineRect, trackPaint);
    }

    private void drawProgress(Canvas canvas) {
        int finish_width = (progress * width) / MAX;
        progressLineRect.set(0,
                0,
                finish_width,
                height);
        canvas.drawRect(progressLineRect, progressPaint);
    }

    public void startProgress(final int targetedProgressValue) {
        progressHandler = new Handler();
        progressRunnable = new Runnable() {
            @Override
            public void run() {
                if (progress >= targetedProgressValue) {
                    progressHandler.removeCallbacks(progressRunnable);
                } else {
                    setProgress(progress);
                    progress = progress + PROGRESS_STEP;
                    progressHandler.postDelayed(this, PROGRESS_DELAY);
                }
            }
        };
        progressHandler.post(progressRunnable);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        setMeasuredDimension(width, height);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        if (this.progress > MAX) {
            this.progress %= MAX;
        }
        invalidate();
    }


}
