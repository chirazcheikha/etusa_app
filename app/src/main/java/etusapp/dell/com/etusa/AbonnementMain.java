package etusapp.dell.com.etusa;

/**
 * Created by DELL on 07/05/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.View;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class AbonnementMain extends Activity {


    private List<Abonnement> mAboList;
    private DatabaseHelper mDBHelper;
    private ListView lvAbo;
    private ListAboAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abonnement);
        lvAbo = (ListView)findViewById(R.id.listview_abonnement);
        mDBHelper = new DatabaseHelper(this);


        lvAbo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent( AbonnementMain.this,Contenuabo.class);
                Abonnement abo = (Abonnement)mAboList.get(position);
                i.putExtra("idbya",abo.getIdbya());
                i.putExtra("titreabo",abo.getTitreabo());


                startActivity(i);
            }
        });


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
        mAboList = mDBHelper.getListabo();
        adapter = new ListAboAdapter(this, mAboList);
        lvAbo.setAdapter(adapter);

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
            Log.w("MainActivity","DB copied");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



            }



