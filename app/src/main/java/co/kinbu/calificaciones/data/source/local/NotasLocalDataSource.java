package co.kinbu.calificaciones.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import co.kinbu.calificaciones.data.Nota;
import co.kinbu.calificaciones.data.source.NotasDataSource;

/**
 * NotasLocalDataSource
 * Created by jedabero on 14/04/16.
 */
public class NotasLocalDataSource implements NotasDataSource {

    public static NotasLocalDataSource INSTANCE;

    private DbHelper mDbHelper;

    private NotasLocalDataSource(@NonNull Context context) {
        //TODO chn
        mDbHelper = new DbHelper(context);
    }

    public static NotasLocalDataSource getINSTANCE(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new NotasLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getNotas(@NonNull LoadNotasCallback callback) {

    }

    @Override
    public void getNotas(@NonNull String asignaturaId, @NonNull LoadNotasCallback callback) {

    }

    @Override
    public void getNota(@NonNull String notaId, @NonNull LoadNotaCallback callback) {

    }

    @Override
    public void saveNota(@NonNull Nota nota) {

    }

    @Override
    public void deleteNota(@NonNull String notaId) {

    }
}
