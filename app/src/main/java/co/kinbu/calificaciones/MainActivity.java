package co.kinbu.calificaciones;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private Realm mRealm;
    private RealmConfiguration mRealmConfig;

    private RecyclerView mNotasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInstances();

        mRealm.beginTransaction();
        Nota nota = mRealm.createObject(Nota.class);
        nota.setValor(4);
        nota.setPeso(3);
        nota = mRealm.createObject(Nota.class);
        nota.setValor(3);
        nota.setPeso(3);
        nota = mRealm.createObject(Nota.class);
        nota.setValor(3.5);
        nota.setPeso(4);
        mRealm.commitTransaction();

        List<Nota> notas = mRealm.allObjects(Nota.class);
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate: " + notas.toString());
        mNotasView.setAdapter(new NotaRecyclerViewAdapter(notas));

    }

    private void initInstances() {
        mRealmConfig = new RealmConfiguration.Builder(this).build();
        mRealm = Realm.getInstance(mRealmConfig);

        mNotasView = (RecyclerView) findViewById(R.id.list_notas);
        mNotasView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRealm != null) mRealm.close();
    }
}
