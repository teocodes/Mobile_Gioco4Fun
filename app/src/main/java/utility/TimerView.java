package utility;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.text.TextPaint;
import android.view.View;

/**
 * Created by matteo on 10/03/2016.
 */
public class TimerView extends View {
    private final Paint backgroundPaint;
    private final Paint progressPaint;
    private final Paint textPaint;

    private long startTime;
    private long currentTime;
    private long maxTime;

    private long progressMillisecond;
    private double progress;

    private RectF circleBounds;
    private float radius;
    private float handleRadius;
    private float textHeight;
    private float textOffset;

    private final Handler viewHandler;
    private final Runnable updateView;

    private boolean flag;


    public TimerView(Context context) {
        super(context);

        // used to fit the circle into
        circleBounds = new RectF();

        // size of circle and handle
        radius = 200;
        handleRadius = 10;

        // limit the counter to go up to maxTime ms
        maxTime = 5000;

        // start and current time
        startTime = System.currentTimeMillis();
        currentTime = startTime;


        // the style of the background
        backgroundPaint = new Paint();
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStrokeWidth(10);
        backgroundPaint.setStrokeCap(Paint.Cap.SQUARE);
        backgroundPaint.setColor(Color.parseColor("#4D4D4D"));  // dark gray

        // the style of the 'progress'
        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setAntiAlias(true);
        progressPaint.setStrokeWidth(10);
        progressPaint.setStrokeCap(Paint.Cap.SQUARE);
        progressPaint.setColor(Color.parseColor("#00A9FF"));    // light blue

        // the style for the text in the middle
        textPaint = new TextPaint();
        textPaint.setTextSize(radius / 2);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);

        // text attributes
        textHeight = textPaint.descent() - textPaint.ascent();
        textOffset = (textHeight / 2) - textPaint.descent();


        // This will ensure the animation will run periodically
        viewHandler = new Handler();
        updateView = new Runnable() {
            @Override
            public void run() {
                // update current time
                currentTime = System.currentTimeMillis();

                // get elapsed time in milliseconds and clamp between <0, maxTime>
                progressMillisecond = maxTime -(currentTime - startTime) % maxTime;//togliere (maxtime -) x farlo crescente

                // get current progress on a range <0, 1>
                progress = (double) progressMillisecond / maxTime;

                TimerView.this.invalidate();
                if ((currentTime - startTime) < maxTime)
                    viewHandler.postDelayed(updateView, 1000 / 60);
                else{
                    progressMillisecond = 0;
                    progress = 0;
                    viewHandler.postDelayed(updateView, 1000 / 60);
                }

            }
        };
        viewHandler.post(updateView);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // get the center of the view
        float centerWidth = canvas.getWidth() / 2;
        float centerHeight = canvas.getHeight() / 2;


        // set bound of our circle in the middle of the view
        circleBounds.set(centerWidth - radius,
                centerHeight - radius,
                centerWidth + radius,
                centerHeight + radius);


        // draw background circle
        canvas.drawCircle(centerWidth, centerHeight, radius, backgroundPaint);

        // we want to start at -90°, 0° is pointing to the right
        canvas.drawArc(circleBounds, -90, (float) (progress * 360), false, progressPaint);

        // display text inside the circle
        canvas.drawText((double) (progressMillisecond / 100) / 10 + "s",
                centerWidth,
                centerHeight + textOffset,
                textPaint);

        // draw handle or the circle
        canvas.drawCircle((float) (centerWidth + (Math.sin(progress * 2 * Math.PI) * radius)),
                (float) (centerHeight - (Math.cos(progress * 2 * Math.PI) * radius)),
                handleRadius,
                progressPaint);
    }

    public long getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(long maxTime) {
        this.maxTime = maxTime;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getTextHeight() {
        return textHeight;
    }

    public void setTextHeight(float textHeight) {
        this.textHeight = textHeight;
    }

    public float getTextOffset() {
        return textOffset;
    }

    public void setTextOffset(float textOffset) {
        this.textOffset = textOffset;
    }

}


