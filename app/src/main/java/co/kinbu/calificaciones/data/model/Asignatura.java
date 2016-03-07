package co.kinbu.calificaciones.data.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Asignatura
 * Created by jedabero on 6/03/16.
 */
public class Asignatura extends RealmObject {

    @Required
    private String nombre;
    private double definitiva;

    private RealmList<Nota> notas;

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

    public RealmList<Nota> getNotas() {
        return notas;
    }

    public void setNotas(RealmList<Nota> notas) {
        this.notas = notas;
    }

}
