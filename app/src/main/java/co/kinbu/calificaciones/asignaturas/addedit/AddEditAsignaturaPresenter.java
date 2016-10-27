package co.kinbu.calificaciones.asignaturas.addedit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import co.kinbu.calificaciones.data.Asignatura;
import co.kinbu.calificaciones.data.source.AsignaturasDataSource;
import co.kinbu.calificaciones.data.source.NotasDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 * Created by jedabero on 26/10/16.
 */

public final class AddEditAsignaturaPresenter implements
        AddEditAsignaturaContract.Presenter,
        AsignaturasDataSource.LoadAsignaturaCallback {

    @NonNull
    private final AsignaturasDataSource mAsignaturasDataSource;

    @NonNull
    private final NotasDataSource mNotasDataSource;

    @NonNull
    private final AddEditAsignaturaContract.View mView;

    @NonNull
    private String mPeriodoId;

    @Nullable
    private String mAsignaturaId;

    private double mAsignaturaDefinitiva = 0d;

    public AddEditAsignaturaPresenter(@NonNull String periodoId, @Nullable String asignaturaId,
                                      @NonNull AsignaturasDataSource asignaturasDataSource,
                                      @NonNull NotasDataSource notasDataSource,
                                      @NonNull AddEditAsignaturaContract.View view) {
        mPeriodoId = periodoId;
        mAsignaturaId = asignaturaId;

        mAsignaturasDataSource = checkNotNull(asignaturasDataSource);
        mNotasDataSource = checkNotNull(notasDataSource);
        mView = checkNotNull(view);

        mView.setPresenter(this);

    }

    @Override
    public void start() {
        if (!isNewAsignatura()) {
            populateAsignatura();
        } else {
            mView.setNombre("");
        }
    }

    @Override
    public void saveAsignatura(String nombre) {
        if (isNewAsignatura()) {
            createAsignatura(nombre);
        } else {
            updateAsignatura(nombre);
        }
    }

    @SuppressWarnings("all")
    @Override
    public void populateAsignatura() {
        if (isNewAsignatura()) {
            throw new RuntimeException("populateAsignatura() should only be called when editing.");
        }
        mAsignaturasDataSource.getAsignatura(mAsignaturaId, mNotasDataSource, this);
    }

    @Override
    public void onAsignaturaLoaded(Asignatura asignatura) {
        mAsignaturaDefinitiva = asignatura.getDefinitiva();
        if (mView.isActive()) {
            mView.setNombre(asignatura.getNombre());
        }
    }

    @Override
    public void onDataNotAvailable() {
        if (mView.isActive()) {
            mView.showEmptyAsignaturaError();
        }
    }

    private boolean isNewAsignatura() {
        return null == mAsignaturaId;
    }

    private void createAsignatura(String nombre) {
        Asignatura asignatura = new Asignatura(mPeriodoId, nombre);
        if (asignatura.isEmpty()) {
            mView.showEmptyAsignaturaError();
        } else {
            mAsignaturasDataSource.saveAsignatura(asignatura);
            mView.showAsignaturas(mPeriodoId);
        }
    }

    private void updateAsignatura(String nombre) {
        if (isNewAsignatura()) {
            throw new RuntimeException("updateAsignatura() should only be called when editing.");
        }
        mAsignaturasDataSource.updateAsignatura(new Asignatura(mPeriodoId, mAsignaturaId, nombre, mAsignaturaDefinitiva));
        mView.showAsignaturas(mPeriodoId);
    }

}
