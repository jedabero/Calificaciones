package co.kinbu.calificaciones;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.kinbu.calificaciones.data.NotasManager;
import co.kinbu.calificaciones.data.model.Nota;
import co.kinbu.calificaciones.view.NotasFragment;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity implements
        NotasFragment.OnFragmentInteractionListener {

    public static final String TAG = "MainActivity";

    private Realm mRealm;
    private RealmConfiguration mRealmConfig;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInstances();

        /*mRealm.beginTransaction();
        for (int i = 0; i < 3; i++) {
            Nota nota = mRealm.createObject(Nota.class);
            nota.setValor(1 + i);
            nota.setPeso(20 - i);
        }
        mRealm.commitTransaction();*/

        List<Nota> notas = new ArrayList<>();
        notas.addAll(mRealm.allObjects(Nota.class));

        NotasFragment fragment = NotasFragment.newInstance();
        fragment.setArguments(getIntent().getExtras());

        String backstack = fragment.getClass().getName();
        mFragmentManager.beginTransaction().add(R.id.fragment_container, fragment, backstack)
                .addToBackStack(backstack).commit();

        fragment.setNotas(notas);
    }

    private void initInstances() {
        mRealmConfig = new RealmConfiguration.Builder(this).build();
        mRealm = Realm.getInstance(mRealmConfig);

        mFragmentManager = getSupportFragmentManager();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRealm != null) mRealm.close();
    }

    @Override
    public void onFragmentInteraction(Nota nota) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onFragmentInteraction: "+ NotasManager.toString(nota));
    }

    @Override
    public Nota onAddNota(@NonNull Nota nota) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onFragmentInteraction: "+ NotasManager.toString(nota));
        mRealm.beginTransaction();
        Nota notaRealm = mRealm.copyToRealm(nota);
        mRealm.commitTransaction();
        return notaRealm;
    }

    @Override
    public void onDeleteNota(Nota nota) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onFragmentInteraction: "+ NotasManager.toString(nota));
        if (nota == null) return;
        mRealm.beginTransaction();
        nota.removeFromRealm();
        mRealm.commitTransaction();
    }
}
