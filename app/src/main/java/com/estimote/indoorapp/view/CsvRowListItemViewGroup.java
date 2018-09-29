package com.estimote.indoorapp.view;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.estimote.indoorapp.R;

public class CsvRowListItemViewGroup extends BaseCustomViewGroup {
    TextView tvCountRows, tvMillisec, tvTimestamp, tvTimestampLong, tvIs_stop_engine;
    TextView tvAcce_x, tvAcce_y, tvAcce_z, tvX_position, tvY_position;

    public CsvRowListItemViewGroup(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public CsvRowListItemViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
    }

    public CsvRowListItemViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
    }

    @TargetApi(21)
    public CsvRowListItemViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
    }

    private void initInflate() {
        inflate(getContext(), R.layout.list_csv_row_attr, this);
    }

    private void initInstances() {
        // findViewById here
        tvCountRows = findViewById(R.id.tvCountRows);
        tvMillisec = findViewById(R.id.tvMillisec);
        tvTimestamp = findViewById(R.id.tvTimestamp);
        tvIs_stop_engine = findViewById(R.id.tvIs_stop_engine);
        tvAcce_x = findViewById(R.id.tvAcce_x);
        tvAcce_y = findViewById(R.id.tvAcce_y);
        tvAcce_z = findViewById(R.id.tvAcce_z);
        tvTimestampLong = findViewById(R.id.tvTimestampLong);
        tvX_position = findViewById(R.id.tvX_position);
        tvY_position = findViewById(R.id.tvY_position);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        /*
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StyleableName,
                defStyleAttr, defStyleRes);

        try {

        } finally {
            a.recycle();
        }
        */
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        BundleSavedState savedState = new BundleSavedState(superState);
        // Save Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        // savedState.getBundle().putString("key", value);

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        Bundle bundle = ss.getBundle();
        // Restore State from bundle here
    }

    public TextView getTvCountRows() {
        return tvCountRows;
    }

    public void setTvCountRows(int tvCountRows) {
        this.tvCountRows.setText("(" + tvCountRows + ") ");
    }

    public TextView getTvMillisec() {
        return tvMillisec;
    }

    public void setTvMillisec(long tvMillisec) {
        this.tvMillisec.setText(tvMillisec + ",");
    }

    public TextView getTvTimestamp() {
        return tvTimestamp;
    }

    public void setTvTimestamp(String  tvTimestamp) {
        this.tvTimestamp.setText(tvTimestamp + ",");
    }

    public TextView getTvTimestampLong() {
        return tvTimestampLong;
    }

    public void setTvTimestampLong(long  tvTimestampLong) {
        this.tvTimestampLong.setText("" + tvTimestampLong);
    }

    public TextView getTvIs_stop_engine() {
        return tvIs_stop_engine;
    }

    public TextView getTvAcce_x() {
        return tvAcce_x;
    }

    public void setTvAcce_x(double tvAcce_x) {
        this.tvAcce_x.setText("(Acce) x=" + tvAcce_x + ",");
    }

    public TextView getTvAcce_y() {
        return tvAcce_y;
    }

    public void setTvAcce_y(double tvAcce_y) {
        this.tvAcce_y.setText("y=" + tvAcce_y + ",");
    }

    public TextView getTvAcce_z() {
        return tvAcce_z;
    }

    public void setTvAcce_z(double tvAcce_z) {
        this.tvAcce_z.setText("z=" + tvAcce_z);
    }

    public TextView getTvX_position() {
        return tvX_position;
    }

    public void setTvIs_stop_engine(boolean tvIs_stop_engine) {
        this.tvIs_stop_engine.setText(""+tvIs_stop_engine + ", ");
    }

    public void setTvX_position(double tvX_position) {
        this.tvX_position.setText(tvX_position + ",");
    }

    public TextView getTvY_position() {
        return tvY_position;
    }

    public void setTvY_position(double  tvY_position) {
        this.tvY_position.setText("" + tvY_position);
    }
}
