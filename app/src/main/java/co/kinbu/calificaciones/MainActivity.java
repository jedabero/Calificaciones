package co.kinbu.calificaciones;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import co.kinbu.calificaciones.data.AsignaturasManager;
import co.kinbu.calificaciones.data.NotasManager;
import co.kinbu.calificaciones.data.model.Asignatura;
import co.kinbu.calificaciones.data.model.Nota;
import co.kinbu.calificaciones.view.AsignaturaFragment;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity implements
        AsignaturaFragment.OnFragmentInteractionListener {

    public static final String TAG = "MainActivity";

    private Realm mRealm;
    private RealmConfiguration mRealmConfig;
    private FragmentManager mFragmentManager;

    private AsignaturaFragment asignaturaFragment;
    private Asignatura asignatura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInstances();

        mRealm.beginTransaction();
        asignatura = mRealm.createObject(Asignatura.class);
        asignatura.setNombre("Teoria de Automatas");
        double definitiva = 0;
        int pesoTotal = 0;
        for (int i = 0; i < 3; i++) {
            Nota nota = mRealm.createObject(Nota.class);
            nota.setValor(2 + i);
            nota.setPeso(4 - i);
            asignatura.getNotas().add(nota);
            definitiva += nota.getValor()*nota.getPeso();
            pesoTotal += nota.getPeso();
        }
        definitiva /= pesoTotal;
        asignatura.setDefinitiva(definitiva);
        mRealm.commitTransaction();

        asignaturaFragment = AsignaturaFragment.newInstance();
        asignaturaFragment.setArguments(getIntent().getExtras());

        String backstack = asignaturaFragment.getClass().getName();
        mFragmentManager.beginTransaction().add(R.id.fragment_container, asignaturaFragment, backstack)
                .addToBackStack(backstack).commit();

        asignaturaFragment.setAsignatura(asignatura);
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
        if (BuildConfig.DEBUG) Log.d(TAG, "onFragmentInteraction: " + NotasManager.toString(nota));
        if (nota == null) return;
        mRealm.beginTransaction();
        nota.removeFromRealm();
        mRealm.commitTransaction();
    }

    @Override
    public void onNotaValorListener(Nota n, Double s) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onNotaValorListener: " + n + ", newValor:" + s);
        mRealm.beginTransaction();
        n.setValor(s);
        AsignaturasManager.actualizarDefinitiva(asignatura);
        mRealm.commitTransaction();
        asignaturaFragment.updatePromedio();
    }

    @Override
    public void onNotaPesoListener(Nota n, Integer s) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onNotaValorListener: " + n + ", newPeso:" + s);
        mRealm.beginTransaction();
        n.setPeso(s);
        AsignaturasManager.actualizarDefinitiva(asignatura);
        mRealm.commitTransaction();
        asignaturaFragment.updatePromedio();
    }

}
