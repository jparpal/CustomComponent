package com.tfg.componentcapturaescriptura;

import android.graphics.Paint;
import android.graphics.Path;

public class Stroke {
    Path path;
    Paint paint;
    float pressure;
    long timestamp;
    float angle;

    Stroke() {
        path = new Path();
        paint = new Paint();
    }
}
