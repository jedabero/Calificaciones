package co.kinbu.calificaciones.data;

import com.google.common.base.Objects;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import co.kinbu.calificaciones.data.source.local.PersistenceContract.NotaEntry;

import java.util.UUID;

/**
 * Nota
 * Created by jedabero on 2/03/16.
 */
public class Nota {

    private String id;
    @SerializedName(NotaEntry.COLUMN_NAME_ASIGNATURA_ID)
    private String asignaturaId;

    private double valor;
    private int peso;

    /**
     * Instantiates a new Nota.
     *
     * @param asignaturaId the asignatura id
     */
    public Nota(String asignaturaId) {
        this(asignaturaId, 0d, 1);
    }

    /**
     * Instantiates a new Nota.
     *
     * @param asignaturaId the asignatura id
     * @param valor        the valor
     * @param peso         the peso
     */
    public Nota(String asignaturaId, double valor, int peso) {
        this(UUID.randomUUID().toString(), asignaturaId, valor, peso);
    }

    /**
     * Instantiates a new Nota.
     *
     * @param uuid         the uuid
     * @param asignaturaId the asignatura id
     * @param valor        the valor
     * @param peso         the peso
     */
    public Nota(String uuid, String asignaturaId, double valor, int peso) {
        this.id = uuid;
        this.asignaturaId = asignaturaId;
        this.valor = valor;
        if (peso < 1) peso = 1;
        this.peso = peso;
    }

    public String getId() {
        return id;
    }

    public String getAsignaturaId() {
        return asignaturaId;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != getClass()) return false;

        Nota nota = (Nota) obj;
        return Objects.equal(nota.id, id) &&
                Objects.equal(nota.asignaturaId, asignaturaId) &&
                Objects.equal(nota.valor, valor) &&
                Objects.equal(nota.peso, peso);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, asignaturaId, valor, peso);
    }

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.serializeNulls().create();
        return gson.toJson(this);
    }

}
