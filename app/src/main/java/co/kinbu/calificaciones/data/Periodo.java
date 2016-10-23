package co.kinbu.calificaciones.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import com.google.common.base.Objects;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Periodo
 * Created by jedabero on 15/04/16.
 */
public class Periodo {

    private String id;

    private String nombre;
    private double promedio;

    private List<Asignatura> asignaturas;

    /**
     * Use este constructor para crear un periodo {Calendar.YEAR}-{Calendar.MONTH}.
     */
    public Periodo() {
        this(Calendar.getInstance().get(Calendar.YEAR) + "-" + Calendar.getInstance().get(Calendar.MONTH));
    }

    /**
     * Crear un periodo con un nombre especifico
     *
     * @param nombre el nombre
     */
    public Periodo(String nombre) {
        this(UUID.randomUUID().toString(), nombre);
    }

    /**
     * Crear un periodo con un idnombre especifico
     *
     * @param nombre el nombre
     */
    public Periodo(String id, String nombre) {
        this(id, nombre, 0d);
    }

    /**
     * Instantiates a new Periodo.
     *
     * @param id       the id
     * @param nombre   the nombre
     * @param promedio the promedio
     */
    public Periodo(String id, String nombre, double promedio) {
        this.id = id;
        this.nombre = nombre;
        this.promedio = promedio;
        asignaturas = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public boolean isEmpty() {
        return nombre.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != getClass()) return false;

        Periodo periodo = (Periodo) obj;
        return Objects.equal(periodo.id, id) &&
                Objects.equal(periodo.nombre, nombre) &&
                Objects.equal(periodo.promedio, promedio);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, nombre, promedio);
    }

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.serializeNulls().create();
        return gson.toJson(this);
    }
}
