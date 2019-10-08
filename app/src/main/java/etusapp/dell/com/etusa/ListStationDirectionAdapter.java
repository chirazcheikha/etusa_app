package etusapp.dell.com.etusa;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DELL on 06/06/2018.
 */

public class ListStationDirectionAdapter extends BaseAdapter {
    private Context mContext;
    private List<Direction> mDirectionList;

    public   ListStationDirectionAdapter(Context mContext, List<Direction> mDirectionList) {
        this.mContext = mContext;
        this.mDirectionList = mDirectionList;
    }

    @Override
    public int getCount() {
        return mDirectionList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDirectionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mDirectionList.get(position).getIdLigne();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext, R.layout.activity_itemdirection, null);

        TextView tvnomDirection = (TextView)v.findViewById(R.id.nomDirection);

        tvnomDirection.setText(mDirectionList.get(position).getDirection());


        return v;
    }
}
