package co.kinbu.calificaciones.data;

import org.json.JSONException;
import org.json.JSONObject;

import co.kinbu.calificaciones.data.model.Nota;

/**
 * NotasManager
 * Created by jedabero on 3/03/16.
 */
public class NotasManager {

    public NotasManager() {

    }

    public static boolean equals(Nota n, Object o) {
        if (n == o) return true;
        if (!(o instanceof Nota)) return false;

        Nota nota = (Nota) o;

        if (Double.compare(nota.getValor(), n.getValor()) != 0) return false;
        return n.getPeso() == nota.getPeso();
    }

    public static int hashCode(Nota n) {
        int result;
        long temp;
        temp = Double.doubleToLongBits(n.getValor());
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + n.getPeso();
        return result;
    }


    public static JSONObject toJSON(Nota n) {
        JSONObject nota = new JSONObject();
        try {
            nota.put("valor", n.getValor());
            nota.put("peso", n.getPeso());
        } catch (JSONException e) { e.printStackTrace(); }
        return nota;
    }

    public static String toString(Nota n) {
        return "Nota" + toJSON(n);
    }

}
