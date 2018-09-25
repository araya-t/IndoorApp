package com.estimote.indoorapp.View;

import com.estimote.indoorapp.R;
import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Button;


public class StopEngineButtonViewGroup extends BaseCustomViewGroup {
    private Button btnStopEngine;

    public StopEngineButtonViewGroup(@NonNull Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public StopEngineButtonViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
    }

    public StopEngineButtonViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
    }

    @TargetApi(21)
    public StopEngineButtonViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
    }

    private void initInflate() {
        inflate(getContext(), R.layout.layout_stop_engine_button, this);
    }

    private void initInstances() {
        btnStopEngine = findViewById(R.id.btnStopEngine);
    }

    public Button getBtnStopEngine(){
        return btnStopEngine;
    }

}
