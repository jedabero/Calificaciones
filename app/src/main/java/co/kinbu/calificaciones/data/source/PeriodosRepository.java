package co.kinbu.calificaciones.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import co.kinbu.calificaciones.data.Periodo;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * PeriodosRepository
 * Created by jedabero on 15/04/16.
 */
public class PeriodosRepository implements PeriodosDataSource {

    private static PeriodosRepository INSTANCE = null;

    private final PeriodosDataSource mPeriodosLocalDataSource;

    Map<String, Periodo> mCachedPeriodos;

    boolean mCacheIsDirty = false;

    private PeriodosRepository(@NonNull PeriodosDataSource periodosLocalDataSource) {
        this.mPeriodosLocalDataSource = periodosLocalDataSource;
    }

    public static PeriodosRepository getInstance(PeriodosDataSource periodosLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new PeriodosRepository(periodosLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public void refreshPeriodos() {
        mCacheIsDirty = true;
    }

    @Override
    public void getPeriodos(@NonNull final LoadPeriodosCallback callback) {
        checkNotNull(callback);

        if (mCachedPeriodos != null && ! mCacheIsDirty) {
            callback.onPeriodosLoaded(new ArrayList<>(mCachedPeriodos.values()));
            return;
        }

        mPeriodosLocalDataSource.getPeriodos(new LoadPeriodosCallback() {
            @Override
            public void onPeriodosLoaded(List<Periodo> periodos) {
                refreshCache(periodos);
                callback.onPeriodosLoaded(periodos);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getPeriodo(@NonNull String periodoId, @NonNull AsignaturasDataSource asignaturasDataSource, @NonNull LoadPeriodoCallback callback) {
        checkNotNull(periodoId);
        checkNotNull(asignaturasDataSource);
        checkNotNull(callback);

        Periodo cachedPeriodo = getPeriodoWithId(periodoId);

        if (cachedPeriodo != null) {
            callback.onPeriodoLoaded(cachedPeriodo);
            return;
        }

        mPeriodosLocalDataSource.getPeriodo(periodoId, asignaturasDataSource, callback);
    }

    @Override
    public void savePeriodo(@NonNull Periodo periodo) {
        mPeriodosLocalDataSource.savePeriodo(periodo);
        setupCachedPeriodos();
        mCachedPeriodos.put(periodo.getId(), periodo);
    }

    @Override
    public void updatePeriodo(@NonNull Periodo periodo) {
        mPeriodosLocalDataSource.updatePeriodo(periodo);
        setupCachedPeriodos();
        mCachedPeriodos.put(periodo.getId(), periodo);
    }

    @Override
    public void deletePeriodo(@NonNull String periodoId) {
        mPeriodosLocalDataSource.deletePeriodo(periodoId);

        mCachedPeriodos.remove(periodoId);
    }

    @Nullable
    private Periodo getPeriodoWithId(@NonNull String id) {
        checkNotNull(id);
        if (mCachedPeriodos == null || mCachedPeriodos.isEmpty()) {
            return null;
        } else {
            return mCachedPeriodos.get(id);
        }
    }

    private void refreshCache(List<Periodo> periodos) {
        setupCachedPeriodos();
        mCachedPeriodos.clear();
        for (Periodo periodo : periodos) {
            mCachedPeriodos.put(periodo.getId(), periodo);
        }
        mCacheIsDirty = false;
    }

    private void setupCachedPeriodos() {
        if (mCachedPeriodos == null) {
            mCachedPeriodos = new LinkedHashMap<>();
        }
    }
}
