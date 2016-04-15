package co.kinbu.calificaciones.data.source;

import android.support.annotation.NonNull;

import java.util.List;

import co.kinbu.calificaciones.data.Nota;

/**
 * NotasDataSource
 * Created by jedabero on 14/04/16.
 */
public interface NotasDataSource {

    interface LoadNotasCallback {
        void onNotasLoaded(List<Nota> notas);
        void onDataNotAvailable();
    }

    interface LoadNotaCallback {
        void onNotaLoaded(Nota nota);
        void onDataNotAvailable();
    }

    void getNotas(@NonNull LoadNotasCallback callback);

    void getNotas(@NonNull String asignaturaId, @NonNull LoadNotasCallback callback);

    void getNota(@NonNull String notaId, @NonNull LoadNotaCallback callback);

    void saveNota(@NonNull Nota nota);

    void deleteNota(@NonNull String notaId);

}
