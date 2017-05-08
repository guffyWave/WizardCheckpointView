package com.gufran.wizardcheckpointapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Description ...
 *
 * @author Gufran Khurshid
 * @version 1.0
 * @since 5/4/17
 */

public class WizardCheckpointView extends View {

    public final static int UNSELECTED_INCOMPLETE = 0;
    public final static int UNSELECTED_COMPLETE = 1;
    public final static int SELECTED_INCOMPLETE = 2;
    public final static int SELECTED_COMPLETE = 3;

    private int checkPointPrimaryColor, checkPointDisabledColor;
    private int state;
    private Drawable icon_complete_selected, icon_complete_unselected, icon_incomplete_selected, icon_incomplete_unselected;
    private int selectedStrokeWidth, unSelectedStrokeWidth;
    private int height, width;
    private int progress = 0;
    private int max = 100;

    private Paint finishedPaint;
    private Paint unfinishedPaint;
    private RectF finishedOuterRect = new RectF();
    private RectF unfinishedOuterRect = new RectF();

    private Handler progressHandler;
    private Runnable progressRunnable;
    final private int PROGRESS_STEP = 2;
    final private int PROGRESS_DELAY = 15;

    public WizardCheckpointView(Context context) {
        this(context, null);
    }

    public WizardCheckpointView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WizardCheckpointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WizardCheckpointView, 0, defStyleAttr);
        checkPointPrimaryColor = typedArray
                .getColor(R.styleable.WizardCheckpointView_checkPointPrimaryColor, 0);
        checkPointDisabledColor = typedArray
                .getColor(R.styleable.WizardCheckpointView_checkPointDisabledColor, 0);
        icon_complete_selected = typedArray
                .getDrawable(R.styleable.WizardCheckpointView_icon_complete_selected);
        icon_complete_unselected = typedArray
                .getDrawable(R.styleable.WizardCheckpointView_icon_complete_unselected);
        icon_incomplete_selected = typedArray
                .getDrawable(R.styleable.WizardCheckpointView_icon_incomplete_selected);
        icon_incomplete_unselected = typedArray
                .getDrawable(R.styleable.WizardCheckpointView_icon_incomplete_unselected);
        state = typedArray.getInteger(R.styleable.WizardCheckpointView_state, 0);
        selectedStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.WizardCheckpointView_selectedStrokeWidth, 0);
        unSelectedStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.WizardCheckpointView_unSelectedStrokeWidth, 0);
        setMax(typedArray.getInt(R.styleable.WizardCheckpointView_max, 100));
        setProgress(typedArray.getInt(R.styleable.WizardCheckpointView_progress, 0));
        typedArray.recycle();
        initPainters();
    }

    protected void initPainters() {
        finishedPaint = new Paint();
        finishedPaint.setColor(checkPointPrimaryColor);
        finishedPaint.setStrokeWidth(selectedStrokeWidth);
        finishedPaint.setStyle(Paint.Style.STROKE);
        finishedPaint.setAntiAlias(true);

        unfinishedPaint = new Paint();
        unfinishedPaint.setColor(checkPointDisabledColor);
        unfinishedPaint.setStrokeWidth(unSelectedStrokeWidth);
        unfinishedPaint.setStyle(Paint.Style.STROKE);
        unfinishedPaint.setAntiAlias(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (state) {
            case SELECTED_COMPLETE:
                drawSelectedComplete(canvas);
                break;
            case SELECTED_INCOMPLETE:
                drawSelectedInComplete(canvas);
                break;
            case UNSELECTED_COMPLETE:
                drawUnselectedComplete(canvas);
                break;
            default:
                drawUnselectedInComplete(canvas);
                break;
        }
    }

    public void setState(int state) {
        this.state = state;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        height = View.resolveSize(getDesiredHeight(), heightMeasureSpec);
        width = View.resolveSize(getDesiredWidth(), widthMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private void drawSelectedComplete(Canvas canvas) {
        setProgress(max);
        unfinishedPaint.setColor(checkPointDisabledColor);
        unfinishedPaint.setStrokeWidth(selectedStrokeWidth);

        finishedPaint.setColor(checkPointPrimaryColor);
        finishedPaint.setStrokeWidth(selectedStrokeWidth);

        drawOutline(canvas);
        drawDrawableAtCenter(canvas, icon_complete_selected, true);
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

    private void drawSelectedInComplete(Canvas canvas) {
        unfinishedPaint.setColor(checkPointDisabledColor);
        unfinishedPaint.setStrokeWidth(selectedStrokeWidth);

        finishedPaint.setColor(checkPointPrimaryColor);
        finishedPaint.setStrokeWidth(selectedStrokeWidth);

        drawOutline(canvas);
        drawDrawableAtCenter(canvas, icon_incomplete_selected, true);
    }

    private void drawUnselectedComplete(Canvas canvas) {
        setProgress(max);
        unfinishedPaint.setColor(checkPointDisabledColor);
        unfinishedPaint.setStrokeWidth(unSelectedStrokeWidth);

        finishedPaint.setColor(checkPointPrimaryColor);
        finishedPaint.setStrokeWidth(unSelectedStrokeWidth);

        drawOutline(canvas);
        drawDrawableAtCenter(canvas, icon_complete_unselected, false);
    }

    private void drawUnselectedInComplete(Canvas canvas) {
        unfinishedPaint.setColor(checkPointDisabledColor);
        unfinishedPaint.setStrokeWidth(unSelectedStrokeWidth);

        finishedPaint.setColor(checkPointPrimaryColor);
        finishedPaint.setStrokeWidth(unSelectedStrokeWidth);

        drawOutline(canvas);
        drawDrawableAtCenter(canvas, icon_incomplete_unselected, false);
    }

    private void drawOutline(Canvas canvas) {
        float delta = Math.max(selectedStrokeWidth, unSelectedStrokeWidth) + (float) (0.10 * height);
        finishedOuterRect.set(delta,
                delta,
                width - delta,
                height - delta);
        unfinishedOuterRect.set(delta,
                delta,
                width - delta,
                height - delta);
        canvas.drawArc(finishedOuterRect, -90, getProgressAngle(), false, finishedPaint);
        canvas.drawArc(unfinishedOuterRect, -90 + getProgressAngle(), 360 - getProgressAngle(), false, unfinishedPaint);
    }

    private void drawDrawableAtCenter(Canvas canvas, Drawable drawable, boolean coloredBackground) {

        if (coloredBackground) {
            float bgPadding = 4.3f * selectedStrokeWidth;
            Paint bgPaint = new Paint();
            bgPaint.setColor(checkPointPrimaryColor);
            bgPaint.setStyle(Paint.Style.FILL);
            bgPaint.setAntiAlias(true);

            int cx = (int) width / 2;
            int cy = (int) height / 2;
            canvas.drawCircle(cx, cy, cx - bgPadding, bgPaint);
        }

        int start = (int) width / 2 - (int) drawable.getIntrinsicWidth() / 2 + getPaddingLeft();
        int top = (int) height / 2 - (int) drawable.getIntrinsicHeight() / 2 + getPaddingTop();
        int end = (int) width / 2 + (int) drawable.getIntrinsicWidth() / 2 - getPaddingRight();
        int bottom = (int) height / 2 + (int) drawable.getIntrinsicHeight() / 2 - getPaddingBottom();
        drawable.setBounds(start, top, end, bottom);
        drawable.draw(canvas);
    }

    private float getProgressAngle() {
        return getProgress() / (float) max * 360f;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        if (this.progress > getMax()) {
            this.progress %= getMax();
        }
        invalidate();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        if (max > 0) {
            this.max = max;
            invalidate();
        }
    }

    private int getDesiredHeight() {
        return getHeight() == 0 ? getMinimumHeight() : getHeight();
    }

    private int getDesiredWidth() {
        return getWidth() == 0 ? getMinimumWidth() : getWidth();
    }


}
