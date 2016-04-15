package co.kinbu.calificaciones.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import co.kinbu.calificaciones.data.Asignatura;
import co.kinbu.calificaciones.data.source.AsignaturasDataSource;

/**
 * AsignaturasLocalDataSource
 * Created by jedabero on 14/04/16.
 */
public class AsignaturasLocalDataSource implements AsignaturasDataSource {

    private static AsignaturasLocalDataSource INSTANCE;

    private DbHelper mDbHelper;

    private AsignaturasLocalDataSource(@NonNull Context context) {
        mDbHelper = new DbHelper(context);
    }

    public static AsignaturasLocalDataSource getINSTANCE(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new AsignaturasLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getAsignaturas(@NonNull LoadAsignaturasCallback callback) {

    }

    @Override
    public void getAsignatura(@NonNull String asignaturaId, @NonNull LoadAsignaturaCallback callback) {

    }

    @Override
    public void saveAsignatura(@NonNull Asignatura asignatura) {

    }

    @Override
    public void deleteAsignatura(@NonNull String asignaturaId) {

    }
}
