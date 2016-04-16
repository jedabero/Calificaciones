package co.kinbu.calificaciones.data.source;

import android.support.annotation.NonNull;

import java.util.List;

import co.kinbu.calificaciones.data.Periodo;

/**
 * PeriodosDataSource
 * Created by jedabero on 15/04/16.
 */
public interface PeriodosDataSource {

    interface LoadPeriodosCallback {
        void onPeriodosLoaded(List<Periodo> periodos);
        void onDataNotAvailable();
    }

    interface LoadPeriodoCallback {
        void onPeriodoLoaded(Periodo periodo);
        void onDataNotAvailable();
    }

    void getPeriodos(@NonNull LoadPeriodosCallback callback);

    void getPeriodo(@NonNull String periodoId, @NonNull AsignaturasDataSource asignaturasDataSource,
                    @NonNull LoadPeriodoCallback callback);

    void savePeriodo(@NonNull Periodo periodo);

    void updatePeriodo(@NonNull Periodo periodo);

    void deletePeriodo(@NonNull String periodoId);

}
