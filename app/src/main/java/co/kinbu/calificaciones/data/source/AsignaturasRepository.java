package co.kinbu.calificaciones.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import co.kinbu.calificaciones.data.Asignatura;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * AsignaturasRepository
 * Created by jedabero on 14/04/16.
 */
public class AsignaturasRepository implements AsignaturasDataSource {

    private static AsignaturasRepository INSTANCE = null;

    private final AsignaturasDataSource mAsignaturasLocalDataSource;

    Map<String, Asignatura> mCachedAsignaturas;

    boolean mCacheIsDirty = false;

    private AsignaturasRepository(@NonNull AsignaturasDataSource asignaturasDataSource) {
        this.mAsignaturasLocalDataSource = checkNotNull(asignaturasDataSource);
    }

    public static AsignaturasRepository getInstance(AsignaturasDataSource asignaturasDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new AsignaturasRepository(asignaturasDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public void refreshAsignaturas() {
        mCacheIsDirty = true;
    }


    @Override
    public void getAsignaturas(@NonNull final LoadAsignaturasCallback callback) {
        checkNotNull(callback);
        if (mCachedAsignaturas != null && !mCacheIsDirty) {
            callback.onAsignaturasLoaded(new ArrayList<>(mCachedAsignaturas.values()));
            return;
        }

        mAsignaturasLocalDataSource.getAsignaturas(new LoadAsignaturasCallback() {
            @Override
            public void onAsignaturasLoaded(List<Asignatura> asignaturas) {
                refreshCache(asignaturas);
                callback.onAsignaturasLoaded(asignaturas);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getAsignatura(@NonNull String asignaturaId, @NonNull LoadAsignaturaCallback callback) {
        checkNotNull(asignaturaId);
        checkNotNull(callback);

        Asignatura cachedAsignatura = getAsignaturaWithId(asignaturaId);
        if (cachedAsignatura != null) {
            callback.onAsignaturaLoaded(cachedAsignatura);
            return;
        }

        mAsignaturasLocalDataSource.getAsignatura(asignaturaId, callback);
    }

    @Override
    public void saveAsignatura(@NonNull Asignatura asignatura) {
        mAsignaturasLocalDataSource.saveAsignatura(asignatura);
        setupCachedAsignaturas();
        mCachedAsignaturas.put(asignatura.getId(), asignatura);
    }

    @Override
    public void updateAsignatura(@NonNull Asignatura asignatura) {
        mAsignaturasLocalDataSource.updateAsignatura(asignatura);
        setupCachedAsignaturas();
        mCachedAsignaturas.put(asignatura.getId(), asignatura);
    }

    @Override
    public void deleteAsignatura(@NonNull String asignaturaId) {
        mAsignaturasLocalDataSource.deleteAsignatura(asignaturaId);

        mCachedAsignaturas.remove(asignaturaId);
    }

    @Nullable
    private Asignatura getAsignaturaWithId(@NonNull String asignaturaId) {
        checkNotNull(asignaturaId);
        if (mCachedAsignaturas == null || mCachedAsignaturas.isEmpty()) {
            return null;
        } else {
            return mCachedAsignaturas.get(asignaturaId);
        }
    }

    private void refreshCache(List<Asignatura> asignaturas) {
        setupCachedAsignaturas();
        mCachedAsignaturas.clear();
        for (Asignatura asignatura : asignaturas) {
            mCachedAsignaturas.put(asignatura.getId(), asignatura);
        }
        mCacheIsDirty = false;
    }

    private void setupCachedAsignaturas() {
        if (mCachedAsignaturas == null) {
            mCachedAsignaturas = new LinkedHashMap<>();
        }
    }
}
