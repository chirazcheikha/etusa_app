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

public class ListStationAdapter extends BaseAdapter {
    private Context mContext;
    private List<Station> mStationList;

    public   ListStationAdapter(Context mContext, List<Station> mStationList) {
        this.mContext = mContext;
        this.mStationList = mStationList;
    }

    @Override
    public int getCount() {
        return mStationList.size();
    }

    @Override
    public Object getItem(int position) {
        return mStationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mStationList.get(position).getIdStation();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext, R.layout.activity_itemstation, null);

        TextView tvnomstation = (TextView)v.findViewById(R.id.nomStation);

        tvnomstation.setText(mStationList.get(position).getNomstation());


        return v;
    }
}
