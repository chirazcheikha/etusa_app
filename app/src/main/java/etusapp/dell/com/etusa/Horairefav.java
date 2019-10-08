package etusapp.dell.com.etusa;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by DELL on 16/06/2018.
 */

public class Horairefav extends Activity {


        private ListView lvHoraireFav;
        private ListHorairesAdapter adapter;
        private List<Horaire> mHoraireFavList;
        private DatabaseHelper mDBHelper;
        int favligne;
        String o;
        String d;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.listview_horairefav);
            lvHoraireFav = (ListView)findViewById(R.id.horairefav);
            mDBHelper = new DatabaseHelper(this);

            favligne= Integer.parseInt(getIntent().getStringExtra("favligneid"));
            o=getIntent().getStringExtra("origine");
            d=getIntent().getStringExtra("destination");

            TextView tv1=(TextView)findViewById(R.id.Station1);
            TextView tv2=(TextView)findViewById(R.id.textView133);

            tv1.setText(o);
            tv2.setText(d);



            File database = getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);
            if(false == database.exists()) {
                mDBHelper.getReadableDatabase();
                if(copyDatabase(this)) {
                    Toast.makeText(this, "Copy database succes", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Copy data error", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            mHoraireFavList = mDBHelper.getListHorairebydirection(favligne);
            adapter = new ListHorairesAdapter(getApplicationContext(), mHoraireFavList);
            lvHoraireFav.setAdapter(adapter);


        }


        private boolean copyDatabase(Context context) {
            try {

                InputStream inputStream = context.getAssets().open(DatabaseHelper.DBNAME);
                String outFileName = DatabaseHelper.DBLOCATION + DatabaseHelper.DBNAME;
                OutputStream outputStream = new FileOutputStream(outFileName);
                byte[]buff = new byte[1024];
                int length = 0;
                while ((length = inputStream.read(buff)) > 0) {
                    outputStream.write(buff, 0, length);
                }
                outputStream.flush();
                outputStream.close();
                Log.w("horairesmain","DB copied");
                return true;
            }catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

    }

