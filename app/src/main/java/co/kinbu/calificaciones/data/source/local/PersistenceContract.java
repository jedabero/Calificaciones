package co.kinbu.calificaciones.data.source.local;

import android.provider.BaseColumns;

/**
 * PersistenceContract
 * Created by jedabero on 14/04/16.
 */
public final class PersistenceContract {

    public PersistenceContract() {}

    public static abstract class PeriodoEntry implements BaseColumns {
        public static final String TABLE_NAME = "periodos";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NOMBRE = "nombre";
        public static final String COLUMN_NAME_PROMEDIO = "promedio";
    }

    public static abstract class AsignaturaEntry implements BaseColumns {
        public static final String TABLE_NAME = "asignaturas";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_PERIODO_ID = "periodo_id";
        public static final String COLUMN_NAME_NOMBRE = "nombre";
        public static final String COLUMN_NAME_DEFINITIVA = "definitiva";
    }

    public static abstract class NotaEntry implements BaseColumns {
        public static final String TABLE_NAME = "notas";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_ASIGNATURA_ID = "asignatura_id";
        public static final String COLUMN_NAME_VALOR = "valor";
        public static final String COLUMN_NAME_PESO = "peso";
    }


}
