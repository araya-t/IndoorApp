package com.estimote.indoorapp.view;

import com.estimote.indoorapp.R;
import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Button;

public class StartStopButtonViewGroup extends BaseCustomViewGroup {
    private Button btnStartRecord;
    private Button btnStopRecord;

    public StartStopButtonViewGroup(@NonNull Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public StartStopButtonViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
    }

    public StartStopButtonViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
    }

    @TargetApi(21)
    public StartStopButtonViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
    }

    private void initInflate() {
        // Inflate Layout here
        inflate(getContext(), R.layout.layout_start_stop_button, this);

    }

    private void initInstances() {
        // findViewById of item in ViewGroup here
        btnStartRecord = findViewById(R.id.btnStartRecord);
        btnStopRecord = findViewById(R.id.btnStopRecord);
    }

    public Button getBtnStart(){
        return btnStartRecord;
    }

    public Button getBtnStop(){
        return btnStopRecord;
    }
}
