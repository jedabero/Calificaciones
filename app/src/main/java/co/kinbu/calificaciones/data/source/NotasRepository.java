package co.kinbu.calificaciones.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import co.kinbu.calificaciones.data.Nota;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * NotasRepository
 * Created by jedabero on 14/04/16.
 */
public class NotasRepository implements NotasDataSource {

    private static NotasRepository INSTANCE = null;

    private final NotasDataSource mNotasLocalDataSource;

    Map<String, Nota> mCachedNotas;

    boolean mCacheIsDirty = false;

    private NotasRepository(@NonNull NotasDataSource notasLocalDataSource) {
        this.mNotasLocalDataSource = checkNotNull(notasLocalDataSource);
    }

    public static NotasRepository getInstance(NotasDataSource notasLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new NotasRepository(notasLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public void refreshNotas() {
        mCacheIsDirty = true;
    }

    @Override
    public void getNotas(@NonNull final LoadNotasCallback callback, @Nullable String selection,
                         @Nullable String[] selectionArgs) {
        checkNotNull(callback);

        if (mCachedNotas != null && !mCacheIsDirty) {
            callback.onNotasLoaded(new ArrayList<>(mCachedNotas.values()));
            return;
        }

        //if (mCacheIsDirty)
        mNotasLocalDataSource.getNotas(new LoadNotasCallback() {
            @Override
            public void onNotasLoaded(List<Nota> notas) {
                refreshCache(notas);
                callback.onNotasLoaded(notas);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        }, selection, selectionArgs);
    }

    @Override
    public void getNotas(@NonNull String asignaturaId, @NonNull LoadNotasCallback callback) {
        checkNotNull(asignaturaId);
        checkNotNull(callback);

        List<Nota> notasAsignatura = new ArrayList<>();

        if (mCachedNotas != null && !mCacheIsDirty) {
            for (Map.Entry<String, Nota> entry : mCachedNotas.entrySet()) {
                Nota nota = entry.getValue();
                if (nota.getAsignaturaId().equals(asignaturaId)) {
                    notasAsignatura.add(nota);
                }
            }
            if (notasAsignatura.isEmpty()) {
                callback.onDataNotAvailable();
            } else {
                callback.onNotasLoaded(notasAsignatura);
            }
            return;
        }

        //if (mCacheIsDirty)
        mNotasLocalDataSource.getNotas(asignaturaId, callback);
    }

    @Override
    public void getNota(@NonNull String notaId, @NonNull LoadNotaCallback callback) {
        checkNotNull(notaId);
        checkNotNull(callback);

        Nota cachedNota = getNotaWithId(notaId);

        if (cachedNota != null){
            callback.onNotaLoaded(cachedNota);
            return;
        }

        mNotasLocalDataSource.getNota(notaId, callback);
    }

    @Override
    public void saveNota(@NonNull Nota nota) {
        mNotasLocalDataSource.saveNota(nota);
        setupCachedNotas();
        mCachedNotas.put(nota.getId(), nota);
    }

    @Override
    public void updateNota(@NonNull Nota nota) {
        mNotasLocalDataSource.updateNota(nota);
        setupCachedNotas();
        mCachedNotas.put(nota.getId(), nota);
    }

    @Override
    public void deleteNota(@NonNull String notaId) {
        mNotasLocalDataSource.deleteNota(notaId);

        mCachedNotas.remove(notaId);
    }

    @Nullable
    private Nota getNotaWithId(@NonNull String id) {
        checkNotNull(id);
        if (mCachedNotas == null || mCachedNotas.isEmpty()) {
            return null;
        } else {
            return mCachedNotas.get(id);
        }
    }

    private void refreshCache(List<Nota> notas) {
        setupCachedNotas();
        mCachedNotas.clear();
        for (Nota nota : notas) {
            mCachedNotas.put(nota.getId(), nota);
        }
        mCacheIsDirty = false;
    }

    private void setupCachedNotas() {
        if (mCachedNotas == null) {
            mCachedNotas = new LinkedHashMap<>();
        }
    }

}
