package com.tfg.componentcapturaescriptura;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DrawingViewTest {
    private DrawingView drawingView;
    private Context context;
    private AlertDialog.Builder builderMock;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        drawingView = new DrawingView(context);
        builderMock = mock(AlertDialog.Builder.class);
    }

    @After
    public void tearDown() {
        drawingView = null;
        context = null;
        builderMock = null;
    }


    @Test
    public void testOnTouchEvent() {
        MotionEvent motionEvent = MotionEvent.obtain(0L, 0L, MotionEvent.ACTION_DOWN, 10f, 10f, 0);

        boolean result = drawingView.onTouchEvent(motionEvent);

        List<PointF> points = drawingView.getPoints();
        List<Float> pressures = drawingView.getPressures();
        List<Long> timestamps = drawingView.getTimestamps();
        List<Float> angles = drawingView.getAngles();
        assertEquals(true, result);
        assertEquals(1, points.size());
        assertEquals(1, pressures.size());
        assertEquals(1, timestamps.size());
        assertEquals(1, angles.size());
        assertEquals(10f, points.get(0).x, 0f);
        assertEquals(10f, points.get(0).y, 0f);
    }

}
