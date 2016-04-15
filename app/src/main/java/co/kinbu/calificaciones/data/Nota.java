package co.kinbu.calificaciones.data;

import java.util.UUID;

/**
 * Nota
 * Created by jedabero on 2/03/16.
 */
public class Nota {

    private String id;
    private String asignaturaId;

    private double valor;
    private int peso;

    public Nota() {
        this(0d, 1);
    }

    public Nota(double valor, int peso) {
        this(UUID.randomUUID().toString(), valor, peso);
    }

    public Nota(String uuid, double valor, int peso) {
        this.id = uuid;
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

    public void setAsignaturaId(String asignaturaId) {
        this.asignaturaId = asignaturaId;
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

}
