package co.kinbu.calificaciones.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import co.kinbu.calificaciones.data.Asignatura;
import co.kinbu.calificaciones.data.source.AsignaturasDataSource;
import co.kinbu.calificaciones.data.source.local.PersistenceContract.AsignaturaEntry;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * AsignaturasLocalDataSource
 * Created by jedabero on 14/04/16.
 */
public class AsignaturasLocalDataSource implements AsignaturasDataSource {

    private static AsignaturasLocalDataSource INSTANCE;

    private DbHelper mDbHelper;

    private AsignaturasLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
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
        List<Asignatura> asignaturas = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                AsignaturaEntry.COLUMN_NAME_ID,
                AsignaturaEntry.COLUMN_NAME_NOMBRE,
                AsignaturaEntry.COLUMN_NAME_DEFINITIVA
        };

        Cursor c = db.query(AsignaturaEntry.TABLE_NAME, projection, null, null, null, null, null);
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String id = c.getString(c.getColumnIndex(AsignaturaEntry.COLUMN_NAME_ID));
                String nombre = c.getString(c.getColumnIndex(AsignaturaEntry.COLUMN_NAME_NOMBRE));
                Double definitiva = c.getDouble(c.getColumnIndex(AsignaturaEntry.COLUMN_NAME_DEFINITIVA));

                Asignatura asignatura = new Asignatura(id, nombre, definitiva);
                asignaturas.add(asignatura);
            }
        }
        if (c != null) {
            c.close();
        }
        db.close();

        if (asignaturas.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onAsignaturasLoaded(asignaturas);
        }
    }

    @Override
    public void getAsignatura(@NonNull String asignaturaId, @NonNull LoadAsignaturaCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                AsignaturaEntry.COLUMN_NAME_NOMBRE,
                AsignaturaEntry.COLUMN_NAME_DEFINITIVA
        };

        String selection = AsignaturaEntry.COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = { asignaturaId };

        Cursor c = db.query(AsignaturaEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        Asignatura asignatura = null;
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            String nombre = c.getString(c.getColumnIndex(AsignaturaEntry.COLUMN_NAME_NOMBRE));
            Double definitiva = c.getDouble(c.getColumnIndex(AsignaturaEntry.COLUMN_NAME_DEFINITIVA));

            asignatura = new Asignatura(asignaturaId, nombre, definitiva);
        }
        if (c != null) {
            c.close();
        }
        db.close();

        if (asignatura == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onAsignaturaLoaded(asignatura);
        }
    }

    @Override
    public void saveAsignatura(@NonNull Asignatura asignatura) {
        checkNotNull(asignatura);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AsignaturaEntry.COLUMN_NAME_ID, asignatura.getId());
        values.put(AsignaturaEntry.COLUMN_NAME_NOMBRE, asignatura.getNombre());
        values.put(AsignaturaEntry.COLUMN_NAME_DEFINITIVA, asignatura.getDefinitiva());

        db.insert(AsignaturaEntry.TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void updateAsignatura(@NonNull Asignatura asignatura) {
        checkNotNull(asignatura);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AsignaturaEntry.COLUMN_NAME_NOMBRE, asignatura.getNombre());
        values.put(AsignaturaEntry.COLUMN_NAME_DEFINITIVA, asignatura.getDefinitiva());

        String whereClause = AsignaturaEntry.COLUMN_NAME_ID + " LIKE ?";
        String[] whereArgs = { asignatura.getId() };

        db.update(AsignaturaEntry.TABLE_NAME,values, whereClause, whereArgs);

        db.close();
    }

    @Override
    public void deleteAsignatura(@NonNull String asignaturaId) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String whereClause = AsignaturaEntry.COLUMN_NAME_ID + " LIKE ?";
        String[] whereArgs = { asignaturaId };

        db.delete(AsignaturaEntry.TABLE_NAME, whereClause, whereArgs);

        db.close();
    }
}