package co.kinbu.calificaciones.periodos;

import android.support.annotation.NonNull;

import java.util.List;

import co.kinbu.calificaciones.BasePresenter;
import co.kinbu.calificaciones.BaseView;
import co.kinbu.calificaciones.data.Periodo;

/**
 *
 * Created by jedabero on 18/10/16.
 */

interface PeriodosContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showPeriodos(List<Periodo> periodos);

        void showAddPeriodo();

        void showPeriodoDetailsUi(String periodoId);

        void showLoadingPeriodosError();

        void showNoPeriodos();

        void showSuccessfullySavedMessage();

        boolean isActive();

    }

    interface Presenter extends BasePresenter {

        void loadPeriodos(boolean fordeUpdate);

        void addNewPeriodo();

        void openPeriodoDetails(@NonNull Periodo periodo);

    }

}
