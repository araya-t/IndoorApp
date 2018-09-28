package com.estimote.indoorapp.Adapter.Parka;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.estimote.indoorapp.Manager.Contextor;
import com.estimote.indoorapp.Model.Parka.CsvRow;
import com.estimote.indoorapp.View.CsvRowListItemViewGroup;

import java.util.List;

public class CsvRowListAdapter extends BaseAdapter {
    List<CsvRow> csvRows;

    //How many items are in the data set represented by this Adapter.
    //@return Count of items.
    @Override
    public int getCount() {
        if(csvRows == null)
            return 0;
        return csvRows.size();
    }

    //Get the data item associated with the specified position in the data set.
    //@param position Position of the item whose data we want within the adapter's data set.
    //@return The data at the specified position.
    @Override
    public Object getItem(int position) {
        return csvRows.get(position);
    }

    //Get the row id associated with the specified position in the list.
    //@param position The position of the item within the adapter's data set whose row id we want.
    //@return The id of the item at the specified position.
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = new CsvRowListItemViewGroup(Contextor.getInstance().getContext());
        }
        CsvRowListItemViewGroup view = (CsvRowListItemViewGroup) convertView;
        CsvRow csvRow = (CsvRow) getItem(position);

//        Log.i("CsvRowListAdapter"," mill&timeStamp+timeStampLong \n"+ csvRow.getMillisec()
//                + " | " + csvRow.getTimeStamp()
//                + " | " + csvRow.getTimeStampLong());

        view.setTvCountRows(csvRow.getCountRow());
        view.setTvMillisec(csvRow.getMillisec());
        view.setTvTimestamp(csvRow.getTimeStamp());
        view.setTvTimestampLong(csvRow.getTimeStampLong());
        view.setTvAcce_x(csvRow.getAcce_x());
        view.setTvAcce_y(csvRow.getAcce_y());
        view.setTvAcce_z(csvRow.getAcce_z());
        view.setTvIs_stop_engine(csvRow.is_stop_engine());
        view.setTvX_position(csvRow.getX_position());
        view.setTvY_position(csvRow.getY_position());

        return view;
    }

    public void setCsvRows(List<CsvRow> csvRows){
        this.csvRows = csvRows;
    }
}
