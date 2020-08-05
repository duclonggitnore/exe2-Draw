package tdc.edu.vn.smile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class SmileyView extends View {
    private Paint mCirclePaint;
    private float mCenterX;
    private float mCenterY;
    private float mRadius;
    Path path = new Path();
    Paint paint = new Paint();

    public SmileyView(Context context) {
        this(context, null);
    }

    public SmileyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmileyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaints();
    }

    private void initPaints() {

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(16 * getResources().getDisplayMetrics().density);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(w, h);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mCirclePaint);
        canvas.drawPath(path,paint);

        Path path = createHeartPath(canvas.getClipBounds().right,canvas.getClipBounds().bottom);
        canvas.drawPath(path,paint);

        super.onDraw(canvas);

    }

    private Path createHeartPath(int width, int height) {
        Path path = new Path();
        float pX = width/2f;
        float pY = (height/100f)*33.33f;

        float x1 = (width/100f)*50;
        float y1 = (height/100f)*5;
        float x2 = (width/100f)*90;
        float y2 = (height/100f)*10;
        float x3 = (width/100f)*90;
        float y3 = (height/100f)*33.33f;

        path.moveTo(pX,pY);
        path.cubicTo(x1, y1, x2, y2, x3, y3);
        path.moveTo(x3,pY);

        x1 = (width/100f)*90;
        y1 = (height/100f)*55f;
        x2 = (width/100f)*65;
        y2 = (height/100f)*60f;
        x3 = (width/100f)*50;
        y3 = (height/100f)*90f;

        path.cubicTo(x1, y1, x2, y2, x3, y3);
        path.lineTo(pX,pY);


        x1 = (width/100f)*50;
        y1 = (height/100f)*5;
        x2 = (width/100f)*10;
        y2 = (height/100f)*10;
        x3 = (width/100f)*10;
        y3 = (height/100f)*33.33f;

        path.moveTo(pX,pY);
        path.cubicTo(x1, y1, x2, y2, x3, y3);
        path.moveTo(x3,pY);

        x1 = (width/100f)*10;
        y1 = (height/100f)*55f;
        x2 = (width/100f)*35f;
        y2 = (height/100f)*60f;
        x3 = (width/100f)*50f;
        y3 = (height/100f)*90f;

        path.cubicTo(x1, y1, x2, y2, x3, y3);
        path.lineTo(pX,pY);

        path.moveTo(x3,y3);
        path.close();

        return path;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenterX = w / 2f;
        mCenterY = h / 2f;
        mRadius = Math.min(w, h) / 2f;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX, eventY);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(eventX, eventY);
                break;
            case MotionEvent.ACTION_UP:
                path.addCircle(eventX, eventY, 10, Path.Direction.CW);
                break;
            case MotionEvent.ACTION_CANCEL: {
                path.addCircle(eventX, eventY, 10, Path.Direction.CW);
                break;
            }
            default:
                return false;
        }
        invalidate();
        return true;
    }
}
