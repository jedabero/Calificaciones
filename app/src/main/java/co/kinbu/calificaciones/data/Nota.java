package co.kinbu.calificaciones.data;

/**
 * Nota
 * Created by jedabero on 2/03/16.
 */
public class Nota {

    private double valor;
    private int peso;

    public Nota() {
        this(0d, 1);
    }

    public Nota(double valor, int peso) {
        this.valor = valor;
        if (peso < 1) peso = 1;
        this.peso = peso;
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
