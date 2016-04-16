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

    public Nota(String asignaturaId) {
        this(asignaturaId, 0d, 1);
    }

    public Nota(String asignaturaId, double valor, int peso) {
        this(UUID.randomUUID().toString(), asignaturaId, valor, peso);
    }

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

}
