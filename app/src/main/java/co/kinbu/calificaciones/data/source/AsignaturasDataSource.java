package co.kinbu.calificaciones.data.source;

import android.support.annotation.NonNull;

import java.util.List;

import co.kinbu.calificaciones.data.Asignatura;

/**
 * NotasDataSource
 * Created by jedabero on 14/04/16.
 */
public interface AsignaturasDataSource {

    interface LoadAsignaturasCallback {
        void onNotasLoaded(List<Asignatura> asignaturas);
        void onDataNotAvailable();
    }

    interface LoadAsignaturaCallback {
        void onNotaLoaded(Asignatura asignatura);
        void onDataNotAvailable();
    }

    void getAsignaturas(@NonNull LoadAsignaturasCallback callback);

    void getAsignatura(@NonNull String asignaturaId, @NonNull LoadAsignaturaCallback callback);

    void saveAsignatura(@NonNull Asignatura asignatura);

    void deleteAsignatura(@NonNull String asignaturaId);

}
