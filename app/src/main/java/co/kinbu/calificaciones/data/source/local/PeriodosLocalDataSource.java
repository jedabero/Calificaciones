package co.kinbu.calificaciones.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import co.kinbu.calificaciones.data.Asignatura;
import co.kinbu.calificaciones.data.Periodo;
import co.kinbu.calificaciones.data.source.AsignaturasDataSource;
import co.kinbu.calificaciones.data.source.PeriodosDataSource;
import co.kinbu.calificaciones.data.source.local.PersistenceContract.PeriodoEntry;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * PeriodosLocalDataSource
 * Created by jedabero on 15/04/16.
 */
public class PeriodosLocalDataSource implements PeriodosDataSource {

    public static PeriodosLocalDataSource INSTANCE;

    private DbHelper mDbHelper;

    private PeriodosLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new DbHelper(context);
    }

    public static PeriodosLocalDataSource getInstance(@NonNull Context context) {
        checkNotNull(context);
        if (INSTANCE == null) {
            INSTANCE = new PeriodosLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getPeriodos(@NonNull LoadPeriodosCallback callback) {
        List<Periodo> periodos = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                PeriodoEntry.COLUMN_NAME_ID,
                PeriodoEntry.COLUMN_NAME_NOMBRE,
                PeriodoEntry.COLUMN_NAME_PROMEDIO
        };

        Cursor c = db.query(PeriodoEntry.TABLE_NAME, projection, null, null, null, null, null);
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String id = c.getString(c.getColumnIndex(PeriodoEntry.COLUMN_NAME_ID));
                String nombre = c.getString(c.getColumnIndex(PeriodoEntry.COLUMN_NAME_NOMBRE));
                double promedio = c.getDouble(c.getColumnIndex(PeriodoEntry.COLUMN_NAME_PROMEDIO));

                Periodo periodo = new Periodo(id, nombre, promedio);
                periodos.add(periodo);
            }
        }
        if (c != null) {
            c.close();
        }
        db.close();

        if (periodos.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onPeriodosLoaded(periodos);
        }
    }

    @Override
    public void getPeriodo(@NonNull String periodoId,
                           @NonNull AsignaturasDataSource asignaturasDataSource,
                           @NonNull LoadPeriodoCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                PeriodoEntry.COLUMN_NAME_NOMBRE,
                PeriodoEntry.COLUMN_NAME_PROMEDIO
        };

        String selection = PeriodoEntry.COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = { periodoId };

        Cursor c = db.query(PeriodoEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        final Periodo periodo;
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            String nombre = c.getString(c.getColumnIndex(PeriodoEntry.COLUMN_NAME_NOMBRE));
            double promedio = c.getDouble(c.getColumnIndex(PeriodoEntry.COLUMN_NAME_PROMEDIO));

            periodo = new Periodo(periodoId, nombre, promedio);
            asignaturasDataSource.getAsignaturas(periodoId, new AsignaturasDataSource.LoadAsignaturasCallback() {
                @Override
                public void onAsignaturasLoaded(List<Asignatura> asignaturas) {
                    periodo.getAsignaturas().addAll(asignaturas);
                }

                @Override
                public void onDataNotAvailable() {}
            });
        } else {
            periodo = null;
        }
        if (c!= null) {
            c.close();
        }
        db.close();

        if (periodo == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onPeriodoLoaded(periodo);
        }
    }

    @Override
    public void savePeriodo(@NonNull Periodo periodo) {
        checkNotNull(periodo);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PeriodoEntry.COLUMN_NAME_ID, periodo.getId());
        values.put(PeriodoEntry.COLUMN_NAME_NOMBRE, periodo.getNombre());
        values.put(PeriodoEntry.COLUMN_NAME_PROMEDIO, periodo.getPromedio());

        db.insert(PeriodoEntry.TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void updatePeriodo(@NonNull Periodo periodo) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PeriodoEntry.COLUMN_NAME_NOMBRE, periodo.getNombre());
        values.put(PeriodoEntry.COLUMN_NAME_PROMEDIO, periodo.getPromedio());

        String whereClause = PeriodoEntry.COLUMN_NAME_ID + " LIKE ?";
        String[] whereArgs = { periodo.getId() };

        db.update(PeriodoEntry.TABLE_NAME, values, whereClause, whereArgs);

        db.close();
    }

    @Override
    public void deletePeriodo(@NonNull String periodoId) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String whereClause = PeriodoEntry.COLUMN_NAME_ID + " LIKE ?";
        String[] whereArgs = { periodoId };

        db.delete(PeriodoEntry.TABLE_NAME, whereClause, whereArgs);

        db.close();
    }
}
