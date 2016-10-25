package co.kinbu.calificaciones;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import co.kinbu.calificaciones.asignaturas.AsignaturasFragment;
import co.kinbu.calificaciones.asignaturas.AsignaturasPresenter;
import co.kinbu.calificaciones.data.source.AsignaturasRepository;
import co.kinbu.calificaciones.data.source.PeriodosRepository;
import co.kinbu.calificaciones.data.source.local.AsignaturasLocalDataSource;
import co.kinbu.calificaciones.data.source.local.PeriodosLocalDataSource;
import co.kinbu.calificaciones.periodos.PeriodosFragment;
import co.kinbu.calificaciones.periodos.PeriodosPresenter;
import co.kinbu.calificaciones.periodos.addedit.AddEditPeriodoFragment;
import co.kinbu.calificaciones.periodos.addedit.AddEditPeriodoPresenter;
import co.kinbu.calificaciones.util.ViewUtils;

public class MainActivity extends AppCompatActivity implements
        PeriodosFragment.OnPeriodosFragmentInteractionListener,
        AddEditPeriodoFragment.OnAddEditPeriodoFragmentInteractionListener,
        AsignaturasFragment.OnAsignaturasFragmentInteractionListener {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeriodosFragment fragment =
                (PeriodosFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = PeriodosFragment.newInstance(false);
            ViewUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
        }

        final PeriodosRepository mPeriodosRepository = PeriodosRepository.getInstance(
                PeriodosLocalDataSource.getINSTANCE(getApplicationContext())
        );

        new PeriodosPresenter(mPeriodosRepository, fragment);
        /*
        mFragmentManager.popBackStack();

        PeriodoFragment periodoFragment = PeriodoFragment.newInstance();
        periodoFragment.setArguments(getIntent().getExtras());

        String backstack = periodoFragment.getClass().getName();
        mFragmentManager.beginTransaction().add(R.id.fragment_container, periodoFragment, backstack)
                .addToBackStack(backstack).commit();
        */

        // if (savedInstanceState != null) {  }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onShowAddPeriodo() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null || !(fragment instanceof AddEditPeriodoFragment)) {
            fragment = AddEditPeriodoFragment.newInstance();
        }
        ViewUtils.replaceFragmentOnActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);

        final PeriodosRepository mPeriodosRepository = PeriodosRepository.getInstance(
                PeriodosLocalDataSource.getINSTANCE(getApplicationContext())
        );

        final AsignaturasRepository asignaturasRepository = AsignaturasRepository.getInstance(
                AsignaturasLocalDataSource.getINSTANCE(getApplicationContext())
        );

        new AddEditPeriodoPresenter(null, mPeriodosRepository,
                asignaturasRepository, (AddEditPeriodoFragment) fragment);
    }

    @Override
    public void onShowPeriodoDetailsUi(String periodoId) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null || !(fragment instanceof AsignaturasFragment)) {
            fragment = AsignaturasFragment.newInstance(periodoId);
        }
        ViewUtils.replaceFragmentOnActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);

        final PeriodosRepository mPeriodosRepository = PeriodosRepository.getInstance(
                PeriodosLocalDataSource.getINSTANCE(getApplicationContext())
        );

        final AsignaturasRepository asignaturasRepository = AsignaturasRepository.getInstance(
                AsignaturasLocalDataSource.getINSTANCE(getApplicationContext())
        );

        new AsignaturasPresenter(periodoId, mPeriodosRepository,
                asignaturasRepository, (AsignaturasFragment) fragment);
    }

    @Override
    public void onShowPeriodos() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null || !(fragment instanceof PeriodosFragment)) {
            fragment = PeriodosFragment.newInstance(true);
        }
        ViewUtils.replaceFragmentOnActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
        final PeriodosRepository mPeriodosRepository = PeriodosRepository.getInstance(
                PeriodosLocalDataSource.getINSTANCE(getApplicationContext())
        );
        new PeriodosPresenter(mPeriodosRepository, (PeriodosFragment) fragment);
    }

    @Override
    public void onShowAddAsignatura() {
        // TODO
    }

    @Override
    public void onShowAsignaturaDetailsUi(String asignaturaId) {
        // TODO
    }
}
