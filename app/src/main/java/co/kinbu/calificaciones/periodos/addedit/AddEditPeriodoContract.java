package co.kinbu.calificaciones.periodos.addedit;

import co.kinbu.calificaciones.BasePresenter;
import co.kinbu.calificaciones.BaseView;

/**
 *
 * Created by jedabero on 22/10/16.
 */

public interface AddEditPeriodoContract {

    interface View extends BaseView<AddEditPeriodoContract.Presenter> {

        void showEmptyPeriodoError();

        void showPeriodos();

        void setNombre(String nombre);

        boolean isActive();

    }

    interface Presenter extends BasePresenter {

        void savePeriodo(String nombre);

        void populatePeriodo();

    }

}
