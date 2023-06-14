package com.tfg.componentcapturaescriptura;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
public class CustomComponent extends ConstraintLayout {
    public Button eraseButton;
    public Button saveButton;
    public DrawingView drawingView;

    public CustomComponent(Context context) {
        super(context);
        init(context);
    }

    public CustomComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.custom_component, this);

        eraseButton = findViewById(R.id.erase_button);
        saveButton = findViewById(R.id.save_button);
        drawingView = findViewById(R.id.drawing_view);

        eraseButton.setOnClickListener(v -> drawingView.clearCanvas());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveButton.setOnClickListener(v -> drawingView.saveDrawing());
        }
    }

    public void setButtonSize(int width, int height) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        eraseButton.setLayoutParams(layoutParams);
        saveButton.setLayoutParams(layoutParams);
    }

    public void setComponentBackgroundColor(int color) {
        drawingView.setBackgroundColor(color);
        drawingView.setColor(color);
    }

    public void setInkColor(int color) {
        drawingView.setInkColor(color);
    }
}

