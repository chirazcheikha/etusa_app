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

public class ListHorairesAdapter extends BaseAdapter {
    private Context mContext;
    private List<Horaire> mHorairesList;

    public   ListHorairesAdapter(Context mContext, List<Horaire> mHorairesList) {
        this.mContext = mContext;
        this.mHorairesList = mHorairesList;
    }

    @Override
    public int getCount() {
        return mHorairesList.size();
    }

    @Override
    public Object getItem(int position) {
        return mHorairesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mHorairesList.get(position).getIdhoraire();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext, R.layout.activity_itemhoraire, null);
        v.setOnClickListener(null);
        TextView tvhoraire = (TextView)v.findViewById(R.id.texthoraire);
        tvhoraire.setText(mHorairesList.get(position).getHoraire());
        return v;
    }
}
