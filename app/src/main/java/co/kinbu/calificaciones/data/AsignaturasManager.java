package co.kinbu.calificaciones.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.kinbu.calificaciones.data.model.Asignatura;
import co.kinbu.calificaciones.data.model.Nota;

/**
 * AsignaturasManager
 * Created by jedabero on 6/03/16.
 */
public class AsignaturasManager {

    public AsignaturasManager() {

    }

    public static void actualizarDefinitiva(Asignatura asignatura) {
        double definitiva = 0;
        int pesoTotal = 0;
        for (Nota nota : asignatura.getNotas()) {
            definitiva += nota.getValor()*nota.getPeso();
            pesoTotal += nota.getPeso();
        }
        definitiva /= pesoTotal;
        asignatura.setDefinitiva(definitiva);
    }

    public static boolean equals(Asignatura me, Object o) {
        if (me == o) return true;
        if (!(o instanceof Asignatura)) return false;

        Asignatura that = (Asignatura) o;

        if (Double.compare(that.getDefinitiva(), me.getDefinitiva()) != 0) return false;
        if (me.getNombre() != null ? !me.getNombre().equals(that.getNombre()) : that.getNombre() != null)
            return false;
        return !(me.getNotas() != null ? !me.getNotas().equals(that.getNotas()) : that.getNotas() != null);
    }

    public static int hashCode(Asignatura me) {
        int result;
        long temp;
        result = me.getNombre() != null ? me.getNombre().hashCode() : 0;
        temp = Double.doubleToLongBits(me.getDefinitiva());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (me.getNotas() != null ? me.getNotas().hashCode() : 0);
        return result;
    }

    public static JSONObject toJSON(Asignatura me) {
        JSONObject json = new JSONObject();
        try {
            json.put("nombre", me.getNombre());
            json.put("definitiva", me.getDefinitiva());
            JSONArray notas = new JSONArray();
            for (Nota nota : me.getNotas()) {
                notas.put(NotasManager.toJSON(nota));
            }
        } catch (JSONException e) { e.printStackTrace(); }
        return json;
    }

    public static String toString(Asignatura n) {
        return "Asignatura" + toJSON(n);
    }

}
