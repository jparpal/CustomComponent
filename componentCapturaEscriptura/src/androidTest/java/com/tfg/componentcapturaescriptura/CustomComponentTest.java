package com.tfg.componentcapturaescriptura;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CustomComponentTest {
    private Context context;
    private CustomComponent customComponent;

    @Before
    public void setup() {
        // Context of the app under test.
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        customComponent = new CustomComponent(context);
    }


    @Test
    public void testCustomComponentInitialization() {
        // Test the initialization of CustomComponent
        assertNotNull(customComponent);
        assertNotNull(customComponent.eraseButton);
        assertNotNull(customComponent.saveButton);
        assertNotNull(customComponent.drawingView);
    }

    @Test
    public void testSetButtonSize() {
        int width = 200;
        int height = 100;
        customComponent.setButtonSize(width, height);
        Button eraseButton = customComponent.eraseButton;
        Button saveButton = customComponent.saveButton;

        eraseButton.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        eraseButton.layout(0, 0, width, height);

        saveButton.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        saveButton.layout(0, 0, width, height);

        assertEquals(width, eraseButton.getWidth());
        assertEquals(height, eraseButton.getHeight());
        assertEquals(width, saveButton.getWidth());
        assertEquals(height, saveButton.getHeight());
    }

    @Test
    public void testSetComponentBackgroundColor() {
        int color = Color.RED;
        customComponent.setComponentBackgroundColor(color);
        assertEquals(color, customComponent.drawingView.getBackgroundColor());
    }

    @Test
    public void testSetInkColor() {
        int color = Color.BLUE;
        customComponent.setInkColor(color);
        assertEquals(color, customComponent.drawingView.getInkColor());
    }

}