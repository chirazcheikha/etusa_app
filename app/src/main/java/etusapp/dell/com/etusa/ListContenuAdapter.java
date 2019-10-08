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

public class ListContenuAdapter extends BaseAdapter {
    private Context mContext;
    private List<Contenu> mAboList;

    public   ListContenuAdapter(Context mContext, List<Contenu> mAboList) {
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
        return mAboList.get(position).getIdc();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext, R.layout.activity_itemcontenuabo, null);
        v.setOnClickListener(null);
        TextView tvage = (TextView)v.findViewById(R.id.age);
        TextView tvtarif = (TextView)v.findViewById(R.id.tarif);

        tvage.setText(mAboList.get(position).getAgec());
        tvtarif.setText(mAboList.get(position).getTarifc());


        return v;
    }
}
