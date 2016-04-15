package co.kinbu.calificaciones.data;

import java.util.List;
import java.util.UUID;

/**
 * Asignatura
 * Created by jedabero on 6/03/16.
 */
public class Asignatura {

    private String id;

    private String nombre;
    private double definitiva;

    private List<Nota> notas;

    public Asignatura() {
        this("Asignatura");
    }

    public Asignatura(String nombre) {
        this(UUID.randomUUID().toString(), nombre);
    }

    public Asignatura(String uuid, String nombre) {
        this.id = uuid;
        this.nombre = nombre;
    }

    public Asignatura(String id, String nombre, double definitiva) {
        this(id, nombre);
        this.definitiva = definitiva;
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

    public double getDefinitiva() {
        return definitiva;
    }

    public void setDefinitiva(double definitiva) {
        this.definitiva = definitiva;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

}
