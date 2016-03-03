package co.kinbu.calificaciones;

import io.realm.RealmObject;

/**
 * Nota
 * Created by jedabero on 2/03/16.
 */
public class Nota extends RealmObject {

    private double valor;
    private int peso;

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
