package co.kinbu.calificaciones.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import co.kinbu.calificaciones.data.Nota;
import co.kinbu.calificaciones.data.source.NotasDataSource;
import co.kinbu.calificaciones.data.source.local.PersistenceContract.NotaEntry;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * NotasLocalDataSource
 * Created by jedabero on 14/04/16.
 */
public class NotasLocalDataSource implements NotasDataSource {

    public static NotasLocalDataSource INSTANCE;

    private DbHelper mDbHelper;

    private NotasLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
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
        List<Nota> notas = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                NotaEntry.COLUMN_NAME_ID,
                NotaEntry.COLUMN_NAME_ASIGNATURA_ID,
                NotaEntry.COLUMN_NAME_VALOR,
                NotaEntry.COLUMN_NAME_PESO
        };

        Cursor c = db.query(NotaEntry.TABLE_NAME, projection, null, null, null, null, null);
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String id = c.getString(c.getColumnIndex(NotaEntry.COLUMN_NAME_ID));
                String asignaturaId = c.getString(c.getColumnIndex(NotaEntry.COLUMN_NAME_ASIGNATURA_ID));
                Double valor = c.getDouble(c.getColumnIndex(NotaEntry.COLUMN_NAME_VALOR));
                Integer peso = c.getInt(c.getColumnIndex(NotaEntry.COLUMN_NAME_PESO));

                Nota nota = new Nota(id, valor, peso);
                nota.setAsignaturaId(asignaturaId);
                notas.add(nota);
            }
        }
        if (c != null) {
            c.close();
        }
        db.close();

        if (notas.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onNotasLoaded(notas);
        }
    }

    @Override
    public void getNotas(@NonNull String asignaturaId, @NonNull LoadNotasCallback callback) {
        List<Nota> notas = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                NotaEntry.COLUMN_NAME_ID,
                NotaEntry.COLUMN_NAME_VALOR,
                NotaEntry.COLUMN_NAME_PESO
        };

        String selection = NotaEntry.COLUMN_NAME_ASIGNATURA_ID + " LIKE ?";
        String[] selectionArgs = { asignaturaId };

        Cursor c = db.query(NotaEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String id = c.getString(c.getColumnIndex(NotaEntry.COLUMN_NAME_ID));
                Double valor = c.getDouble(c.getColumnIndex(NotaEntry.COLUMN_NAME_VALOR));
                Integer peso = c.getInt(c.getColumnIndex(NotaEntry.COLUMN_NAME_PESO));

                Nota nota = new Nota(id, valor, peso);
                nota.setAsignaturaId(asignaturaId);
                notas.add(nota);
            }
        }
        if (c != null) {
            c.close();
        }
        db.close();

        if (notas.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onNotasLoaded(notas);
        }
    }

    @Override
    public void getNota(@NonNull String notaId, @NonNull LoadNotaCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                NotaEntry.COLUMN_NAME_ASIGNATURA_ID,
                NotaEntry.COLUMN_NAME_VALOR,
                NotaEntry.COLUMN_NAME_PESO
        };

        String selection = NotaEntry.COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = { notaId };

        Cursor c = db.query(NotaEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        Nota nota = null;
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            String asignaturaId = c.getString(c.getColumnIndex(NotaEntry.COLUMN_NAME_ASIGNATURA_ID));
            Double valor = c.getDouble(c.getColumnIndex(NotaEntry.COLUMN_NAME_VALOR));
            Integer peso = c.getInt(c.getColumnIndex(NotaEntry.COLUMN_NAME_PESO));

            nota = new Nota(notaId, valor, peso);
            nota.setAsignaturaId(asignaturaId);
        }
        if (c != null) {
            c.close();
        }
        db.close();

        if (nota == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onNotaLoaded(nota);
        }
    }

    @Override
    public void saveNota(@NonNull Nota nota) {
        checkNotNull(nota);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NotaEntry.COLUMN_NAME_ID, nota.getId());
        values.put(NotaEntry.COLUMN_NAME_ASIGNATURA_ID, nota.getAsignaturaId());
        values.put(NotaEntry.COLUMN_NAME_VALOR, nota.getValor());
        values.put(NotaEntry.COLUMN_NAME_PESO, nota.getPeso());

        db.insert(NotaEntry.TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void updateNota(@NonNull Nota nota) {
        checkNotNull(nota);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NotaEntry.COLUMN_NAME_ASIGNATURA_ID, nota.getAsignaturaId());
        values.put(NotaEntry.COLUMN_NAME_VALOR, nota.getValor());
        values.put(NotaEntry.COLUMN_NAME_PESO, nota.getPeso());

        String whereClause = NotaEntry.COLUMN_NAME_ID + " LIKE ?";
        String[] whereArgs = { nota.getId() };

        db.update(NotaEntry.TABLE_NAME, values, whereClause, whereArgs);

        db.close();
    }

    @Override
    public void deleteNota(@NonNull String notaId) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String whereClause = NotaEntry.COLUMN_NAME_ID + " LIKE ?";
        String[] whereArgs = { notaId };

        db.delete(NotaEntry.TABLE_NAME, whereClause, whereArgs);

        db.close();
    }
}
