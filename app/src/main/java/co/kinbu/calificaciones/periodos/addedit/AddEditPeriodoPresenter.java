package co.kinbu.calificaciones.periodos.addedit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import co.kinbu.calificaciones.data.Periodo;
import co.kinbu.calificaciones.data.source.AsignaturasDataSource;
import co.kinbu.calificaciones.data.source.PeriodosDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 * Created by jedabero on 22/10/16.
 */

public final class AddEditPeriodoPresenter implements AddEditPeriodoContract.Presenter, PeriodosDataSource.LoadPeriodoCallback {

    @NonNull
    private final PeriodosDataSource mPeriodosDataSource;

    @NonNull
    private final AsignaturasDataSource mAsignaturasDataSource;

    @NonNull
    private final AddEditPeriodoContract.View mAddEditPeriodoView;

    @Nullable
    private String mPeriodoId;

    private double mPeriodoPromedio = 0d;

    /**
     * Instantiates a new Add edit periodo presenter.
     *
     * @param periodoId          the periodo id
     * @param periodosDataSource the periodos data source
     * @param addEditPeriodoView the add edit periodo view
     */
    public AddEditPeriodoPresenter(@Nullable String periodoId, @NonNull PeriodosDataSource periodosDataSource,
                                   @NonNull AsignaturasDataSource asignaturasDataSource,
                                   @NonNull AddEditPeriodoContract.View addEditPeriodoView) {
        mPeriodoId = periodoId;
        mPeriodosDataSource = checkNotNull(periodosDataSource);
        mAsignaturasDataSource = checkNotNull(asignaturasDataSource);
        mAddEditPeriodoView = checkNotNull(addEditPeriodoView);

        addEditPeriodoView.setPresenter(this);
    }

    @Override
    public void start() {
        if (!isNewPeriodo()) {
            populatePeriodo();
        } else {
            mAddEditPeriodoView.setNombre("");
        }
    }

    @Override
    public void savePeriodo(String nombre) {
        if (isNewPeriodo()) {
            createPeriodo(nombre);
        } else {
            updatePeriodo(nombre);
        }
    }

    @SuppressWarnings("all")
    @Override
    public void populatePeriodo() {
        if (isNewPeriodo()) {
            throw new RuntimeException("populatePeriodo() should only be called when editing.");
        }
        mPeriodosDataSource.getPeriodo(mPeriodoId, mAsignaturasDataSource, this);
    }

    @Override
    public void onPeriodoLoaded(Periodo periodo) {
        mPeriodoPromedio = periodo.getPromedio();
        if (mAddEditPeriodoView.isActive()) {
            mAddEditPeriodoView.setNombre(periodo.getNombre());
        }
    }

    @Override
    public void onDataNotAvailable() {
        if (mAddEditPeriodoView.isActive()) {
            mAddEditPeriodoView.showEmptyPeriodoError();
        }
    }

    private boolean isNewPeriodo() {
        return mPeriodoId == null;
    }

    private void createPeriodo(String nombre) {
        Periodo newPeriodo = new Periodo(nombre);
        if (newPeriodo.isEmpty()) {
            mAddEditPeriodoView.showEmptyPeriodoError();
        } else {
            mPeriodosDataSource.savePeriodo(newPeriodo);
            mAddEditPeriodoView.showPeriodos();
        }
    }

    private void updatePeriodo(String nombre) {
        if (isNewPeriodo()) {
            throw new RuntimeException("createPeriodo() should only be called when editing.");
        }
        mPeriodosDataSource.updatePeriodo(new Periodo(mPeriodoId, nombre, mPeriodoPromedio));
        mAddEditPeriodoView.showPeriodos();
    }

}
