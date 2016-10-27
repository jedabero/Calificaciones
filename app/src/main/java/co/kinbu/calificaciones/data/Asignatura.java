package co.kinbu.calificaciones.data;

import android.support.annotation.NonNull;

import com.google.common.base.Objects;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.kinbu.calificaciones.data.source.local.PersistenceContract.AsignaturaEntry;

/**
 * Asignatura
 * Created by jedabero on 6/03/16.
 */
public class Asignatura {

    private String id;
    @NonNull
    @SerializedName(AsignaturaEntry.COLUMN_NAME_PERIODO_ID)
    private String periodoId;

    private String nombre;
    private double definitiva;

    private List<Nota> notas;

    /**
     * Instantiates a new Asignatura.
     *
     * @param periodoId the periodo id
     */
    public Asignatura(@NonNull String periodoId) {
        this(periodoId, "Asignatura");
    }

    /**
     * Instantiates a new Asignatura.
     *
     * @param periodoId the periodo id
     * @param nombre    the nombre
     */
    public Asignatura(@NonNull String periodoId, String nombre) {
        this(periodoId, UUID.randomUUID().toString(), nombre);
    }

    /**
     * Instantiates a new Asignatura.
     *
     * @param periodoId the periodo id
     * @param id        the id
     * @param nombre    the nombre
     */
    public Asignatura(@NonNull String periodoId, String id, String nombre) {
        this(id, periodoId, nombre, 0d);
    }

    /**
     * Instantiates a new Asignatura.
     *
     * @param periodoId  the periodo id
     * @param id         the id
     * @param nombre     the nombre
     * @param definitiva the definitiva
     */
    public Asignatura(@NonNull String periodoId, String id, String nombre, double definitiva) {
        this.periodoId = periodoId;
        this.id = id;
        this.nombre = nombre;
        this.definitiva = definitiva;
        this.notas = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getPeriodoId() {
        return periodoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getDefinitiva() {
        return definitiva;
    }

    public void setDefinitiva(double definitiva) {
        this.definitiva = definitiva;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public boolean isEmpty() {
        return nombre.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != getClass()) return false;

        Asignatura asignatura = (Asignatura) obj;
        return Objects.equal(asignatura.id, id) &&
                Objects.equal(asignatura.periodoId, periodoId) &&
                Objects.equal(asignatura.nombre, nombre) &&
                Objects.equal(asignatura.definitiva, definitiva);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, periodoId, nombre, definitiva);
    }

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.serializeNulls().create();
        return gson.toJson(this);
    }

}
