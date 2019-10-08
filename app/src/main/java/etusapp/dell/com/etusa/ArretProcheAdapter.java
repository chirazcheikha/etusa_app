package etusapp.dell.com.etusa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import etusapp.localisation.ArretProche;

/**
 * Created by DELL on 24/06/2018.
 */

public class ArretProcheAdapter extends BaseAdapter {


    Context mContext;
    LayoutInflater inflater;
    private List<ArretProche> arretprochelist = null;
    private ArrayList<ArretProche> arraylist;

    public ArretProcheAdapter(Context context, List<ArretProche> arretprochelist) {
        mContext = context;
        this.arretprochelist = arretprochelist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<ArretProche>();
        this.arraylist.addAll(arretprochelist);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return arretprochelist.size();
    }

    @Override
    public ArretProche getItem(int position) {
        return arretprochelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.activity_itemhoraire, null);
            holder.name = (TextView) view.findViewById(R.id.texthoraire);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(arretprochelist.get(position).getArret());
        return view;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        arretprochelist.clear();
        if (charText.length() == 0) {
            arretprochelist.addAll(arraylist);
        } else {
            for (ArretProche wp : arraylist) {
                if (wp.getArret().toLowerCase(Locale.getDefault()).contains(charText)) {
                    arretprochelist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
