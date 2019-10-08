package etusapp.dell.com.etusa;

/**
 * Created by DELL on 07/05/2018.
 */


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAboAdapter extends BaseAdapter {
    private Context mContext;
    private List<Abonnement> mAboList;

    public   ListAboAdapter(Context mContext, List<Abonnement> mAboList) {
        this.mContext = mContext;
        this.mAboList = mAboList;
    }

    @Override
    public int getCount() {
        return mAboList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAboList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mAboList.get(position).getIdbo();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext, R.layout.activity_itemabo, null);
        TextView tvtitreabo = (TextView)v.findViewById(R.id.titreabo);

        tvtitreabo.setText(mAboList.get(position).getTitreabo());

        return v;
    }
}
