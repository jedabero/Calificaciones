package co.kinbu.calificaciones.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * Periodo
 * Created by jedabero on 15/04/16.
 */
public class Periodo {

    private String id;

    private String nombre;
    private double promedio;

    private List<Asignatura> asignaturas;

    public Periodo() {
        this(Calendar.getInstance().get(Calendar.YEAR) + "-" + Calendar.getInstance().get(Calendar.MONTH));
    }

    public Periodo(String nombre) {
        this(UUID.randomUUID().toString(), nombre);
    }

    public Periodo(String id, String nombre) {
        this(id, nombre, 0d);
    }

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

}
