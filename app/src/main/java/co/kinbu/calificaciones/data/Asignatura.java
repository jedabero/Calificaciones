package co.kinbu.calificaciones.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Asignatura
 * Created by jedabero on 6/03/16.
 */
public class Asignatura {

    private String id;
    private String periodoId;

    private String nombre;
    private double definitiva;

    private List<Nota> notas;

    public Asignatura(String periodoId) {
        this(periodoId, "Asignatura");
    }

    public Asignatura(String periodoId, String nombre) {
        this(UUID.randomUUID().toString(), periodoId, nombre);
    }

    public Asignatura(String id, String periodoId, String nombre) {
        this(id, periodoId, nombre, 0d);
    }

    public Asignatura(String id, String periodoId, String nombre, double definitiva) {
        this.id = id;
        this.periodoId = periodoId;
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

}
