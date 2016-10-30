package co.kinbu.calificaciones.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import co.kinbu.calificaciones.data.source.local.PersistenceContract.AsignaturaEntry;
import co.kinbu.calificaciones.data.source.local.PersistenceContract.NotaEntry;
import co.kinbu.calificaciones.data.source.local.PersistenceContract.PeriodoEntry;

/**
 * DbHelper
 * Created by jedabero on 14/04/16.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;

    public static final String DATABASE_NAME = "calificaciones.db";

    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String FOREIGN_KEY = " FOREIGN KEY";
    private static final String REFERENCES = " REFERENCES ";
    private static final String TEXT = " TEXT";
    private static final String INTEGER = " INTEGER";
    private static final String REAL = " REAL";
    private static final String COMMA = ",";

    private static final String SQL_CREATE_PERIODOS =
            "CREATE TABLE " + PeriodoEntry.TABLE_NAME + " (" +
                    PeriodoEntry.COLUMN_NAME_ID + TEXT + PRIMARY_KEY + COMMA +
                    PeriodoEntry.COLUMN_NAME_NOMBRE + TEXT + COMMA +
                    PeriodoEntry.COLUMN_NAME_PROMEDIO + REAL +
            ")";

    private static final String SQL_CREATE_ASIGNATURAS =
            "CREATE TABLE " + AsignaturaEntry.TABLE_NAME + " (" +
                    AsignaturaEntry.COLUMN_NAME_ID + TEXT + PRIMARY_KEY + COMMA +
                    AsignaturaEntry.COLUMN_NAME_PERIODO_ID + TEXT + COMMA +
                    AsignaturaEntry.COLUMN_NAME_NOMBRE + TEXT + COMMA +
                    AsignaturaEntry.COLUMN_NAME_DEFINITIVA + REAL + //COMMA +
                    //FOREIGN_KEY + "(" + AsignaturaEntry.COLUMN_NAME_PERIODO_ID + ") " + REFERENCES +
                    //PeriodoEntry.TABLE_NAME + " (" + PeriodoEntry.COLUMN_NAME_ID + ")" +
            ")";

    private static final String SQL_DROP_ASIGNATURAS = "DROP TABLE " + AsignaturaEntry.TABLE_NAME;

    private static final String SQL_TEMP_ASIGNATURAS_1 =
            "CREATE TEMP TABLE temp_" + AsignaturaEntry.TABLE_NAME +
                    " AS SELECT * FROM " + AsignaturaEntry.TABLE_NAME;

    private static final String SQL_POPULATE_ASIGNATURAS_1 =
            "INSERT INTO " + AsignaturaEntry.TABLE_NAME + " SELECT " +
                    AsignaturaEntry.COLUMN_NAME_ID + COMMA +
                    "0" +
                    AsignaturaEntry.COLUMN_NAME_NOMBRE + COMMA +
                    AsignaturaEntry.COLUMN_NAME_DEFINITIVA +
                    " FROM temp_" + AsignaturaEntry.TABLE_NAME;


    private static final String SQL_CREATE_NOTAS =
            "CREATE TABLE " + NotaEntry.TABLE_NAME + " (" +
                    NotaEntry.COLUMN_NAME_ID + TEXT + PRIMARY_KEY + COMMA +
                    NotaEntry.COLUMN_NAME_ASIGNATURA_ID + TEXT + COMMA +
                    NotaEntry.COLUMN_NAME_VALOR + REAL + COMMA +
                    NotaEntry.COLUMN_NAME_PESO + INTEGER + //COMMA +
                    //FOREIGN_KEY + "(" + NotaEntry.COLUMN_NAME_ASIGNATURA_ID + ") " + REFERENCES +
                    //AsignaturaEntry.TABLE_NAME + " (" + AsignaturaEntry.COLUMN_NAME_ID + ")" +
            ")";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Log.d("DBHELPER", SQL_CREATE_PERIODOS);
        db.execSQL(SQL_CREATE_PERIODOS);
        // Log.d("DBHELPER", SQL_CREATE_ASIGNATURAS);
        db.execSQL(SQL_CREATE_ASIGNATURAS);
        // Log.d("DBHELPER", SQL_CREATE_NOTAS);
        db.execSQL(SQL_CREATE_NOTAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
