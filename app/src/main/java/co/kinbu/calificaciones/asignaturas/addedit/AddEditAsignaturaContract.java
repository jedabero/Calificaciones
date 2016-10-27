package co.kinbu.calificaciones.asignaturas.addedit;

import co.kinbu.calificaciones.BasePresenter;
import co.kinbu.calificaciones.BaseView;

/**
 *
 * Created by jedabero on 25/10/16.
 */

public interface AddEditAsignaturaContract {

    interface View extends BaseView<AddEditAsignaturaContract.Presenter> {

        void showEmptyAsignaturaError();

        void showAsignaturas(String periodoId);

        void setNombre(String nombre);

        boolean isActive();

    }

    interface Presenter extends BasePresenter {

        void saveAsignatura(String nombre);

        void populateAsignatura();

    }

}
