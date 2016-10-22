package co.kinbu.calificaciones;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import co.kinbu.calificaciones.data.Asignatura;
import co.kinbu.calificaciones.periodos.PeriodosFragment;
import co.kinbu.calificaciones.periodos.PeriodosPresenter;
import co.kinbu.calificaciones.util.AsignaturasManager;
import co.kinbu.calificaciones.data.Nota;
import co.kinbu.calificaciones.data.Periodo;
import co.kinbu.calificaciones.data.source.AsignaturasRepository;
import co.kinbu.calificaciones.data.source.NotasRepository;
import co.kinbu.calificaciones.data.source.PeriodosDataSource;
import co.kinbu.calificaciones.data.source.PeriodosRepository;
import co.kinbu.calificaciones.data.source.local.AsignaturasLocalDataSource;
import co.kinbu.calificaciones.data.source.local.NotasLocalDataSource;
import co.kinbu.calificaciones.data.source.local.PeriodosLocalDataSource;
import co.kinbu.calificaciones.util.ViewUtils;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private PeriodosPresenter mPeriodosPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInstances();


        PeriodosFragment fragment =
                (PeriodosFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = PeriodosFragment.newInstance();
            ViewUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
        }

        final PeriodosRepository mPeriodosRepository = PeriodosRepository.getInstance(
                PeriodosLocalDataSource.getINSTANCE(getApplicationContext())
        );

        mPeriodosPresenter = new PeriodosPresenter(mPeriodosRepository, fragment);

        /*
        mPeriodosRepository.getPeriodos(new PeriodosDataSource.LoadPeriodosCallback() {
            @Override
            public void onPeriodosLoaded(List<Periodo> periodosLista) {

            }

            @Override
            public void onDataNotAvailable() {
                Periodo periodo = new Periodo();
                List <Asignatura> asignaturas = new ArrayList<>();
                Asignatura asignatura = new Asignatura(periodo.getId());
                List<Nota> notas = new ArrayList<>();
                AsignaturasRepository mAsignaturasRepository = AsignaturasRepository.getInstance(
                        AsignaturasLocalDataSource.getINSTANCE(getApplicationContext()));
                mAsignaturasRepository.refreshAsignaturas();
                NotasRepository mNotasRepository= NotasRepository.getInstance(
                        NotasLocalDataSource.getInstance(getApplicationContext()));
                mNotasRepository.refreshNotas();
                for (int i = 0; i < 3; ++i) {
                    Nota nota = new Nota(asignatura.getId());
                    notas.add(nota);
                    mNotasRepository.saveNota(nota);
                }
                asignatura.getNotas().addAll(notas);
                AsignaturasManager.actualizarDefinitiva(asignatura);
                mAsignaturasRepository.saveAsignatura(asignatura);
                asignaturas.add(asignatura);
                periodo.getAsignaturas().addAll(asignaturas);
                periodo.setPromedio(asignatura.getDefinitiva());
                mPeriodosRepository.savePeriodo(periodo);

            }
        });
        */

        /*
        mFragmentManager.popBackStack();

        PeriodoFragment periodoFragment = PeriodoFragment.newInstance();
        periodoFragment.setArguments(getIntent().getExtras());

        String backstack = periodoFragment.getClass().getName();
        mFragmentManager.beginTransaction().add(R.id.fragment_container, periodoFragment, backstack)
                .addToBackStack(backstack).commit();
        */

        //if (savedInstanceState != null) {  }
    }

    private void initInstances() {


        /*
        mAsignaturasRepository = AsignaturasRepository.getInstance(
                AsignaturasLocalDataSource.getINSTANCE(getApplicationContext()));
        mAsignaturasRepository.refreshAsignaturas();
        mNotasRepository= NotasRepository.getInstance(
                NotasLocalDataSource.getInstance(getApplicationContext()));
        mNotasRepository.refreshNotas();
        */
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

/*
    @Override
    public void onFragmentInteraction(Nota nota) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onFragmentInteraction: "+ nota);
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
        if (BuildConfig.DEBUG) Log.d(TAG, "onFragmentInteraction: " + nota);
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
*/
}
