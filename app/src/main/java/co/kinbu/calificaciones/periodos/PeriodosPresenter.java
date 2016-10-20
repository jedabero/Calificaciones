package co.kinbu.calificaciones.periodos;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.util.List;

import co.kinbu.calificaciones.data.Periodo;
import co.kinbu.calificaciones.data.source.PeriodosDataSource;
import co.kinbu.calificaciones.data.source.PeriodosRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 * Created by jedabero on 19/10/16.
 */

public final class PeriodosPresenter implements PeriodosContract.Presenter {

    private final PeriodosRepository mPeriodosRepository;

    private final PeriodosContract.View mPeriodosView;

    private boolean mFirstLoad = true;

    public PeriodosPresenter(@NonNull PeriodosRepository periodosRepository,
                             @NonNull PeriodosContract.View periodosView) {
        mPeriodosRepository = checkNotNull(periodosRepository);
        mPeriodosView = checkNotNull(periodosView);

        mPeriodosView.setPresenter(this);
    }

    @Override
    public void start() {
        loadPeriodos(false);
    }

    @Override
    public void result(int requestCode, int resultCode) {
        if (/* REQUEST_ADD_PERIODO && */ Activity.RESULT_OK == resultCode) {
            mPeriodosView.showSuccessfullySavedMessage();
        }
    }

    @Override
    public void loadPeriodos(boolean fordeUpdate) {
        loadPeriodos(fordeUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadPeriodos(boolean fordeUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mPeriodosView.setLoadingIndicator(true);
        }
        if (fordeUpdate) {
            mPeriodosRepository.refreshPeriodos();
        }

        mPeriodosRepository.getPeriodos(new PeriodosDataSource.LoadPeriodosCallback() {
            @Override
            public void onPeriodosLoaded(List<Periodo> periodos) {
                if (!mPeriodosView.isActive()) {
                    return;
                }
                if (showLoadingUI) {
                    mPeriodosView.setLoadingIndicator(true);
                }
                processPeriodos(periodos);
            }

            @Override
            public void onDataNotAvailable() {
                if (!mPeriodosView.isActive()) {
                    return;
                }
                mPeriodosView.showLoadingPeriodosError();
            }
        });
    }

    private void processPeriodos(List<Periodo> periodos) {
        if (periodos.isEmpty()) {
            mPeriodosView.showNoPeriodos();
        } else {
            mPeriodosView.showPeriodos(periodos);
        }
    }

    @Override
    public void addNewPeriodo() {
        mPeriodosView.showAddPeriodo();
    }

    @Override
    public void openPeriodoDetails(@NonNull Periodo periodo) {
        checkNotNull(periodo);
        mPeriodosView.showPeriodoDetailsUi(periodo.getId());
    }
}
