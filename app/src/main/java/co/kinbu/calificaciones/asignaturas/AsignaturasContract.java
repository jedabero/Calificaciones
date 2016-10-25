package co.kinbu.calificaciones.asignaturas;

import android.support.annotation.NonNull;

import java.util.List;

import co.kinbu.calificaciones.BasePresenter;
import co.kinbu.calificaciones.BaseView;
import co.kinbu.calificaciones.data.Asignatura;

/**
 *
 * Created by jedabero on 23/10/16.
 */

public interface AsignaturasContract {

    interface View extends BaseView<Presenter> {

        void setLoadingInticator(boolean active);

        void showMissingPeriodo();

        void showNombre(String nombre);

        void showPromedio(double promedio);

        void showEditPeriodo(String periodoId);

        void showPeriodoDeleted();

        void showAsignaturas(List<Asignatura> asignaturas);

        void showAddAsignatura();

        void showAsignaturaDetailsUi(String asignaturaId);

        void showLoadingAsignaturasError();

        void showNoAsignaturas();

        void showSuccessfullySavedMessage();

        boolean isActive();

    }

    interface Presenter extends BasePresenter {

        void editPeriodo();

        void deletePeriodo();

        void loadPeriodo(boolean forceUpdate);

        void addNewAsignatura();

        void openAsignaturaDetails(@NonNull Asignatura asignatura);

    }

}
