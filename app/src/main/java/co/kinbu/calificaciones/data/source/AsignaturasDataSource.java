package co.kinbu.calificaciones.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import co.kinbu.calificaciones.data.Asignatura;

/**
 * NotasDataSource
 * Created by jedabero on 14/04/16.
 */
public interface AsignaturasDataSource {

    interface LoadAsignaturasCallback {
        void onAsignaturasLoaded(List<Asignatura> asignaturas);
        void onDataNotAvailable();
    }

    interface LoadAsignaturaCallback {
        void onAsignaturaLoaded(Asignatura asignatura);
        void onDataNotAvailable();
    }

    void getAsignaturas(@NonNull LoadAsignaturasCallback callback, @Nullable String selection,
                        @Nullable String[] selectionArgs);

    void getAsignaturas(@NonNull String periodoId, @NonNull LoadAsignaturasCallback callback);

    void getAsignatura(@NonNull String asignaturaId, @NonNull NotasDataSource notasDataSource,
                       @NonNull LoadAsignaturaCallback callback);

    void saveAsignatura(@NonNull Asignatura asignatura);

    void updateAsignatura(@NonNull Asignatura asignatura);

    void deleteAsignatura(@NonNull String asignaturaId);

}
