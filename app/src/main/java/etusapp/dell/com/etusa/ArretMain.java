package etusapp.dell.com.etusa;

/**
 * Created by DELL on 07/05/2018.
 */


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;


public class ArretMain extends Activity {
    private ListView lvArret;
    private ListArretAdapter adapter;
    private List<Arret> mArretList;
    private DatabaseHelper mDBHelper;
    int idligne;
    int numl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listviewarret);
        lvArret = (ListView)findViewById(R.id.listview_arret);


        idligne = getIntent().getIntExtra("ID",0);
        numl = getIntent().getIntExtra("numLigne",0);

        mDBHelper = new DatabaseHelper(this);

        TextView tvnumligne = (TextView)findViewById(R.id.numligne);

        tvnumligne.setText(String.valueOf(numl));



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


        mArretList = mDBHelper.getListArretByLigne(idligne);
        adapter = new ListArretAdapter(getApplicationContext(), mArretList);
        lvArret.setAdapter(adapter);

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
            Log.w("ArretMain","DB copied");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
