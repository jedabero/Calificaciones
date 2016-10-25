package co.kinbu.calificaciones.asignaturas;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Strings;

import co.kinbu.calificaciones.data.Asignatura;
import co.kinbu.calificaciones.data.Periodo;
import co.kinbu.calificaciones.data.source.AsignaturasRepository;
import co.kinbu.calificaciones.data.source.PeriodosDataSource;
import co.kinbu.calificaciones.data.source.PeriodosRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 * Created by jedabero on 24/10/16.
 */

public final class AsignaturasPresenter implements AsignaturasContract.Presenter {

    private final PeriodosRepository mPeriodosRepository;
    private final AsignaturasRepository mAsignaturasRepository;

    private final AsignaturasContract.View mAsignaturasView;

    @Nullable
    private String mPeriodoId;

    private boolean mFirstLoad = true;

    public AsignaturasPresenter(@Nullable String periodoId,
                                @NonNull PeriodosRepository periodosRepository,
                                @NonNull AsignaturasRepository asignaturasRepository,
                                @NonNull AsignaturasContract.View asignaturasView) {
        mPeriodoId = periodoId;
        mAsignaturasRepository = checkNotNull(asignaturasRepository);
        mPeriodosRepository = checkNotNull(periodosRepository);
        mAsignaturasView = checkNotNull(asignaturasView);

        mAsignaturasView.setPresenter(this);
    }

    @Override
    public void start() {
        loadPeriodo(false);
    }

    @Override
    public void editPeriodo() {
        if (Strings.isNullOrEmpty(mPeriodoId)) {
            mAsignaturasView.showMissingPeriodo();
            return;
        }
        mAsignaturasView.showEditPeriodo(mPeriodoId);
    }

    @Override
    public void deletePeriodo() {
        if (Strings.isNullOrEmpty(mPeriodoId)) {
            mAsignaturasView.showMissingPeriodo();
            return;
        }
        mPeriodosRepository.deletePeriodo(mPeriodoId);
        mAsignaturasView.showPeriodoDeleted();
    }

    @Override
    public void loadPeriodo(boolean forceUpdate) {
        loadPeriodo(forceUpdate || mFirstLoad, true);
        mFirstLoad = true;
    }

    private void loadPeriodo(boolean forceUpdate, final boolean showLoadingUI) {
        if (Strings.isNullOrEmpty(mPeriodoId)) {
            mAsignaturasView.showMissingPeriodo();
            return;
        }
        if (showLoadingUI) {
            mAsignaturasView.setLoadingInticator(true);
        }
        if (forceUpdate) {
            mPeriodosRepository.refreshPeriodos();
        }

        mPeriodosRepository.getPeriodo(mPeriodoId, mAsignaturasRepository, new PeriodosDataSource.LoadPeriodoCallback() {
            @Override
            public void onPeriodoLoaded(Periodo periodo) {
                if (!mAsignaturasView.isActive()) {
                    return;
                }
                if (showLoadingUI) {
                    mAsignaturasView.setLoadingInticator(false);
                }
                if (null == periodo) {
                    mAsignaturasView.showMissingPeriodo();
                } else {
                    showPeriodo(periodo);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (!mAsignaturasView.isActive()) {
                    return;
                }
                mAsignaturasView.showMissingPeriodo();
            }
        });
    }

    private void showPeriodo(@NonNull Periodo periodo) {
        mAsignaturasView.showNombre(periodo.getNombre());
        mAsignaturasView.showPromedio(periodo.getPromedio());
        if (periodo.getAsignaturas().isEmpty()) {
            mAsignaturasView.showNoAsignaturas();
        } else {
            mAsignaturasView.showAsignaturas(periodo.getAsignaturas());
        }
    }


    @Override
    public void addNewAsignatura() {
        mAsignaturasView.showAddAsignatura();
    }

    @Override
    public void openAsignaturaDetails(@NonNull Asignatura asignatura) {
        checkNotNull(asignatura);
        mAsignaturasView.showAsignaturaDetailsUi(asignatura.getId());
    }
}
