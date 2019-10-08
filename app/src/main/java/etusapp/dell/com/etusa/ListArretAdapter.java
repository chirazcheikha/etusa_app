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

public class ListArretAdapter extends BaseAdapter {
    private Context mContext;
    private List<Arret> mArretList;

    public   ListArretAdapter(Context mContext, List<Arret> mArretList) {
        this.mContext = mContext;
        this.mArretList = mArretList;
    }

    @Override
    public int getCount() {
        return mArretList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArretList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mArretList.get(position).getIdArret();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext, R.layout.itemarret, null);
        v.setOnClickListener(null);
        TextView tvnomArret = (TextView)v.findViewById(R.id.nomArret);

       tvnomArret.setText(mArretList.get(position).getNomArret());


        return v;
    }
}
