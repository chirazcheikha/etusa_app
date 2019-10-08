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

public class ListLigneAdapter extends BaseAdapter {
    private Context mContext;
    private List<Ligne> mLigneList;

    public ListLigneAdapter(Context mContext, List<Ligne> mLigneList) {
        this.mContext = mContext;
        this.mLigneList = mLigneList;
    }

    @Override
    public int getCount() {
        return mLigneList.size();
    }

    @Override
    public Object getItem(int position) {
        return mLigneList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mLigneList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext, R.layout.itemligne, null);
        TextView tvnumLigneTextView = (TextView)v.findViewById(R.id.numLigneTextView);
        TextView tvorigineTextView = (TextView)v.findViewById(R.id.origineTextView);
        TextView tvdestinationTextView = (TextView)v.findViewById(R.id.destinationTextView);

        tvnumLigneTextView.setText(String.valueOf(mLigneList.get(position).getNumLigne()));
        tvorigineTextView.setText(mLigneList.get(position).getOrigine());
        tvdestinationTextView.setText(mLigneList.get(position).getDestination());


        return v;
    }
}
