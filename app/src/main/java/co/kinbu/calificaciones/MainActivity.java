package co.kinbu.calificaciones;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.kinbu.calificaciones.data.Asignatura;
import co.kinbu.calificaciones.data.AsignaturasManager;
import co.kinbu.calificaciones.data.Nota;
import co.kinbu.calificaciones.data.NotasManager;
import co.kinbu.calificaciones.data.source.AsignaturasDataSource;
import co.kinbu.calificaciones.data.source.AsignaturasRepository;
import co.kinbu.calificaciones.data.source.NotasRepository;
import co.kinbu.calificaciones.data.source.local.AsignaturasLocalDataSource;
import co.kinbu.calificaciones.data.source.local.NotasLocalDataSource;
import co.kinbu.calificaciones.view.AsignaturaFragment;

public class MainActivity extends AppCompatActivity implements
        AsignaturaFragment.OnFragmentInteractionListener {

    public static final String TAG = "MainActivity";

    private AsignaturasRepository mAsignaturasRepository;
    private NotasRepository mNotasRepository;
    private FragmentManager mFragmentManager;

    private AsignaturaFragment asignaturaFragment;
    private Asignatura asignatura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInstances();

        mAsignaturasRepository.getAsignaturas(mNotasRepository, new AsignaturasDataSource.LoadAsignaturasCallback() {
            @Override
            public void onAsignaturasLoaded(List<Asignatura> asignaturas) {
                asignatura = asignaturas.get(0);
            }

            @Override
            public void onDataNotAvailable() {
                asignatura = new Asignatura();
                List<Nota> notas = new ArrayList<>();
                for (int i = 0; i < 3; ++i) {
                    Nota nota = new Nota(asignatura.getId());
                    notas.add(nota);
                    mNotasRepository.saveNota(nota);
                }
                asignatura.getNotas().addAll(notas);
                AsignaturasManager.actualizarDefinitiva(asignatura);
                mAsignaturasRepository.saveAsignatura(asignatura);
            }
        });

        mFragmentManager.popBackStack();

        asignaturaFragment = AsignaturaFragment.newInstance();
        asignaturaFragment.setArguments(getIntent().getExtras());

        String backstack = asignaturaFragment.getClass().getName();
        mFragmentManager.beginTransaction().add(R.id.fragment_container, asignaturaFragment, backstack)
                .addToBackStack(backstack).commit();

        asignaturaFragment.setAsignatura(asignatura);
    }

    private void initInstances() {
        mAsignaturasRepository = AsignaturasRepository.getInstance(
                AsignaturasLocalDataSource.getINSTANCE(getApplicationContext()));
        mAsignaturasRepository.refreshAsignaturas();
        mNotasRepository= NotasRepository.getInstance(
                NotasLocalDataSource.getINSTANCE(getApplicationContext()));
        mNotasRepository.refreshNotas();

        mFragmentManager = getSupportFragmentManager();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AsignaturasRepository.destroyInstance();
    }

    @Override
    public void onFragmentInteraction(Nota nota) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onFragmentInteraction: "+ NotasManager.toString(nota));
    }

    @Override
    public void onAsignaturaNombreChange(String nombre, Asignatura asignatura) {
        asignatura.setNombre(nombre);
        mAsignaturasRepository.updateAsignatura(asignatura);
    }

    @Override
    public void onAddNota() {
        Nota nota = new Nota(asignatura.getId());
        mNotasRepository.saveNota(nota);
        asignatura.getNotas().add(nota);
        AsignaturasManager.actualizarDefinitiva(asignatura);
        mAsignaturasRepository.updateAsignatura(asignatura);
        asignaturaFragment.updatePromedio();
    }

    @Override
    public void onDeleteNota(Nota nota) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onFragmentInteraction: " + NotasManager.toString(nota));
        if (nota == null) return;
        mNotasRepository.deleteNota(nota.getId());
        asignatura.getNotas().remove(asignatura.getNotas().indexOf(nota));
        AsignaturasManager.actualizarDefinitiva(asignatura);
        mAsignaturasRepository.updateAsignatura(asignatura);
        asignaturaFragment.updatePromedio();
    }

    @Override
    public void onNotaValorListener(Nota n, Double s) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onNotaValorListener: " + n + ", newValor:" + s);
        n.setValor(s);
        mNotasRepository.updateNota(n);
        AsignaturasManager.actualizarDefinitiva(asignatura);
        mAsignaturasRepository.updateAsignatura(asignatura);
        asignaturaFragment.updatePromedio();
    }

    @Override
    public void onNotaPesoListener(Nota n, Integer s) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onNotaValorListener: " + n + ", newPeso:" + s);
        n.setPeso(s);
        mNotasRepository.updateNota(n);
        AsignaturasManager.actualizarDefinitiva(asignatura);
        mAsignaturasRepository.updateAsignatura(asignatura);
        asignaturaFragment.updatePromedio();
    }

}
