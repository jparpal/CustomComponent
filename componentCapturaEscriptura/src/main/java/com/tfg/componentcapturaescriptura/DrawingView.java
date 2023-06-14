package com.tfg.componentcapturaescriptura;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class DrawingView extends View {
    private Context context;
    private Path drawPath;
    private Paint drawPaint;
    private Paint canvasPaint;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    private int backgroundColor;
    private List<PointF> points;
    private List<Float> pressures;
    private List<Long> timestamps;
    private List<Float> angles;
    private int inkColor;

    public DrawingView(Context context) {
        super(context);
        this.context = context;
        setupDrawing();
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setupDrawing();
    }

    public DrawingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        setupDrawing();
    }
    private void setupDrawing() {
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(Color.BLACK);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        drawPaint.setStrokeWidth(8f);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
        backgroundColor = Color.WHITE;
        points = new ArrayList<>();
        pressures = new ArrayList<>();
        timestamps = new ArrayList<>();
        angles = new ArrayList<>();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        if (width <= 0 || height <= 0) {
            return;
        }

        canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvasBitmap != null) {
            canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        }
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        float pressure = event.getPressure();
        long timestamp = event.getEventTime();
        float angle = event.getOrientation();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startNewStroke(touchX, touchY, pressure, timestamp, angle);
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        points.add(new PointF(touchX, touchY));
        pressures.add(pressure);
        timestamps.add(timestamp);
        angles.add(angle);
        invalidate();
        return true;
    }

    public void clearCanvas() {
        drawCanvas.drawColor(this.backgroundColor);
        invalidate();

        points.clear();
        pressures.clear();
        timestamps.clear();
        angles.clear();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void saveDrawing() {
        List<PointF> points = getPoints();
        List<Float> pressures = getPressures();
        List<Long> timestamps = getTimestamps();
        List<Float> angles = getAngles();

        if (points.size() > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Save Drawing");
            final EditText input = new EditText(context);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain");
            values.put(MediaStore.MediaColumns.RELATIVE_PATH,Environment.DIRECTORY_DOCUMENTS);
            builder.setPositiveButton("Save", (dialog, which) -> {
                String fileName = input.getText().toString();
                if (!fileName.isEmpty()) {
                    values.put(MediaStore.MediaColumns.DISPLAY_NAME, (fileName+".txt"));
                    ContentResolver contentResolver = context.getContentResolver();
                    Uri uri = contentResolver.insert(MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY), values);
                    try {
                        OutputStream outputStream = contentResolver.openOutputStream(uri);
                        Writer writer = new OutputStreamWriter(outputStream);
                        for (int i = 0; i < points.size(); i++) {
                            PointF point = points.get(i);
                            float pressure = pressures.get(i);
                            long timestamp = timestamps.get(i);
                            float angle = angles.get(i);

                            String line = String.format("(%f,%f),%f,%d,%f", point.x, point.y, pressure, timestamp, angle);
                            writer.write(line);
                            writer.write(System.lineSeparator());
                        }

                        writer.close();
                        outputStream.close();

                        Toast.makeText(context, "Drawing data saved successfully.", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Failed to save drawing data.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "File name cannot be empty.", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        } else {
            Toast.makeText(context, "No drawing data available.", Toast.LENGTH_SHORT).show();
        }
    }


    public void setInkColor(int color) {
        drawPaint.setColor(color);
        this.inkColor = color;
        invalidate();
    }

    public void setCanvasBackgroundColor(int color) {
        setBackgroundColor(color);
        setColor(color);
    }
    private void startNewStroke(float x, float y, float pressure, long timestamp, float angle) {
        Stroke currentStroke = new Stroke();
        currentStroke.path.moveTo(x, y);
        currentStroke.paint = new Paint(drawPaint);
        currentStroke.pressure = pressure;
        currentStroke.timestamp = timestamp;
        currentStroke.angle = angle;
    }

    public List<PointF> getPoints() {
        return points;
    }

    public List<Float> getPressures() {
        return pressures;
    }

    public List<Long> getTimestamps() {
        return timestamps;
    }

    public List<Float> getAngles() { return angles;
    }

    protected void setColor(int color) {
        this.backgroundColor = color;
    }

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public int getInkColor() {
        return this.inkColor;
    }
}


