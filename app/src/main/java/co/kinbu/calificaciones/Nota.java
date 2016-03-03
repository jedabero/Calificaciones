package co.kinbu.calificaciones;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Nota
 * Created by jedabero on 2/03/16.
 */
public class Nota {

    private double valor;
    private int peso;

    public Nota() { }

    public Nota(double valor, int peso) {
        this.valor = valor;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Nota)) return false;

        Nota nota = (Nota) o;

        if (Double.compare(nota.getValor(), getValor()) != 0) return false;
        return getPeso() == nota.getPeso();

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getValor());
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + getPeso();
        return result;
    }


    public JSONObject toJSON() {
        JSONObject nota = new JSONObject();
        try {
            nota.put("valor", valor);
            nota.put("peso", peso);
        } catch (JSONException e) { e.printStackTrace(); }
        return nota;
    }

    @Override
    public String toString() {
        return "Nota" + toJSON();
    }
}
